package Test;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Network r = new Network(50, 100, 200, 100, 50);
		NetworkPersistenceManager npm = new NetworkPersistenceManager(
				"." + File.separator + "data" + File.separator + "model" + ".DATA");
		npm.save(r);
		r = npm.load();
	}
}
