package ud.prog3.pr02;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Date;

import javax.swing.ImageIcon;

public class JLabelEstrella extends JLabel {
		private static final long serialVersionUID = 1L;  // Para serialización
		public static final int TAMANYO_ESTRELLA = 40;  // píxels (igual ancho que algo)
		public static final int RADIO_ESFERA_ESTRELLA = 17;  // Radio en píxels del bounding circle del coche (para choques)
		private static final boolean DIBUJAR_ESFERA_ESTRELLA = true;  // Dibujado (para depuración) del bounding circle de choque del coche
		private  Date hora;
		private int posX=0;
		private int posY=0;
		public int getPosX() {
			return posX;
		}

		public void setPosX(int posX) {
			this.posX = posX;
		}

		public int getPosY() {
			return posY;
		}

		public void setPosY(int posY) {
			this.posY = posY;
		}

		/** 
		 */
		public JLabelEstrella() {
			// Esto se haría para acceder por sistema de ficheros
			// 		super( new ImageIcon( "bin/ud/prog3/pr00/coche.png" ) );
			// Esto se hace para acceder tanto por recurso (jar) como por fichero
			hora=new Date();
			try {
				setIcon( new ImageIcon( JLabelCoche.class.getResource( "img/estrella.png" ).toURI().toURL() ) );
			} catch (Exception e) {
				System.err.println( "Error en carga de recurso: coche.png no encontrado" );
				e.printStackTrace();
			}
			setBounds( 0, 0, TAMANYO_ESTRELLA, TAMANYO_ESTRELLA);
			// Esto sería útil cuando hay algún problema con el gráfico: borde de color del JLabel
			// setBorder( BorderFactory.createLineBorder( Color.blue, 4 ));
		}
		
		public  Date getHora() {
			return hora;
		}

		// giro
		private double miGiro = Math.PI/2;
		/** Cambia el giro del JLabel
		 * @param gradosGiro	Grados a los que tiene que "apuntar" la estrella,
		 * 						considerados con el 0 en el eje OX positivo,
		 * 						positivo en sentido antihorario, negativo horario.
		 */
		/*public void setGiro( double gradosGiro ) {
			// De grados a radianes...
			miGiro = gradosGiro/180*Math.PI;
			// El giro en la pantalla es en sentido horario (inverso):
			miGiro = -miGiro;  // Cambio el sentido del giro
			// Y el gráfico del coche apunta hacia arriba (en lugar de derecha OX)
			miGiro = miGiro + Math.PI/2; // Sumo 90º para que corresponda al origen OX
		}*/
		/**
		 * me he cargado tu metodo porque no me funcionaba el que girase las estrellas, 
		 * basicamente lo que he hecho es que los grados se marquen en grados y no en radianes 
		 * entonces cada vez que se hace un giro lo que hago es sumar el giro a los
		 * grados que habia (no hago la correccion de los 90º porque no tiene influencia)
		 * luego en el paintComponent lo que he modificado es que como el angulo esta en grados
		 * para hacer el rotate lo tengo que pasar a radianes con el Math. 
		 * @param gradosGiro
		 */
		public void setGiro(double gradosGiro){
			miGiro=this.miGiro+gradosGiro;
		}
		// Redefinición del paintComponent para que se escale y se rote el gráfico
		@Override
		protected void paintComponent(Graphics g) {
//			super.paintComponent(g);   // En este caso no nos sirve el pintado normal de un JLabel
			Image img = ((ImageIcon)getIcon()).getImage();
			Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
			// Escalado más fino con estos 3 parámetros:
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
			// Prepara rotación (siguientes operaciones se rotarán)
	        g2.rotate( Math.toRadians(miGiro), TAMANYO_ESTRELLA/2, TAMANYO_ESTRELLA/2 ); // getIcon().getIconWidth()/2, getIcon().getIconHeight()/2 );
	        // Dibujado de la imagen
	        g2.drawImage( img, 0, 0, TAMANYO_ESTRELLA, TAMANYO_ESTRELLA, null );
	        if (DIBUJAR_ESFERA_ESTRELLA) g2.drawOval( TAMANYO_ESTRELLA/2-RADIO_ESFERA_ESTRELLA, TAMANYO_ESTRELLA/2-RADIO_ESFERA_ESTRELLA,
	        		RADIO_ESFERA_ESTRELLA*2, RADIO_ESFERA_ESTRELLA*2 );
		}
}


