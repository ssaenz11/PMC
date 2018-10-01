import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Viajes {

	HashSet<String> hrefs;

	private int contador;

	private  List<Hoteles> listaHoteles;

	public Viajes() throws InterruptedException {



		//Lista en donde se guardar� le informaci�n de los apps(v�ase clase App)
		listaHoteles = new ArrayList<Hoteles>();

		//Contador de las aplicaciones
		contador =0;

		// son los documentos que guardaran la informaici�n de los HTML 
		Document arregloDocumento = null;


		try {
			// la informaci?n "start=0&num=60" de la url parmite agrupar las apps , en este caso start implica que se emplieza desde la app 0 y num indica la cantidad de elementos 
			// que se pueden obtener (usualemtne 60 es lo m?ximo que permite)


			arregloDocumento =  (Document) Jsoup.connect ("https://www.tripadvisor.co/Hotels").timeout(0).maxBodySize(0).get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		decodificarYGuardar(arregloDocumento);

	}


	//Método que decodifica la información del documento HTML
	public void decodificarYGuardar(Document doc) {

		hrefs = new HashSet<String>();

		Document detailDoc = null;

		Elements anchors = doc.getElementsByClass("item");

		for(Element element: anchors) {


			hrefs.add("https://www.tripadvisor.co/Hotels/" +element.attr("href").toString());
		}



		for(String url: hrefs) {
			try {
				System.out.println(url);

				HashSet<String> hash2 = new HashSet<String>();




				detailDoc = Jsoup.connect(url).timeout(0).get();

				Document detailDoc2 = null;

				Elements anchor1 = detailDoc.getElementsByClass("review_count");

				String nombre = null;
				String ubicacion = null;
				String numeroRatings = null; 
				String estrellas = null;
				String comentarios = null;
				String direccion = null;

				for(Element element: anchor1){

					hash2.add("https://www.tripadvisor.co" +element.attr("href").toString());



					for(String url1:hash2){

						System.out.println(url1);

						detailDoc2 = Jsoup.connect(url1).timeout(0).get();

						nombre = detailDoc2.getElementsByClass("subtitle__subtitle--eEauT").text();

						ubicacion = detailDoc2.getElementsByClass("locality").text();

						numeroRatings = detailDoc2.getElementsByClass("reviewCount").text();

						estrellas = detailDoc2.getElementsByClass("overallRating").text();

						comentarios= detailDoc2.getElementsByClass("partial_entry").text();

						direccion = detailDoc2.getElementsByClass("street-address").text();

						Hoteles hot = new Hoteles(ubicacion, nombre, direccion, numeroRatings, estrellas, comentarios);

						listaHoteles.add(hot);
						System.out.println("i");
						System.out.println(nombre);
						System.out.println(ubicacion);
						System.out.println(numeroRatings);
						System.out.println(estrellas);
						System.out.println(comentarios);
						System.out.println(direccion);


					}
				}



			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


	}







	public List<Hoteles> getListaApps() {
		return listaHoteles;
	}

	public void setListaApps(List<Hoteles> listaApps) {
		this.listaHoteles = listaApps;
	}

	public static void main (String[] args) throws InterruptedException, IOException {

		Viajes vi = new Viajes();

		Gson gson = new Gson();
		String txt = gson.toJson(vi.getListaApps());

		BufferedWriter output = null;
		try {
			File file = new File("example.txt");
			output = new BufferedWriter(new FileWriter(file));
			output.write(txt);
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			if ( output != null ) {
				output.close();
			}
		}

	}

}