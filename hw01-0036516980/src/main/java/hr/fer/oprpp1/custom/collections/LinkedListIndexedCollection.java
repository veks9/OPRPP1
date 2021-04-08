package hr.fer.oprpp1.custom.collections;

/**
 * Implementacija kolekcije koja ima u pozadini povezanu listu. Duplikati su
 * dopušteni, ali <code>null</code> vrijednosti nisu
 * 
 * @author vedran
 *
 */
public class LinkedListIndexedCollection extends Collection {

	private int size; // broj elemenata uistinu pohranjen, broj nodeova u listi
	private ListNode first;
	private ListNode last;

	/**
	 * Jedan čvor povezane liste. Sastoji se od reference na idući, reference na
	 * prošli član i podataka
	 * 
	 * @author vedran
	 *
	 */
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object data;

		ListNode(Object data) {
			this.data = data;
		}
	}

	public LinkedListIndexedCollection() {
		super();
	}

	public LinkedListIndexedCollection(Collection collection) {
		if (collection == null)
			throw new NullPointerException();

		addAll(collection);
		size = collection.size();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException();

		ListNode newListNode = new ListNode(value);

		if (first == null) {
			first = newListNode;
			last = newListNode;
			size++;
			return;
		}

		last.next = newListNode;
		newListNode.previous = last;
		last = newListNode;
		size++;

	}

	@Override
	public boolean contains(Object value) {
		if (indexOf(value) < 0)
			return false;
		else
			return true;
	}

	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index >= 0) {
			remove(index);
			return true;
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode node = first;

		for (int i = 0; i < size; i++) {
			array[i] = node.data;
			node = node.next;
		}
		return array;
	}

	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		for (int i = 0; i < size; i++) {
			processor.process(node.data);
			node = node.next;
		}
	}

	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Vraća objekt koji je pohranjen na predanom <code>index</code>-u. Valjani
	 * indexi su iz intervala 0 do <code>size</code>-1
	 * 
	 * @param indexindex čiji se objekt traži
	 * @return objekt koji je na poziciji <code>index</code>
	 * @throws IndexOutOfBoundsException ako je <code>index</code> nevaljan
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("Predani indeks je izvan dopuštenog intervala!");

		if (index == 0)
			return first.data;
		if (index == size - 1)
			return last.data;

		int halfSize = size / 2;
		if (index <= halfSize) {
			int n = 0;
			ListNode node = first;
			while (n <= halfSize) {
				if (n == halfSize)
					return node.data;
				else {
					node = node.next;
					n++;
				}
			}
		} else {
			int n = size - 1;
			ListNode node = last;
			while (n > halfSize) {
				if (n == halfSize) {
					return node.data;
				} else {
					node = node.previous;
					n--;
				}
			}
		}
		return null;
	}

	/**
	 * Metoda inserta <code>value</code> na <code>position</code> u povezanu listu.
	 * Ne overwrita nego pomakne sve od pozicije na koju se želi insertati udesno.
	 * Legalne pozicije su iz intervala od 0 do <code>size</code>
	 * 
	 * @param valuebjekt koji se želi insertati na određenu poziciju u povezanoj
	 *                   listi
	 * @param position   pozicija na koju se objekt želi insertati
	 * @throws IndexOutOfBoundsException ako je <code>position</code> nevaljan
	 */
	public void insert(Object value, int position) {
		if (value == null)
			throw new NullPointerException();
		if (position < 0 || position > size)
			throw new IndexOutOfBoundsException("Predana pozicija je izvan dopuštenog intervala!");

		ListNode node = first;
		for (int i = 0; i < size; i++) {
			if (position == 0) {
				ListNode newListNode = new ListNode(value);

				node.previous = newListNode;
				newListNode.next = node;

				first = newListNode;
				size++;

				break;
			} else if (position == size) {
				add(value);

				break;
			} else if (i == position) {
				ListNode prevNode = node.previous;
				ListNode newListNode = new ListNode(value);

				prevNode.next = newListNode;
				newListNode.previous = prevNode;
				newListNode.next = node;
				node.previous = newListNode;
				size++;

				break;
			}
			node = node.next;
		}
	}

	/**
	 * Vraća poziciju prvog pojavljivanja predanog objekta, ako nije nađena vraća -1
	 * 
	 * @param value objekt koji tražimo u povezanoj listi
	 * @return pozicija objekta ako je nađena, inače -1
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;

		ListNode node = first;
		for (int i = 0; i < size; i++) {
			if (value.equals(node.data)) {
				return i;
			}
			node = node.next;
		}
		return -1;
	}

	/**
	 * Uklanja element na predanom <code>index</code>-u. Svi ostali elementi se
	 * pomiču za jedno mjesto ulijevo
	 * 
	 * @param index indeks s kojeg želimo ukloniti element
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("Predani indeks je izvan dopuštenog intervala!");

		ListNode node = first;
		for (int i = 0; i < size; i++) {
			if (index == 0) {
				ListNode nextNode = node.next;

				nextNode.previous = null;
				first = nextNode;

				break;
			} else if (index == size - 1) {
				node = last;
				ListNode prevNode = node.previous;

				prevNode.next = null;
				last = prevNode;

				break;
			} else if (i == index) {
				ListNode prevNode = node.previous;
				ListNode nextNode = node.next;

				prevNode.next = nextNode;
				nextNode.previous = prevNode;

				break;
			}
			node = node.next;
		}
		size--;
	}

}
