package parteGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import logicaPrograma.Foto;
import logicaPrograma.Sesion;

public class VentanaFoto extends JFrame {
	private Foto foto;
	private Sesion sesion;
	private JPanel panelCentral=new JPanel();
	private JPanel botonera=new JPanel();
	private JPanel arriba=new JPanel();
	private JTextField textoUsuario=new JTextField();
	private JPanel panelComentarios=new JPanel();
	private JButton acertar=new JButton("Adivinar");
	private JButton comentar=new JButton("Comentar");
	private JLabel puntuacion;
	private DefaultListModel<String>comentarios=new DefaultListModel<String>();
	private JList<String> listaComentarios=new JList<String>(comentarios);
	private JScrollPane panelC;
	
	public VentanaFoto(Foto foto,Sesion sesion)throws NullPointerException{
		panelC=new JScrollPane(listaComentarios);
		this.foto=foto;
		this.sesion=sesion;
		setVisible(true);
		setSize(900,800);
		panelC.setVerticalScrollBarPolicy(panelC.VERTICAL_SCROLLBAR_ALWAYS);
		for(int i=0;i<foto.getComentarios().size();i++){
			comentarios.addElement(foto.getComentarios().get(i).comentarioFoto);
		}
		panelCentral.setPreferredSize(new Dimension(800,500));
		ImageIcon fotoI=new ImageIcon(foto.getPath());
		JLabel fotoL=new JLabel();
		fotoL.setPreferredSize(new Dimension(500,500));
		fotoI=new ImageIcon(fotoI.getImage().getScaledInstance(400,400,Image.SCALE_DEFAULT));
		fotoL.setIcon(fotoI);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		botonera.setLayout(new GridLayout(0, 1));
		botonera.setPreferredSize(new Dimension(90,500));
		puntuacion=new JLabel("Numero Personas Adivinadas: "+foto.getNumeroPersonasAdivinadas());
		panelCentral.add(puntuacion);
		panelCentral.add(fotoL);
		panelC.setPreferredSize(new Dimension(getWidth()-20,150));
		panelComentarios.setPreferredSize(new Dimension(getWidth(),150));
		getContentPane().add(panelCentral,BorderLayout.CENTER);
		arriba.add(new JLabel("Pon tu comentario, o intenta adivinar el titulo de la foto:"));
		arriba.add(textoUsuario);
		textoUsuario.setPreferredSize(new Dimension((int)getWidth()/2,100));
		botonera.add(acertar);
		botonera.add(comentar);
		panelComentarios.add(panelC);
		getContentPane().add(botonera,BorderLayout.EAST);
		getContentPane().add(arriba,BorderLayout.NORTH);
		getContentPane().add(panelComentarios,BorderLayout.SOUTH);
		
		panelCentral.setBackground(new Color(28,255,10));
		botonera.setBackground(new Color(28,255,10));
		puntuacion.setBackground(new Color(28,255,10));
		getContentPane().setBackground(new Color(28,255,10));
		arriba.setBackground(new Color(28,255,10));
		
		acertar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(panelCentral,sesion.adivinarFoto(foto, textoUsuario.getText()));
				puntuacion.setText("Numero Personas Adivinadas: "+foto.getNumeroPersonasAdivinadas());
				repaint();
			}
		});
		comentar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(panelCentral,sesion.comentar(textoUsuario.getText(), foto));
				comentarios.addElement(foto.getComentarios().get(foto.getComentarios().size()-1).comentarioFoto);	
				repaint();
			}
		});
	}
}
