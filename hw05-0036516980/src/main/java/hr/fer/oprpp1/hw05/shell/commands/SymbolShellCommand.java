package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.parser.Parser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju naredbe symbol
 * 
 * @author vedran
 *
 */
public class SymbolShellCommand implements ShellCommand {

	private static final String commandName = "symbol";
	private static final String PROMPT = "PROMPT";
	private static final String MULTILINES = "MULTILINES";
	private static final String MORELINES = "MORELINES";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("This command expects ");
		description.add("one or two arguments. First one ");
		description.add("represents the symbol type(PROMPT, MORELINES, MULTILINE) ");
		description.add("and second(optional) represents new symbol which is to be set as symbol for ");
		description.add("the given type. If second argument isn't given, current symbol ");
		description.add("for given type is printed.");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}

		Parser parser = new Parser(environment);
		String[] argumentsArray = parser.parse(arguments);

		if (argumentsArray.length == 0) {
			environment.writeln("Argument was not given!");
			return ShellStatus.CONTINUE;
		}
		if (argumentsArray.length > 2) {
			environment.writeln("Too many arguments!");
			return ShellStatus.CONTINUE;
		}
		if (argumentsArray.length == 1) {
			String symbol = argumentsArray[0].toUpperCase().trim();
			switch (symbol) {
			case PROMPT: {
				printInfo(environment, PROMPT, environment.getPromptSymbol());
				break;
			}
			case MULTILINES: {
				printInfo(environment, MULTILINES, environment.getMultilineSymbol());
				break;
			}
			case MORELINES: {
				printInfo(environment, MORELINES, environment.getMorelinesSymbol());
				break;
			}
			default:
				environment.writeln("There is no such keyword!");
			}
		} else {
			String symbol = argumentsArray[0].toUpperCase().trim();
			switch (symbol) {
			case PROMPT: {
				char old = environment.getPromptSymbol();
				environment.setPromptSymbol(argumentsArray[1].charAt(0));
				printChange(environment, PROMPT, environment.getPromptSymbol(), old);
				break;
			}
			case MULTILINES: {
				char old = environment.getMultilineSymbol();
				environment.setMultilineSymbol(argumentsArray[1].charAt(0));
				printChange(environment, MULTILINES, environment.getMultilineSymbol(), old);
				break;
			}
			case MORELINES: {
				char old = environment.getMorelinesSymbol();
				environment.setMorelinesSymbol(argumentsArray[1].charAt(0));
				printChange(environment, MORELINES, environment.getMorelinesSymbol(), old);
				break;
			}
			default:
				environment.writeln("There is no such keyword!");
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda formatira ispis i ispisuje za slučaj kada se mijenjao simbol
	 * 
	 * @param env        okruženje
	 * @param symbolName naziv simbola
	 * @param symbol     simbol
	 * @param old        stara vrijednost
	 */
	private void printChange(Environment env, String symbolName, char symbol, char old) {
		env.writeln("Symbol for " + symbolName + " changed from '" + old + "'" + " to '" + symbol + "'");
	}

	/**
	 * Metoda formatira ispis i ispisuje za slučaj kada se želi saznati simbol za
	 * tip
	 * 
	 * @param env        okruženje
	 * @param symbolName naziv simbola
	 * @param symbol     simbol
	 */
	private void printInfo(Environment env, String symbolName, char symbol) {
		env.writeln("Symbol for " + symbolName + " is '" + symbol + "'");
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
