
public class Libro {
	private int isbn;
	private String nombre;
	private String autor;
	private int precio;
	
	public Libro(int isbn, String nombre, String autor, int precio) {
		super();
		this.isbn = isbn;
		this.nombre = nombre;
		this.autor = autor;
		this.precio = precio;
	}
	
	public Libro() {
		super();
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Libro [isbn=" + isbn + ", nombre=" + nombre + ", autor=" + autor + ", precio=" + precio + "]";
	}
	
	
	
}
