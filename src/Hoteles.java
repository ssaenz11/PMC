import java.util.ArrayList;

public class Hoteles {

	private String categoria;
	
	private String nombreHotel;

	private String ubicacion;

	private String numeroRatings;

	private String estrellas;

	private String descripciones;





	public Hoteles(String cCategoria ,String nNombreHotel,String uUbicacion, String numeroRatings2, String eEstrellas
			, String descripcion2) 
	{

		setCategoria(cCategoria);
		setNombreHotel(nNombreHotel);
		setUbicacion(uUbicacion);
		setNumeroRatings(numeroRatings2);
		setEstrellas(eEstrellas);
		setDescripciones(descripcion2);
		




	}



	public String getNombreHotel() {
		return nombreHotel;
	}



	public void setNombreHotel(String nombreHotel) {
		this.nombreHotel = nombreHotel;
	}



	public String getUbicacion() {
		return ubicacion;
	}



	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}



	public String getNumeroRatings() {
		return numeroRatings;
	}



	public void setNumeroRatings(String numeroRatings) {
		this.numeroRatings = numeroRatings;
	}



	public String getEstrellas() {
		return estrellas;
	}



	public void setEstrellas(String estrellas) {
		this.estrellas = estrellas;
	}



	public String getDescripciones() {
		return descripciones;
	}



	public void setDescripciones(String descripcion2) {
		this.descripciones = descripcion2;
	}






	public String getCategoria() {
		return categoria;
	}



	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}





}
