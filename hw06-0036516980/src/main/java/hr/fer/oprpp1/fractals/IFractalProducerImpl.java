package hr.fer.oprpp1.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Klasa predstavlja implementaciju producera koji stvara podatke potrebne za
 * crtanje
 * 
 * @author vedran
 *
 */
public class IFractalProducerImpl implements IFractalProducer {

	private final double CONVERGENCE_TRESHOLD = 1E-3;
	private ComplexPolynomial polynomial;
	private ComplexPolynomial derived;
	private ComplexRootedPolynomial rootedPolynomial;

	public IFractalProducerImpl(ComplexRootedPolynomial complexPolynom) {
		this.rootedPolynomial = complexPolynom;
		this.polynomial = rootedPolynomial.toComplexPolynom();
		this.derived = this.polynomial.derive();
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		System.out.println("Zapocinjem izracun...");
		int m = 16 * 16 * 16;
		int offset = 0;

		short[] data = new short[width * height];
		for (int y = 0; y < height; y++) {
			if (cancel.get())
				break;
			for (int x = 0; x < width; x++) {
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
				Complex zn = new Complex(cre, cim);
				double module = 0;
				int iters = 0;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex znold = zn;
					Complex fraction = numerator.divide(denominator);
					zn = zn.sub(fraction);
					module = znold.sub(zn).module();
					iters++;
				} while (iters < m && module > CONVERGENCE_TRESHOLD);
				data[offset] = (short) (rootedPolynomial.indexOfClosestRootFor(zn, 0.002) + 1);
				offset++;
			}
		}
		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
	}

}
