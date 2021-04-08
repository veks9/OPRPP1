package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju naredbe exit
 * 
 * @author vedran
 *
 */
public class ExitShellCommand implements ShellCommand {

	private static final String commandName = "exit";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("Command terminates application.");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}
		if (arguments.trim().length() > 0) {
			environment.writeln("Exit should be called with no arguments!");
			return ShellStatus.CONTINUE;
		} else {
			return ShellStatus.TERMINATE;
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
