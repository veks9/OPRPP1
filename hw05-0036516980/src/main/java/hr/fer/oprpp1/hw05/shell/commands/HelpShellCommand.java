package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.parser.Parser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju naredbe help
 * 
 * @author vedran
 *
 */
public class HelpShellCommand implements ShellCommand {

	private static final String commandName = "help";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("If started with no arguments, it lists names of all supported commands.");
		description.add("If started with single argument it prints name nad description of selected command.");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}

		if (arguments.trim().length() == 0) {
			environment.writeln("List of all possible commands:");
			environment.commands().forEach((s, c) -> {
				environment.writeln(" ".repeat(2) + s);
			});
		} else {
			Parser parser = new Parser(environment);
			String[] argumentsArray = parser.parse(arguments);
			if (environment.commands().containsKey(argumentsArray[0])) {
				ShellCommand command = environment.commands().get(argumentsArray[0]);
				environment.writeln("Name:\n" + " ".repeat(2) + command.getCommandName() + "\n" + "Description:");
				command.getCommandDescription().forEach(d -> environment.writeln(" ".repeat(2) + d));
			} else {
				environment.writeln("There is no such command!");
			}
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
