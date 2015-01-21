package parteGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import logicaPrograma.Foto;
import logicaPrograma.Sesion;
import logicaPrograma.Usuario;
/**
 * Ventana de perfil del amigo, podemos ver sus fotos e interactuar con ellas
 *en ella tambien vemos su foto de perfil
 */
public class VentanaVisitarAmigo extends JFrame {
	private JList<Foto> listaFotos;
	private ModeloPanelFotos modeloFotos = new ModeloPanelFotos();
	private JScrollPane panelFotos;
	private Sesion sesion;
	private JLabel foto=new JLabel();
	private JPanel panelLeft=new JPanel(new GridLayout(0,1));
	public VentanaVisitarAmigo(Usuario amigo,Sesion sesion){
		setVisible(true);
		setTitle(amigo.getMail());
		setSize(getToolkit().getScreenSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.sesion=sesion;
		listaFotos=new JList<Foto>(modeloFotos);
		panelFotos=new JScrollPane(listaFotos);
		panelFotos.setVerticalScrollBarPolicy(panelFotos.VERTICAL_SCROLLBAR_ALWAYS);
		ImageIcon a1=new ImageIcon(amigo.getFotoPerfil());
		a1= new ImageIcon(a1.getImage().getScaledInstance(200, getToolkit().getScreenSize().height/3, Image.SCALE_DEFAULT));
		panelLeft.setPreferredSize(new Dimension(300,getToolkit().getScreenSize().height));
		foto.setPreferredSize(new Dimension(250,getToolkit().getScreenSize().height/2));
		foto.setIcon(a1);
		modeloFotos.paraPrueba = sesion.ordenarListaFecha(sesion.a.obtenerFotos(amigo.getMail()));
		modeloFotos.avisarAnyadido(0);
		JLabel nombre=new JLabel(amigo.getMail());
		nombre.setPreferredSize(new Dimension(getToolkit().getScreenSize().width,20));
		JLabel relleno=new JLabel();
		relleno.setPreferredSize(new Dimension(300,getToolkit().getScreenSize().height/2));
		panelLeft.add(foto);
		panelLeft.add(relleno);
		panelLeft.setBackground(new Color(28,255,10));
		getContentPane().add(nombre,BorderLayout.NORTH);
		getContentPane().add(panelFotos,BorderLayout.CENTER);
		getContentPane().add(panelLeft,BorderLayout.WEST);
		
		listaFotos.addMouseListener( new MouseAdapter() {  
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
		            int posi = listaFotos.locationToIndex( e.getPoint() );
		            VentanaFoto ventana=new VentanaFoto(listaFotos.getModel().getElementAt(posi),sesion);
		        }
			}
		});
		listaFotos.setCellRenderer(new DefaultListCellRenderer(){ //Objeto default list con metodo sobreescrito
			@Override
			public Component getListCellRendererComponent(JList<?>list, Object value, int index,boolean isSelected,boolean cellHasFocus){
				DefaultListModel<String>comentarios=new DefaultListModel<String>();
				JList<String> listaComentarios=new JList<String>(comentarios);
				JPanel panelP=new JPanel();				
				JScrollPane panelC=new JScrollPane(listaComentarios);
				panelC.setPreferredSize(new Dimension(panelP.getWidth(),60));
				panelC.setVerticalScrollBarPolicy(panelC.VERTICAL_SCROLLBAR_ALWAYS);
				panelP.setLayout(new BorderLayout());
				panelP.setPreferredSize(new Dimension(500,300));
				ImageIcon foto=new ImageIcon(((Foto)value).getPath());
				foto=new ImageIcon(foto.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
				JLabel fotoL=new JLabel();
				fotoL.setIcon(foto);
				fotoL.setPreferredSize(new Dimension(300,300));
				for(int i=0;i<((Foto)value).getComentarios().size();i++){
					comentarios.addElement(((Foto)value).getComentarios().get(i).getUsuario().getMail()+" dice: "+((Foto)value).getComentarios().get(i).comentarioFoto);
				}	
				panelP.setBackground(new Color(28,255,10));
				getContentPane().setBackground(new Color(28,255,10));
				JPanel panelParcial=new JPanel();
				panelParcial.setBackground(new Color(28,255,10));
				panelParcial.add(new JLabel());
				panelParcial.add(fotoL);
				panelParcial.add(new JLabel());
				panelP.add(panelParcial,BorderLayout.CENTER);		
				panelP.add(panelC,BorderLayout.SOUTH);
				return panelP;
			}
			});
	}
}
