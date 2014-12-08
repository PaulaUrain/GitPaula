package logicaPrograma;
import java.util.HashSet;

public class Sesion {
	private Usuario usuario;
	//private ArrayList<Foto> fotosVisibles;
	private ConexionBaseDeDatos a=new ConexionBaseDeDatos();
	private boolean peticionOamigo=false;
	public  Sesion(Usuario usuario){
		this.setUsuario(usuario);
		usuario.setIntentos((a.obtenerIntentosFotos(usuario)));
		usuario.setAmigos(a.obtenerAmigos(usuario.getMail()));
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
	//true es si ha adivinado el título, y false si no. Hacer excepcion por si la foto ya está adivinada
	public boolean adivinarFoto(Foto foto, String tituloUsuario){
		boolean acertada=false;
		boolean aparece=false;
		int posicionArrayFoto=0;
		for(int i=0;i<usuario.getIntentos().size();i++){
			if(usuario.getIntentos().get(i).getFoto().getPath().equals(foto.getPath())){
				acertada=usuario.getIntentos().get(i).isAdivinado();
				posicionArrayFoto=i;
				aparece=true;
			}
		}
		if(!acertada){
			if(foto.getTituloFoto().equals(tituloUsuario)){
				if(aparece){
				usuario.getIntentos().get(posicionArrayFoto).setNumIntento(usuario.getIntentos().get(posicionArrayFoto).getNumIntento()+1);
				IntentoFoto nuevo=new IntentoFoto(usuario,foto,System.currentTimeMillis(),true,usuario.getIntentos().get(posicionArrayFoto).getNumIntento());
				usuario.getIntentos().get(posicionArrayFoto).setAdivinado(true);
				BaseDeDatos.insertInto(nuevo.insertInto());
				}else{
				IntentoFoto nuevo=new IntentoFoto(usuario,foto,System.currentTimeMillis(),true,1);
				usuario.getIntentos().add(nuevo);	
				BaseDeDatos.insertInto(nuevo.insertInto());	
				}
				return true;
			}else{
				if(aparece){
					usuario.getIntentos().get(posicionArrayFoto).setNumIntento(usuario.getIntentos().get(posicionArrayFoto).getNumIntento()+1);
					IntentoFoto nuevo=new IntentoFoto(usuario,foto,System.currentTimeMillis(),false,usuario.getIntentos().get(posicionArrayFoto).getNumIntento());
					usuario.getIntentos().get(posicionArrayFoto).setAdivinado(false);
					BaseDeDatos.insertInto(nuevo.insertInto());
					}else{
					IntentoFoto nuevo=new IntentoFoto(usuario,foto,System.currentTimeMillis(),false,1);
					usuario.getIntentos().add(nuevo);	
					BaseDeDatos.insertInto(nuevo.insertInto());	
					}
				return false;
			}
		}else{
			//saltar la excepcion, porque ya está acertada
		return false;
		}
	
	}
}
