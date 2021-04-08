package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju charset naredbe
 * 
 * @author vedran
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	private static final String commandName = "charsets";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("Takes no arguments and lists names of supported charsets for your Java platform");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}
		if (arguments.trim().length() != 0) {
			environment.writeln("Charsets command is called without arguments!");
			return ShellStatus.CONTINUE;
		}
		
		environment.writeln("List of supported charsets:");
		Charset.availableCharsets().forEach((s, c) -> environment.writeln("  " + s));

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
