package dlmu.mislab.tool;

import java.util.List;

public final class BinaryObjSearch<T extends Comparable<T>> {
	private BinaryObjSearch() {
	};

	// public static BinaryObjSearch<?> getInstance() {
	// return new BinaryObjSearch();
	// }

	public Comparable<T> searchObj(T[] dataset, T data) {
		int beginIndex = 0;
		int endIndex = dataset.length - 1;
		int midIndex = -1;

		if (data.compareTo(dataset[beginIndex]) < 0
				|| data.compareTo(dataset[endIndex]) > 0
				|| beginIndex > endIndex) {
			return null;
		}
		while (beginIndex <= endIndex) {
			midIndex = (beginIndex / 2 + endIndex / 2);
			if (data.compareTo(dataset[midIndex]) < 0) {
				endIndex = midIndex - 1;
			} else if (data.compareTo(dataset[midIndex]) > 0) {
				beginIndex = midIndex + 1;
			} else {
				return dataset[midIndex];
			}
		}
		return null;
	}

	public Comparable<T> searchObj(List<T> dataset, T data) {
		int beginIndex = 0;
		int endIndex = dataset.size() - 1;
		int midIndex = -1;

		if (data.compareTo(dataset.get(beginIndex)) < 0
				|| data.compareTo(dataset.get(endIndex)) > 0
				|| beginIndex > endIndex) {
			return null;
		}
		while (beginIndex <= endIndex) {
			midIndex = (beginIndex / 2 + endIndex / 2);
			if (data.compareTo(dataset.get(midIndex)) < 0) {
				endIndex = midIndex - 1;
			} else if (data.compareTo(dataset.get(midIndex)) > 0) {
				beginIndex = midIndex + 1;
			} else {
				return dataset.get(midIndex);
			}
		}
		return null;
	}
}
