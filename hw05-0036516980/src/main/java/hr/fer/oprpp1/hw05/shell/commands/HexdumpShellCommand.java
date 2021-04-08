package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
 * Klasa predstavlja implementaciju naredbe hexdump
 * 
 * @author vedran
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	private static final String commandName = "hexdump";
	private static final List<String> description = new ArrayList<>();
	static {
		description.add("Command expects a single argument: file name and produces a hexdump.");
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
			environment.writeln("Command expects one argument!");
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(argumentsArray[0])))) {
			String s = "";
			byte buff[] = new byte[4096];
			while (true) {
				int len = is.read(buff);
				if (len < 0)
					break;
				s += formatHexDump(buff, 0, len);
			}

			environment.writeln(s);
		} catch (IOException exception) {
			environment.writeln("There was an error while reading a file!");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda izvodi naredbu
	 * 
	 * @param array  ulazno polje bajtova
	 * @param offset offset
	 * @param length duljina predanih podataka
	 * @return formatiran ispis hexdumpa
	 */
	private static String formatHexDump(byte[] array, int offset, int length) {
		final int width = 16;
		StringBuilder builder = new StringBuilder();
		
		for (int rowOffset = offset; rowOffset < offset + length; rowOffset += width) {
			builder.append(String.format("%06x:  ", rowOffset));

			for (int index = 0; index < width; index++) {
				if (index == 7)
					builder.append("| ");
				if (rowOffset + index < length) {
					byte b = array[rowOffset + index];
					if (b < 32 || b > 127)
						b = 0x2e;
					builder.append(String.format("%02x ", b));
				} else {
					builder.append("   ");
				}
			}

			if (rowOffset < array.length) {
				int asciiWidth = Math.min(width, array.length - rowOffset);
				builder.append(" | ");
				try {
					builder.append(new String(array, rowOffset, asciiWidth, "UTF-8").replaceAll("\r\n", " ")
							.replaceAll("\n", " "));
				} catch (UnsupportedEncodingException ignored) {
				}
			}

			builder.append(String.format("%n"));
		}

		return builder.toString();
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
