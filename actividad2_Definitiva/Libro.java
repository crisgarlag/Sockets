package actividad2_Definitiva;


/**
 * Representa un libro con sus distintos atributos.
 * @author cristiangarcialagar
 *
 */
public class Libro {
	
	private String ISBN;
	private String titulo;
	private String autor;
	private double precio;
	
	public Libro() {
		super();
	};
	
	public Libro(String ISBN, String titulo, String autor, double precio) {
		
		this.ISBN=ISBN;
		this.titulo=titulo;
		this.autor=autor;
		this.precio=precio;
	}
	

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Libro [ISBN=" + ISBN + ", titulo=" + titulo + ", autor=" + autor + ", precio=" + precio + "]";
	};
	

}
