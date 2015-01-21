package parteGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import logicaPrograma.Foto;
import logicaPrograma.Sesion;
import logicaPrograma.Usuario;

public class VentanaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private Sesion sesion;
	boolean sigo = true;
	private JButton interactuarFoto = new JButton("Comentar/Adivinar foto");
	private JButton subirFoto = new JButton("Subir Foto");
	private JButton cambiarFotoPerfil = new JButton("Cambiar Foto Perfil");
	private JPanel panelLeft = new JPanel();
	private JScrollPane panelCenter;
	private JList<Foto> listaFotos;
	private JList<Usuario> ranking;
	private ModeloListaRanking modeloListaRanking;
	private ModeloPanelFotos modeloFotos = new ModeloPanelFotos();
	private JPanel panelRight = new JPanel();
	private JButton verMisFotos=new JButton ("Ver mis fotos");
	private JButton buscarAmigos = new JButton("BuscarAmigos");
	private JButton peticiones = new JButton("Peticiones pendientes ");
	private VentanaPeticiones ventanaPeticiones = new VentanaPeticiones();
	private VentanaBuscarAmigos ventanaNueva;
	private JLabel fotoPerfil=new JLabel();
	public VentanaPrincipal(Sesion sesion){
		this.sesion=sesion;
		
		setTitle(sesion.getUsuario().getMail());
		setVisible(true);
		setSize(getToolkit().getScreenSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//se pone nuestra foto de perfiñ
		ponerFotoPerfil();
		peticiones.setText("Peticiones pendientes "+sesion.getPeticionesPen().size());
		listaFotos=new JList<Foto>(modeloFotos);
		modeloListaRanking=new ModeloListaRanking(sesion.getUsuario().getAmigos());
		ranking=new JList<Usuario>(modeloListaRanking);
		//llamamos al metodo que recupera las fotos de los amigos
		recuperarFoto();
		panelCenter=new JScrollPane(listaFotos);
		panelCenter.setPreferredSize(new Dimension((getWidth()/2),getHeight()));
		panelCenter.setVerticalScrollBarPolicy(panelCenter.VERTICAL_SCROLLBAR_ALWAYS);
		panelCenter.setBackground(new Color(28,255,10));
		panelRight.setLayout(new GridLayout(0,1));
		panelRight.add(buscarAmigos);
		panelRight.add(interactuarFoto);	
		panelRight.add(peticiones);
		panelRight.add(subirFoto);
		panelRight.add(cambiarFotoPerfil);
		GridBagLayout layoutPanelLeft=new GridBagLayout(); 
		panelLeft.setLayout(layoutPanelLeft);
		JScrollPane panelRanking=new JScrollPane(ranking);
		panelRanking.setPreferredSize(new Dimension((getWidth()/4),getHeight()/2));
		panelRanking.setVerticalScrollBarPolicy(panelRanking.VERTICAL_SCROLLBAR_ALWAYS);
		fotoPerfil.setPreferredSize(new Dimension((getWidth()/4),getHeight()/3));
		verMisFotos.setPreferredSize(new Dimension(getWidth()/4,getHeight()/18));
		JPanel relleno=new JPanel();
		JPanel relleno2=new JPanel();
		relleno2.setPreferredSize(new Dimension(getWidth()/4,getHeight()/45));
		relleno.setPreferredSize(new Dimension(getWidth()/4,getHeight()/45));
		GridBagConstraints rellenoG=new GridBagConstraints();
		rellenoG.gridx=0;
		//color para el panel LEFT
		relleno2.setBackground(new Color(28,255,10));
		panelLeft.setBackground(new Color(28,255,10));
		panelRanking.setBackground(new Color(28,255,10));
		ranking.setBackground(new Color(28,255,10));
		panelLeft.add(fotoPerfil,rellenoG);
		panelLeft.add(verMisFotos,rellenoG);
		panelLeft.add(panelRanking,rellenoG);
		getContentPane().add(panelRight,BorderLayout.EAST);
		getContentPane().add(panelLeft,BorderLayout.WEST);
		getContentPane().add(panelCenter,BorderLayout.CENTER);
		//abre la ventana de buscar amigos para enviar peticiones
		buscarAmigos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventanaNueva=new VentanaBuscarAmigos();
				}
		});			
		//peticiones pendientes que nos han llegado, abre ventana con las peticiones
		peticiones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventanaPeticiones.modelo.cambio(sesion.getPeticionesPen());
				ventanaPeticiones.setVisible(true);
			}
		});
		//abre la ventana de la foto para poder participar en el juego
		interactuarFoto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Foto a=listaFotos.getSelectedValue();
				try{
				VentanaFoto b=new VentanaFoto(a,sesion);
				}catch(NullPointerException exce){
					JOptionPane.showMessageDialog(interactuarFoto, "No has seleccionado una imagen, por favor selecciona");
					Window.getWindows()[Window.getWindows().length-2].dispose();
					return;
				}
			}
		});
		//para subir una foto
		subirFoto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaSubirFoto a=new VentanaSubirFoto(sesion);
			}
		});
		// permite actualizar nuestra foto de perfil, llamando al metodo correspondiente
		cambiarFotoPerfil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sesion.fotoPerfil(subirFotoPerfil());
				ponerFotoPerfil();
				
			}
		});
		//boton que abre ventana para ver mis fotos, me permite ver todas mis fotos y borrar la que quiera
		verMisFotos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new VentanaMisFotos();
			}
		});
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sigo=false; //para acabar el hilo que no es el de la ventana al cerrar la ventana
			}
		});
		//si hace doble clik en el nombre del amigo te abre su pagina de perfil
		ranking.addMouseListener( new MouseAdapter() {  
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
		            int posi = ranking.locationToIndex( e.getPoint() );
		            VentanaVisitarAmigo ventana=new VentanaVisitarAmigo(ranking.getModel().getElementAt(posi),sesion);
		        }
			}
		});
		Thread hilo=new Thread(){
			//con el hilo refrescamos cada minuto distintas partes que van actualizando los usuarios
			//las posiciones del ranking, los amigos nuevos, las peticiones, las fotos que van subiendo...
			public void run(){
			//metodo que quiero que haga el hilo, es para que un metodo se ejecute a través de otro hilo y no afecte al hilo principal
			while(sigo){
				modeloListaRanking.cambio(sesion.getUsuario().getAmigos());
				sesion.recuperarFotos();
				modeloFotos.paraPrueba=sesion.getFotosAmigos();
				modeloFotos.avisarAnyadido(0);
				sesion.recuperarPeticiones();
				ventanaPeticiones.modelo.cambio(sesion.getPeticionesPen());
				peticiones.setText("Peticiones pendientes "+sesion.getPeticionesPen().size());
				repaint();
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		};
		hilo.start();
		//renderer para que en el ranking salga el amigo seleccionado en verde y su foto de perfil
		ranking.setCellRenderer(new DefaultListCellRenderer(){ //Objeto default list con metodo sobreescrito
			@Override
			public Component getListCellRendererComponent(JList<?>list, Object value, int index,boolean isSelected,boolean cellHasFocus){
				JPanel panelRanking=new JPanel();
				JLabel mailUsuario=new JLabel(((Usuario)value).getMail()+" "+((Usuario)value).getAcertadas());
				mailUsuario.setBackground(new Color(28,255,10));
				panelRanking.setBackground(new Color(28,255,10));
				if(isSelected){
					mailUsuario.setForeground(Color.RED);
					}
				ImageIcon imagenUsuario=new ImageIcon(((Usuario)value).getFotoPerfil());
				imagenUsuario= new ImageIcon(imagenUsuario.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
				JLabel fotoUsuario=new JLabel();
				fotoUsuario.setPreferredSize(new Dimension(40,40));
				fotoUsuario.setIcon(imagenUsuario);
				panelRanking.add(fotoUsuario);
				panelRanking.add(mailUsuario);
				return panelRanking;
			}
			});
		//renderer de las fotos, en rojo la seleccionada
		listaFotos.setCellRenderer(new DefaultListCellRenderer(){ //Objeto default list con metodo sobreescrito
			@Override
			public Component getListCellRendererComponent(JList<?>list, Object value, int index,boolean isSelected,boolean cellHasFocus){
				DefaultListModel<String>comentarios=new DefaultListModel<String>();
				JList<String> listaComentarios=new JList<String>(comentarios);
				JPanel panelCentral=new JPanel();
				JPanel panelPrincipal=new JPanel();	
				JScrollPane panelComentarios=new JScrollPane(listaComentarios);
				panelPrincipal.setBackground(new Color(28,255,10));
				panelCentral.setBackground(new Color(28,255,10));
				panelComentarios.setPreferredSize(new Dimension(panelPrincipal.getWidth(),60));
				panelComentarios.setVerticalScrollBarPolicy(panelCenter.VERTICAL_SCROLLBAR_ALWAYS);
				panelPrincipal.setLayout(new BorderLayout());
				panelPrincipal.setPreferredSize(new Dimension(500,300));
				ImageIcon foto=new ImageIcon(((Foto)value).getPath());
				foto=new ImageIcon(foto.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
				JLabel fotoL=new JLabel();
				fotoL.setIcon(foto);
				fotoL.setPreferredSize(new Dimension(200,200));
				if(isSelected){
					panelPrincipal.setBackground(Color.RED);
					panelCentral.setBackground(Color.red);
					}
				for(int i=0;i<((Foto)value).getComentarios().size();i++){				
					comentarios.addElement(((Foto)value).getComentarios().get(i).getUsuario().getMail()+" dice: "+((Foto)value).getComentarios().get(i).comentarioFoto);
				}	
				JLabel mailUsuario=(new JLabel(((Foto)value).getEmailUsuario()));
				mailUsuario.setFont(new Font(mailUsuario.getFont().getFontName(),mailUsuario.getFont().getStyle(),20));
				panelPrincipal.add(mailUsuario,BorderLayout.NORTH);
				panelCentral.setLayout(new GridLayout(0,3));
				panelCentral.add(new JLabel());
				panelCentral.add(fotoL);
				panelCentral.add(new JLabel());
				panelPrincipal.add(panelCentral,BorderLayout.CENTER);		
				panelPrincipal.add(panelComentarios,BorderLayout.SOUTH);
				return panelPrincipal;
			}
		});
	}
	//metodo que refresca la foto de perfil (metodo de clase sesion)
	public void ponerFotoPerfil(){
		ImageIcon a1=new ImageIcon(sesion.getUsuario().getFotoPerfil());
		a1= new ImageIcon(a1.getImage().getScaledInstance(getWidth()/4,getHeight()/4, Image.SCALE_DEFAULT));
		fotoPerfil.setIcon(a1);		
		repaint();
	}
	//cuando clickamos en subir foto perfil escogemos una imagen desde archivo; solo JPEG
	public String subirFotoPerfil() {
		// TODO Auto-generated method stub
		boolean cumple=false;
		File dirActual = new File( System.getProperty("user.dir") );
		JFileChooser chooser = new JFileChooser( dirActual );
		chooser.setFileFilter(new FileNameExtensionFilter("JPEG","jpg","JPG"));
		chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
		int returnVal = chooser.showOpenDialog( null );
		if (returnVal == JFileChooser.APPROVE_OPTION){
			if((chooser.getSelectedFile().getAbsolutePath().endsWith(".jpg"))){
				cumple=true;	
				}
			if((chooser.getSelectedFile().getAbsolutePath().endsWith(".JPG"))&&!cumple){
				cumple=true;
			}
			if(cumple){
				return chooser.getSelectedFile().getAbsolutePath();
			}else{
				JOptionPane.showMessageDialog(this, "Selecciona una imagen con formato JPEG");
				subirFotoPerfil();
				return " ";
			}
		}return " ";
	}
	/**
	 * Recuperamos las fotos de los amigos del usuario, previamente se tiene que actualizar el Array de la sesión
	 * con el metodo sesion.recuperarFotos()
	 * @param fechaRecupera podemos 
	 */
	/**
	 * recuperamos las fotos de los amigos al principio y cada vez que refresquemos la pantalla
	 */
	public void recuperarFoto() {
		modeloFotos.paraPrueba = sesion.getFotosAmigos();
		modeloFotos.avisarAnyadido(0);
	}
	/**
	 * ventana de las peticiones de los amigos, aparecen los amigos en una jlist. podemos aceptar o rechazar al
	 * seleccionado
	 */
	public class VentanaPeticiones extends JFrame {
		JList<Usuario> usuariosPeticiones;
		JButton rechazar = new JButton("Rechazar");
		JButton aceptar = new JButton("Aceptar");
		ModeloListaPeticiones modelo = new ModeloListaPeticiones();
		JPanel botonera = new JPanel();
		JScrollPane panelScroll;
		public VentanaPeticiones() {
			usuariosPeticiones = new JList<Usuario>(modelo);
			panelScroll = new JScrollPane(usuariosPeticiones);
			this.setSize(400, 400);
			panelScroll.setPreferredSize(new Dimension(200, 200));
			panelScroll.setVerticalScrollBarPolicy(panelScroll.VERTICAL_SCROLLBAR_ALWAYS);
			setLocation(getToolkit().getScreenSize().width / 2, getToolkit().getScreenSize().height / 4);// calcular sacar miti
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			panelScroll.setBackground(new Color(28,255,10));
			botonera.setBackground(new Color(0,0,255));
			getContentPane().setBackground(new Color(28,255,10));
			usuariosPeticiones.setBackground(new Color(28,255,10));	
			//si aceptamos anyadimos el amigo al array de amigos del usuario de la sesion actual 
			aceptar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sesion.aceptarAmigo(usuariosPeticiones.getSelectedValue(),
							usuariosPeticiones.getSelectedIndex());
					peticiones.setText("Peticiones pendientes "
							+ sesion.getPeticionesPen().size());
					modeloListaRanking.cambio(sesion.getUsuario().getAmigos());
					sesion.recuperarFotos();
					modeloFotos.paraPrueba = sesion.getFotosAmigos();
					modeloFotos.avisarAnyadido(0);
					repaint();
				}
			});
			//sliminamos la peticion, desaparece del array de peticiones
			rechazar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sesion.rechazarAmigo(usuariosPeticiones.getSelectedValue(),
							usuariosPeticiones.getSelectedIndex());
					peticiones.setText("Peticiones pendientes "
							+ sesion.getPeticionesPen().size());
					modeloListaRanking.cambio(sesion.getUsuario().getAmigos());
					sesion.recuperarFotos();
					modeloFotos.paraPrueba = sesion.getFotosAmigos();
					modeloFotos.avisarAnyadido(0);
					repaint();
				}
			});
			//seleccionado sale en rojo
			usuariosPeticiones.setCellRenderer(new DefaultListCellRenderer() { 
						@Override
						public Component getListCellRendererComponent(JList<?> list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
							JPanel panelPrincipal = new JPanel();
							JLabel nombreUsuario = (JLabel) super.getListCellRendererComponent(list, value,index, isSelected, cellHasFocus);
							nombreUsuario.setBackground(new Color(28,255,10));
							panelPrincipal.setBackground(new Color(28,255,10));
							if (isSelected) {
								nombreUsuario.setForeground(Color.RED);
							}
							ImageIcon a1 = new ImageIcon(((Usuario)value).getFotoPerfil());
							a1 = new ImageIcon(a1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
							JLabel foto = new JLabel();
							foto.setPreferredSize(new Dimension(40, 40));
							foto.setIcon(a1);
							panelPrincipal.add(foto);
							panelPrincipal.add(nombreUsuario);
							return panelPrincipal;
						}
					});
			botonera.add(aceptar);
			botonera.add(rechazar);
			getContentPane().add(panelScroll, BorderLayout.CENTER);
			getContentPane().add(botonera, BorderLayout.NORTH);
		}
	}
	/**
	 * Clase para crear la ventana de Buscar amigos, contiene el
	 * modeloListaAmigos, un jtextfield en el que hay que escribir el mail del amigo en cuestion para que aparezca
	 * si ya has enviado la peticion, o ya son amigos no te deja enviar de nuevo
	 * @author Alumno
	 *
	 */
	public class VentanaBuscarAmigos extends JFrame {
		ArrayList<Usuario> listaAmigos = new ArrayList<Usuario>();
		JTextField buscarNombre = new JTextField();
		JList<Usuario> usuariosPosibles;
		JButton enviarPeticion = new JButton("Enviar Peticion");
		JButton buscar = new JButton("Buscar");
		public ModeloListaAmigos modelo = new ModeloListaAmigos();
		JPanel panelCentral = new JPanel();
		JPanel botonera = new JPanel();
		JScrollPane panelScroll;
		public VentanaBuscarAmigos() {
			buscarNombre.setPreferredSize(new Dimension(40, 40));
			usuariosPosibles = new JList<Usuario>(modelo);
			panelScroll = new JScrollPane(usuariosPosibles);
			this.setSize(400, 400);
			panelScroll.setPreferredSize(new Dimension(200, 200));
			panelScroll.setVerticalScrollBarPolicy(panelScroll.VERTICAL_SCROLLBAR_ALWAYS);
			panelScroll.setBackground(new Color(28,255,10));
			usuariosPosibles.setBackground(new Color(0,0,255));
			panelCentral.setBackground(new Color(28,255,10));
			botonera.setBackground(new Color(28,255,10));
			setVisible(true);
			setLocation(getToolkit().getScreenSize().width / 2, getToolkit().getScreenSize().height / 4);// calcular sacar miti
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			buscar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					listaAmigos = sesion.a.usuarioParaAmigos(buscarNombre.getText());
					modelo.paraPrueba = listaAmigos;
					modelo.avisarAnyadido(modelo.paraPrueba.size() - 1);
				}
			});

			usuariosPosibles.setCellRenderer(new DefaultListCellRenderer() { 
						@Override
						public Component getListCellRendererComponent(JList<?> list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
							System.out.println(((Usuario)value).getClass());
							JPanel b = new JPanel();
							JLabel a = (JLabel) super.getListCellRendererComponent(list, value,index, isSelected, cellHasFocus);
							b.setBackground(new Color(28,255,10));
							a.setBackground(new Color(28,255,10));
							if (isSelected) {
								a.setForeground(Color.RED);
							}
							ImageIcon a1=new ImageIcon(((Usuario)value).getFotoPerfil());
							System.out.println(((Usuario)value).getFotoPerfil());
							a1= new ImageIcon(a1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
							JLabel foto=new JLabel();
							foto.setPreferredSize(new Dimension(40, 40));
							foto.setIcon(a1);
							b.add(a);
							b.add(foto);
							return b;
						}
					});
			enviarPeticion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String message = sesion.pedirAmigo(usuariosPosibles
							.getSelectedValue());
					JOptionPane.showMessageDialog(panelCentral, message);
				}
			});
			panelCentral.add(buscarNombre);
			panelCentral.add(panelScroll);
			botonera.add(buscar);
			botonera.add(enviarPeticion);
			getContentPane().add(panelCentral, BorderLayout.CENTER);
			getContentPane().add(botonera, BorderLayout.NORTH);
		}
	}
	/**
	 * Clase para ver mis fotos aparecen en una jlist, la foto seleccionada sale en rojo
	 * si clickas dos veces en la imagen te da la opcion de eliminarla 
	 *
	 */
	public class VentanaMisFotos extends JFrame {
		JScrollPane panelFotos;
		public VentanaMisFotos(){
			setSize(600,600);
			setLocation(new Point(getToolkit().getScreenSize().width/4,getToolkit().getScreenSize().height/5));
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ModeloPanelFotos fotosPropiasM=new ModeloPanelFotos();
			JList<Foto>fotosPropias=new JList<Foto>(fotosPropiasM);
			panelFotos=new JScrollPane(fotosPropias);
			fotosPropiasM.paraPrueba=sesion.ordenarListaFecha(sesion.a.obtenerFotos(sesion.getUsuario().getMail()));
			fotosPropiasM.avisarAnyadido(0);
			getContentPane().add(panelFotos);
			
			fotosPropias.setCellRenderer(new DefaultListCellRenderer(){ //Objeto default list con metodo sobreescrito
				@Override
				public Component getListCellRendererComponent(JList<?>list, Object value, int index,boolean isSelected,boolean cellHasFocus){
					JPanel panelFoto=new JPanel();
					ImageIcon foto=new ImageIcon(((Foto)value).getPath());
					foto=new ImageIcon(foto.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
					JLabel fotoL=new JLabel();
					fotoL.setIcon(foto);
					fotoL.setPreferredSize(new Dimension(200,200));
					panelFoto.setBackground(new Color(28,255,10));
					if(isSelected){
						panelFoto.setBackground(Color.RED);
						}
					panelFoto.add(fotoL);		
					return panelFoto;
				}
				});
			fotosPropias.addMouseListener( new MouseAdapter() {  
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int posi = fotosPropias.locationToIndex( e.getPoint() );
						int returnVal= JOptionPane.showConfirmDialog(panelFotos, "¿Estás seguro de que quieres eliminar esta foto?");
						if(returnVal==JOptionPane.YES_OPTION){
							sesion.eliminarFoto(fotosPropias.getSelectedValue(), posi);
							fotosPropiasM.paraPrueba=sesion.ordenarListaFecha(sesion.a.obtenerFotos(sesion.getUsuario().getMail()));
							
							return;
						}
						else if(returnVal==JOptionPane.CANCEL_OPTION){
							return;
						}
					}
				}
			});
		}
	}
	//modelo para la lista de amigos
	public class ModeloListaAmigos implements ListModel<Usuario> {
		ArrayList<Usuario> paraPrueba;
		ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();

		public ModeloListaAmigos() {
			paraPrueba = new ArrayList<Usuario>();
		}

		public void add(Usuario a) {

			this.paraPrueba.add(a);
			avisarAnyadido(paraPrueba.size() - 1);
		}

		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return paraPrueba.size();
		}

		@Override
		public Usuario getElementAt(int index) {
			return paraPrueba.get(index);

		}

		public void setElementAt(int index, Usuario element) {
			paraPrueba.set(index, element);
			avisarAnyadido(index);
		}

		public void removeElementAt(int index) {
			paraPrueba.remove(index);
			avisarAnyadido(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}

		private void avisarAnyadido(int posi) {
			for (ListDataListener ldl : listeners) {
				if (paraPrueba.size() == 0) {
					ldl.intervalAdded(new ListDataEvent(this,
							ListDataEvent.INTERVAL_ADDED, 0, paraPrueba.size()));
				} else {
					ldl.intervalAdded(new ListDataEvent(this,
							ListDataEvent.INTERVAL_ADDED, 0,
							paraPrueba.size() - 1));
				}
			}
		}
	}
	//modelo utilizado para la lista de peticiones
	public class ModeloListaPeticiones implements ListModel<Usuario> {
		ArrayList<Usuario> paraPrueba;
		ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();

		public ModeloListaPeticiones() {
			paraPrueba = new ArrayList<Usuario>();
			avisarAnyadido(0);
		}

		public void cambio(ArrayList<Usuario> listaPeticiones) {
			paraPrueba = listaPeticiones;
			avisarAnyadido(0);
		}

		public void add(Usuario a) {
			this.paraPrueba.add(a);
			avisarAnyadido(paraPrueba.size() - 1);
		}

		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return paraPrueba.size();
		}

		@Override
		public Usuario getElementAt(int index) {
			return paraPrueba.get(index);

		}

		public void setElementAt(int index, Usuario element) {
			paraPrueba.set(index, element);
			avisarAnyadido(index);
		}

		public void removeElementAt(int index) {
			paraPrueba.remove(index);
			avisarAnyadido(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}

		private void avisarAnyadido(int posi) {
			for (ListDataListener ldl : listeners) {
				if (paraPrueba.size() == 0) {
					ldl.intervalAdded(new ListDataEvent(this,
							ListDataEvent.INTERVAL_ADDED, 0, paraPrueba.size()));
				} else {
					ldl.intervalAdded(new ListDataEvent(this,
							ListDataEvent.INTERVAL_ADDED, 0,
							paraPrueba.size() - 1));
				}
			}
		}
	}
	//modelo utilizado para el ranking de puntuaciones
	public class ModeloListaRanking implements ListModel<Usuario> {
		ArrayList<Usuario> paraPrueba;
		ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();

		public ModeloListaRanking(ArrayList<Usuario> amigos) {
			paraPrueba = new ArrayList<Usuario>();
			paraPrueba = amigos;
			avisarAnyadido(0);
		}

		public void cambio(ArrayList<Usuario> amigos) {
			paraPrueba = amigos;
			avisarAnyadido(0);
		}

		public void add(Usuario a) {

			this.paraPrueba.add(a);
			avisarAnyadido(paraPrueba.size() - 1);
		}

		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return paraPrueba.size();
		}

		@Override
		public Usuario getElementAt(int index) {
			return paraPrueba.get(index);

		}

		public void setElementAt(int index, Usuario element) {
			paraPrueba.set(index, element);
			avisarAnyadido(index);
		}

		public void removeElementAt(int index) {
			paraPrueba.remove(index);
			avisarAnyadido(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}

		private void avisarAnyadido(int posi) {
			for (ListDataListener ldl : listeners) {
				if (paraPrueba.size() == 0) {
					ldl.intervalAdded(new ListDataEvent(this,
							ListDataEvent.INTERVAL_ADDED, 0, paraPrueba.size()));
				} else {
					ldl.intervalAdded(new ListDataEvent(this,
							ListDataEvent.INTERVAL_ADDED, 0,
							paraPrueba.size() - 1));
				}
			}
		}
	}
}
