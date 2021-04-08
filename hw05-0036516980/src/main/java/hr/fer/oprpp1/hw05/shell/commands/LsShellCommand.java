package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.oprpp1.hw05.parser.Parser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa predstavlja implementaciju naredbe ls
 * 
 * @author vedran
 *
 */
public class LsShellCommand implements ShellCommand {

	private static final String commandName = "ls";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("Takes a single argument – directory – and writes a directory listing (not recursive)");
		description
				.add("The output consists of 4 columns. First column indicates if current object is directory ( d ), ");
		description.add("readable ( r ), writable ( w ) and executable ( x )");
		description.add("Second column contains object size in bytes.");
		description.add("Follows file creation date/time and finally file name.");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		if (arguments == null) {
			environment.writeln("Invalid command name!");
			return ShellStatus.CONTINUE;
		}

		Parser parser = new Parser(environment);
		String[] argumentsArray = parser.parse(arguments);
		if (argumentsArray.length != 1 || argumentsArray[0].length() == 0) {
			environment.writeln("Command ls expects one argument!");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(argumentsArray[0]);
		if (!Files.exists(path)) {
			environment.writeln("There is no such directory!");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(path)) {
			environment.writeln("Given path should be a directory!");
			return ShellStatus.CONTINUE;
		}
		File dir = new File(path.toString());
		File[] children = dir.listFiles();
		for (File file : children) {
			print(file.toPath(), environment);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda formatira ispis i ispisuje
	 * 
	 * @param path        put do datoteke ili direktorija
	 * @param environment okruženje
	 */
	private void print(Path path, Environment environment) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
					LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

			String fileInfo = firstColumnFormat(path);
			long fileSize = Files.size(path);
			String fileName = path.getFileName().toString();
			environment.writeln(String.format("%s %10d %s %s", fileInfo, fileSize, formattedDateTime, fileName));
		} catch (IOException e) {
			environment.writeln("There was an error while getting information of files!");
		}
	}

	/**
	 * Metoda formatira prvi stupac ispisa
	 * 
	 * @param path put do datoteke ili direktorija
	 * @return formatiran prvi stupac
	 */
	private String firstColumnFormat(Path path) {
		StringBuilder sb = new StringBuilder();

		sb.append(Files.isDirectory(path) ? "d" : "-");
		sb.append(Files.isReadable(path) ? "r" : "-");
		sb.append(Files.isWritable(path) ? "w" : "-");
		sb.append(Files.isExecutable(path) ? "x" : "-");

		return sb.toString();
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
