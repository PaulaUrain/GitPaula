
public class IntentoFoto extends Accion{

	private boolean adivinado=false;
	private int numIntento=0;
	public IntentoFoto(Usuario usuario, Foto foto,long tiempo, boolean adivinado) {
		super(usuario, foto, tiempo);
		// TODO Auto-generated constructor stub
		this.adivinado=adivinado;
		numIntento++;
	}
	public String insertInto(){
		String insert=	"insert into adivinar values('"+getUsuario().getMail()+"', '"+getFoto().getPath()+"', '"+this.adivinado+"', '"+
				getTiempo()+"')";
			return insert;
		}
}
