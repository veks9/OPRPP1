package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.Tester;

public class Demo1pz5 {
	public static void main(String[] args) {
		
		class EvenIntegerTester implements Tester {
			
			public boolean test(Object obj) {
				if(!(obj instanceof Integer)) return false;
				Integer i = (Integer)obj;
				return i % 2 == 0;
			}	
		}

		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));

		System.out.println("-----------------------------------");
		
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		
		col2.forEach(System.out::println);
		}
}