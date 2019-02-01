import java.util.ArrayList;
import java.util.Scanner;

/*
 * Yuvraj Nayak
 * 1/30/19
 * ACSL Senior Division programming contest
 * Uses specified algorithm to find differences in files
 */
public class acsl2Senior {
	public static void main(String[] args) {
		final int NUM_ITERATIONS = 5;
		Scanner input = new Scanner(System.in);
		
		ArrayList<String> entryListA = new ArrayList<String>();
		ArrayList<String> entryListB = new ArrayList<String>();
		
		for (int i = 0; i < NUM_ITERATIONS; i++) {
			entryListA.add(input.nextLine());
			entryListB.add(input.nextLine());
			input.nextLine();
		}
		input.close();
		
		for (String entryA : entryListA) {
			ArrayList<String> wordsInA = new ArrayList<String>();
			ArrayList<String> wordsInB = new ArrayList<String>();
			
			String[] splitA = entryA.split(" ");
			String[] splitB = entryListB.get(entryListA.indexOf(entryA)).split(" ");
			
			String output1 = ""; 
			String output2 = "";
			String finalStringOutput = "";
			
			for (String s : splitA) {
				wordsInA.add(s);
			}
			for (String s : splitB) {
				wordsInB.add(s);
			}
			
			ArrayList<String> o1 = compareWords(wordsInA, wordsInB);
			ArrayList<String> o2 = compareWords(wordsInB, wordsInA);
			
			for (String s : o1) {
				output1 += s;
			}
			
			for (String s : o2) {
				output2 += s;
			}
			
			ArrayList<Character> finalStringArray = compareChars(output1, output2);
			
			if (finalStringArray.isEmpty()) {
				System.out.println("NONE");
			} else {
				for (Character c : finalStringArray) {
					finalStringOutput += c;
				}
				System.out.println(finalStringOutput);
			}
		}
	}
	
	public static ArrayList<String> compareWords(ArrayList<String> inputA, ArrayList<String> inputB) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		
		for (String s : inputA) {
			a.add(s);
		}
		for (String s : inputB) {
			b.add(s);
		}
		
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				if (b.get(j).equals(a.get(i))) {
					result.add(a.get(i));
					b.remove(b.get(j));
					j = b.size();
				} else if (b.get(j).contains(a.get(i))) {
					result.add(a.get(i));
					b.set(j, b.get(j).replace(a.get(i), ""));
					j = b.size();
				}
			}
		}
		return result;
	}
	
	public static ArrayList<Character> compareChars(String a, String b) {
		char[] aArray = a.toCharArray();
		char[] bArray = b.toCharArray();
		ArrayList<Character> result = new ArrayList<Character>();
		
		for (int i = 0; i < aArray.length; i++) {
			for (int j = 0; j < bArray.length; j++) {
				if (aArray[i] == bArray[j]) {
					result.add(aArray[i]);
					bArray = arrayChopper(bArray, j + 1);
					j = bArray.length;
				}
			}
		}
		return result;
	}
	
	public static char[] arrayChopper(char[] a, int n) {
		char[] result = new char[a.length - n];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = a[i + n];
		}
		return result;
	}
}