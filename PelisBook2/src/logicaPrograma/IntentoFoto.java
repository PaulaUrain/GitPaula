package logicaPrograma;

public class IntentoFoto extends Accion{
	private boolean adivinado=false;
	private int numIntento=0;
	public IntentoFoto(Usuario usuario, Foto foto,long tiempo, boolean adivinado,int numIntento) {
		super(usuario, foto, tiempo);
		// TODO Auto-generated constructor stub
		this.setAdivinado(adivinado);
		this.setNumIntento(numIntento);
	}
	public String insertInto(){
		String insert=	"insert into adivinar values('"+getUsuario().getMail()+"', '"+getFoto().getPath()+"', '"+this.isAdivinado()+"',"
				+"'"+getNumIntento()+"','"+getTiempo()+"')";
			return insert;
		}
	public boolean isAdivinado() {
		return adivinado;
	}
	public void setAdivinado(boolean adivinado) {
		this.adivinado = adivinado;
	}
	public int getNumIntento() {
		return numIntento;
	}
	public void setNumIntento(int numIntento) {
		this.numIntento = numIntento;
	}
}
