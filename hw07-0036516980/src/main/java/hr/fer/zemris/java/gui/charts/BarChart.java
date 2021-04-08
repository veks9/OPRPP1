package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa predstavlja model za crtanje grafa
 * @author vedran
 *
 */
public class BarChart {

	private List<XYValue> list = new ArrayList<>();
	private String xDescription;
	private String yDescription;
	private int yMin;
	private int yMax;
	private int gap;
	
	public BarChart(List<XYValue> list, String xDescription, String yDescription, int yMin, int yMax, int gap) {
		super();
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		if(yMin < 0)
			throw new IllegalArgumentException("Minimalni y ne smije biti negativan!");
		this.yMin = yMin;
		if(yMax <= yMin)
			throw new IllegalArgumentException("Maksimalni y mora biti strogo veći od minimalnog y!");
		this.yMax = yMax;
		if(gap <= 0)
			throw new IllegalArgumentException("Razmak između dva y-ona mora biti strogo veći od 0!");
		this.gap = gap;
	}

	/**
	 * Getter liste u kojoj su pohranjeni uređeni parovi
	 * @return
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Getter opisa x osi
	 * @return
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter opisa y osi
	 * @return
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter početne vrijednosti y osi
	 * @return
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Getter krajnje vrijednosti y osi
	 * @return
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Getter za brojčani razmak između dva broja na y osi
	 * @return
	 */
	public int getGap() {
		return gap;
	}
	
	
}
