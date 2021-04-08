package hr.oprpp1.custom.scripting.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptParserTest {

	@Test
	public void parserOnlyTextTest(){
		String docBody = "Ovo je \n"
				+ "sve jedan text node";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		Assertions.assertTrue(document.equals(document2));
	}
	
	@Test
	public void parserOnlyTextTest2(){
		String docBody = "Ovo je \n"
				+ "sve jedan \\{$ text node";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		Assertions.assertTrue(document.equals(document2));
	}
	
	@Disabled
	@Test
	public void parserEscapeTextTest(){
		String docBody = "Ovo je \n"
				+ "sve jedan \\\\\\{$text node";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		Assertions.assertTrue(document.equals(document2));
	}
	
	@Test
	public void parserExceptionTest(){
		String docBody = "Ovo se ruši s iznimkom \\n \n"
				+ "jer je escape ilegalan ovdje.";	

		Assertions.assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void parserExceptionTest2(){
		String docBody = "Ovo se ruši \"s iznimkom \\n\" \n"
				+ "jer je escape ilegalan ovdje.";	

		Assertions.assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void parserEmptyTagTest(){
		String docBody = "Ovo je OK {$ = \"String ide\n"
				+ "u više redaka\n"
				+ "čak tri\" $}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		Assertions.assertTrue(document.equals(document2));
	}
	
	@Test
	public void parserEmptyTagTest2(){
		String docBody = "Ovo je isto OK {$ = \"String ide\n"
				+ "u \"više\" \\nredaka\n"
				+ "ovdje a stvarno četiri\" $}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		Assertions.assertTrue(document.equals(document2));
	}
	
	@Test
	public void parserEmptyTagExceptionTest(){
		String docBody = "Ovo se ruši {$ = \"String ide\n"
				+ "u više \\{$ redaka\n"
				+ "čak tri\" $}";
		Assertions.assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void parserEmptyTagExceptionTest2(){
		String docBody = "Ovo se ruši ${ = \\n $}";
		Assertions.assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void parserForTagTest(){
		String docBody = "{$ FOR i -1 10 1 $}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		Assertions.assertTrue(document.equals(document2));
	}
	
	@Test
	public void parserInvalidVariableNameTest(){
		String docBody = "{$ FOR 3 -1 10 1 $}";
		Assertions.assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));

	}

}
