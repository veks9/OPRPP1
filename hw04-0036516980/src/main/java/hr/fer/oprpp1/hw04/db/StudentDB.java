package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {
	public static void main(String[] args) {
		List<String> lines = new ArrayList<>();
		try {
			lines = loadDatabase();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		StudentDatabase database = new StudentDatabase(lines);

		Scanner sc = new Scanner(System.in);
		while (true) {
			if (sc.next().equalsIgnoreCase("exit")) {
				System.out.println("Goodbye!");
				break;
			}


			List<StudentRecord> list = selectStudentRecords(database, new QueryParser(sc.nextLine()));
			ispis(list);

		}
		sc.close();
	}

	private static List<StudentRecord> selectStudentRecords(StudentDatabase database, QueryParser parser) {
		if (parser.isDirectQuery()) {
			System.out.println("Using index for record retrieval.");
			List<StudentRecord> list = new ArrayList<>();
			list.add(database.forJMBAG(parser.getQueriedJMBAG()));
			return list;
		} else {
			List<StudentRecord> list = database.filter(new QueryFilter(parser.getQuery()));
			return list;
		}
	}

	private static void ispis(List<StudentRecord> list) {
		if (list.size() == 0) {
			System.out.println("Records selected: 0");
			return;
		}
		int maxName = 0, maxLastName = 0;
		for (StudentRecord r : list) {
			if (r.getFirstName().length() > maxName)
				maxName = r.getFirstName().length();

			if (r.getLastName().length() > maxLastName)
				maxLastName = r.getLastName().length();
		}

		String headerAndFooter = "+============+";
		for (int i = 0; i < maxLastName + 2; i++) {
			headerAndFooter += "=";
		}
		headerAndFooter += "+";
		for (int i = 0; i < maxName + 2; i++) {
			headerAndFooter += "=";
		}
		headerAndFooter += "+===+";

		String s = "";
		s += headerAndFooter + "\n";
		for (StudentRecord r : list) {
			s += "| " + r.getJmbag() + " | " + r.getLastName();

			for (int i = 0; i < maxLastName - r.getLastName().length(); i++)
				s += " ";

			s += " | " + r.getFirstName();

			for (int i = 0; i < maxName - r.getFirstName().length(); i++)
				s += " ";

			s += " | " + r.getfinalGrade() + " |\n";
		}
		s += headerAndFooter + '\n' + "Records selected: " + list.size();

		System.out.println(s);

	}

	private static List<String> loadDatabase() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./src/test/resources/database/database.txt"),
				StandardCharsets.UTF_8);
		return lines;
	}
}
