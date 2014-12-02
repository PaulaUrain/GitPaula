import javax.swing.JFrame;


public class Inicio extends JFrame {
	ConexionBaseDeDatos a =new ConexionBaseDeDatos();
	//tendrá JTEXT para el mail y usuario y un botón para registrar a un nuevo usuario.
	public Inicio(){
		
	}
	public Sesion iniciarSesion(String mail, String contrasenya){
		if(a.iniciarSesion(mail, contrasenya)){
			Sesion nuevaSesion=new Sesion(a.usuarioSesion(mail));
			return nuevaSesion;
		}else{
			System.out.println("contrasenya o mail incorrect, vuelva a introducir datos");
			return null;
		}
	}
	public void registrarNuevoUsuario(String nombre, String apellido, String mail, String contrasenya){
		RegistroUsuario a=new RegistroUsuario(new Usuario(nombre,apellido,mail,contrasenya)/*Mandar un usuario*/);
	}
}
