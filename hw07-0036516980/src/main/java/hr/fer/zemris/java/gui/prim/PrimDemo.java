package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Ulazna točka za program koji ispisuje proste brojeve
 * 
 * @author vedran
 *
 */
public class PrimDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGui();
		pack();
		
	}

	/**
	 * Metoda radi izgled prozora
	 */
	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.add(new JScrollPane(list1));
		panel.add(new JScrollPane(list2));
		
		cp.add(panel, BorderLayout.CENTER);
		
		JButton slj = new JButton("sljedeći");
		cp.add(slj, BorderLayout.PAGE_END);
		slj.addActionListener(e -> {
			model.next();
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PrimDemo demo = new PrimDemo();
			demo.setVisible(true);
		});
	}
	
}
