import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class acsl2 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(input.nextLine());
		entryList.add(input.nextLine());
		entryList.add(input.nextLine());
		entryList.add(input.nextLine());
		entryList.add(input.nextLine());
		input.close();
		
		for (String entry : entryList) {
			String[] split = entry.split(" ");
					
			ArrayList<Character> e1 = compare(split[0], split[1]);
			ArrayList<Character> e2 = compare(split[1], split[0]);
			ArrayList<Character> e3 = compare(reverser(split[0]), reverser(split[1]));
			ArrayList<Character> e4 = compare(reverser(split[1]), reverser(split[0]));
			
			e1.retainAll(e2);
			e1.retainAll(e3);
			e1.retainAll(e4);
			
			Collections.sort(e1);
			
			e1 = removeDuplicates(e1);
			
			if (e1.isEmpty()) {
				System.out.println("NONE");
			} else {
				for (Character c : e1) {
					System.out.print(c);
				}
				System.out.println();
			}
		}
	}
	
	public static ArrayList<Character> compare(String a, String b) {
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
	
	public static String reverser(String s) {
		String reversed = "";
		char[] sArray = s.toCharArray();
		
		for (int i = sArray.length - 1; i >= 0; i--) {
			reversed += sArray[i];
		}
		return reversed;
	}
	
	public static ArrayList<Character> removeDuplicates(ArrayList<Character> a) {
		for (int i = 0; i < a.size() - 1; i++) {
			if (a.get(i) == a.get(i + 1)) {
				a.remove(i);
				i--;
			}
		}
		return a;
	}
}