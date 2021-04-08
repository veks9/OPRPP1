package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	@Test
	public void ComparisonOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		
		IComparisonOperator oper2 = ComparisonOperators.LIKE;
		assertFalse(oper2.satisfied("Zagreb", "Aba*"));
		assertFalse(oper2.satisfied("AAA", "AA*AA"));
		assertTrue(oper2.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	public void forJMBAGTest() {
		List<String> lines = new ArrayList<>();
		try {
			lines = loadDatabase();			
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		StudentDatabase database = new StudentDatabase(lines);
		
		assertNotNull(database.forJMBAG("0000000001"));
		assertEquals(null, database.forJMBAG("12345678901"));
	}
	
	@Test
	public void filterTest() {
		IFilter trueFilter = studentRecord -> true;
		IFilter falseFilter = studentRecord -> false;
		
		List<String> lines = new ArrayList<>();
		try {
			lines = loadDatabase();			
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		StudentDatabase database = new StudentDatabase(lines);
		
		assertEquals(0, database.filter(falseFilter).size());
		assertEquals(lines.size(), database.filter(trueFilter).size());
	}
	
	@Test
	public void fieldValueGettersTest() {
		List<String> lines = new ArrayList<>();
		try {
			lines = loadDatabase();			
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		StudentDatabase database = new StudentDatabase(lines);
		StudentRecord record = database.forJMBAG("0000000002");
		
		assertEquals("Petra", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Bakamović", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("0000000002", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void conditionalExpressionTest() {
		List<String> lines = new ArrayList<>();
		try {
			lines = loadDatabase();			
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		StudentDatabase database = new StudentDatabase(lines);
		
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE
		);
		assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldValueGetter());
		assertEquals("Bos*", expr.getLiteral());
		assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());
		
		StudentRecord record = database.forJMBAG("0000000003");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldValueGetter().get(record), // returns lastName from given record
				expr.getLiteral() // returns "Bos*"
		);
		
		assertTrue(recordSatisfies);
	}
	
	@Test
	public void queryParserTest() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");

		assertFalse(qp2.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
		assertEquals(2, qp2.getQuery().size());
		
	}
	
 	private List<String> loadDatabase() throws IOException {
		List<String> lines = Files.readAllLines(
				Paths.get("./src/test/resources/database/database.txt"),
				StandardCharsets.UTF_8
		);
		return lines;
	}
	
}