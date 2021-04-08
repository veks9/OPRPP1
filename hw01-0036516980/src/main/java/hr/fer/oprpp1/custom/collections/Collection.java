package hr.fer.oprpp1.custom.collections;

/**
 * Predstavlja kolekciju u koju se mogu pohranjivati objekti
 * 
 * @author vedran
 *
 */
public class Collection {

	protected Collection() {
		super();
	}

	/**
	 * Vraća <code>true</code> ako kolekcija ne sadrži elemente, inače
	 * <code>false</code>
	 * 
	 * @return <code>true</code> ako kolekcija ne sadrži elemente, inače
	 *         <code>false</code>
	 */
	public boolean isEmpty() {
		return size() == 0;

	}

	/**
	 * Vraća broj trenutno pohranjenih objekata u kolekciji
	 * 
	 * @return broj trenutno pohranjenih objekata u kolekciji
	 */
	public int size() {
		return 0;

	}

	/**
	 * Dodaje objekt <code>value</code> u kolekciju. Predani objekt
	 * <code>value</code> ne smije biti <code>null</code>
	 * 
	 * @param value objekt koji treba dodati u kolekciju
	 */
	public void add(Object value) {

	}

	/**
	 * Vraća <code>true</code> ako kolekcija sadrži objekt <code>value</code>. U
	 * implementaciji koristi metodu <code>equals</code>
	 * 
	 * @param value objekt za koji se provjerava je li u kolekciji
	 * @return <code>true</code> ako kolekcija sadrži objekt <code>value</code>,
	 *         inače <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;

	}

	/**
	 * Vraća <code>true</code> ako kolekcija sadrži objekt <code>value</code>
	 * (dobiveno preko metode <code>equals</code>) i ukloni njegovu jednu pojavu
	 * 
	 * @param value objekt čija se jedna pojava uklanja ako postoji u kolekciji
	 * @return <code>true</code> ako kolekcija sadrži objekt <code>value</code>
	 *         (dobiveno preko metode <code>equals</code>) i ukloni njegovu jednu
	 *         pojavu, inače <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;

	}

	/**
	 * Alocira novo polje veličine jednake veličini ove kolekcije, popunjava ga
	 * sadržajem kolekcije i vraća alocirano polje. Nikada ne vraća
	 * <code>null</code>
	 * 
	 * @return novo alocirano polje
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();

	}

	/**
	 * Metoda zove <code>processor.process(.)</code> za svaki element kolekcije.
	 * Poredak kojim se elementi šalju nije definiran u ovoj klasi
	 * 
	 * @param processor procesor nad kojim se zove metoda <code>process</code>
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Metoda dodaje sve elemente u trenutnu kolekciju iz predane kolekcije. Predana
	 * kolekcija(<code>other</code>) ostaje nepromijenjena.
	 * 
	 * @param other kolekcija iz koje se svi elementi dodaju u trenutnu kolekciju
	 */
	public void addAll(Collection other) {

		class LocalProcessor extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new LocalProcessor());
	}

	/**
	 * Uklanja sve elemente iz kolekcije
	 */
	public void clear() {

	}

}
