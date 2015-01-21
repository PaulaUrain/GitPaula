package logicaPrograma;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class Sesion {
	private Usuario usuario;
	//private ArrayList<Foto> fotosVisibles;
	public ConexionBaseDeDatos a=new ConexionBaseDeDatos();
	private boolean peticionOamigo=false;
	//Arraylist de las fotos que se ven en el panel principal
	private ArrayList<Foto> fotosAmigos=new ArrayList<Foto>();
	private ArrayList<Usuario>peticionesPen=new ArrayList<Usuario>();
	public  Sesion(Usuario usuario){
		this.setUsuario(usuario);
		usuario.setIntentos((a.obtenerIntentosFotos(usuario)));
		usuario.setAmigos(a.obtenerAmigos(usuario.getMail()));
		System.out.println("AMIFO "+usuario.getAmigos().size());
		//cargar las fotos que ha subido el propio usuario
		usuario.setFotos(a.obtenerFotos(usuario.getMail()));
		recuperarPeticiones();
		//para recuperar ftos visibles en el timeline
		recuperarFotos();
	}
	public void recuperarPeticiones(){
		getPeticionesPen().clear();
		ArrayList<String>mails=new ArrayList<String>();
		mails=a.numeroPeticiones(usuario.getMail());
		for(int i=0;i<mails.size();i++){
			getPeticionesPen().add(a.usuarioParcial(mails.get(i)));
		}
	}
	/**
	 * Metodo para recuperar las fotos de los amigos del usuario, se recorre el array de amigos y se van obteniendo
	 * las fotos de cada usuario,despu�s se ordenan por fecha de la m�s nueva a la m�s antigua.
	 */
	public void recuperarFotos(){
		fotosAmigos.clear();
		for(int i=0;i<usuario.getAmigos().size();i++){
			ArrayList<Foto>arrayParcial=a.obtenerFotos(usuario.getAmigos().get(i).getMail());		
			for(int o=0;o<arrayParcial.size();o++){
				fotosAmigos.add(arrayParcial.get(o));
			}
		}
		fotosAmigos=ordenarListaFecha(fotosAmigos);
	}
	//ordena las fotos de m�s nuevo a m�s antiguo.
	public ArrayList<Foto> ordenarListaFecha(ArrayList<Foto> lista){
		if(lista.size()<2){
			return lista;
		}
		else{
			int puntoM=lista.size()/2;
			ArrayList<Foto> parte1 = new ArrayList<Foto>();
			ArrayList<Foto> parte2=new ArrayList<Foto>();
			for(int i=0;i<puntoM;i++){
				parte1.add(lista.get(i));
			}
			for(int a=puntoM;a<lista.size();a++){
				parte2.add(lista.get(a));
			}
			parte1=ordenarListaFecha(parte1);
			parte2=ordenarListaFecha(parte2);
			return algoritmoOrFecha(parte1,parte2);
		}
	}
	public ArrayList<Foto> getFotosAmigos() {
		return fotosAmigos;
	}
	public void setFotosAmigos(ArrayList<Foto> fotosAmigos) {
		this.fotosAmigos = fotosAmigos;
	}
	public ArrayList<Foto> algoritmoOrFecha(ArrayList<Foto> parte1,ArrayList<Foto> parte2){
		ArrayList<Foto> or=new ArrayList<Foto>();
		int p1=0;
		int p2=0;
		int global=0;
		int comp=parte1.size()+parte2.size();
		for(;global<comp;global++){
			if(p1<parte1.size()&&p2<parte2.size()){
				if(parte1.get(p1).getFecha()>parte2.get(p2).getFecha()){
					or.add(parte1.get(p1));
					p1++;
				}
				else{
					or.add(parte2.get(p2));
					p2++;
				}
			}
			else if(p1==parte1.size()){
				or.add(parte2.get(p2));
				p2++;
			}
			else if(p2==parte2.size()){
				or.add(parte1.get(p1));
				p1++;
			}
		}
		return or;
	}
	//VAMOS A HACER CON EXCEPCIONES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public String comentar(String comentario,Foto foto){
		HashSet <String> a=new HashSet<String>();
		for (int i=0;i<foto.getPalabrasProhibidas().length;i++){
			a.add(foto.getPalabrasProhibidas()[i]);
		}
		String coment[] = comentario.split(" ");
		for(String palabra : coment){
			if(a.contains(palabra)){
				return "comentario no valido";
			}
		}
		//Si ha llegado hasta este punto es que el comentario es valido
		Comentario comentario1=new Comentario(comentario,foto,this.getUsuario(),System.currentTimeMillis());
		BaseDeDatos.insertInto(comentario1.insertInto());
		//se anyade al array de comentarios de la foto. En la ventana, habr� que avisar a la JList de que ha habido cambios.
		foto.getComentarios().add(comentario1);
		return "Gracias por comentar";
	}
	public void peticionEnviadaOAmigo(Usuario amigo){
		if(a.peticionEnviada(this.usuario.getMail(), amigo.getMail())){
			peticionOamigo=true;
		}
		else{
			peticionOamigo=false;
		}
	}
	public String pedirAmigo(Usuario amigo){
		peticionEnviadaOAmigo(amigo);
		if(peticionOamigo){
			return "Los usuarios ya son amigos, o la peticion esta enviada";
		}else{
		String insert="insert into amigos values('"+this.getUsuario().getMail()+"', '"+amigo.getMail()+"', '"+false+"', '"
				+System.currentTimeMillis()+"')";
		BaseDeDatos.insertInto(insert);
		return "Has enviado peticion";
		}
	}
	public void aceptarAmigo(Usuario amigo,int posicion){
		boolean confirma=true;
		String actualizar="update amigos set "+""+ "confirmado ='"+confirma+"',"+ "fecha ='"+System.currentTimeMillis()+"'"
				+ "where ((mail2 = '"+this.getUsuario().getMail()+"')&(mail1 = '"+amigo.getMail()+"'))";
		BaseDeDatos.insertInto(actualizar);
		this.getUsuario().anyadirAmigo(amigo);
		this.peticionesPen.remove(posicion);
		//HABR�A QUE MIRAR SI EST� CONECTADO EL AMIGO, SI EST� CONECTADO DE SE LE A�ADE A LA LISTA
		//SI NO, SE LE A�ADIRA CUANDO SE CONECTE
	}
	public void rechazarAmigo(Usuario amigo,int posicion){
		String actualizar="delete from amigos where ((mail2 = '"+this.getUsuario().getMail()+"')&(mail1 = '"+amigo.getMail()+"'))";
		 
		this.peticionesPen.remove(posicion);
		BaseDeDatos.insertInto(actualizar);
	}
	/**
	 * 
	 * @param titulo
	 * @param comentarios
	 * @param pathFoto
	 * @param form true si es para subir una foto normal, false si es para subir foto perfil
	 * @return
	 */
	public String meterFoto(String titulo, String[]comentarios, String pathFoto,boolean form) {
		// TODO Auto-generated method stub
		File foto=null;
		if(form){
			File prueba = new File("c:\\pelisBookFotos\\"+usuario.getMail());
			prueba.mkdirs();
			foto=new File("c:\\pelisBookFotos\\"+usuario.getMail()+"\\"+System.currentTimeMillis());
		
		}else{
			File prueba = new File("c:\\pelisBookFotos\\"+usuario.getMail()+"p");
			try{
				//prueba.delete();
				prueba.mkdirs();
				foto=new File("c:\\pelisBookFotos\\"+usuario.getMail()+"p"+"\\"+System.currentTimeMillis());	
			}catch(Exception e){
					e.printStackTrace();
			}
		}
		BufferedImage imagen;
		try {
			imagen = ImageIO.read(new File(pathFoto));
			ImageIO.write(imagen,"jpg",foto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Foto fotoF;
		if(form){
		fotoF=new Foto(titulo,comentarios,foto.getAbsolutePath(),usuario.getMail(),System.currentTimeMillis(),0);
		}else{
		fotoF=new Foto(titulo,comentarios,foto.getAbsolutePath(),usuario.getMail()+"p",System.currentTimeMillis(),0);	
		}
		BaseDeDatos.insertInto(fotoF.insertInto());
		
		getUsuario().anyadirFoto(fotoF);
		return fotoF.getPath();
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/**
	 * Se comprueba si la foto ya ha sido intentada por el usuario adem�s de si ha sido acertada, si no ocurre lo �ltimo
	 * ,despu�s se comprueba si el usuario ha acertado el titulo.
	 * @param foto la foto que se est� intentando adivinar
	 * @param tituloUsuario titulo propuesto por el usuario
	 * @param aparece: saber si la foto ha sido intentanda con anterioridad por el usuario
	 * @return
	 */
	public String adivinarFoto(Foto foto, String tituloUsuario){
		//usuario.setIntentos((a.obtenerIntentosFotos(usuario)));
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
				BaseDeDatos.insertInto(foto.sumarAcierto());
				usuario.setAcertadas(usuario.getAcertadas()+1);
				BaseDeDatos.insertInto("update usuario set acertadas='"+usuario.getAcertadas()+"' where mail='"+usuario.getMail()+"'");
				return "Enhorabuena has acertado el titulo!!";
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
				return "Prueba otra vez!";
			}
		}else{
			//saltar la excepcion, porque ya est� acertada
		return "No seas tramposo, esta foto ya la has adivinado!!";
		}
	
	}
	public ArrayList<Usuario> getPeticionesPen() {
		return peticionesPen;
	}
	public void setPeticionesPen(ArrayList<Usuario> peticionesPen) {
		this.peticionesPen = peticionesPen;
	}
	public void fotoPerfil(String pathFoto){
		if(!pathFoto.equals(" ")){
		usuario.setFotoPerfil(meterFoto(" ", new String[10], pathFoto,false));
		BaseDeDatos.insertInto("update usuario set fotoPerfil='"+usuario.getFotoPerfil()+"' where mail='"+usuario.getMail()+"'");
		}
	}
	public void eliminarFoto(Foto foto,int posicion){
		usuario.getFotos().remove(posicion);
		String borrar="delete from foto where ((path = '"+foto.getPath()+"'))";
		BaseDeDatos.insertInto(borrar);
	}
}
