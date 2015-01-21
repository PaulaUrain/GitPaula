package logicaPrograma;
import javax.swing.JFrame;

import parteGUI.DatosErroneos;


public class Inicio{
	ConexionBaseDeDatos a =new ConexionBaseDeDatos();
	//tendrá JTEXT para el mail y usuario y un botón para registrar a un nuevo usuario.
	public Inicio(){
		
	}
	public Sesion iniciarSesion(String mail, String contrasenya)throws DatosErroneos{
		System.out.println(mail+" "+contrasenya);
		if(a.iniciarSesion(mail,contrasenya)){
			Sesion nuevaSesion=new Sesion(a.usuarioSesion(mail));
			return nuevaSesion;
		}else{
			throw new DatosErroneos();
		}
	}
	public RegistroUsuario registrarNuevoUsuario(){
		RegistroUsuario a=new RegistroUsuario();
		return a;
	}
}
