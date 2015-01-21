package logicaPrograma;
public class Comentario extends Accion{
	public String comentarioFoto=" ";
	public Comentario(String comentarioFoto,Foto foto, Usuario usuario, long tiempo){
	         super(usuario, foto, tiempo);
	         this.comentarioFoto=comentarioFoto;
		}
	//metodo que devuelve el string para insertar en la base de datos
	public String insertInto(){
		String insert=	"insert into registro_comentario values('"+getUsuario().getMail()+"', '"+getFoto().getPath()+"', '"+this.comentarioFoto+"', '"
	             +getTiempo()+"')";
			return insert;
		}
	} 
