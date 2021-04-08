package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.parser.Parser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju naredbe tree
 * 
 * @author vedran
 *
 */
public class TreeShellCommand implements ShellCommand {

	private static final String commandName = "tree";
	private static final List<String> description = new ArrayList<>();

	static {
		description.add("This command expects a single argument: ");
		description.add("directory name and prints a tree.");
	}

	/**
	 * Klasa predstavlja implementaciu {@link FileVisitor}a
	 * @author vedran
	 *
	 */
	private class Tree implements FileVisitor<Path> {
		private Environment environment;
		private int NOBlanks;
		private final int SHIFT = 2;

		public Tree(Environment environment) {
			this.environment = environment;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			NOBlanks += SHIFT;

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			throw new IOException("Can't visit " + file.getFileName());
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			NOBlanks -= SHIFT;
			return FileVisitResult.CONTINUE;
		}

		private void print(Path file) {
			environment.writeln(" ".repeat(NOBlanks) + file.getFileName());
		}

	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}

		Parser parser = new Parser(environment);
		String[] argumentsArray = parser.parse(arguments);
		if (argumentsArray.length != 1) {
			environment.writeln("Command tree expects one argument!");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(argumentsArray[0]);
		if (!Files.isDirectory(path)) {
			environment.writeln("Given path should be a directory!");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(path, new Tree(environment));
		} catch (IOException e) {
			environment.writeln("There was an error while walking the tree!");
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
