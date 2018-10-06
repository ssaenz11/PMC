package Test;

import static java.nio.ByteBuffer.wrap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class NetworkPersistenceManager {
	private final String path;

	public NetworkPersistenceManager(String path) {
		this.path = path;
	}

	public void save(Network network) throws IOException {
		try (FileOutputStream f = new FileOutputStream(path)) {
			save(f, network);
			f.flush();
		}
	}

	public Network load() throws IOException {
		try (FileInputStream s = new FileInputStream(path)) {
			return load(s);
		}
	}

	private static void save(OutputStream outputStream, Network network) throws IOException {
		final int[] layers = network.getLayers();
		final int NLayers = layers.length;
		final double[][] biases = network.getBiases();
		final double[][][] weigth = network.getWeigth();

		ByteBuffer ints = wrap(new byte[4 + (NLayers << 2)]);
		ints.putInt(NLayers);
		int maxLayer = 0;
		for (int i : layers) {
			maxLayer = Integer.max(maxLayer, i);
			ints.putInt(i);
		}
		outputStream.write(ints.array());
		outputStream.flush();

		ints = wrap(new byte[maxLayer << 3]);
		for (int i = 0; i < NLayers - 1; i++) {
			for (int j = 0; j < biases[i].length; j++)
				ints.putDouble(biases[i][j]);
			outputStream.write(ints.array(), 0, biases[i].length << 3);
			ints.clear();
		}
		outputStream.flush();

		for (int i = 0; i < NLayers - 1; i++) {
			for (int j = 0; j < weigth[i].length; j++) {
				ints.clear();

				for (int k = 0; k < weigth[i][j].length; k++)
					ints.putDouble(weigth[i][j][k]);
				outputStream.write(ints.array(), 0, weigth[i][j].length << 3);
			}
		}
		outputStream.flush();
	}

	private static Network load(InputStream inputStream) throws IOException {
		final int[] layers;
		final int NLayers;
		final double[][] biases;
		final double[][][] weigth;

		byte[] ints = new byte[4];
		inputStream.read(ints);
		ByteBuffer bb = wrap(ints);
		NLayers = bb.getInt();

		layers = new int[NLayers];
		ints = new byte[NLayers << 2];
		inputStream.read(ints);
		bb = wrap(ints);
		for (int i = 0; i < NLayers; i++)
			layers[i] = bb.getInt();
		int maxLayer = 0;
		for (int i = 0; i < NLayers; i++)
			maxLayer = Integer.max(maxLayer, layers[i]);

		biases = new double[NLayers - 1][];
		ints = new byte[maxLayer << 3];
		int len;
		for (int i = 0; i < NLayers - 1; i++) {
			len = layers[i + 1];
			biases[i] = new double[len];
			inputStream.read(ints, 0, len << 3);
			bb = wrap(ints);

			for (int j = 0; j < len; j++)
				biases[i][j] = bb.getDouble();
		}

		weigth = new double[NLayers - 1][][];
		for (int i = 0; i < NLayers - 1; i++) {
			weigth[i] = new double[layers[i + 1]][layers[i]];

			for (int j = 0; j < weigth[i].length; j++) {
				inputStream.read(ints, 0, weigth[i][j].length << 3);
				bb = wrap(ints);
				for (int k = 0; k < weigth[i][j].length; k++)
					weigth[i][j][k] = bb.getDouble();
			}
		}

		return new Network(layers, biases, weigth);
	}
}
