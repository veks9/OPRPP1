package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Klasa koja implementira model koji generira proste brojeve
 * 
 * @author vedran
 *
 */
public class PrimListModel implements ListModel<Integer> {

	List<Integer> primNums = new ArrayList<>();
	List<ListDataListener> listeners = new ArrayList<>();

	public PrimListModel() {
		primNums.add(1);
	}

	@Override
	public int getSize() {
		return primNums.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primNums.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Metoda dohvaća i sprema u internu listu idući prosti broj
	 */
	public void next() {
		int prime = primNums.get(getSize() - 1) + 1;
		while (!isNextPrime(prime)) {
			prime++;
		}
		primNums.add(prime);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 1);
		listeners.forEach(l -> l.intervalAdded(event));

	}

	/**
	 * Metoda ispituje je li predani broj prost
	 * 
	 * @param prime
	 * @return
	 */
	private boolean isNextPrime(int prime) {
		boolean isItPrime = true;

		if (prime <= 1) {
			isItPrime = false;

			return isItPrime;
		} else {
			for (int i = 2; i <= prime / 2; i++) {
				if ((prime % i) == 0) {
					isItPrime = false;

					break;
				}
			}

			return isItPrime;
		}
	}

}
