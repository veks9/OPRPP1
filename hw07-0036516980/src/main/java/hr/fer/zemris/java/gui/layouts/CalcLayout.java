package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * Klasa predstavlja LayoutManager za kalkulator
 * 
 * @author vedran
 *
 */
public class CalcLayout implements LayoutManager2 {

	private static final int ROWS = 5;
	private static final int COLUMNS = 7;
	private static final int DEFAULT_GAP = 0;
	private static final int FIRST_COMPONENT_START = 1;
	private static final int FIRST_COMPONENT_END = 5;
	private static final int FIRST_COMPONENT_ROW = 1;
	private static final int[][] horizontalComponentSize = { { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 1, 0, 0, 0, 0 }, { 1, 0, 1, 0, 1, 0, 0 }, { 1, 0, 1, 0, 1, 0, 1 }, { 1, 1, 1, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1, 0, 1 } };
	private static final int[][] verticalComponentSize = { { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0 }, { 1, 0, 1, 0, 0 },
			{ 1, 0, 1, 0, 1 }, { 1, 1, 1, 0, 1 } };
	private int gap;
	private Component components[][] = new Component[ROWS][COLUMNS];

	public CalcLayout(int gap) {
		super();
		if (gap < 0)
			throw new IllegalArgumentException("Razmak između elemenata ne smije biti manji od 0!");

		this.gap = gap;
	}

	public CalcLayout() {
		this(DEFAULT_GAP);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (components[i][j].equals(comp))
					components[i][j] = null;
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		System.out.println();
		return getLayoutDimension(parent, "pref");
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutDimension(parent, "min");
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutDimension(target, "max");
	}

	/**
	 * Pomoćna metoda koja računa veličinu s obzirom na predano ime(min, max pref)
	 * 
	 * @param parent {@link Container} čiji se layout radi
	 * @param name   min max ili pref
	 * @return objekt tipa {@link Dimension}
	 */
	private Dimension getLayoutDimension(Container parent, String name) {
		Dimension size = null;

		int h = 0, w = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Component c = components[i][j];

				if (c == null)
					continue;

				switch (name) {
				case "pref":
					size = c.getPreferredSize();
					break;
				case "min":
					size = c.getMinimumSize();
					break;
				case "max":
					size = c.getMaximumSize();
					break;
				default:
					throw new IllegalArgumentException();
				}

				if (size.getWidth() > w) {
					w = (int) size.getWidth();
					if (i == 0 && j == 0) {
						w = (w - (FIRST_COMPONENT_END - FIRST_COMPONENT_START) * gap) / 5;
					}
				}
				if (size.getHeight() > h) {
					h = (int) size.getHeight();
				}

			}
		}

		Insets insets = parent.getInsets();
		int width = COLUMNS * w + (COLUMNS - 1) * gap + insets.left + insets.right;
		int height = ROWS * h + (ROWS - 1) * gap + insets.top + insets.bottom;
		return new Dimension(width, height);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();

		int realHeight = parent.getHeight() - (insets.top + insets.bottom) - (ROWS - 1) * gap;
		int realWidth = parent.getWidth() - (insets.left + insets.right) - (COLUMNS - 1) * gap;

		int height = realHeight / ROWS;
		int width = realWidth / COLUMNS;

		int heightRemaining = realHeight % ROWS;
		int widthRemaining = realWidth % COLUMNS;

		for (int i = 0, y = insets.top; i < ROWS; i++) {
			int x = insets.left;
			for (int j = 0; j < COLUMNS; j++) {
				Component c = components[i][j];

				if (i == 0 && (j > 0 && j < 5))
					continue;

				int w = width + horizontalComponentSize[widthRemaining][j];
				int h = height + verticalComponentSize[heightRemaining][i];

				if (i == 0 && j == 0) {
					for (int k = 0; k < (FIRST_COMPONENT_END - FIRST_COMPONENT_START); k++) {
						w += width + horizontalComponentSize[widthRemaining][k] + gap;
					}
					if (c != null)
						c.setBounds(x, y, w, h);
					x += w + gap;
					continue;
				}

				if (c != null)
					c.setBounds(x, y, w, h);
				x += w + gap;
				if (j == COLUMNS - 1)
					y += h + gap;
			}
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof String) {
			addToComponents(comp, RCPosition.parse(constraints.toString()));
		} else if (constraints instanceof RCPosition) {
			addToComponents(comp, (RCPosition) constraints);
		} else {
			if (comp == null || constraints == null)
				throw new NullPointerException();
			throw new IllegalArgumentException("Ograničenje mora biti tipa String ili RCPosition!");
		}
	}

	/**
	 * Pomoćna metoda koja dodaje komponentu na točno pravo mjesto u layoutu
	 * 
	 * @param comp        komponenta
	 * @param constraints ograničenja gdje se treba komponenta nalaziti
	 */
	private void addToComponents(Component comp, RCPosition constraints) {
		if (!isValid(constraints))
			throw new CalcLayoutException("Nevaljana pozicija!");

		int row = constraints.getRow();
		int column = constraints.getColumn();

		if (components[row - 1][column - 1] != null)
			throw new CalcLayoutException();

		components[row - 1][column - 1] = comp;
	}

	/**
	 * Pomoćna metoda koja ispituje je li predano ograničenje validno
	 * 
	 * @param constraints ograničenje
	 * @return <code>true</code> ako je, inače <code>false</code>
	 */
	private boolean isValid(RCPosition constraints) {
		int row = constraints.getRow();
		int column = constraints.getColumn();

		if (row < 1 || row > ROWS || column < 1 || column > COLUMNS)
			return false;
		if (row == FIRST_COMPONENT_ROW && column > FIRST_COMPONENT_START && column <= FIRST_COMPONENT_END)
			return false;
		return true;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub

	}

}