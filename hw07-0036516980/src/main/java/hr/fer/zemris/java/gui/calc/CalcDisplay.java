package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Klasa predstavlja ekran na kojem se ispisuju rješenja
 * @author vedran
 *
 */
public class CalcDisplay extends JLabel implements CalcValueListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalcDisplay() {
		setBackground(Color.YELLOW);
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(getFont().deriveFont(30f));
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		SwingUtilities.invokeLater(() -> {
			setText(model.toString());
			});
	}

}
