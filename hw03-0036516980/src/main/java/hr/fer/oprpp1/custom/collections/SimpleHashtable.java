package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Klasa predstavlja implementaciju mape sa uređenim parovima ključ-vrijednost
 * 
 * @author vedran
 *
 * @param <K> klasa od ključa
 * @param <V> klasa od vrijednosti
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private TableEntry<K, V>[] table;
	private int size;
	private long modificationCount = 0L;

	/**
	 * Klasa predstavlja uređeni par ključ-vrijednost
	 * 
	 * @author vedran
	 *
	 * @param <K> klasa od ključa
	 * @param <V> klasa od vrijednosti
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;

		/**
		 * Konstruktor prima ključ i za vrijednost stavlja na <code>null</code>
		 * 
		 * @param key ključ
		 * @throws IllegalArgumentException ako je ključ <code>null</code>
		 */
		public TableEntry(K key) {
			if (key == null)
				throw new IllegalArgumentException("Ključ ne smije biti null!");

			this.key = key;
		}

		/**
		 * Konstruktor koji prima ključ i vrijednost i stavlja ih u uređeni par
		 * 
		 * @param key   ključ
		 * @param value vrijednost
		 * @throws IllegalArgumentException ako je ključ <code>null</code>
		 */
		public TableEntry(K key, V value) {
			if (key == null)
				throw new IllegalArgumentException("Ključ ne smije biti null!");

			this.key = key;
			this.value = value;
		}

		/**
		 * Metoda vraća ključ od uređenog para
		 * 
		 * @return ključ
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Metoda vraća vrijednost od uređenog para
		 * 
		 * @return vrijednost
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Metoda postavlja vrijednost od uređenog para
		 * 
		 * @param value nova vrijednost za uređeni par
		 */
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry<K, V> other = (TableEntry<K, V>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}
	}

	/**
	 * Klasa predstavlja implementaciju iteratora za klasu {@link SimpleHashtable}
	 * 
	 * @author vedran
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private TableEntry<K, V> currentEntry;
		private TableEntry<K, V> nextEntry;
		private int currentSlot;
		private long expectedModificationCount = modificationCount;

		public IteratorImpl() {
			currentSlot = 0;
			currentEntry = null;
			nextEntry = null;
			while (nextEntry == null && currentSlot < table.length)
				nextEntry = table[currentSlot++];
		}

		/**
		 * Metoda koja vraća <code>true</code> ako postoji idući element u tablici
		 * 
		 * @return <code>true</code> ako postoji, inače <code>false</code>
		 * 
		 */
		public boolean hasNext() {
			return nextEntry != null ? true : false;
		}

		/**
		 * Metoda koja dohvaća sljedeći element iz tablice
		 * 
		 * @return idući element iz tablice
		 * @throws ConcurrentModificationException ako se dogodi modifikacija tablice
		 * @throws NoSuchElementException          ako se pokuša dohvatiti idući element
		 *                                         kojeg nema
		 */
		public SimpleHashtable.TableEntry next() {
			if (modificationCount != expectedModificationCount)
				throw new ConcurrentModificationException();
			if (!hasNext())
				throw new NoSuchElementException();
			currentEntry = nextEntry;
			if (nextEntry.next != null) {
				nextEntry = nextEntry.next;
			} else {
				nextEntry = null;
				while (nextEntry == null && currentSlot < table.length)
					nextEntry = table[currentSlot++];
			}

			return currentEntry;
		}

		/**
		 * Metoda čijim se pozivom iz tablice briše trenutni element. Trenutni je onaj
		 * koji je zadnji vraćen metodom next()
		 * 
		 * @throws IllegalStateException           ako je trenutni element
		 *                                         <code>null</code>(ako se metoda
		 *                                         remove() pozvala više od jednom nakon
		 *                                         metode next())
		 * @throws ConcurrentModificationException ako se dogodi modifikacija tablice
		 */
		public void remove() {
			if (currentEntry == null)
				throw new IllegalStateException();
			if (modificationCount != expectedModificationCount)
				throw new ConcurrentModificationException();

			SimpleHashtable.this.remove(currentEntry.getKey());
			expectedModificationCount = modificationCount;
			currentEntry = null;

		}

	}

	/**
	 * Defaultni konstruktor koji stvara tablicu veličine 16
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_INITIAL_CAPACITY];
	}

	/**
	 * Konstruktor koji prima kapacitet i radi tablicu te veličine
	 * 
	 * @param capacity veličina interne tablice
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Kapacitet ne smije biti manji od 1!");

		int newCapacity = 1;
		while (newCapacity < capacity)
			newCapacity *= 2;

		table = (TableEntry<K, V>[]) new TableEntry[newCapacity];
	}

	/**
	 * Metoda dodaje zapis u tablicu. Ako već postoji zapis sa istim ključem, onda
	 * ažurira vrijednost
	 * 
	 * @param key   ključ od uređenog para
	 * @param value vrijednost od uređenog para
	 * @return ako je postojao ključ onda vrijednost, inače <code>null</code>
	 */
	public V put(K key, V value) {
		TableEntry<K, V> entry = new TableEntry<K, V>(key, value);
		int slot = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> entryFromTable = table[slot];
		if (entryFromTable == null) {
			if (Double.valueOf(size) / table.length >= DEFAULT_LOAD_FACTOR) {
				ensureCapacity();
				slot = Math.abs(key.hashCode()) % table.length;
			}
			table[slot] = entry;
			size++;
			modificationCount++;
			return null;
		}

		do {
			if (entry.key.equals(entryFromTable.key)) {
				V old = entryFromTable.value;

				entryFromTable.setValue(value);
				return old;
			}

			if (entryFromTable.next == null) {
				if (Double.valueOf(size) / table.length >= DEFAULT_LOAD_FACTOR) {
					ensureCapacity();
					V ret = put(key, value);
					return ret;
				}

				entryFromTable.next = entry;
				size++;
				modificationCount++;
				return null;
			}
			entryFromTable = entryFromTable.next;
		} while (true);
	}

	/**
	 * Metoda povećava kapacitet tablice za duplo
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity() {
		TableEntry<K, V>[] tableCopy = toArray();

		table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		size = 0;

		for (int i = 0; i < tableCopy.length; i++) {
			put(tableCopy[i].getKey(), tableCopy[i].getValue());
		}
	}

	/**
	 * Metoda prima ključ i vraća vrijednost koja se nalazi u tom uređenom paru
	 * 
	 * @param key ključ od kojeg se vrijednost traži
	 * @return vrijednost koja za ključ ima predani
	 */
	public V get(K key) {
		if (key == null)
			return null;
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> entryFromTable = table[slot];

		while (entryFromTable != null) {
			if (entryFromTable.getKey().equals(key)) {
				V retValue = entryFromTable.getValue();
				return retValue;
			}
			entryFromTable = entryFromTable.next;
		}
		return null;
	}

	/**
	 * Metoda vraća broj zapisa u tablici
	 * 
	 * @return broj zapisa u tablici
	 */
	public int size() {
		return size;
	}

	/**
	 * Metoda ispituje postoji li predani ključ u tablici
	 * 
	 * @param key ključ za koji se ispituje je li u tablici
	 * @return <code>true</code> ako je, inače <code>false</code>
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			throw new IllegalArgumentException("Ključ ne smije biti null!");

		int slot = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> entryFromTable = table[slot];

		while (entryFromTable != null) {
			if (entryFromTable.getKey().equals(key))
				return true;

			entryFromTable = entryFromTable.next;
		}
		return false;
	}

	/**
	 * Metoda ispituje postoji li predana vrijednost u tablici
	 * 
	 * @param value vrijednost za koju se ispituje je li u tablici
	 * @return <code>true</code> ako je, inače <code>false</code>
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {

			TableEntry<K, V> entryFromTable = table[i];

			while (entryFromTable != null) {
				if (value == null) {
					if (entryFromTable.getValue() == null)
						return true;
					else {
						entryFromTable = entryFromTable.next;
						continue;
					}
				}

				if (entryFromTable.getValue().equals(value))
					return true;

				entryFromTable = entryFromTable.next;
			}
		}
		return false;
	}

	/**
	 * Metoda uklanja iz tablice uređeni par sa zadanim ključem(ako postoji) i vraća
	 * pozivatelju tu vrijednost (ako ne postoji onda ne radi ništa i vraća
	 * <code>null</code>). Ako se preda <code>null</code> kao ključ metoda ne radi
	 * ništa
	 * 
	 * @param key
	 * @return
	 */
	public V remove(Object key) {
		if (key == null)
			return null;

		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> entryFromTable = table[slot];

		if (entryFromTable.getKey().equals(key)) { // ako je glava
			V retValue = entryFromTable.getValue();
			table[slot] = entryFromTable.next;
			size--;
			modificationCount++;
			return retValue;
		}

		while (entryFromTable.next != null)
			;
		{
			TableEntry<K, V> prev = entryFromTable;
			entryFromTable = entryFromTable.next;

			if (entryFromTable.getKey().equals(key)) {
				V retValue = entryFromTable.getValue();
				prev.next = entryFromTable.next;
				size--;
				modificationCount++;
				return retValue;
			}
		}
		return null;
	}

	/**
	 * Metoda provjerava jel ima zapisa u tablici.
	 * 
	 * @return <code>true</code> ako ima, inače <code>false</code>
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		String s = "";
		s += "[";

		TableEntry<K, V>[] arr = toArray();

		for (int i = 0; i < arr.length; i++) {
			s += arr[i].toString();
			if (i != arr.length - 1)
				s += ", ";
		}

		s += "]";
		return s;
	}

	/**
	 * Metoda toArray vraća polje referenci na pohranjene zapise; to polje ima
	 * onoliko elemenata koliki je trenutni size kolekcije (dakle, to nije isto što
	 * i interna tablica)
	 * 
	 * @return nova tablica u kojoj se nalaze
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] array = (TableEntry<K, V>[]) new TableEntry[size];

		int arrayIndex = 0;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;

			TableEntry<K, V> entry = table[i];
			array[arrayIndex++] = entry;

			while (entry.next != null) {
				array[arrayIndex++] = entry.next;
				entry = entry.next;
			}
		}

		return array;

	}

	/**
	 * Metoda briše sve iz kolekcije, ali ne mijenja kapacitet
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());
	}
}
