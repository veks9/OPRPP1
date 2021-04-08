package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Sučelje predstavlja naredbu za aplikaciju
 * 
 * @author vedran
 *
 */
public interface ShellCommand {
	/**
	 * Metoda izvodi posao od naredbe
	 * 
	 * @param env       okruženje
	 * @param arguments argumenti predani kod unosa
	 * @return {@link ShellStatus}
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Metoda vraća naziv naredbe
	 * 
	 * @return naziv naredbe
	 */
	String getCommandName();

	/**
	 * Metoda vraća opis naredbe
	 * 
	 * @return opis naredbe
	 */
	List<String> getCommandDescription();
}
