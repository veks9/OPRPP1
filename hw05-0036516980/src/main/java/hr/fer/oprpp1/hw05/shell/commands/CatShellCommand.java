package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import hr.fer.oprpp1.hw05.parser.Parser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju cat naredbe
 * @author vedran
 *
 */
public class CatShellCommand implements ShellCommand {

	private static final String commandName = "cat";
	private static final List<String> description = new ArrayList<>();
	private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
	static {
		description.add("Takes one or two arguments. The first argument is path to some file and is mandatory.");
		description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset is used");

	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}
		Parser parser = new Parser(environment);
		String[] argumentsArray = parser.parse(arguments);
		if (argumentsArray.length > 2) {
			environment.writeln("Command expects one or two arguments!");
			return ShellStatus.CONTINUE;
		}

		if (argumentsArray.length == 1) {
			if (argumentsArray[0].length() == 0) {
				environment.writeln("Command expects at least one argument!");
				return ShellStatus.CONTINUE;
			}

			execute(environment, argumentsArray[0], DEFAULT_CHARSET);
		} else if (argumentsArray.length == 2)
			execute(environment, argumentsArray[0], Charset.forName(argumentsArray[1]));
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda izvodi naredbu cat
	 * @param environment okruženje
	 * @param path put do datoteke
	 * @param charset kodna stranica koja se koristi
	 */
	private void execute(Environment environment, String path, Charset charset) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(path)))) {
			String s = "";

			byte buff[] = new byte[4096];
			while (true) {
				int len = is.read(buff);
				if (len < 0)
					break;
				environment.write(new String(buff, 0, len, charset.toString()));
			}
			environment.writeln("");
			
		} catch (IOException exception) {
			environment.writeln("There was an error while reading a file!");
		}

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
