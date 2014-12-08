package logicaPrograma;
import javax.swing.JFrame;

public class RegistroUsuario extends JFrame {
	//de normal RegistroUsuario no va a tener par�metros, pero con el fin de comprobar l�gica antes de empezar con ventanas,
	//pasamos los datos por par�metro
	ConexionBaseDeDatos a=new ConexionBaseDeDatos();
	boolean existe=false;
	public RegistroUsuario(Usuario nuevoUsuario){
		comprobarExistencia(nuevoUsuario);
		registrarUsuario(nuevoUsuario);
	}
	public void comprobarExistencia(Usuario nuevoUsuario){
		if(a.existeUsuario(nuevoUsuario.getMail())){
			existe=true;
			//System.out.println("Usuario Cogido"); //aparecer� el mail en rojo
		}else{
			existe=false;
		}
	}
	public void registrarUsuario(Usuario nuevoUsuario){
		if(existe){
			System.out.println("No te puedes registrar el usuario ya existe");
			//se vaciar� la casilla del mail y la de contrasenya
		}
		else{
			System.out.println("Bienvenido!!!!!");
			BaseDeDatos.insertInto(nuevoUsuario.insertInto());
		}
	}
}
