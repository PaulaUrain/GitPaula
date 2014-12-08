package parteGUI;
import javax.swing.JFrame;
import logicaPrograma.Sesion;

public class VentanaPrincipal extends JFrame {
	Sesion sesion;
	public VentanaPrincipal(Sesion sesion){
		this.sesion=sesion;
		setVisible(true);
		setSize(getToolkit().getScreenSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(sesion.getUsuario().getMail());
	}
}
