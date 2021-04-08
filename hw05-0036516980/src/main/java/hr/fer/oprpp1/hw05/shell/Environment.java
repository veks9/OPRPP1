package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Sučelje predstavlja okruženje
 * 
 * @author vedran
 *
 */
public interface Environment {
	/**
	 * Metoda čita korisnikov unos
	 * 
	 * @return korisnikov unos
	 * @throws ShellIOException ako je došlo do greške
	 */
	String readLine() throws ShellIOException;

	/**
	 * Metoda ispisuje korisniku predanu poruku
	 * 
	 * @param text predana poruka
	 * @throws ShellIOException ako je došlo do greške
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Metoda ispisuje korisniku predanu poruku i prelazi u novi red
	 * 
	 * @param text predana poruka
	 * @throws ShellIOException ako je došlo do greške
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Metoda vraća mapu naredbi koje su dopuštene
	 * 
	 * @return mapa naredbi
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Metoda vraća simbol za više redaka
	 * 
	 * @return simbol za više redaka
	 */
	Character getMultilineSymbol();

	/**
	 * Metoda postavlja simbol za više redaka
	 * 
	 * @param symbol simbol za više redaka
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Metoda vraća prompt simbol tj. simbol koji se ispisuje na početku svake
	 * linije
	 * 
	 * @return prompt simbol
	 */
	Character getPromptSymbol();

	/**
	 * Metoda postavlja prompt simbol tj. simbol koji se ispisuje na početku svake
	 * linije
	 * 
	 * @param symbol prompt simbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Metoda vraća simbol za više redaka kod unosa
	 * 
	 * @return simbol za više redaka kod unosa
	 */
	Character getMorelinesSymbol();

	/**
	 * Metoda postavlja simbol za više redaka kod unosa
	 * 
	 * @param symbol simbol za više redaka kod unosa
	 */
	void setMorelinesSymbol(Character symbol);
}
