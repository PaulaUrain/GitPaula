package parteGUI;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logicaPrograma.Inicio;
import logicaPrograma.RegistroUsuario;
import logicaPrograma.Sesion;
import logicaPrograma.Usuario;
import logicaPrograma.UsuarioExistente;
public class VentanaInicio extends JFrame {
	private VentanaRegistrar venRegistrar;
	protected Sesion sesion;
	protected Inicio inicio;
	private JTextField usuario;
	private JTextField contrasenya;
	private JButton entrar;
	private JButton registrar;
	private JLabel pelisBook=new JLabel("pelisBOOK");
	private JLabel usu=new JLabel("Usuario");
	private JLabel contra=new JLabel("Contraseña");
	public VentanaInicio (){
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inicio=new Inicio();
		this.setSize(this.getToolkit().getScreenSize());
		this.setTitle("PelisBook");
		//this.getContentPane().setBackground(new Color(28,255,100));
		usuario=new JTextField();
		contrasenya=new JTextField();
		entrar=new JButton("ENTRAR");
		registrar=new JButton("REGISTRAR");
		JPanel panelCentral=new JPanel(new GridLayout(0,2));
		panelCentral.add(pelisBook);
		JPanel panelInteraccion=new JPanel(new GridLayout(0,1));
		panelInteraccion.add(usu);
		panelInteraccion.add(usuario);
		panelInteraccion.add(contra);
		panelInteraccion.add(contrasenya);
		JPanel botones=new JPanel(new GridLayout(1,0));
		botones.add(entrar);
		botones.add(registrar);
		panelInteraccion.add(botones);
		panelCentral.add(panelInteraccion);
		this.getContentPane().add(panelCentral);
		panelInteraccion.setBackground(new Color(28,255,100));
		panelCentral.setBackground(new Color(28,255,100));
		entrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				sesion=inicio.iniciarSesion(usuario.getText(), contrasenya.getText());
				VentanaPrincipal ven=new VentanaPrincipal(sesion);
				setVisible(false);
				}catch(DatosErroneos g){
				JOptionPane.showMessageDialog(panelCentral,g.getMessage());	
				usuario.setText(" ");
				contrasenya.setText(" ");
				}
			}
		});
		registrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread hilo=new Thread(){
					public void run(){
					venRegistrar=new VentanaRegistrar();
					venRegistrar.setVisible(true);
					setVisible(false);
					}
				};
				hilo.start();
			}
		});
	}	
	public void volverVentanaPrincipal(){
		setVisible(true);
	}
	class VentanaRegistrar extends JFrame{
		boolean comprobarMail=true;
		RegistroUsuario registro;
		JButton registrar=new JButton("REGISTRAR");
		JButton cancelar=new JButton("CANCELAR");
		JTextField nombre;
		JTextField apellido;
		JTextField mail;
		JTextField contrasenya;
		JLabel n=new JLabel("NOMBRE");
		JLabel a=new JLabel("APELLIDO");
		JLabel m=new JLabel("MAIL");
		JLabel c=new JLabel("CONTRASEÑA");
		public VentanaRegistrar(){
			this.registro=inicio.registrarNuevoUsuario();
			this.nombre=new JTextField();
			this.apellido=new JTextField();
			this.mail=new JTextField();
			this.contrasenya=new JTextField();
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setSize(this.getToolkit().getScreenSize());
			JPanel panelCentral=new JPanel(new GridLayout(0,2));
			JPanel panelInteractivo=new JPanel(new GridLayout(0,2));
			panelInteractivo.add(n);
			panelInteractivo.add(this.nombre);
			panelInteractivo.add(a);
			panelInteractivo.add(this.apellido);
			panelInteractivo.add(m);
			panelInteractivo.add(this.mail);
			panelInteractivo.add(c);
			panelInteractivo.add(this.contrasenya);
			panelInteractivo.add(this.registrar);
			panelInteractivo.add(this.cancelar);
			panelInteractivo.setBackground(new Color(28,255,100));
			panelCentral.setBackground(new Color(28,255,100));
			panelCentral.add(panelInteractivo);
			this.getContentPane().add(panelCentral);
			Thread hiloRegistro=new Thread(){
				public void run(){
					while(comprobarMail){
						registro.comprobarExistencia(mail.getText());
						if(registro.isExiste()){
							mail.setBackground(new Color(255,0,0));
							}else{
								mail.setBackground(new Color(255,255,255));
							}
						try{
							Thread.sleep(1000);
						}catch(Exception e){
						}
					}
				}
			};
			hiloRegistro.start();
			this.registrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try{
						registro.registrarUsuario(new Usuario(nombre.getText(),apellido.getText(),mail.getText(),contrasenya.getText()));
						comprobarMail=false;
						JOptionPane.showMessageDialog(panelCentral,"Usuario registrado correctamente");
						setVisible(false);
						volverVentanaPrincipal();					
					}catch(UsuarioExistente g){
						JOptionPane.showMessageDialog(panelCentral,g.getMessage());
						mail.setText(" ");
						contrasenya.setText(" ");
					}
				}
			});
			this.cancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					volverVentanaPrincipal();					
				}
			});

		}
	}
}

