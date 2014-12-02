import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JFrame;


public class Sesion extends JFrame {
	private Usuario usuario;
	private ArrayList<Foto> fotosVisibles;
	private ConexionBaseDeDatos a=new ConexionBaseDeDatos();
	private boolean peticionOamigo=false;
	public  Sesion(Usuario usuario){
		this.setUsuario(usuario);
		//hay que cargar las fotos visibles
		usuario.setFotos(a.obtenerFotos(usuario.getMail()));
	}
	//VAMOS A HACER CON EXCEPCIONES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public void comentar(String comentario,Foto foto){
		HashSet <String> a=new HashSet<String>();
		for (int i=0;i<foto.getPalabrasProhibidas().length;i++){
			a.add(foto.getPalabrasProhibidas()[i]);
		}
		String coment[] = comentario.split(" ");
		for(String palabra : coment){
			if(a.contains(palabra)){
				System.out.println("comentario no valido");
				return;
			}
		}
		//Si ha llegado hasta este punto es que el comentario es valido
		Comentario comentario1=new Comentario(comentario,foto,this.getUsuario(),System.currentTimeMillis());
		BaseDeDatos.insertInto(comentario1.insertInto());
		foto.getComentarios().add(comentario1);
	}
	public void peticionEnviadaOAmigo(Usuario amigo){
		if(a.peticionEnviada(this.usuario.getMail(), amigo.getMail())){
			peticionOamigo=true;
		}
		else{
			peticionOamigo=false;
		}
	}
	public void pedirAmigo(Usuario amigo){
		peticionEnviadaOAmigo(amigo);
		if(peticionOamigo){
			System.out.println("Los usuarios ya son amigos, o la peticion esta enviada");
		}else{
			System.out.println("Has enviado peticion");
		String insert="insert into amigos values('"+this.getUsuario().getMail()+"', '"+amigo.getMail()+"', '"+false+"', '"
				+System.currentTimeMillis()+"')";
		BaseDeDatos.insertInto(insert);
		}
	}
	public void aceptarAmigo(Usuario amigo){
		String actualizar="update amigos set "+""+ "confirmado ='"+true+"',"+ "fecha ='"+System.currentTimeMillis()+"'"
				+ "where ((mail2 = '"+this.getUsuario().getMail()+"')&(mail1 = '"+amigo.getMail()+"'))";
		BaseDeDatos.insertInto(actualizar);
		this.getUsuario().anyadirAmigo(amigo);
		//HABRÍA QUE MIRAR SI ESTÁ CONECTADO EL AMIGO, SI ESTÁ CONECTADO DE SE LE AÑADE A LA LISTA
		//SI NO, SE LE AÑADIRA CUANDO SE CONECTE
	}
	public void meterFoto(Foto foto){
		BaseDeDatos.insertInto(foto.insertInto());
		getUsuario().anyadirFoto(foto);
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
