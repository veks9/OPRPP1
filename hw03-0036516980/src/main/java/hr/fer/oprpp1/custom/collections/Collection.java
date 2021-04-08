package hr.fer.oprpp1.custom.collections;

/**
 * Predstavlja kolekciju u koju se mogu pohranjivati objekti
 * 
 * @author vedran
 *
 */
public interface Collection<T> {

	/**
	 * Vraća <code>true</code> ako kolekcija ne sadrži elemente, inače
	 * <code>false</code>
	 * 
	 * @return <code>true</code> ako kolekcija ne sadrži elemente, inače
	 *         <code>false</code>
	 */
	public default boolean isEmpty() {
		return size() == 0;

	}

	/**
	 * Vraća broj trenutno pohranjenih objekata u kolekciji
	 * 
	 * @return broj trenutno pohranjenih objekata u kolekciji
	 */
	int size();

	/**
	 * Dodaje objekt <code>value</code> u kolekciju. Predani objekt
	 * <code>value</code> ne smije biti <code>null</code>
	 * 
	 * @param value objekt koji treba dodati u kolekciju
	 */
	void add(T value);

	/**
	 * Vraća <code>true</code> ako kolekcija sadrži objekt <code>value</code>. U
	 * implementaciji koristi metodu <code>equals</code>
	 * 
	 * @param value objekt za koji se provjerava je li u kolekciji
	 * @return <code>true</code> ako kolekcija sadrži objekt <code>value</code>,
	 *         inače <code>false</code>
	 */
	boolean contains(Object value);

	/**
	 * Vraća <code>true</code> ako kolekcija sadrži objekt <code>value</code>
	 * (dobiveno preko metode <code>equals</code>) i ukloni njegovu jednu pojavu
	 * 
	 * @param value objekt čija se jedna pojava uklanja ako postoji u kolekciji
	 * @return <code>true</code> ako kolekcija sadrži objekt <code>value</code>
	 *         (dobiveno preko metode <code>equals</code>) i ukloni njegovu jednu
	 *         pojavu, inače <code>false</code>
	 */
	boolean remove(Object value);

	/**
	 * Alocira novo polje veličine jednake veličini ove kolekcije, popunjava ga
	 * sadržajem kolekcije i vraća alocirano polje. Nikada ne vraća
	 * <code>null</code>
	 * 
	 * @return novo alocirano polje
	 * @throws UnsupportedOperationException
	 */
	Object[] toArray();

	// <T> T[] toArray(T[] a);

	/**
	 * Metoda zove <code>processor.process(.)</code> za svaki element kolekcije.
	 * Poredak kojim se elementi šalju nije definiran u ovoj klasi
	 * 
	 * @param processor procesor nad kojim se zove metoda <code>process</code>
	 */
	public default void forEach(Processor<? super T> processor) {
		ElementsGetter<? extends T> getter = createElementsGetter();
		getter.processRemaining(processor);

	}

	/**
	 * Metoda dodaje sve elemente u trenutnu kolekciju iz predane kolekcije. Predana
	 * kolekcija(<code>other</code>) ostaje nepromijenjena.
	 * 
	 * @param collection kolekcija iz koje se svi elementi dodaju u trenutnu
	 *                   kolekciju
	 */
	public default void addAll(Collection<? extends T> collection) {
		collection.forEach(value -> add((T) value));
	}

	/**
	 * Uklanja sve elemente iz kolekcije
	 */
	void clear();

	/**
	 * Metoda koja stvara i vraća novi objekt tipa {@link ElementsGetter}
	 * 
	 * @return novi objekt tipa {@link ElementsGetter}
	 */
	ElementsGetter<T> createElementsGetter();

	/**
	 * Metoda dohvaća elemente iz predane kolekcije, te u trenutnu kolekciju treba
	 * na kraj dodati sve elemente koje predani tester prihvati
	 * 
	 * @param col
	 * @param tester
	 */
	public default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		col.forEach(obj -> {
			if (tester.test(obj))
				add(obj);
		});
	}
}
