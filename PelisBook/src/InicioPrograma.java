import java.sql.SQLException;

public class InicioPrograma {
	public static void main(String[] args) {
		System.out.println(BaseDeDatos.initBD("pb.bd"));
		BaseDeDatos.crearTablaBD();
		ConexionBaseDeDatos a=new ConexionBaseDeDatos();
		Inicio inicio=new Inicio();
		Sesion sesion=inicio.iniciarSesion("asierrodri", "ara");
		inicio.registrarNuevoUsuario("Asier","Rod","asierrodri","pepe");
		sesion.pedirAmigo(a.usuarioSesion("paulaue"));
	}
}
	/*	if(a.iniciarSesion("asierrodri","ara")){	
			
			System.out.println(true);
			//amistad
		//	Sesion sesion= new Sesion(a.usuarioSesion("asierrodri"));
		//	sesion.pedirAmigo(a.usuarioSesion("paulaue"));
			Sesion sesion2= new Sesion(a.usuarioSesion("paulaue"));
			sesion2.aceptarAmigo(a.usuarioSesion("asierrodri"));
		//	sesion2.pedirAmigo(a.usuarioSesion("alvura"));
			String[] palabrasProhibidas=new String[10];
			for(int i=0;i<10;i++){
				palabrasProhibidas[i]="palabra"+i;
			}
		//	sesion2.meterFoto(new Foto("Foto1",palabrasProhibidas,"foto1.jpg","paulaue",System.currentTimeMillis()));
			sesion2.comentar("Comentario no valido palabra1", sesion2.getUsuario().getFotos().get(0));
			sesion2.comentar("Comentario  valido ", sesion2.getUsuario().getFotos().get(0));
		}
		else{
			//NO existe el usuario
		}
		BaseDeDatos.close();
		/*Usuario usuario1=new Usuario("Asier","Rodriguez","asierrodri","ara");
		Usuario usuario2=new Usuario("Paula","Urain","paulaue","pup");
		Usuario usuario3=new Usuario("Alvaro","Echart","alvura","aea");
		BaseDeDatos.insertInto(usuario1.insertInto());
		BaseDeDatos.insertInto(usuario2.insertInto());
		BaseDeDatos.insertInto(usuario3.insertInto());
		BaseDeDatos.close();*/
		


