package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.parser.Parser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju naredbe mkdir
 * 
 * @author vedran
 *
 */
public class MkdirShellCommand implements ShellCommand {

	private static final String commandName = "mkdir";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("Command takes a single argument: directory name, ");
		description.add("and creates the appropriate directory structure.");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}

		Parser parser = new Parser(environment);
		String[] argumentsArray = parser.parse(arguments);

		if (argumentsArray[0].length() == 0) {
			environment.writeln("Command expects a single argument!");
		}
		Path path = Paths.get(argumentsArray[0]);
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			environment.writeln("There was an error while creating a directory!");
		}
		return ShellStatus.CONTINUE;
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
