
public class App {
	
	
	private String apk;
	private int id;
	
	private String nombre;
	
	private double numeroRatings;
	
	private double ratingPromedio;
	
	private String descripcion;
	
	private String CambiosRecientes;
	
	public App(int id2, String nombre2, double numeroRatings2, double ratingPromedio2
			, String descripcion2, String cambiosRecientes2, String apk2) {
		
		id = id2;
		nombre = nombre2;
		numeroRatings = numeroRatings2;
		ratingPromedio = ratingPromedio2;
		descripcion = descripcion2;
		CambiosRecientes = cambiosRecientes2; 
		apk = apk2;
		
		
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getNumeroRatings() {
		return numeroRatings;
	}

	public void setNumeroRatings(double numeroRatings) {
		this.numeroRatings = numeroRatings;
	}

	public double getRatingPromedio() {
		return ratingPromedio;
	}

	public void setRatingPromedio(double ratingPromedio) {
		this.ratingPromedio = ratingPromedio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCambiosRecientes() {
		return CambiosRecientes;
	}

	public void setCambiosRecientes(String cambiosRecientes) {
		CambiosRecientes = cambiosRecientes;
	}

	public String getApk() {
		return apk;
	}

	public void setApk(String apk) {
		this.apk = apk;
	}
	
	

}
