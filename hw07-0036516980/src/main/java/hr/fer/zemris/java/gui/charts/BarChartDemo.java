package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Klasa predstavlja ulaznu točku za iscrtavanje grafa
 * @author vedran
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BarChartDemo(BarChart model) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(10, 20);
		setSize(800, 500);
		setLayout(new BorderLayout());
		
		BarChartComponent comp = new BarChartComponent(model);
		getContentPane().add(comp, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		if(args.length != 1)
			throw new IllegalArgumentException("Očekuje se put do jedne datoteke!");
		
		BarChart model = createModel(args[0]);

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(model).setVisible(true);

		});
	}

	/**
	 * Pomoćna metoda koja stvara model iz datoteke koja je predana u obliku
	 * Stringa path
	 * @param path
	 * @return
	 */
	private static BarChart createModel(String path) {
		try (Scanner sc = new Scanner(Paths.get(path))) {
			String xDescription = sc.nextLine().trim();
			String yDescription = sc.nextLine().trim();
			
			List<XYValue> list = createList(sc.nextLine().trim());
			
			int yMin = Integer.parseInt(sc.nextLine().trim());
			int yMax = Integer.parseInt(sc.nextLine().trim());
			int step = Integer.parseInt(sc.nextLine().trim());

			return new BarChart(list, xDescription, yDescription, yMin, yMax, step);

		} catch (Exception e) {
			throw new IllegalArgumentException("Greška u čitanju iz datoteke!");
		}

	}

	/**
	 * Pomoćna metoda koja iz predanog stringa izvlači uređene parove i radi 
	 * od njih listu objekata tipa {@link XYValue}
	 * @param input string u obliku "x,y x,y x,y..."
	 * @return list objekata tipa {@link XYValue}
	 */
	private static List<XYValue> createList(String input) {
		String[] pairs = input.trim().split(" ");
		List<XYValue> retList = new ArrayList<>();
		for (String pair : pairs) {
			String[] pairSplitted = pair.trim().split(",");
			retList.add(new XYValue(Integer.parseInt(pairSplitted[0]), Integer.parseInt(pairSplitted[1])));
		}

		return retList;
	}
}
