package parteGUI;

public class DatosErroneos extends Exception {
	public DatosErroneos(){
		super("El usuario o la contrasenya es incorrecta");
	}
}
