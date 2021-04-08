package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa predstavlja bazu podataka studenata
 * 
 * @author vedran
 *
 */
public class StudentDatabase {

	List<StudentRecord> studentRecordList = new ArrayList<>();
	Map<String, StudentRecord> map = new HashMap<>();

	/**
	 * Konstruktor koji prima listu stringova koja se posprema u bazu podataka
	 * 
	 * @param list
	 */
	public StudentDatabase(List<String> list) {
		list.forEach(v -> importDatabase(v));
	}

	/**
	 * Pomoćna metoda koja uvozi bazu podataka
	 * 
	 * @param input
	 */
	private void importDatabase(String input) {
		String[] line = input.split("\t");

		if (map.containsKey(line[0]))
			throw new IllegalArgumentException("Ne smije biti više istih JMBAG-ova!");

		int finalGrade = Integer.parseInt(line[line.length - 1]);
		if (finalGrade < 1 || finalGrade > 5)
			throw new IllegalArgumentException("Ocjena je nevaljana!");

		StudentRecord record = new StudentRecord(line[0], line[1], line[2], finalGrade);
		studentRecordList.add(record);
		map.put(line[0], record);

	}

	/**
	 * Metoda vraća redak tablice koji odgovara predanom jmbagu
	 * 
	 * @param jmbag jmbag od retka tablice koji želimo naći
	 * @return redak tablice sa zadanim jmbagom
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return map.get(jmbag);
	}

	/**
	 * Metoda filtrira retke u tablici i vraća one koji su zadovoljili uvjete
	 * 
	 * @param filter instanca na filter
	 * @return oni retci u tablici koji su zadovoljili uvjete
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> returnList = new ArrayList<>();
		studentRecordList.forEach(record -> {
			if (filter.accepts(record))
				returnList.add(record);
		});
		return returnList;
	}
}
