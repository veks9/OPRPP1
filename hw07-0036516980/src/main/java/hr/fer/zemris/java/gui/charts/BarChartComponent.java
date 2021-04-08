package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * Klasa predstavlja komponentu koja se iscrtava u obliku 
 * grafa nad predanim modelom
 * @author vedran
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChart barChart;
	private final static int DIST_FROM_EDGE = 15;
	private final static int DIST_FROM_DESC_TO_NUMBERS = 20;
	private final static int DIST_FROM_NUMBERS_TO_AXIS = 8;
	private final static int TRIANGLE_HALF_BASE = 5;
	private final static int TRIANGLE_HEIGHT = 8;

	private int xOfYAxis;
	private int yOfXAxis;

	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		FontMetrics fontMetrics = g.getFontMetrics(getFont());
		Insets insets = getInsets();

		xOfYAxis = insets.left + DIST_FROM_EDGE + fontMetrics.getHeight() + DIST_FROM_DESC_TO_NUMBERS
				+ fontMetrics.stringWidth(Integer.toString(barChart.getyMax())) + DIST_FROM_NUMBERS_TO_AXIS;
		yOfXAxis = getHeight() - insets.bottom - DIST_FROM_EDGE - fontMetrics.getHeight() - DIST_FROM_DESC_TO_NUMBERS
				- fontMetrics.getHeight() - DIST_FROM_NUMBERS_TO_AXIS;

		addAxisDescription(g, fontMetrics, insets);
		addNumbers(g, fontMetrics, insets);
		
		addTriangles(g, fontMetrics, insets);
		addBars(g, fontMetrics, insets);
		addAxis(g, fontMetrics, insets);
		
		super.paintComponent(g);
	}

	/**
	 * Pomoćna metoda koja crta barove u grafu
	 * @param g objekt s kojim crtamo
	 * @param fontMetrics informacije o fontu
	 * @param insets informacije o borderima
	 */
	private void addBars(Graphics g, FontMetrics fontMetrics, Insets insets) {
		g.setColor(Color.ORANGE);
		List<Integer> list = barChart.getList().stream()
				.sorted((p1,p2) -> p1.getX() - p2.getX())
				.map(p -> p.getY())
				.collect(Collectors.toList());
		int lengthOfX = getWidth() - DIST_FROM_EDGE - insets.right  - xOfYAxis;
		int horizontalDistanceFromNumbers = lengthOfX / list.size();
		int heightOfy = yOfXAxis - (DIST_FROM_EDGE + insets.top + TRIANGLE_HEIGHT);
		int heightOfOneGrid = heightOfy / barChart.getyMax();
		
		int x = xOfYAxis;
		int y;
		for(int i = 0; i < list.size(); i++) {
			y = yOfXAxis - list.get(i) * heightOfOneGrid;
			g.fillRect(x, y, horizontalDistanceFromNumbers - 1, list.get(i) * heightOfOneGrid);
			x += horizontalDistanceFromNumbers;			
		}
		g.setColor(Color.BLACK);
	}

	/**
	 * Pomoćna metoda koja dodaje brojeve na x i y os
	 * @param g objekt s kojim crtamo
	 * @param fontMetrics informacije o fontu
	 * @param insets informacije o borderima
	 */
	private void addNumbers(Graphics g, FontMetrics fontMetrics, Insets insets) {
		int y = yOfXAxis + fontMetrics.getAscent()/2;
		int heightOfy = yOfXAxis - (DIST_FROM_EDGE + insets.top + TRIANGLE_HEIGHT);
		int verticalDisntaceFromNumbers = heightOfy / (barChart.getyMax() / barChart.getGap());
		
		for (int i = barChart.getyMin(); i <= barChart.getyMax(); i += barChart.getGap()) {
			int x = xOfYAxis - DIST_FROM_NUMBERS_TO_AXIS - fontMetrics.stringWidth(Integer.toString(i));
			g.drawString(Integer.toString(i), x, y);
			y -= verticalDisntaceFromNumbers;
		}
		
		y = yOfXAxis + DIST_FROM_NUMBERS_TO_AXIS + fontMetrics.getAscent();
		int lengthOfX = getWidth() - DIST_FROM_EDGE - insets.right  - xOfYAxis;
		
		
		List<Integer> list = barChart.getList().stream()
				.map(p -> p.getX())
				.sorted()
				.collect(Collectors.toList());
		int horizontalDistanceFromNumbers = lengthOfX / list.size();
		int x = xOfYAxis + horizontalDistanceFromNumbers / 2;
		for(int i = 0; i < list.size(); i++) {
			g.drawString(Integer.toString(list.get(i)), x, y);
			x += horizontalDistanceFromNumbers;
		}
		
		
		
	}

	/**
	 * Pomoćna metoda koja dodaje strelice na kraj osi
	 * @param g objekt s kojim crtamo
	 * @param fontMetrics informacije o fontu
	 * @param insets informacije o borderima
	 */
	private void addTriangles(Graphics g, FontMetrics fontMetrics, Insets insets) {
		g.fillPolygon(new int[] { xOfYAxis - TRIANGLE_HALF_BASE, xOfYAxis, xOfYAxis + TRIANGLE_HALF_BASE },
				new int[] { DIST_FROM_EDGE + insets.top + TRIANGLE_HEIGHT, DIST_FROM_EDGE + insets.top,
						DIST_FROM_EDGE + insets.top + TRIANGLE_HEIGHT },
				3);

		g.fillPolygon(
				new int[] { getWidth() - insets.right - DIST_FROM_EDGE ,
						getWidth() - insets.right - DIST_FROM_EDGE,
						getWidth() - insets.right - DIST_FROM_EDGE + TRIANGLE_HEIGHT},
				new int[] { yOfXAxis - TRIANGLE_HALF_BASE, yOfXAxis + TRIANGLE_HALF_BASE, yOfXAxis }, 3);
	}

	/**
	 * Pomoćna metoda koja crta osi
	 * @param g objekt s kojim crtamo
	 * @param fontMetrics informacije o fontu
	 * @param insets informacije o borderima
	 */
	private void addAxis(Graphics g, FontMetrics fontMetrics, Insets insets) {
		g.drawLine(xOfYAxis, yOfXAxis, getWidth() - DIST_FROM_EDGE - insets.right, yOfXAxis);

		g.drawLine(xOfYAxis, yOfXAxis, xOfYAxis, DIST_FROM_EDGE + insets.top);

	}

	/**
	 * Pomoćna metoda koja piše opis osi na polovicu visine i polovicu širine
	 * @param g objekt s kojim crtamo
	 * @param fontMetrics informacije o fontu
	 * @param insets informacije o borderima
	 */
	private void addAxisDescription(Graphics g, FontMetrics fontMetrics, Insets insets) {

		String xDescription = barChart.getxDescription();
		int xX = (getWidthMinusBorders() / 2) - (fontMetrics.stringWidth(xDescription) / 2);
		int yX = getHeight() - insets.bottom - DIST_FROM_EDGE ;

		g.drawString(xDescription, xX, yX);

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		String yDescription = barChart.getyDescription();
		int xY = -(getHeightMinusBorders() / 2) - (fontMetrics.stringWidth(yDescription) / 2);
		int yY = insets.left + DIST_FROM_EDGE + fontMetrics.getAscent();

		g2d.drawString(yDescription, xY, yY);

		g2d.setTransform(defaultAt);
	}
	
	/**
	 * Pomoćna metoda koja vraća širinu prozora kada postoje borderi
	 * @return
	 */
	private int getWidthMinusBorders() {
		Insets insets = getInsets();
		return getWidth() - insets.left - insets.right;
 	}
	
	/**
	 * Pomoćna metoda koja vraća visinu prozora kada postoje borderi
	 * @return
	 */
	private int getHeightMinusBorders() {
		Insets insets = getInsets();
		return getHeight() - insets.bottom - insets.top;
 	}

}
