package hr.fer.oprpp1.custom.collections;

/**
 * Klasa predstavlja rječnik koji ima u sebi pohranjene uređene parove
 * ključ-vrijednost
 * 
 * @author vedran
 *
 * @param <K> klasa od ključa
 * @param <V> klasa od vrijednosti
 */
public class Dictionary<K, V> {
	private ArrayIndexedCollection<Entry<K, V>> arrIndxCol = new ArrayIndexedCollection<>();
	private ElementsGetter<Entry<K, V>> getter = arrIndxCol.createElementsGetter();

	/**
	 * Klasa predstavlja uređeni par ključ-vrijednost
	 * 
	 * @author vedran
	 *
	 * @param <K> klasa od ključa
	 * @param <V> klasa od vrijednosti
	 */
	private class Entry<K, V> {
		private K key;
		private V value;

		public Entry(K key) {
			if (key == null)
				throw new IllegalArgumentException("Ključ ne smije biti null!");

			this.key = key;
		}

		public Entry(K key, V value) {
			if (key == null)
				throw new IllegalArgumentException("Ključ ne smije biti null!");

			this.key = key;
			this.value = value;
		}

		/**
		 * Getter za ključ {@link Entry}-a
		 * 
		 * @return ključ
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Getter za ključ {@link Entry}-a
		 * 
		 * @return vrijednost
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter za vrijednost. Stavlja predanu vrijednost kao vrijednost od tog
		 * {@link Entry}-a
		 * 
		 * @param value vrijednost
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
			@SuppressWarnings("unchecked")
			Entry<K, V> other = (Entry<K, V>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}
	}

	/**
	 * Vraća <code>true</code> ako rječnik ne sadrži elemente, inače
	 * <code>false</code>
	 * 
	 * @return <code>true</code> ako rječnik ne sadrži elemente, inače
	 *         <code>false</code>
	 */
	public boolean isEmpty() {
		return arrIndxCol.isEmpty();
	}

	/**
	 * Vraća broj trenutno pohranjenih objekata u rječniku
	 * 
	 * @return broj trenutno pohranjenih objekata u rječniku
	 */
	public int size() {
		return arrIndxCol.size();
	}

	/**
	 * Uklanja sve elemente iz rječnika
	 */
	public void clear() {
		arrIndxCol.clear();
	}

	/**
	 * Metoda stavlja ključ i vrijednost u rječnik. Ako postoji ključ onda ažurira
	 * vrijednost i vraća staru vrijednost (ako je nije bilo onda vraća
	 * <code>null</code>)
	 * 
	 * @param key   ključ
	 * @param value vrijednost
	 * @return <code>null</code> ako nije postojala vrijednost na tom ključu, inače
	 *         staru vrijednost
	 */
	V put(K key, V value) {
		Entry<K, V> entry = new Entry<>(key, value);
		int index = arrIndxCol.indexOf(entry);

		if (index < 0) {
			arrIndxCol.add(entry);
			return null;
		} else {
			V old = arrIndxCol.get(index).getValue();
			arrIndxCol.get(index).setValue(value);

			return old;
		}
	}

	/**
	 * Metoda dohvaća vrijednost sa predanim ključem. Ako ključ nije u rječniku,
	 * vraća se <code>null</code>
	 * 
	 * @param key ključ čiju vrijednost želimo dohvatiti
	 * @return <code>null</code> ako ključ ne postoji u rječniku, inače vrijednost
	 *         tog ključa
	 */
	V get(Object key) {
		@SuppressWarnings("unchecked")
		Entry<K, V> entry = new Entry<>((K) key);
		int index = arrIndxCol.indexOf(entry);

		if (index >= 0) {
			V ret = arrIndxCol.get(index).getValue();
			return ret;
		}

		return null;
	}

	/**
	 * Metoda uklanja čitav zapis sa zadanim ključem iz rječnika i vraća staru
	 * vrijednost koja je bila upisana za taj ključ(ako postoji, inače
	 * <code>null</code>)
	 * 
	 * @param key ključ koji želimo ukloniti iz rječnika
	 * @return <code>null</code> ako nije postojao zapis na tom ključu ili ako nije
	 *         postojao taj ključ, inače vraća vrijednost koja je bila na tom ključu
	 */
	V remove(K key) {
		Entry<K, V> entry = new Entry<>(key);
		int index = arrIndxCol.indexOf(entry);

		if (index >= 0) {
			V old = arrIndxCol.get(index).getValue();

			arrIndxCol.remove(index);

			return old;
		}
		return null;
	}

}
