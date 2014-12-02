import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConexionBaseDeDatos {
	public ConexionBaseDeDatos(){	
	}
	public boolean existeUsuario(String mailUsuario,String contrasenya){
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
					Usuario a=new Usuario(nombre,apellido,email,contrasenya );
					return a;
				}
			}
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
	ArrayList listaAmigos=new ArrayList <String>();
	String query="SELECT * FROM tabla_amigos";
	try{
	ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
	while(rs.next()){
		if(rs.getString("mail1").equals(mailUsuario)){//nose si usuario1 va encomillado o sin encomillar.
			String mailAmigo=rs.getString("mail1");
			//Una vez localizado la tupla en la que tenga un amigo, procedemos a descargar los datos del amigo para guardarlo en la lista de amigos
			// de usuario, para ello buscamos en la base de datos de usuarios los datos del amigo mediante el mail.
			String queryParcial="SELECT "+mailAmigo+" FROM usuario";
			ResultSet rsp=BaseDeDatos.getStatement().executeQuery(queryParcial);
			Usuario amigo=new Usuario(rsp.getString("nombre"),rsp.getString("apellido"),rsp.getString("mail"));
			listaAmigos.add(amigo);
		}
		else if(rs.getString("mail2").equals(mailUsuario)){
			String mailAmigo=rs.getString("mail2");
			//Una vez localizado la tupla en la que tenga un amigo, procedemos a descargar los datos del amigo para guardarlo en la lista de amigos
			// de usuario, para ello buscamos en la base de datos de usuarios los datos del amigo mediante el mail.
			String queryParcial="SELECT "+mailAmigo+" FROM usuario";
			ResultSet rsp=BaseDeDatos.getStatement().executeQuery(queryParcial);
			Usuario amigo=new Usuario(rsp.getString("nombre"),rsp.getString("apellido"),rsp.getString("mail"));
			listaAmigos.add(amigo);
			}
		}
		return listaAmigos;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return listaAmigos;
	}
	public ArrayList<Comentario> obtenerComentariosFoto(String pathFoto){
		ArrayList<Comentario> listaComentarios=new ArrayList<Comentario>();
		try {
		Foto foto=devFotoSin(pathFoto);
		String query="SELECT * FROM registro_comentario";
		ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
		while(rs.next()){
			if(rs.getString("pathFoto").equals(pathFoto)){
				String queryParcial2="SELECT * FROM usuario WHERE mail== '"+rs.getString("mail")+"'";
				ResultSet rsu=BaseDeDatos.getStatement().executeQuery(queryParcial2);
				Usuario usu=new Usuario(rsu.getString("nombre"),rsu.getString("apellido"), rsu.getString("mail"));
				Comentario nuevoComentario=new Comentario(rs.getString("comentario"),foto,usu,rs.getLong("fecha"));
				listaComentarios.add(nuevoComentario);
			}
		}
		return listaComentarios;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaComentarios;
	}
	//SIN TERMINAR!!
	public ArrayList <IntentoFoto> obtenerIntentosFotos(Usuario usuario){
		ArrayList listaIntentos=new ArrayList<Accion>();
		String query="SELECT * FROM adivinar";
		try{
		ResultSet rs=BaseDeDatos.getStatement().executeQuery(query);
		while(rs.next()){
			if(rs.getString("mail").equals(usuario.getMail())){
				//para guardar foto
				String queryParcial="SELECT * FROM Foto WHERE pathFoto== '"+rs.getString("pathFoto")+"'";
				ResultSet rsp=BaseDeDatos.getStatement().executeQuery(queryParcial);
				Foto foto=devFotoSin(rs.getString("pathFoto"));
				//hAY QUE METER LOS dATOS dE LA TABLA AdIVINAR EN EL ORdEN QUE PONGAMOS EN EL CONSTRUCTOR dE LA CLASE INTENTOAdIVINAR);
				Accion nuevoIntento=new IntentoFoto(usuario, foto, System.currentTimeMillis(),rs.getBoolean("adivinado"));
				listaIntentos.add(nuevoIntento);
			}
		}
		return listaIntentos;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaIntentos;
	}
	public Foto devFotoSin(String pathFoto){
		String queryParcial="SELECT * FROM Foto WHERE pathFoto== '"+pathFoto+"'";
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
				Foto foto=new Foto(rs.getString("titulo"),palabrasProhibidas,rs.getString("path"),mail,rs.getLong("fecha"));
				//hAY QUE METER LOS dATOS dE LA TABLA AdIVINAR EN EL ORdEN QUE PONGAMOS EN EL CONSTRUCTOR dE LA CLASE INTENTOAdIVINAR);
				fotos.add(foto);
			}
		}
		return fotos;}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}	
}

