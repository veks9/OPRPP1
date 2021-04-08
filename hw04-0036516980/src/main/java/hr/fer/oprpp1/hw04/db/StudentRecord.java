package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja jedan redak u tablici
 * 
 * @author vedran
 *
 */
public class StudentRecord {
	private String jmbag;
	private String firstName;
	private String lastName;
	private int finalGrade;

	/**
	 * Konstruktor koji stvara redak u tablici
	 * 
	 * @param jmbag     jmbag
	 * @param firstName ime
	 * @param lastName  prezime
	 * @param finalGrade     ocjena
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter za jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter za ime
	 * 
	 * @return ime
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter za prezime
	 * 
	 * @return prezime
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter za ocjenu
	 * 
	 * @return ocjena
	 */
	public int getfinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "| " + jmbag + " | " + lastName + " | " + firstName + " | " + finalGrade + " |";
	}

}
