package logicaPrograma;
public abstract class Accion {
	private Usuario usuario;
	private Foto foto;
	private long tiempo;
	public Accion(Usuario usuario, Foto foto, long tiempo){
		this.usuario=usuario;
		this.foto=foto;
		this.tiempo=tiempo;
	};

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Foto getFoto() {
		return foto;
	}
	public float getTiempo() {
		return tiempo;
	}
}
