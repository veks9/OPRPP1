package hr.fer.oprpp1.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Klasa predstavlja implementaciju producera koji stvara podatke potrebne za crtanje
 * @author vedran
 *
 */
public class IFractalProducerParallelImpl implements IFractalProducer {

	private ComplexRootedPolynomial rootedPolynomial;
	private int workers;
	private int tracks;

	public IFractalProducerParallelImpl(ComplexRootedPolynomial rootedPolynomial, int workers, int tracks) {
		this.rootedPolynomial = rootedPolynomial;
		if (workers == 0) {
			this.workers = Runtime.getRuntime().availableProcessors();
		} else {
			this.workers = workers;
		}
		if (tracks == 0) {
			this.tracks = 4 * Runtime.getRuntime().availableProcessors();
		} else {
			this.tracks = tracks;
		}
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		System.out.println("Zapocinjem izracun...");
		int m = 16 * 16 * 16;
		short[] data = new short[width * height];
		final int numberOfTracks = tracks > height ? height : tracks;
		int numberOfYByTrack = height / numberOfTracks;
		System.out.println("workers: "+workers);
		System.out.println("tracks: "+tracks);
		
		final BlockingQueue<Calculate> queue = new LinkedBlockingQueue<>();

		Thread[] radnici = new Thread[workers];
		for (int i = 0; i < radnici.length; i++) {
			radnici[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Calculate p = null;
						try {
							p = queue.take();
							if (p == Calculate.NO_JOB)
								break;
						} catch (InterruptedException e) {
							continue;
						}
						p.run();
					}
				}
			});
		}
		
		for (int i = 0; i < radnici.length; i++) {
			radnici[i].start();
		}

		for (int i = 0; i < numberOfTracks; i++) {
			int yMin = i * numberOfYByTrack;
			int yMax = (i + 1) * numberOfYByTrack - 1;
			if (i == numberOfTracks - 1) {
				yMax = height - 1;
			}

			Calculate posao = new Calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel,
					rootedPolynomial);
			while (true) {
				try {
					queue.put(posao);
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		for (int i = 0; i < radnici.length; i++) {
			while (true) {
				try {
					queue.put(Calculate.NO_JOB);
					break;
				} catch (InterruptedException e) {
				}
			}
		}

		for (int i = 0; i < radnici.length; i++) {
			while (true) {
				try {
					radnici[i].join();
					break;
				} catch (InterruptedException e) {
				}
			}
		}

		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, (short) (rootedPolynomial.toComplexPolynom().order() + 1), requestNo);
	}
}
