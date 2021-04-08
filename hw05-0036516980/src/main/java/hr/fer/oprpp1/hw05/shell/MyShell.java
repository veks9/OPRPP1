package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.CatShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

/**
 * Metoda predstavlja ulaznu točku za aplikaciju
 * 
 * @author vedran
 *
 */
public class MyShell {

	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();

	public static void main(String[] args) {
		fillCommands();
		Environment environment = new EnvironmentImpl(commands);
		environment.writeln("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;

		do {
			environment.write(environment.getPromptSymbol() + " ");
			String[] lineArray = readLineOrLines(environment);
			String commandName = lineArray[0];
			String arguments = lineArray[1];

			ShellCommand command = commands.get(commandName);
			if (command == null) {
				environment.writeln("That command does not exist!");
				continue;
			}

			status = command.executeCommand(environment, arguments);
		} while (status != ShellStatus.TERMINATE);

	}

	/**
	 * Metoda čita i analizira predane znakove na ulazu
	 * 
	 * @param environment okruženje u kojem se radi
	 * @return
	 */
	private static String[] readLineOrLines(Environment environment) {
		String line = environment.readLine();
		StringBuilder sb = new StringBuilder();

		while (isMultiline(line, environment.getMorelinesSymbol())) {
			String actualLine = line.substring(0, line.indexOf(environment.getMorelinesSymbol()));
			sb.append(actualLine);
			environment.write(environment.getMultilineSymbol() + " ");
			line = environment.readLine();
		}
		sb.append(line);

		String wholeLine = sb.toString().trim();
		String[] commandAndArguments = new String[2];
		int firstBlank = wholeLine.indexOf(" ");
		if (firstBlank == -1) {
			commandAndArguments[0] = wholeLine;
			commandAndArguments[1] = "";
		} else {
			commandAndArguments[0] = wholeLine.substring(0, firstBlank);
			commandAndArguments[1] = wholeLine.substring(firstBlank + 1);
		}
		return commandAndArguments;
	}

	/**
	 * Metoda ispituje radi li se o unosu koji se proteže u više redaka
	 * 
	 * @param line            unos
	 * @param moreLinesSymbol simbol za više redaka
	 * @return <code>true</code> ako se proteže u više redaka, inače
	 *         <code>false</code>
	 */
	private static boolean isMultiline(String line, char moreLinesSymbol) {
		line = line.trim();
		return line.lastIndexOf(String.valueOf(moreLinesSymbol)) == line.length()-1;
	}

	/**
	 * Metoda napravi mapu naredbi koje su dopuštene
	 */
	private static void fillCommands() {
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
	}
}
