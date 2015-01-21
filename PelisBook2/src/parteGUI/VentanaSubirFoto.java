package parteGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import logicaPrograma.Sesion;

public class VentanaSubirFoto extends JFrame {
	private Sesion sesion;
	private JTextField tTituloFoto=new JTextField(" ");
	private JLabel lTituloFoto=new JLabel("Titulo Foto");
	private JTextField[]tPalabras=new JTextField[10];
	private JLabel[]lPalabras=new JLabel[10];
	private String pathFoto=null;
	private JButton seleccionar=new JButton("Seleccionar Foto");
	private JLabel fotoS=new JLabel(" ");
	private JButton finalizar=new JButton("Finalizar");
	private JPanel panelCentral=new JPanel();
	public VentanaSubirFoto(Sesion sesion){
		setVisible(true);
		setSize(getToolkit().getScreenSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.sesion=sesion;
		panelCentral.setLayout(new GridLayout(0,2));
		panelCentral.add(lTituloFoto);
		panelCentral.add(tTituloFoto);
		for(int i=0;i<tPalabras.length;i++){
			tPalabras[i]=new JTextField(" ");
			lPalabras[i]=new JLabel("Palabra Prohibida "+(i+1));
			lPalabras[i].setBackground(new Color(28,255,10));
			panelCentral.add(lPalabras[i]);
			panelCentral.add(tPalabras[i]);
		}
		panelCentral.add(seleccionar);
		panelCentral.add(fotoS);
		getContentPane().add(panelCentral,BorderLayout.CENTER);
		getContentPane().add(finalizar,BorderLayout.SOUTH);
		
		panelCentral.setBackground(new Color(28,255,10));
		getContentPane().setBackground(new Color(28,255,10));
		
		seleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				subirFoto();
				fotoS.setText(pathFoto);
				repaint();
			}			
		});
		finalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean noR=false;
				String[]comentarios=new String[10];
				if(pathFoto==null){
					JOptionPane.showMessageDialog(panelCentral, "No has seleccionado una imagen, por favor selecciona");
					return;
				}				
				if(tTituloFoto.getText().equals(" ")){
					JOptionPane.showMessageDialog(panelCentral, "Introduzca un titulo para la foto");
					return;
				}
				for(int i=0;i<lPalabras.length;i++){
					if(tPalabras[i].getText().equals(" ")){
						noR=true;
						comentarios[i]=tPalabras[i].getText();
					}else{
						comentarios[i]=tPalabras[i].getText();
					}
				}
				if(noR){
					int returnVal= JOptionPane.showConfirmDialog(panelCentral, "Hay palabras prohibidas sin rellenar,¿quieres continuar?");
					if(returnVal==JOptionPane.YES_OPTION){
						sesion.meterFoto(tTituloFoto.getText(),comentarios,pathFoto,true);
						for(int i=0;i<tPalabras.length;i++){
							tPalabras[i].setText(" ");
						}
						tTituloFoto.setText(" ");
						pathFoto=null;
						fotoS.setText(" ");
						setVisible(false);
						return;
					}
					else if(returnVal==JOptionPane.CANCEL_OPTION){
						return;
					}else{
						return;
					}
				}
				sesion.meterFoto(tTituloFoto.getText(),comentarios,pathFoto,true);
				for(int i=0;i<tPalabras.length;i++){
					tPalabras[i].setText(" ");
				}
				tTituloFoto.setText(" ");
				pathFoto=null;
				fotoS.setText(" ");
				setVisible(false);
			}
		});
	}
	public void subirFoto() {
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
				pathFoto=chooser.getSelectedFile().getAbsolutePath();
			}else{
				JOptionPane.showMessageDialog(this, "Selecciona una imagen con formato JPEG");
				subirFoto();
			}
		}
	}
}
