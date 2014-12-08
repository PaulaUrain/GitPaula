package logicaPrograma;
import javax.swing.JFrame;

public class RegistroUsuario extends JFrame {
	//de normal RegistroUsuario no va a tener parámetros, pero con el fin de comprobar lógica antes de empezar con ventanas,
	//pasamos los datos por parámetro
	ConexionBaseDeDatos a=new ConexionBaseDeDatos();
	boolean existe=false;
	public RegistroUsuario(Usuario nuevoUsuario){
		comprobarExistencia(nuevoUsuario);
		registrarUsuario(nuevoUsuario);
	}
	public void comprobarExistencia(Usuario nuevoUsuario){
		if(a.existeUsuario(nuevoUsuario.getMail())){
			existe=true;
			//System.out.println("Usuario Cogido"); //aparecerá el mail en rojo
		}else{
			existe=false;
		}
	}
	public void registrarUsuario(Usuario nuevoUsuario){
		if(existe){
			System.out.println("No te puedes registrar el usuario ya existe");
			//se vaciará la casilla del mail y la de contrasenya
		}
		else{
			System.out.println("Bienvenido!!!!!");
			BaseDeDatos.insertInto(nuevoUsuario.insertInto());
		}
	}
}
