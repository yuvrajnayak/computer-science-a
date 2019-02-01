import java.util.*;
import java.io.*;

/*
 * Yuvraj Nayak
 * 11/27/18
 * This is a student class that creates a Student object with a name, ID, Grade, and GPA. The program can
 * read a student roster from a data file and convert it into an ArrayList of students. It can also add,
 * modify, delete, and print the contents in the ArrayList.
 */
public class Student {

	private String sName;
	private int sID;
	private int sGrade;
	private double sGPA;
	
	// constructors
	public Student() {
		sName = "";
		sID = 0;
		sGrade = 0;
		sGPA = 0;
	}

	public Student(String record) {
		String[] splitRecord = record.split(" ");

		sName = splitRecord[0];
		sID = Integer.parseInt(splitRecord[1]);
		sGrade = Integer.parseInt(splitRecord[2]);
		sGPA = Double.parseDouble(splitRecord[3]);

	}

	public Student(String name, int id, int grade, double gpa) {
		sName = name;
		sID = id;
		sGrade = grade;
		sGPA = gpa;
	}
	
	// accessors and modifiers
	public String getName() {
		return sName;
	}

	public void setName(String newName) {
		this.sName = newName;
	}

	public int getID() {
		return sID;
	}

	public void setID(int newID) {
		this.sID = newID;
	}

	public int getGradeLevel() {
		return sGrade;
	}

	public void setGradeLevel(int newGrade) {
		this.sGrade = newGrade;
	}

	public double getGPA() {
		return sGPA;
	}

	public void setGPA(int newGPA) {
		this.sGPA = newGPA;
	}

	public void setAll(String name, int id, int grade, double gpa) {
		sName = name;
		sID = id;
		sGrade = grade;
		sGPA = gpa;
	}
	
	/**
	 * Add method accepts the ArrayList and the Student object to be added as parameters and
	 * adds the object to the ArrayList students
	 */
	public static void add(ArrayList<Student> students, Student newStudent) {
		
		students.add(newStudent);

		System.out.println("Added student!");
		
	}
	
	/**
	 * Modify method accepts the ArrayList, the ID to be replaced, and the Student object to be added as parameters and
	 * replaces the old Student object with the modified Student object replaceStudent
	 */
	public static void modify(ArrayList<Student> students, int replacedID, Student replaceStudent) {
		int replacedIndex = 0;
		Iterator<Student> iterator = students.iterator();

		while (iterator.hasNext()) {
			Student s = iterator.next();
			if (replacedID == s.getID()) {
				students.set(replacedIndex, replaceStudent);
				System.out.println("Entry modified");
			}
			replacedIndex++;
		}

		
	}
	
	/**
	 * Delete method accepts the ArrayList, and ID to be replaced, and removes the entry with the matching
	 * ID deleteID. If deleteID is not found in the roster, displays error message
	 */
	public static void delete(ArrayList<Student> students, int deleteID) {
		int deleteIndex = 0;
		boolean found = false;
		
		for (Student s : students) {
			if (s.getID() == deleteID) {
				deleteIndex = students.indexOf(s);
				found = true;
			}
		}
		
		if (found) {
			System.out.println("\nEntry deleted: ");
			System.out.println(students.remove(deleteIndex));
		} else {
			System.out.println("ID not found");
		}
	}
	
	/**
	 * Print method accepts the ArrayList and prints every Student object in it. Formatting
	 * is handled by the toString override.
	 */
	public static void print(ArrayList<Student> students) {
		for (Student s : students) {
			System.out.println(s);
		}
	}
	
	/**
	 * Fill method accepts the ArrayList and fills it with the student objects read from the data file. 
	 */
	public static void fill(ArrayList<Student> students) throws IOException {
		Scanner originalFile = new Scanner(new File("/Users/yuvrajnayak/Desktop/Eclipse Files/local-workspace/csa/src/StudentRoster"));
		String line = "";

		while (originalFile.hasNext()) {
			line = originalFile.nextLine();

			students.add(new Student(line));
		}
		originalFile.close();
	}

	/**
	 * Formats Student object to be printed
	 */
	public String toString() {
		String id = Integer.toString(getID());
		String grade = Integer.toString(getGradeLevel());
		String gpa = Double.toString(getGPA());
		String entry = "";

		entry += "\nName:\t" + getName();
		entry += "\nID:\t" + id;
		entry += "\nGrade:\t" + grade;
		entry += "\nGPA:\t" + gpa;

		return entry;
	}
	
	/**
	 * Determines whether two Student objects are equal by comparing their IDs
	 */
	public boolean equals(Student s) {
		boolean equals = false;

		if (s.getID() == sID) {
			equals = true;
		}

		return equals;
	}

	public static void main(String[] args) {
		String cmd;
		ArrayList<Student> students = new ArrayList<Student>();
		Scanner input = new Scanner(System.in);
		boolean exitMenu = false;

		try {
			fill(students);
		} catch (IOException e) {
			System.out.println("Error in Student Roster. Unable to read file.");
		}

		while (!exitMenu) {	
			cmd = "";
			System.out.println("\nAdd\tA");
			System.out.println("Modify\tM");
			System.out.println("Delete\tD");
			System.out.println("Print\tP");
			System.out.println("Exit\tX");
			System.out.print("Enter Command: ");
			cmd = input.nextLine();

			cmd = cmd.toUpperCase();
			if (cmd.equals("A")) {
				
				System.out.print("\nEnter name: ");
				String newName = input.nextLine();
				System.out.print("Enter ID: ");
				int newID = input.nextInt();
				System.out.print("Enter Grade: ");
				int newGrade = input.nextInt();
				System.out.print("Enter GPA: ");
				double newGPA = input.nextDouble();
				input.nextLine();
				
				Student newStudent = new Student(newName, newID, newGrade, newGPA);

				add(students, newStudent);
				
			} else if (cmd.equals("M")) {
				System.out.print("\nEnter ID of student to be modified: ");
				int replaceID = input.nextInt();
				boolean found = false;

				int i = 0;
				for (Student s : students) {
					if (s.getID() == replaceID) {
						System.out.println("\nEntry to be modified: ");
						System.out.println(students.get(i));
						found = true;
					} else {
						i++;
					}
				}
				
				if (found) {
					System.out.print("Enter new name: ");
					String newName = input.next();
					System.out.print("Enter new ID: ");
					int newID = input.nextInt();
					System.out.print("Enter new Grade: ");
					int newGrade = input.nextInt();
					System.out.print("Enter new GPA: ");
					double newGPA = input.nextDouble();
					input.nextLine();

					Student newStudentData = new Student(newName, newID, newGrade, newGPA);
					
					
					modify(students, replaceID, newStudentData);
				
				} else {
					System.out.println("ID not found");
					input.nextLine();
				}
				
			} else if (cmd.equals("D")) {
				System.out.print("\nEnter ID of student to be deleted: ");
				int deleteID = input.nextInt();
				input.nextLine();
					
				delete(students, deleteID);
				
			} else if (cmd.equals("P")) {
				print(students);
			} else if (cmd.equals("X")) {
				System.out.println("Done!");
				exitMenu = true;
			} else if (!(cmd.equals("A") || cmd.equals("M") || cmd.equals("D") || cmd.equals("P") || cmd.equals("X"))) {
				System.out.println("Invalid command!");
			}
		}
		input.close();
	}
}
