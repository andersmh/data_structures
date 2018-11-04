package Utils;

public class MathUtils {

	public static int getDigitFromNumber(int number, int digitIndex) {

		return number / (int) Math.pow(10, digitIndex) % 10;
	}

	public static int getMax(int first, int second) {
		return first > second ? first : second;
	}

	public static double logarithm(double base, double a) {
		return Math.log(a) / Math.log(base);
	}

}