package logicaPrograma;

public class UsuarioExistente extends Exception {
	public UsuarioExistente(){
		super("El usuario ya existe, cambie el mail");
	}
}
