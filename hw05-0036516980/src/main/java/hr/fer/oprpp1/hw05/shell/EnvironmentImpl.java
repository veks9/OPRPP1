package hr.fer.oprpp1.hw05.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.SortedMap;

/**
 * Klasa predstavlja implementaciju sučelja {@link Environment}
 * @author vedran
 *
 */
public class EnvironmentImpl implements Environment {

	private SortedMap<String, ShellCommand> commands;
	private Character multiLineSymbol;
	private Character promptSymbol;
	private Character moreLinesSymbol;
	private BufferedReader br;
	private BufferedWriter bw;
	private final Character DEFAULT_MULTILINE_SYMBOL = '|';
	private final Character DEFAULT_PROMPT_SYMBOL = '>';
	private final Character DEFAULT_MORELINES_SYMBOL = '\\';

	/**
	 * Konstruktor prima mapu naredbi i postavlja mapu naredbi i simbole na defaultne
	 * @param commands
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> commands) {
		this.commands = commands;
		this.multiLineSymbol = DEFAULT_MULTILINE_SYMBOL;
		this.promptSymbol = DEFAULT_PROMPT_SYMBOL;
		this.moreLinesSymbol = DEFAULT_MORELINES_SYMBOL;
		
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
	}
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new ShellIOException("An error occurred during reading!");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			bw.write(text);
			bw.flush();
		} catch (IOException e) {
			throw new ShellIOException("An error occurred during writing!");
		}

	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(text + "\n");

	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;	
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiLineSymbol = symbol;

	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;

	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLinesSymbol = symbol;
	}

}
