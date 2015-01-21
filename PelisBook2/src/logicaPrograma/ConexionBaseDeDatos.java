package logicaPrograma;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * Sirve de puente entre el programa y la base de datos, permite sacar lo que guardamos en ella
 *
 */
public class ConexionBaseDeDatos {
	public ConexionBaseDeDatos(){	
	}
	//para inciar sesion comprobamos que el mail introducido y la contrasenya son los que aparecen en la base de datos
	public boolean iniciarSesion(String mailUsuario,String contrasenya){
		String query="SELECT * FROM usuario ";
		try {
			ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
			while(rs.next()){
				if((mailUsuario.equals(rs.getString("mail")))&&(contrasenya.equals(rs.getString("contrasenya")))){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//en el registro, comprobamos si el mail que introducimos ya esta cogido por un usuario
	public boolean existeUsuario(String mailUsuario){
		String query="SELECT * FROM usuario ";
		try {
			ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
			while(rs.next()){
				if((mailUsuario.equals(rs.getString("mail")))){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//para obtener el usuario de una sesion, recuperamos todos sus datos
	public Usuario usuarioSesion(String mail){
		String query="SELECT * FROM usuario ";
		try {
			ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
			while(rs.next()){
				if(mail.equals(rs.getString("mail"))){
					String nombre=rs.getString("nombre");
					String apellido=rs.getString("apellido");
					String email=rs.getString("mail");
					String contrasenya=rs.getString("contrasenya");
					String fotoPerfil=rs.getString("fotoPerfil");
					Usuario a=new Usuario(nombre,apellido,email,contrasenya,fotoPerfil);
					return a;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	//recuperamos los amigos de un usuario recuperamos el amigo usuario
	public ArrayList<Usuario> usuarioParaAmigos(String mail){
		ArrayList<Usuario> listaAmigos=new ArrayList <Usuario>();
		String query="SELECT * FROM usuario ";
		try {
			ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
			Pattern pFics = Pattern.compile(mail, Pattern.CASE_INSENSITIVE);
			while(rs.next()){
				if(pFics.matcher(rs.getString("mail")).matches()){
					String nombre=rs.getString("nombre");
					String email=rs.getString("mail");
					String fotoPerfil=rs.getString("fotoPerfil");
					Usuario a=new Usuario(nombre," ",email,fotoPerfil,rs.getInt("acertadas"));
					listaAmigos.add(a);
				}
			}return listaAmigos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	/*
	 * se mete el String mailUsuario para recuperar hacer su lista de amigos, se mira en la tabla en la que se guarda la relaciones de amigo
	 * y luego se prodece a descargar sus datos*/
	public ArrayList<Usuario> obtenerAmigos(String mailUsuario){
	ArrayList<Usuario> listaAmigos=new ArrayList <Usuario>();
	ArrayList<String> arrayParcial=new ArrayList<String>();
	String query="SELECT * FROM amigos";
	try{
	ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
	while(rs.next()){
		if((rs.getString("mail1").equals(mailUsuario))&&(rs.getString("confirmado").equals("true"))){//nose si usuario1 va encomillado o sin encomillar.
			String mailAmigo=rs.getString("mail2");
			//Una vez localizado la tupla en la que tenga un amigo, procedemos a descargar los datos del amigo para guardarlo en la lista de amigos
			// de usuario, para ello buscamos en la base de datos de usuarios los datos del amigo mediante el mail.
			arrayParcial.add(mailAmigo);
		}
		else if((rs.getString("mail2").equals(mailUsuario))&&(rs.getString("confirmado").equals("true"))){
			String mailAmigo=rs.getString("mail1");
			arrayParcial.add(mailAmigo);
			}
		}
	//guardamos los mail en un array de string y despues con usuarioParcial obtenemos los objetos usuario que son los que guardaremos
	//en la arraylist amigos
	for(int i=0;i<arrayParcial.size();i++){
		listaAmigos.add(usuarioParcial(arrayParcial.get(i)));
	}
	//posibilidad: guardar todos los mails en array, cuando salgamos del while ejecutar metodo usuarioParcial para cada mail obteniendi
	//los usuarios amigos
		return listaAmigos;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return listaAmigos;
	}
	//obtenemos un usuario pero sin la contrasenya
	public Usuario usuarioParcial(String mail){
		String query="SELECT * FROM usuario ";
		try {
			ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
			while(rs.next()){
				if(mail.equals(rs.getString("mail"))){
					String nombre=rs.getString("nombre");
					String apellido=rs.getString("apellido");
					String email=rs.getString("mail");
					int acertadas=rs.getInt("acertadas");
					String fotoPerfil=rs.getString("fotoPerfil");
					Usuario a=new Usuario(nombre,apellido,email,fotoPerfil,acertadas);
					return a;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	//ppara obtener los comentarios de una foto, hay que hacerlo por pasos para que no casque el result set
	public ArrayList<Comentario> obtenerComentariosFoto(Foto foto){
		ArrayList<Comentario> listaComentarios=new ArrayList<Comentario>();
		ArrayList<String>comentario=new ArrayList<String>();
		ArrayList<Long>fecha=new ArrayList<Long>();
		ArrayList<String>mail=new ArrayList<String>();
		try {
		String query="SELECT * FROM registro_comentario where (pathFoto='"+foto.getPath()+"')";
		ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
		while(rs.next()){
			//primero rellenamos los arrays
				comentario.add(rs.getString("comentario"));
				fecha.add(rs.getLong("fecha"));
				mail.add(rs.getString("mail"));
			}
		//una vez hemos conseguido la infor completa de la base de datos, para obtener el usuario
		//volvemos a requerir de la base de datos y por eso utilizamos el metodo usuarioParcial
		for(int i=0;i<mail.size();i++){
			listaComentarios.add(new Comentario(comentario.get(i),foto,usuarioParcial(mail.get(i)),fecha.get(i)));
		}
		return listaComentarios;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaComentarios;
	}
	//parecido al anterior, epro sirve para saber los intentos que ha hecho una persona sobre una foto
	public ArrayList <IntentoFoto> obtenerIntentosFotos(Usuario usuario){
		ArrayList<IntentoFoto> listaIntentos=new ArrayList<IntentoFoto>();
		HashSet<String> fotosDistintas=new HashSet<String>();
		ArrayList<String> fotos=new ArrayList<String>();
		ArrayList<Long>fecha=new ArrayList<Long>();
		ArrayList<Boolean>acertar=new ArrayList<Boolean>();
		ArrayList<Integer>numeroIntento=new ArrayList<Integer>();
		String query="SELECT * FROM adivinar";
		try{
		ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
		while(rs.next()){		
			if(rs.getString("mail").equals(usuario.getMail())){
				//comprobamos que la foto ya esta en el hashset, si esta actualizamos los intentos
				if(fotosDistintas.contains(rs.getString("pathFoto"))){
					for(int i=0;i<listaIntentos.size();i++){
					if(rs.getString("pathFoto").equals(fotos.get(i))){
						//actualizamos con los intentos que aparezcan en la ultima entrada
						if(rs.getLong("fecha")>fecha.get(i)){
						fecha.set(i, rs.getLong("fecha"));
						acertar.set(i,rs.getString("adivinado").equals("true"));
						numeroIntento.set(i,rs.getInt("numeroIntento"));
							}
						}
					}
				}else {
					//si no esta, anyadimos al hashset la foto y al array los datos
					fotosDistintas.add(rs.getString("pathFoto"));
					fotos.add(rs.getString("pathFoto"));
					fecha.add(rs.getLong("fecha"));
					acertar.add(rs.getString("adivinado").equals("true"));
					numeroIntento.add(rs.getInt("numeroIntento"));
				}
			}
		}
		//una vez mas en dos pasos, aqui hemos llamado al metodo devFotoSin para obtener la foto
		for (int i=0;i<fotos.size();i++){
			listaIntentos.add(new IntentoFoto(usuario,devFotoSin(fotos.get(i)),fecha.get(i),acertar.get(i),numeroIntento.get(i)));
		}
		return listaIntentos;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaIntentos;
	}
	//devuelve una foto, le mandamos el path y la busca
	public Foto devFotoSin(String pathFoto){
		String queryParcial="SELECT * FROM foto WHERE path= '"+pathFoto+"'";
		ResultSet rsp;
		try {
		rsp = BaseDeDatos.getStatement().executeQuery(queryParcial);
		Foto foto=new Foto(rsp.getString("titulo"), pathFoto, rsp.getString("mailUsuario"),rsp.getLong("fecha"));
		return foto;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//sacar fotos de un Usuario
	public ArrayList<Foto> obtenerFotos(String mail){
		ArrayList<Foto> fotos=new ArrayList<Foto>();
		String query="SELECT * FROM foto";
		try{
		ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
		while(rs.next()){
			if(rs.getString("mailUsuario").equals(mail)){
				String[] palabrasProhibidas=new String[10];
				for(int i=1;i<11; i++){
					palabrasProhibidas[i-1]=rs.getString("palabraP"+i);
				}
				Foto foto=new Foto(rs.getString("titulo"),palabrasProhibidas,rs.getString("path"),mail,rs.getLong("fecha"),rs.getInt("numeroPersonasAdivinadas"));
				fotos.add(foto);
			}
		}
		//para poder sacar los comentarios tambien en dos partes, arriba hemos sacado la foto en general
		//aqui volvemos a llamar a la base de datos para obtener los comentarios de esa foto
		for(int i=0;i<fotos.size();i++){
			fotos.get(i).setComentarios(obtenerComentariosFoto(fotos.get(i)));
		}
		return fotos;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	//comprueba si dos amigos aparecen en la tabla de amigos
	public boolean peticionEnviada(String mail1,String mail2){
		String query="SELECT * FROM amigos";
		try{
		ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
		while(rs.next()){
			if((rs.getString("mail1").equals(mail1))&&(rs.getString("mail2").equals(mail2))){//nose si usuario1 va encomillado o sin encomillar.
				return true;
				}
			if((rs.getString("mail2").equals(mail1))&&(rs.getString("mail1").equals(mail2))){//nose si usuario1 va encomillado o sin encomillar.
				return true;
				}
		}
		}catch(Exception e){
		}
		return false;
	}
	//devuelve un array con el numero de filas de este usuario que tienen la columna de confirmado a false
	public ArrayList<String> numeroPeticiones(String mailUsuario){
		ArrayList<String> listaAmigos=new ArrayList <String>();
		String query="SELECT * FROM amigos";
		try{
		ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
		while(rs.next()){
			if((rs.getString("mail2").equals(mailUsuario))&&(rs.getString("confirmado").equals("false"))){
				String mailAmigo=rs.getString("mail1");
				listaAmigos.add(mailAmigo);
				}
			}
		}catch(Exception e){
			
		}
		return listaAmigos;
	}
}


			

