package hr.fer.oprpp1.hw05.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import hr.fer.oprpp1.hw05.shell.Environment;


/**
 * Klasa predstavlja parser
 * 
 * @author vedran
 *
 */
public class Parser {

	private Environment environment;

	public Parser(Environment environmenet) {
		this.environment = environmenet;
	}

	/**
	 * Metoda parsira unos i vraća polje stringova
	 * 
	 * @param input unos
	 * @return polje stringova
	 */
	public String[] parse(String input) {
		Scanner sc = new Scanner(input);
		List<String> lista = new ArrayList<>();
		String line = sc.nextLine();
		char[] data = line.toCharArray();
		int index = 0;
		while (index < data.length) {
			if (!input.contains("\"")) {
				sc.close();
				return input.trim().split("\\s+");
			}

			index = skipBlanks(index, data);
			String ret = "";

			if (data[index] == '"') {
				index++;
				while (data[index] != '"') {
					if(data[index] == '\\') {
 						if(data[index+1] == '\\' || data[index+1] == '"') {
 							index++;
 						}
					}
					ret += data[index++];
				}
				index++;
				if (index < data.length && data[index] != ' ') {
					environment.writeln("There was an error in arguments!");
					sc.close();
					return null;
				}
				lista.add(ret);
			} else {
				while (data[index] != ' ') {
					ret += data[index++];
				}
				lista.add(ret);
			}
		}
		String[] retArray = new String[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			retArray[i] = lista.get(i);
		}
		sc.close();
		return retArray;
	}

	private int skipBlanks(int index, char[] data) {
		while (index < data.length) {
			char symbol = data[index];
			if (symbol == ' ' || symbol == '\n' || symbol == '\t' || symbol == '\r') {
				index++;
				continue;
			}
			break;
		}
		return index;
	}

}
