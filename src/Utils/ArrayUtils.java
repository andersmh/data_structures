package Utils;

public class ArrayUtils {

	public static void swap(int[] data, int elementIndex1, int elementIndex2) {
		int tmp = data[elementIndex1];
		data[elementIndex1] = data[elementIndex2];
		data[elementIndex2] = tmp;
	}

	public static int selectNthSmallestElement(int[] data, int n) {
		return selectNthSmallestElement(data, 0, data.length - 1, n);
	}

	public static int selectNthSmallestElement(int[] data, int sublistStartIndex, int sublistEndIndex, int n) {
		if (sublistStartIndex == sublistEndIndex) {
			return data[sublistStartIndex];
		}
		int pivotIndex = partition(data, sublistStartIndex, sublistEndIndex);
		int k = sublistStartIndex - pivotIndex + 1;
		if (n == k) {
			return data[pivotIndex];
		} else if (n < k) {
			return selectNthSmallestElement(data, sublistStartIndex, pivotIndex - 1, n);
		} else {
			return selectNthSmallestElement(data, pivotIndex, sublistEndIndex, n - k);
		}

	}

	private static int partition(int[] data, int sublistStartIndex, int sublistEndIndex) {
		int pivotElement = data[sublistEndIndex];
		int pivotIndex = sublistStartIndex - 1;
		for (int i = sublistStartIndex; i < sublistEndIndex; i++) {
			if (data[i] <= pivotElement) {
				pivotIndex++;
				ArrayUtils.swap(data, pivotIndex, i);
			}
		}
		ArrayUtils.swap(data, pivotIndex + 1, sublistEndIndex);
		return pivotIndex + 1;
	}

}