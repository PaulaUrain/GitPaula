package ud.prog3.pr02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.lang.reflect.Array;






import javax.swing.*;

/** Clase principal de minijuego de coche para Práctica 02 - Prog III
 * Ventana del minijuego.
 * @author Andoni Eguíluz
 * Facultad de Ingeniería - Universidad de Deusto (2014)
 */
public class VentanaJuego extends JFrame {
	private static final long serialVersionUID = 1L;  // Para serialización
	JPanel pPrincipal; // Panel del juego (layout nulo)
	JLabel lMensaje;
	MundoJuego miMundo;        // Mundo del juego
	CocheJuego miCoche;        // Coche del juego
	MiRunnable miHilo = null;  // Hilo del bucle principal de juego	
	Boolean[]teclasbool={false,false,false,false};//up, down,left, right
	private int estrellasPilladas=0;
	private int estrellasIdas=0;
	private int tope=10;
	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public VentanaJuego() {
		lMensaje=new JLabel();
		Font estilo=new Font(Font.SERIF,Font.BOLD,20 );
		lMensaje.setFont(estilo);
		// Liberación de la ventana por defecto al cerrar
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creación contenedores y componentes
		pPrincipal = new JPanel();
		JPanel resulta = new JPanel();
		// Formato y layouts
		pPrincipal.setLayout( null );
		pPrincipal.setBackground( Color.white );
		// Añadido de componentes a contenedores
		add( pPrincipal, BorderLayout.CENTER );
		resulta.add(lMensaje);
		add( resulta, BorderLayout.SOUTH );
		// Formato de ventana
		setSize( 1100, 700 );
		setResizable( false );
		// Escuchadores de botones
		pPrincipal.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: {
					teclasbool[0]=false;
					break;
					}
				case KeyEvent.VK_DOWN: {
					teclasbool[1]=false;
					break;
					}
				case KeyEvent.VK_LEFT: {
					teclasbool[2]=false;
					break;
					}
				case KeyEvent.VK_RIGHT: {
					teclasbool[3]=false;
					break;
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						teclasbool[0]=true;
						break;
					}
					case KeyEvent.VK_DOWN: {
						teclasbool[1]=true;
						break;
					}
					case KeyEvent.VK_LEFT: {
						teclasbool[2]=true;
						break;
					}
					case KeyEvent.VK_RIGHT: {
						teclasbool[3]=true;
						break;
					}
				}
			}
		});
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		// Cierre del hilo al cierre de la ventana
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	/** Programa principal de la ventana de juego
	 * @param args
	 */
	public static void main(String[] args) {
		// Crea y visibiliza la ventana con el coche
		try {
			final VentanaJuego miVentana = new VentanaJuego();
			SwingUtilities.invokeAndWait( new Runnable() {
				public void run() {
					miVentana.setVisible( true );
				}
			});
			miVentana.miMundo = new MundoJuego( miVentana.pPrincipal );
			miVentana.miMundo.creaCoche( 0, 0 );
			miVentana.miCoche = miVentana.miMundo.getCoche();
			miVentana.miCoche.setPiloto( "Fernando Alonso" );
			// Crea el hilo de movimiento del coche y lo lanza
			miVentana.miHilo = miVentana.new MiRunnable();  // Sintaxis de new para clase interna
			Thread nuevoHilo = new Thread( miVentana.miHilo );
			nuevoHilo.start();
		} catch (Exception e) {
			System.exit(1);  // Error anormal
		}
	}
	
	/** Clase interna para implementación de bucle principal del juego como un hilo
	 * @author Andoni Eguíluz
	 * Facultad de Ingeniería - Universidad de Deusto (2014)
	 */
	class MiRunnable implements Runnable {
		boolean sigo = true;
		int mensaje=0;
		public void run() {
			// Bucle principal forever hasta que se pare el juego...
			while (sigo) {
				// Mover coche
				miCoche.mueve( 0.040 );
				// Chequear choques
				// (se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones (esquinas)
				if (miMundo.hayChoqueHorizontal(miCoche)) // Espejo horizontal si choca en X
					miMundo.rebotaHorizontal(miCoche);
				if (miMundo.hayChoqueVertical(miCoche)) // Espejo vertical si choca en Y
					miMundo.rebotaVertical(miCoche);
				if(teclasbool[0]){
					miMundo.aplicarFuerza(miCoche.fuerzaAceleracionAdelante(), miCoche);
					//miCoche.acelera( -5, 1 );
				}//tengo que llamar a aplicarFuerza aunque las teclas no estén pulsadas
				//porque sino no actua la fuerza de rozamiento
				if(teclasbool[0]==false&&teclasbool[1]==false){
				miMundo.aplicarFuerza(0,miCoche);
				}
				if(teclasbool[1]){
					miMundo.aplicarFuerza(miCoche.fuerzaAceleracionAtras(), miCoche);
					//miCoche.acelera( -5, 1 );
				}
				if(teclasbool[2]){
					miCoche.gira( +10 );
				}
				if(teclasbool[3]){
					miCoche.gira( -10 );
				}
				//utilizo estrella para saber si se ha cogio alguna y así enseñar un mensaje
				//diferente durante 15 vueltas(15*40 ms); estrellasPilladas se actualiza 
				//con choquesConEstrella y estrellas idas va guardando cuantas estrellas se han ido
				int estrellas=estrellasPilladas;
				estrellasPilladas=estrellasPilladas+miMundo.choquesConEstrellas(miCoche);	
				estrellasIdas=estrellasIdas+miMundo.quitaYRotaEstrellas(6000);//6seg en ms
				if(estrellas<estrellasPilladas){
				mensaje=0;				
				}else{
				mensaje=mensaje+1;
				}
				if(mensaje<15){
					lMensaje.setText(" FOLLOW THE STARS!!");
				}else{
					lMensaje.setText("PUNTUACION: "+estrellasPilladas*5+" FALTAN : "+(tope-estrellasIdas));
				}
				if(estrellasIdas==tope){
					JOptionPane.showMessageDialog(pPrincipal, "Has perdido, PUNTUACION:"+estrellasPilladas*5);
					if (miHilo!=null) miHilo.acaba();
				}
				try {
					miMundo.creaEstrella();
					Thread.sleep(40 );
					
				} catch (Exception e) {
				}
			}
		}
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() {
			sigo = false;
		}
	};
	
}
