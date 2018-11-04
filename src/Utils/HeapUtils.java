package Utils;

public class HeapUtils {

	public static void buildMaxHeap(int[] data) {
		for (int i = data.length / 2; i >= 0; i--) {
			maxHeapify(data, i);
		}
	}

	public static void maxHeapify(int[] data, int index) {
		maxHeapify(data, index, data.length);
	}

	public static void maxHeapify(int[] data, int index, int heapSize) {
		int leftLeaf = getLeftLeaf(index);
		int rightLeaf = getRightLeaf(index);
		int largest = index;
		if (leftLeaf < heapSize && data[leftLeaf] > data[largest]) {
			largest = leftLeaf;
		}
		if (rightLeaf < heapSize && data[rightLeaf] > data[largest]) {
			largest = rightLeaf;
		}
		if (largest != index) {
			ArrayUtils.swap(data, index, largest);
			HeapUtils.maxHeapify(data, largest, heapSize);
		}
	}

	public static int getParent(int leafIndex) {
		return leafIndex / 2;
	}

	private static int getLeftLeaf(int parentIndex) {
		return 2 * parentIndex + 1;
	}

	private static int getRightLeaf(int parentIndex) {
		return 2 * parentIndex + 2;
	}
}