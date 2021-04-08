package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class Demo1pz2 {
	public static void main(String[] args) {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();

		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");

		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");

		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();

		ElementsGetter getter3 = col2.createElementsGetter();

		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
	}

//	public static void main(String[] args) {
//		Collection col = new LinkedListIndexedCollection(); // npr. new ArrayIndexedCollection(); (stvori praznu kolekciju)
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		
//		ElementsGetter getter = col.createElementsGetter();
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		}
}
