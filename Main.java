package kmeans;

public class Main {
	public static void main(String[] args) {
		int NUM_CLUSTERS = 5; // Total clusters.
		int SAMPLES[] = new int[] { 1, 2, 3, 6, 7, 9, 11, 22, 44, 50 };

		int centroided_arr[][] = new int[NUM_CLUSTERS][SAMPLES.length];
		Kmeans kMeans = new Kmeans(NUM_CLUSTERS, SAMPLES);
		centroided_arr = kMeans.getCentroid_arr();
		
		int[] arr_sort_val_point = kMeans.printing(centroided_arr);
	
		kMeans.printArray(centroided_arr[1]);
		kMeans.printArray(arr_sort_val_point);
	}
}