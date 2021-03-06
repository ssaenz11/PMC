package Test;

import static java.lang.Math.random;
import static java.lang.Math.exp;
import java.util.Arrays;

public class Network {
	private final int NLayers;
	private final int[] layers;
	private final int maxLayer;
	private final double[][] biases;
	private final double[][][] weigth;

	public Network(int[] layers, double[][] biases, double[][][] weigth) {
		NLayers = layers.length;
		this.layers = layers;
		this.maxLayer = Arrays.stream(layers).max().getAsInt();
		this.biases = biases;
		this.weigth = weigth;
	}

	public Network(int... layers) {
		this.layers = layers;
		NLayers = layers.length;
		biases = new double[NLayers - 1][];
		weigth = new double[NLayers - 1][][];

		int len, prev = layers[0], maxLayer = layers[0];
		for (int i = 0; i < NLayers - 1; i++) {
			len = layers[i + 1];
			maxLayer = Integer.max(maxLayer, len);
			biases[i] = new double[len];
			weigth[i] = new double[len][prev];
			prev = len;

			for (int j = 0; j < biases[i].length; j++)
				biases[i][j] = random();

			for (int j = 0; j < weigth[i].length; j++)
				for (int k = 0; k < weigth[i][j].length; k++)
					weigth[i][j][k] = random();
		}
		this.maxLayer = maxLayer;
	}

	public double[] calculate(double[] array) {
		double cum;
		double[] w, prev = new double[maxLayer];
		double[] act = new double[maxLayer];
		System.arraycopy(array, 0, act, 0, array.length);

		for (int layer = 0; layer < NLayers - 1; layer++) {
			for (int i = 0; i < weigth[layer].length; i++) {
				cum = 0;
				w = weigth[layer][i];
				for (int j = 0; j < w.length; j++)
					cum += w[j] * prev[i];
				act[i] = 1 / (1 + exp(-cum - biases[layer][i]));
			}
			w = prev;
			prev = act;
			act = w;
		}
		double[] ans = new double[biases[NLayers - 2].length];
		System.arraycopy(prev, 0, ans, 0, ans.length);
		return ans;
	}

	public int[] getLayers() {
		return layers;
	}

	public double[][] getBiases() {
		return biases;
	}

	public double[][][] getWeigth() {
		return weigth;
	}
}
