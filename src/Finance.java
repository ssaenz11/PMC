/**
 * Recuerde que este programa no funciona con el wifi de la universidad de estar conectado por lan para probarlo o intentar con 
 * el internet de m�s privado como el de una casa
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;





public class Finance {

	HashSet<String> hrefs;

	private int contador;

	private List<App> listaApps;

	public Finance() throws InterruptedException {

		

		//Lista en donde se guardar� le informaci�n de los apps(v�ase clase App)
		listaApps = new ArrayList<App>();

		//Contador de las aplicaciones
		contador =0;

		// son los documentos que guardaran la informaici�n de los HTML 
		Document[] arregloDocumento= new Document[3];


		try {
			// los links de los cuales vamos a extraer la informaci�n de los Html . Son varios porque google play no permite extarer la informaci�n de una sola vez 
			// la informaci�n "start=0&num=60" de la url parmite agrupar las apps , en este caso start implica que se emplieza desde la app 0 y num indica la cantidad de elementos 
			// que se pueden obtener (usualemtne 60 es lo m�ximo que permite)
			
			int start= 0;
			int num = 120;
			boolean entro = true;
			for (int i = 0; i<arregloDocumento.length; i++) {

			if (entro){
				arregloDocumento[i] =  (Document) Jsoup.connect ("https://play.google.com/store/apps/category/FINANCE?start=0&num=100").timeout(0).maxBodySize(0).get();
				entro = false;
			}
				
				arregloDocumento[i] =  (Document) Jsoup.connect ("https://play.google.com/store/apps/category/FINANCE/collection/topselling_free?start="+start+"&num="+num).timeout(0).maxBodySize(0).get();
				start +=60;
				
					
				
			}
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i<arregloDocumento.length; i++) {

			decodificarYGuardar(arregloDocumento[i]);
		}

	}

	public void decodificarYGuardar(Document doc) {

		hrefs = new HashSet<String>();

		Document detailDoc = null;

		Elements anchors = doc.getElementsByClass("card-click-target");
		for(Element element: anchors) {

			hrefs.add("https://play.google.com/"+ element.attr("href").toString());
		}

		String nombre = null;
		String numeroRatings = null;
		String ratingPromedio = null;
		String cambiosRecientes = null;
		String descripcion = null;


		for(String url: hrefs) {
			try {

				detailDoc = Jsoup.connect(url).timeout(0).get();
				numeroRatings= detailDoc.select("[class=\"rating-count\"]").text().replaceAll(",", "");
				nombre= detailDoc.select("[class=\"id-app-title\"]").text();
				double numeroRatings2 =Double.parseDouble(numeroRatings);
				ratingPromedio= detailDoc.select("[class=\"score\"]").text().replaceAll(",", ".");
				double ratingPromedio2 = Double.parseDouble(ratingPromedio);
				descripcion= detailDoc.select("[class=\"description\"]").text();
				cambiosRecientes= detailDoc.select("[class=\"recent-change\"]").text();
				App app = new App(contador, nombre, numeroRatings2, ratingPromedio2, descripcion, cambiosRecientes);
				contador++;

				listaApps.add(app);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


	}

	public void darInfoPlayStore() {

		for(int i = 0; i<listaApps.size();i++) {

			System.out.println(listaApps.get(i).getId()+ ". "+ listaApps.get(i).getNombre());
			System.out.println("--------------------------------------------------------------------------");

		}
	}
	public void darInfoApp(int i) {
		System.out.println("--------------------------------------------------------------------------");
		System.out.println(listaApps.get(i).getId()+ ". "+ listaApps.get(i).getNombre());
		System.out.println("N�mero de ratings : "+listaApps.get(i).getNumeroRatings() );
		System.out.println("Rating promedio : "+ listaApps.get(i).getRatingPromedio());
		System.out.println("Descripcion : "+ listaApps.get(i).getDescripcion());
		System.out.println("Camcios recientes : "+ listaApps.get(i).getCambiosRecientes());
		System.out.println("--------------------------------------------------------------------------");
	}
	
	public List<App> darLista(){
		
		return listaApps;
	}





	public static void main (String[] args) throws InterruptedException {

		Finance finance = new Finance();

//		System.out.println("Qu� informaci�n desea de la PlayStore , Categor�a Finanzas");
//		System.out.println("1. Lista de las aplicaciones con toda su informaci�n(escriba 1)");
//
//
//		Scanner reader = new Scanner(System.in);
//		int n = reader.nextInt(); // Scans the next token of the input as an int.
//		//once finished
//
//
//		if (n == 1) {
//			finance.darInfoPlayStore();
//			System.out.println("2. Dar info de una aplicaci�n(escriba el id de la apliciaci�n)");
//			Scanner reader1 = new Scanner(System.in);
//			int x = reader1.nextInt(); // Scans the next token of the input as an int.
//			//once finished
//			reader.close(); 
//			finance.darInfoApp(x);
//
//		}

		
		Gson gson = new Gson();
        String jsonNames = gson.toJson(finance.darLista());
        System.out.println("jsonNames = " + jsonNames);







	}

}
