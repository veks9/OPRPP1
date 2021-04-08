package hr.fer.oprpp1.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;

public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		
		int counter = 1;
		System.out.print("Root " + counter + "> ");
		List<Complex> list = new ArrayList<>();
		while(sc.hasNext()) {
			String input = sc.nextLine().trim();
			if(input.equalsIgnoreCase("done")) {
				if(counter < 2) {
					System.out.println("Please enter at least two roots! The last entry is deleted.");
					list.clear();
					counter = 0;
					continue;
				} 
				System.out.println("Image of fractal will appear shortly. Thank you.");
				break;
			}
			
			Complex z = parse(input);
			list.add(z);
			System.out.print("Root " + ++counter + "> ");	
		}
		sc.close();
		
		
		Complex[] arr = new Complex[list.size()];
		for(int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, arr);
		
		FractalViewer.show(new IFractalProducerImpl(rootedPolynomial)); 
		
	}

	private static Complex parse(String s) {
		try {
			if (s.charAt(0) == '+') {
				if (s.charAt(1) == '+' || s.charAt(1) == '-')
					throw new IllegalArgumentException("Format of complex number is not valid");
				s = s.substring(1);
			}

			s = s.replace(" ", "");
			
			String realString = "";
			String imaginaryString = "";
			if (s.length() == 1 && s.endsWith("i")) {
				s = s.replaceAll("i", "1i");
			} else if ((s.lastIndexOf('+') == s.length() - 2 || s.lastIndexOf('-') == s.length() - 2)
					&& s.endsWith("i")) {
				s = s.replaceAll("i", "1i");
			}

			if (s.indexOf('+') > 0) {
				s = s.replaceAll("i", "");

				realString = s.substring(0, s.lastIndexOf('+'));
				imaginaryString = s.substring(s.lastIndexOf('+') + 1, s.length());

				return new Complex(Double.parseDouble(realString), Double.parseDouble(imaginaryString));

			} else if (s.lastIndexOf('-') > 0) {
				s = s.replaceAll("i", "");
				realString = s.substring(0, s.lastIndexOf('-'));
				imaginaryString = s.substring(s.lastIndexOf('-'), s.length());

				return new Complex(Double.parseDouble(realString), Double.parseDouble(imaginaryString));
				
			} else if (!s.endsWith("i") && !s.contains("i")) {

				return new Complex(Double.parseDouble(s), 0);

			} else if (s.endsWith("i") || s.contains("i")) {
				s = s.replaceAll("i", "");

				return new Complex(0, Double.parseDouble(s));

			} else {

				return new Complex();
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Format of complex number is not valid");
		}
	}

}
