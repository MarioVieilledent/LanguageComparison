package java;

import java.util.Arrays;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static final int WIDTH = 2_000;
	public static final int HEIGHT = 2_000;

	public static void main(String[] args) {
		long start = System.nanoTime();
		int[][] plot = Main.mandelbrot();
		long elapsed = System.nanoTime() - start;
		System.out.println("Time elapsed - Writing file: " + elapsed);

		start = System.nanoTime();
		Main.saveResults(plot);
		elapsed = System.nanoTime() - start;
		System.out.println("Time elapsed - Writing file: " + elapsed);
	}

	public static int[][] mandelbrot() {
		int MAX_ITER = 1000;

		double X_SCALE_MIN = -2.0;
		double X_SCALE_MAX = 1.0;
		double Y_SCALE_MIN = -1.5;
		double Y_SCALE_MAX = 1.5;

		double X_SLOPE = (X_SCALE_MAX - X_SCALE_MIN) / (double) Main.WIDTH;
		double Y_SLOPE = (Y_SCALE_MAX - Y_SCALE_MIN) / (double) Main.HEIGHT;

		System.out.println(X_SLOPE);
		System.out.println(Y_SLOPE);

		int[][] plot = {};
		Arrays.fill(plot, 0);

		for (int py = 0; py < (int) Main.HEIGHT; py++) {
			for (int px = 0; px < (int) Main.WIDTH; px++) {
				double x0 = X_SLOPE * (double) px + X_SCALE_MIN;
				double y0 = Y_SLOPE * (double) py + Y_SCALE_MIN;
				double x = 0.0;
				double y = 0.0;
				int iteration = 0;

				while (x * x + y * y <= 4.0 && iteration < MAX_ITER) {
					double x_temp = x * x - y * y + x0;
					y = 2.0 * x * y + y0;
					x = x_temp;
					iteration++;
				}

				plot[py][px] = iteration;
			}
		}
		return plot;
	}

	public static void saveResults(int[][] plot) {
		Gson gson = new Gson();
		String data = gson.toJson(plot);
		try {
			FileWriter myWriter = new FileWriter("../display/mandelbrot/java.json");
			myWriter.write(data);
			myWriter.close();
		} catch (IOException e) {
			System.out.print("Error writing the file: ");
			e.printStackTrace();
		}
	}

}
