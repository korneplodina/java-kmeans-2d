package kmeans;

import java.util.*;
import java.util.Map.Entry;

public class Kmeans {

	private int NUM_CLUSTERS; // Total clusters.

	private int SAMPLES[];

	private int cluster[];

	private int centroid_arr[][];

	Kmeans(int nclusters, int[] samples) {

		this.SAMPLES = new int[samples.length];
		this.cluster = new int[samples.length];
		this.NUM_CLUSTERS = nclusters;
		this.SAMPLES = samples;

		int maxDiffData_arr[] = new int[this.NUM_CLUSTERS];

		maxDiffData_arr = prepare_data(this.SAMPLES);

		this.centroid_arr = new int[2][this.NUM_CLUSTERS];

		for (int i = 0; i < this.NUM_CLUSTERS; i++) {
			this.centroid_arr[0][i] = 0;
			this.centroid_arr[1][i] = this.SAMPLES[maxDiffData_arr[i]];
		}

	}

	public static int[] arr_sort_desc(int[] arr) {
		int tmp;
		for (int k = 0; k < arr.length; k++) {
			for (int j = k + 1; j < arr.length; j++) {
				if (arr[k] < arr[j]) {
					tmp = arr[k];
					arr[k] = arr[j];
					arr[j] = tmp;
				}
			}
		}
		return arr;
	}

	public static int[] arr_sort_asc(int[] arr) {
		int tmp;
		for (int k = 0; k < arr.length; k++) {
			for (int j = k + 1; j < arr.length; j++) {
				if (arr[k] > arr[j]) {
					tmp = arr[k];
					arr[k] = arr[j];
					arr[j] = tmp;
				}
			}
		}
		return arr;
	}

	public static int FindSmallest(int[] arr1) {
		int index = 0;
		int min = arr1[index];
		for (int i = 1; i < arr1.length; i++) {
			if (arr1[i] < min) {
				min = arr1[i];
				index = i;
			}
		}
		return index;
	}

	public int[][] getCentroid_arr() {
		int distance[][] = new int[this.NUM_CLUSTERS][this.SAMPLES.length];
		int clusternodecount[] = new int[this.NUM_CLUSTERS];
		this.centroid_arr[0] = this.centroid_arr[1];
		this.centroid_arr[1] = new int[this.NUM_CLUSTERS];	

		for (int i = 0; i < this.NUM_CLUSTERS; i++) {
			this.centroid_arr[1][i] = 0;
		}

		for (int i = 0; i < this.NUM_CLUSTERS; i++) {
			for (int j = 0; j < this.SAMPLES.length; j++) {
				distance[i][j] = Math.abs(this.SAMPLES[j] - this.centroid_arr[0][i]);
			}
		}

		int smallerDistance = 0;
		int[][] numbers = new int[this.SAMPLES.length][this.NUM_CLUSTERS];

		for (int i = 0; i < this.SAMPLES.length; i++) {
			for (int j = 0; j < this.NUM_CLUSTERS; j++) {
				numbers[i][j] = distance[j][i];
			}
			smallerDistance = FindSmallest(numbers[i]);
			this.centroid_arr[1][smallerDistance] = this.centroid_arr[1][smallerDistance] + this.SAMPLES[i];
			clusternodecount[smallerDistance] = clusternodecount[smallerDistance] + 1;
			this.cluster[i] = smallerDistance;
		}

		for (int k = 0; k < this.NUM_CLUSTERS; k++) {
			this.centroid_arr[1][k] = this.centroid_arr[1][k] / clusternodecount[k];
		}

		boolean isAchived = true;
		for (int j = 0; j < this.NUM_CLUSTERS; j++) {
			if (isAchived && this.centroid_arr[0][j] == this.centroid_arr[1][j]) {
				isAchived = true;
				continue;
			}
			isAchived = false;
		}

		if (!isAchived) {
			this.centroid_arr = this.getCentroid_arr();
		}

		return this.centroid_arr;
	}

	public int[] printing(int[][] centroidArr) {
		int clusterelements[][] = new int[this.NUM_CLUSTERS][this.SAMPLES.length];
		int smallerDistance = 0;
		for (int i = 0; i < this.SAMPLES.length; i++) {
			for (int j = 0; j < this.NUM_CLUSTERS; j++) {
				if (j == this.cluster[i])
					clusterelements[j][i] = this.SAMPLES[i];
			}
		}

		int distTocentroid_arr[][] = new int[this.NUM_CLUSTERS][this.SAMPLES.length];
		for (int i = 0; i < this.NUM_CLUSTERS; i++) {
			for (int j = 0; j < this.SAMPLES.length; j++) {
				distTocentroid_arr[i][j] = Math.abs(centroidArr[1][i]
						- clusterelements[i][j]);
			}
		}

		int sorted_distTocentroid_arr[] = new int[this.NUM_CLUSTERS];
		for (int i = 0; i < this.NUM_CLUSTERS; i++) {
			smallerDistance = FindSmallest(distTocentroid_arr[i]);
			sorted_distTocentroid_arr[i] = clusterelements[i][smallerDistance];
		}
		return arr_sort_asc(sorted_distTocentroid_arr);
	}

	public int[] prepare_data(int[] arr) {
		int sorted_arr[] = arr_sort_desc(this.SAMPLES);
		int maxDiff_arr[] = new int[this.SAMPLES.length];
		int sorted_diff[] = new int[this.SAMPLES.length];

		maxDiff_arr[0] = Math.abs(sorted_arr[0] - sorted_arr[1]);

		for (int i = 1; i < this.SAMPLES.length; i++) {
			maxDiff_arr[i] = Math.abs(sorted_arr[i] - sorted_arr[i - 1]);
		}

		Map<Integer, Integer> m1 = new HashMap<Integer, Integer>();
		int k = 0;
		for (int i : maxDiff_arr) {
			m1.put(k++, i);
		}
		m1 = sortByValues(m1);

		int i = 0;
		for (int key : m1.keySet()) {
			sorted_diff[i++] = key;
		}
		return sorted_diff;

	}

	public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(
			Map<K, V> map) {
		List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		Map<K, V> sortedMap = new LinkedHashMap<K, V>();

		for (Map.Entry<K, V> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
		System.out.println();
	}
}