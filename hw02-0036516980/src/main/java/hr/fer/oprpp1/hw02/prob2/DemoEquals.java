package hr.fer.oprpp1.hw02.prob2;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

public class DemoEquals {
	public static void main(String[] args) {
		String docBody = "Ovo se ruši s iznimkom \\n \n" + "jer je escape ilegalan ovdje.\n";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		System.out.println(same);
	}
}
