package kmeans;

import static org.junit.Assert.*;
import org.junit.Test;

public class KmeansTest2 {

	private static final int NUM_CLUSTERS = 5; // Total clusters.
	private static final int SAMPLES[] = new int[] { 1, 2, 3, 6, 7, 9, 11, 22, 44, 50 };

	@Test
	public void testPrepare_data() {
		int actuals[] = new int[SAMPLES.length];
		Kmeans kMeans = new Kmeans(NUM_CLUSTERS, SAMPLES);
		actuals = kMeans.prepare_data(SAMPLES);
		kMeans.printArray(actuals);

		int expecteds[] = new int[] { 2, 3, 0, 1, 7, 4, 5, 6, 8, 9 };
		assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	public void testGetCentroid_arr() {
		int actuals[] = new int[NUM_CLUSTERS];
		Kmeans kMeans = new Kmeans(NUM_CLUSTERS, SAMPLES);
		actuals = kMeans.arr_sort_asc(kMeans.getCentroid_arr()[1]);
		kMeans.printArray(actuals);

		int expecteds[] = new int[] { 2, 8, 22, 44, 50 };
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testPrinting() {
		int actuals[] = new int[NUM_CLUSTERS];
		int centroided_arr[][] = new int[NUM_CLUSTERS][SAMPLES.length];
		Kmeans kMeans = new Kmeans(NUM_CLUSTERS, SAMPLES);

		centroided_arr = kMeans.getCentroid_arr();
		actuals = kMeans.printing(centroided_arr);		
		kMeans.printArray(actuals);

		int expecteds[] = new int[] { 2, 9, 22, 44, 50 };
		assertArrayEquals(expecteds, actuals);
	}

}
