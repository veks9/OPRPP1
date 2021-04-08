package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * Klasa predstavlja implementaciju metode copy
 * 
 * @author vedran
 *
 */
public class CopyShellCommand implements ShellCommand {

	private static final String commandName = "copy";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("Command expects two arguments: source file name and ");
		description.add("destination file name (i.e. paths and names).");
		description.add("If file exists, user is asked can it be overwritten.");
		description.add("If the second argument is directory, the file is copied to that directory.");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}

		Parser parser = new Parser(environment);
		String[] argumentsArray = parser.parse(arguments);

		if (argumentsArray.length != 2 || (argumentsArray[0].length() == 0 || argumentsArray[1].length() == 0)) {
			environment.writeln("Command expects two arguments");
			return ShellStatus.CONTINUE;
		}

		Path source = Paths.get(argumentsArray[0]);
		if (Files.isDirectory(source)) {
			environment.writeln("First argument(source) cannot be a directory!");
		}

		Path destination = Paths.get(argumentsArray[1]);
		if (Files.isDirectory(destination)) {
			destination = Paths.get(argumentsArray[1] + "/" + argumentsArray[0]);
		}

		boolean overwrite = true;
		if (Files.exists(destination)) {
			while (true) {
				environment.writeln("The given destination path already exists. Do you want to overwrite it? Y/n");
				String answer = environment.readLine().trim();
				if (answer.equalsIgnoreCase("Y")) {
					overwrite = true;
					break;
				} else if (answer.equalsIgnoreCase("n")) {
					overwrite = false;
					break;
				} else
					environment.writeln("Possible inputs are Y or n. You typed in " + answer + ".");
			}
		}

		if (!overwrite) {
			return ShellStatus.CONTINUE;
		}
		try (InputStream is = new BufferedInputStream(Files.newInputStream(source));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(destination))) {

			byte[] buff = new byte[4096];

			while (true) {
				int len = is.read(buff);
				if (len < 0)
					break;
				os.write(buff, 0, len);
			}

		} catch (IOException e) {
			environment.writeln("There was an error while trying to copy files");
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
