package logicaPrograma;
import javax.swing.JFrame;

public class RegistroUsuario extends JFrame {
	//de normal RegistroUsuario no va a tener parámetros, pero con el fin de comprobar lógica antes de empezar con ventanas,
	//pasamos los datos por parámetro
	ConexionBaseDeDatos a=new ConexionBaseDeDatos();
	private boolean existe;
	public RegistroUsuario(){
		this.setExiste(false);
	}
	public void comprobarExistencia(String nuevoUsuario){
		if(a.existeUsuario(nuevoUsuario)){
			setExiste(true);
			//System.out.println("Usuario Cogido"); //aparecerá el mail en rojo
		}else{
			setExiste(false);
		}
	}
	public void registrarUsuario(Usuario nuevoUsuario)throws UsuarioExistente{
		if(isExiste()){
		throw new UsuarioExistente();
		}
		else{
			BaseDeDatos.insertInto(nuevoUsuario.insertInto());
		}
	}
	public boolean isExiste() {
		return existe;
	}
	public void setExiste(boolean existe) {
		this.existe = existe;
	}
}
