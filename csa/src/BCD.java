
/*
 * Yuvraj Nayak
 * 10/23/18
 * This is a Binary Coded Decimal (BCD) Class, which is a technique for working with
 * large numbers where each digit is stored separately. It can add digits to current BCDs, 
 * add two BCDs together, multiply two BCDs together, multiply a BCD by an integer, and 
 * raise a BCD to a power.
 */
public class BCD {

	private int[] digits;
	private boolean valid = true;

	/**
	 * Constructs a BCD from an integer array. Chained to flipped array constructor.
	 */
	public BCD(int bcdDigits[]) {
		this(bcdDigits, false);
	}

	/**
	 * Constructs a BCD from and integer array. Can create BCDs from human readable
	 * array orders or flipped digit array orders, depending on what value of
	 * boolean flip is passed in.
	 */
	public BCD(int[] bcdDigits, boolean flip) {
		if (bcdDigits == null) {
			invalid("Null array passed in");
		} else {
			for (int i = 0; i < bcdDigits.length; i++) {
				if (bcdDigits[i] > 9) {
					invalid("Digit larger than 9");
				} else if (bcdDigits[i] < 0) {
					invalid("Digit less than 0");
				}
			}

			if (!flip) {
				while (bcdDigits.length > 1 && bcdDigits[0] == 0) {
					bcdDigits = trim(bcdDigits, false);
				}

				digits = new int[bcdDigits.length];

				for (int i = 0; i < bcdDigits.length; i++) {
					digits[i] = bcdDigits[bcdDigits.length - 1 - i];
				}
			} else {
				while (bcdDigits.length > 1 && bcdDigits[bcdDigits.length - 1] == 0) {
					bcdDigits = trim(bcdDigits, true);
				}

				digits = new int[bcdDigits.length];

				for (int i = 0; i < bcdDigits.length; i++) {
					digits[i] = bcdDigits[i];
				}
			}
		}
	}

	/**
	 * Invalidates any string passed to construct a BCD by setting the class
	 * variable valid to false.
	 */
	public BCD(String str) {
		invalid("String argument not allowed");
	}

	/**
	 * Constructs a BCD from an integer.
	 */
	public BCD(int num) {
		if (num < 0) {
			invalid("Digit less than 0");
		}
		if (num == 0) {
			int[] bcdDigits = { 0 };
			digits = bcdDigits;
		} else {
			while (num > 0) {
				int digit = num % 10;
				addADigit(digit);
				num /= 10;
			}
		}
	}

	/**
	 * Invalidates when nothing is passed to construct a BCD by setting the class
	 * variable valid to false.
	 */
	public BCD() {
		invalid("Nothing passed in");
	}

	/**
	 * Accessor for valid, which determines if an argument is valid or not.
	 */
	public boolean isValid(int[] b1Digits) {
		return valid;
	}

	/**
	 * Sets valid to false and displays error message when an invalid BCD is
	 * attempted to be created.
	 */
	public void invalid(String msg) {
		System.out.println("Error! " + msg);
		valid = false;
	}

	/**
	 * Accessor for digits.length, which the number of entries in the digits array.
	 */
	public int numberOfDigits() {
		return digits.length;
	}

	/**
	 * Returns the value of an array at the index passed in. If the index is out of
	 * bounds, returns -1.
	 */
	public int nthDigit(int index) {
		if (index < digits.length && index > 0) {
			return digits[index - 1];
		} else {
			return -1;
		}
	}

	/**
	 * Prints a BCD using toString.
	 */
	public void print() {
		System.out.println(this.toString());
	}

	/**
	 * Adds and integer to the front of the BCD, creating a new place value.
	 */
	public void addADigit(int newDigit) {
		int[] newDigits;

		if (digits == null) {
			newDigits = new int[1];
		} else {
			newDigits = new int[digits.length + 1];

			for (int i = 0; i < newDigits.length - 1; i++) {
				newDigits[i] = digits[i];
			}
		}

		newDigits[newDigits.length - 1] = newDigit;

		digits = newDigits;
	}

	/**
	 * Adds two BCDs together and returns the sum.
	 */
	public BCD addBCDs(BCD b2) {
		int[] added;

		if (digits.length > b2.numberOfDigits()) {
			added = new int[digits.length + 1];

			int[] b2temp = new int[digits.length];

			for (int i = 0; i < b2.numberOfDigits(); i++) {
				b2temp[i] = b2.digits[i];
			}

			for (int i = 0; i < added.length - 1; i++) {
				added[i] = digits[i] + b2temp[i];
			}
		} else {
			added = new int[b2.numberOfDigits() + 1];

			int[] b1temp = new int[b2.numberOfDigits()];

			for (int i = 0; i < digits.length; i++) {
				b1temp[i] = digits[i];
			}

			for (int i = 0; i < added.length - 1; i++) {
				added[i] = b2.digits[i] + b1temp[i];
			}
		}

		carry(added);

		BCD addedBCD = new BCD(added, true);
		return addedBCD;
	}

	/**
	 * Multiplies two BCDs together and returns the product.
	 */
	public BCD multiplyBCDs(BCD b2) {
		int[] result_array = new int[digits.length + b2.numberOfDigits() + 1];
		int carry = 0;

		for (int i = 0; i < b2.numberOfDigits(); i++) {
			int otherDigit = b2.digits[i];
			int result_index = i;
			carry = 0;
			for (int j = 0; j < digits.length; j++) {
				int thisDigit = digits[j];
				int result = (otherDigit * thisDigit) + carry;
				carry = 0;

				result += result_array[result_index];

				if (result > 9) {
					carry = result / 10;
					result %= 10;
				}
				result_array[result_index] = result;
				result_index++;

				if (carry > 0 && j == digits.length - 1) {
					result_array[result_index] = carry;
				}
			}
			carry = 0;
		}
		BCD addedBCD = new BCD(result_array, true);
		return addedBCD;
	}

	/**
	 * Multiplies a BCD by an integer and returns the product.
	 */
	public BCD multiplyBy(int factor) {
		if (factor < 0) {
			invalid("Factor is less than 0");
		}
		BCD factorBCD = new BCD(factor);

		BCD result = this.multiplyBCDs(factorBCD);
		return result;
	}

	/**
	 * Returns the factorial of the integer passed in.
	 */
	public static BCD factorial(int num) {
		BCD fac = new BCD(1);

		for (int i = 1; i <= num; i++) {
			BCD index = new BCD(i);
			fac = fac.multiplyBCDs(index);
		}

		return fac;
	}

	/**
	 * Exponentiates a BCD to the power of the value of the integer passed in.
	 * Returns the exponentiated BCD.
	 */
	public BCD pow(int pow) {
		BCD ans = new BCD(1);

		if (pow != 0) {
			for (int i = 1; i <= pow; i++) {
				ans = ans.multiplyBCDs(this);
			}
		}
		return ans;
	}

	/**
	 * Performs carries as part of the addBCDs method. Transfers values into the
	 * next place value when the initial value is greater than 10.
	 */
	public int[] carry(int[] uncarried) {
		for (int i = 0; i < uncarried.length; i++) {
			if (uncarried[i] >= 10) {
				uncarried[i + 1] += (uncarried[i] / 10);
				uncarried[i] %= 10;
			}
		}
		return uncarried;
	}

	/**
	 * Deletes first or last number in an array, depending on the boolean trimBack
	 * passed in. When trimBack is true, the last digit it trimmed, otherwise the
	 * first digit is trimmed. Used mainly for trimming leading zeroes.
	 */
	public int[] trim(int[] untrim, boolean trimBack) {
		int[] trimmed = new int[untrim.length - 1];

		if (trimBack) {
			for (int i = 0; i < untrim.length - 1; i++) {
				trimmed[i] = untrim[i];
			}
		} else {
			for (int i = 0; i < untrim.length - 1; i++) {
				trimmed[i] = untrim[i + 1];
			}
		}
		return trimmed;
	}

	public String toString() {
		String sNum = "";

		if (!valid) {
			return "Error!";
		} else {
			int offset = digits.length % 3;
			int counter = 0 - offset;

			for (int i = digits.length - 1; i >= 0; i--) {
				sNum += digits[i];
				counter++;

				if (counter % 3 == 0 && counter + offset != digits.length) {
					sNum += ",";
				}
			}
			return sNum;
		}
	}

	public static void main(String[] args) {
		validBCDs();
		invalidBCDs();
		addBCDs();
		multBCDs();
		factorialBCDs();
	}

	public static void validBCDs() {
		System.out.println("### validBCDs ###");

		int[] array0s = { 0, 0, 0 };
		BCD bcd = new BCD(array0s);
		System.out.println("Test #1 { 0, 0, 0 } bcd: " + bcd);
		System.out.println("Test #1 bcd.numberOfDigits: " + bcd.numberOfDigits());
		System.out.println("Test #1 bcd.nthDigit(0): " + bcd.nthDigit(0));

		int[] array = { 0, 0, 0, 1, 2, 3, 4, 0, 0, 0 };
		bcd = new BCD(array);
		System.out.println("Test #2 { 0, 0, 0, 1, 2, 3, 4, 0, 0, 0 } bcd: " + bcd);
		System.out.println("Test #2 bcd.numberOfDigits: " + bcd.numberOfDigits());
		System.out.println("Test #2 bcd.nthDigit(2): " + bcd.nthDigit(2));

		int numi = 2018;
		bcd = new BCD(numi);
		System.out.println("Test #3 2,018 bcd: " + bcd);
		System.out.println("Test #3 bcd.numberOfDigits: " + bcd.numberOfDigits());
		System.out.println("Test #3 bcd.nthDigit(3): " + bcd.nthDigit(3));
	}

	public static void invalidBCDs() {
		System.out.println("\n### invalidBCDs ###");

		System.out.println("Test #1 {2,0,1,8,22}");
		int[] array = { 2, 0, 1, 8, 22 };
		BCD bcd = new BCD(array);
		System.out.println("Test #1 {2,0,1,8,22} bcd: " + bcd);

		System.out.println("Test #2 {2,0,1,-8}");
		int[] arraybad = { 2, 0, 1, -8 };
		bcd = new BCD(arraybad);
		System.out.println("Test #2 {2,0,1,-8} bcd: " + bcd);

		System.out.println("Test #3 -2018");
		int numi = -2018;
		bcd = new BCD(numi);
		System.out.println("Test #3 -2018: bcd: " + bcd);

		System.out.println("Test #4 null");
		array = null;
		bcd = new BCD(array);
		System.out.println("Test #4 null bcd: " + bcd);
	}

	public static void addBCDs() {
		System.out.println("\n### addBCDs ###");

		int[] array = { 9, 9, 9 };
		BCD bcd = new BCD(array);
		System.out.println("Test #1 bcd: " + bcd);

		BCD sum = bcd.addBCDs(bcd);
		System.out.println("Test #1 bcd + bcd: " + sum);

		BCD bcd1 = new BCD(1);
		System.out.println("Test #2 bcd1: " + bcd1);

		sum = bcd.addBCDs(bcd1);
		System.out.println("Test #2 bcd + bcd1: " + sum);

		BCD bcd2 = new BCD(2);
		System.out.println("Test #3 bcd2: " + bcd1);

		sum = bcd2.addBCDs(bcd1);
		System.out.println("Test #3 bcd2 + bcd1: " + sum);
	}

	public static void multBCDs() {
		System.out.println("\n### multBCDs ###");

		BCD bcd1 = new BCD(222);
		BCD bcd2 = new BCD(66);
		BCD bcd3 = new BCD(0);
		BCD bcd4 = new BCD(9876543);
		BCD bcd5 = new BCD(1234567);

		BCD result = bcd2.multiplyBCDs(bcd1);
		System.out.println("Test #1 bcd2 * bcd1: " + result);

		result = bcd1.multiplyBCDs(bcd2);
		System.out.println("Test #2 bcd1 * bcd2: " + result);

		result = bcd3.multiplyBCDs(bcd2);
		System.out.println("Test #3 bcd3 * bcd2: " + result);

		result = bcd2.multiplyBCDs(bcd3);
		System.out.println("Test #4 bcd2 * bcd3: " + result);

		result = bcd4.multiplyBCDs(bcd5);
		System.out.println("Test #5 bcd4 * bcd5: " + result);

		result = bcd5.multiplyBCDs(bcd4);
		System.out.println("Test #6 bcd5 * bcd4: " + result);

		result = bcd5.multiplyBy(9876543);
		System.out.println("bcd5 * 987654: " + result);
	}

	public static void factorialBCDs() {
		System.out.println("\n### factorialBCDs ###");

		// BCD Test #1
		int[] list = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		BCD b1 = new BCD(list);
		BCD b2 = new BCD(654321);
		BCD result = b1.addBCDs(b2);
		System.out.println("Test #1: " + result);

		// Test #2
		// 987 ^ 34
		BCD bcd = new BCD(987);
		result = bcd.pow(34);
		System.out.println("Test #2: " + result);

		// Test #3
		// 98!
		result = BCD.factorial(98);
		System.out.println(result);
		System.out.print("Test #3: " + result);

		// Test #4
		// 52^52
		bcd = new BCD(52);
		result = bcd.pow(52);
		System.out.println("Test #4: " + result);

		// Test #5
		// 123!
		result = BCD.factorial(123);
		System.out.println("Test #5: " + result);
	}
}
