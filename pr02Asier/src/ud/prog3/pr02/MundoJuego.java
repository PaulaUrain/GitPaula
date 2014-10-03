package ud.prog3.pr02;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JPanel;

/** "Mundo" del juego del coche.
 * Incluye las físicas para el movimiento y los choques de objetos.
 * Representa un espacio 2D en el que se mueven el coche y los objetos de puntuación.
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class MundoJuego {
	private JPanel panel;  // panel visual del juego
	CocheJuego miCoche;    // Coche del juego
	ArrayList <JLabelEstrella> estrellasMun=new ArrayList <JLabelEstrella>();
	/** Construye un mundo de juego
	 * @param panel	Panel visual del juego
	 */
	public MundoJuego( JPanel panel ) {
		this.panel = panel;
	}

	/** Crea un coche nuevo y lo añade al mundo y al panel visual
	 * @param posX	Posición X de pixel del nuevo coche
	 * @param posY	Posición Y de píxel del nuevo coche
	 */
	public void creaCoche( int posX, int posY ) {
		// Crear y añadir el coche a la ventana
		miCoche = new CocheJuego();
		miCoche.setPosicion( posX, posY );
		panel.add( miCoche.getGrafico() );  // Añade al panel visual
		miCoche.getGrafico().repaint();  // Refresca el dibujado del coche
		creaEstrella();
	}
	
	/** Devuelve el coche del mundo
	 * @return	Coche en el mundo. Si no lo hay, devuelve null
	 */
	public CocheJuego getCoche() {
		return miCoche;
	}

	/** Calcula si hay choque en horizontal con los límites del mundo
	 * @param coche	Coche cuyo choque se comprueba con su posición actual
	 * @return	true si hay choque horizontal, false si no lo hay
	 */
	public boolean hayChoqueHorizontal( CocheJuego coche ) {
		return (coche.getPosX() < JLabelCoche.RADIO_ESFERA_COCHE-JLabelCoche.TAMANYO_COCHE/2 
				|| coche.getPosX()>panel.getWidth()-JLabelCoche.TAMANYO_COCHE/2-JLabelCoche.RADIO_ESFERA_COCHE );
	}
	
	/** Calcula si hay choque en vertical con los límites del mundo
	 * @param coche	Coche cuyo choque se comprueba con su posición actual
	 * @return	true si hay choque vertical, false si no lo hay
	 */
	public boolean hayChoqueVertical( CocheJuego coche ) {
		return (coche.getPosY() < JLabelCoche.RADIO_ESFERA_COCHE-JLabelCoche.TAMANYO_COCHE/2 
				|| coche.getPosY()>panel.getHeight()-JLabelCoche.TAMANYO_COCHE/2-JLabelCoche.RADIO_ESFERA_COCHE );
	}

	/** Realiza un rebote en horizontal del objeto de juego indicado
	 * @param coche	Objeto que rebota en horizontal
	 */
	public void rebotaHorizontal( CocheJuego coche ) {
		// System.out.println( "Choca X");
		double dir = coche.getDireccionActual();
		dir = 180-dir;   // Rebote espejo sobre OY (complementario de 180)
		if (dir < 0) dir = 360+dir;  // Corrección para mantenerlo en [0,360)
		coche.setDireccionActual( dir );
	}
	
	/** Realiza un rebote en vertical del objeto de juego indicado
	 * @param coche	Objeto que rebota en vertical
	 */
	public void rebotaVertical( CocheJuego coche ) {
		// System.out.println( "Choca Y");
		double dir = miCoche.getDireccionActual();
		dir = 360 - dir;  // Rebote espejo sobre OX (complementario de 360)
		miCoche.setDireccionActual( dir );
	}
	
	/** Calcula y devuelve la posición X de un movimiento
	 * @param vel    	Velocidad del movimiento (en píxels por segundo)
	 * @param dir    	Dirección del movimiento en grados (0º = eje OX positivo. Sentido antihorario)
	 * @param tiempo	Tiempo del movimiento (en segundos)
	 * @return
	 */
	public static double calcMovtoX( double vel, double dir, double tiempo ) {
		return vel * Math.cos(dir/180.0*Math.PI) * tiempo;
	}
	
	/** Calcula y devuelve la posición X de un movimiento
	 * @param vel    	Velocidad del movimiento (en píxels por segundo)
	 * @param dir    	Dirección del movimiento en grados (0º = eje OX positivo. Sentido antihorario)
	 * @param tiempo	Tiempo del movimiento (en segundos)
	 * @return
	 */
	public static double calcMovtoY( double vel, double dir, double tiempo ) {
		return vel * -Math.sin(dir/180.0*Math.PI) * tiempo;
		// el negativo es porque en pantalla la Y crece hacia abajo y no hacia arriba
	}
	
	/** Calcula el cambio de velocidad en función de la aceleración
	 * @param vel		Velocidad original
	 * @param acel		Aceleración aplicada (puede ser negativa) en pixels/sg2
	 * @param tiempo	Tiempo transcurrido en segundos
	 * @return	Nueva velocidad
	 */
	public static double calcVelocidadConAceleracion( double vel, double acel, double tiempo ) {
		return vel + (acel*tiempo);
	}
	public static double calcFuerzaRozamiento( double masa, double coefRozSuelo, 
			 double coefRozAire, double vel ) { 
			 double fuerzaRozamientoAire = coefRozAire * (-vel); // En contra del movimiento 
			 double fuerzaRozamientoSuelo = masa * coefRozSuelo * ((vel>0)?(-1):1); // Contra mvto 
			 return fuerzaRozamientoAire + fuerzaRozamientoSuelo; 
			 }
	public static double calcAceleracionConFuerza( double fuerza, double masa ) { 
		 // 2ª ley de Newton: F = m*a ---> a = F/m 
		 return fuerza/masa; 
		 } 
	public static void aplicarFuerza( double fuerza, Coche coche ) { 
		 double fuerzaRozamiento = calcFuerzaRozamiento( coche.getMasa() , 
		 coche.getRozSuelo(), coche.getRozAire(), coche.getVelocidad() ); 
		 double aceleracion = calcAceleracionConFuerza( fuerza+fuerzaRozamiento, coche.getMasa()); 
		 if (fuerza==0) { 
		 // No hay fuerza, solo se aplica el rozamiento 
		 double velAntigua = coche.getVelocidad(); 
		 coche.acelera( aceleracion, 0.04 ); 
		 if (velAntigua>=0 && coche.getVelocidad()<0 
		 || velAntigua<=0 && coche.getVelocidad()>0) { 
		 coche.setVelocidad(0); // Si se está frenando, se para (no anda al revés) 
		 } 
		 } else { 
		 coche.acelera( aceleracion, 0.04 );
		 }
	}
	/** Si han pasado más de 1,2 segundos desde la última,
	* crea una estrella nueva en una posición aleatoria y la añade al mundo y al panel visual */
	public void creaEstrella(){
		if(estrellasMun.size()==0){
			crear();
		}
		else{
			long tiempoActual=tiempoActual();
			long ultimaEstrella=estrellasMun.get(estrellasMun.size()-1).getHora().getTime();
		if((tiempoActual-ultimaEstrella)>1200){//1200 porque long viene en ms y 1,2 s son 1200 ms
			crear();
			}
		}
	}
	public void crear(){
		JLabelEstrella estrella=new JLabelEstrella();
		estrella.setLocation(crearPosicion());
		panel.add( estrella);  // Añade al panel visual
		estrella.repaint();  // Refresca el dibujado de la estrella
		estrellasMun.add(estrella);//anyade a la lista de estrellas 
	}
	public Point crearPosicion(){
		Random r=new Random();
		int x=r.nextInt(panel.getWidth()-JLabelEstrella.TAMANYO_ESTRELLA);
		int y=r.nextInt(panel.getHeight()-JLabelEstrella.TAMANYO_ESTRELLA);
		Point p=new Point(x,y);
		return p;
	}
	//Para devolver el tiempo actual en ms.
	public long tiempoActual(){
		Date fecha=new Date();
		return fecha.getTime();
	}
	/** Quita todas las estrellas que lleven en pantalla demasiado tiempo
	* y rota 10 grados las que sigan estando
	* @param maxTiempo Tiempo máximo para que se mantengan las estrellas (msegs)
	* @return Número de estrellas quitadas */
	public int quitaYRotaEstrellas( long maxTiempo ){
		int contEstrellasquitar=0;
		for(int i=0;i<estrellasMun.size();i++){
			long tiempoActual=tiempoActual();
			long tiempoEstrella=estrellasMun.get(i).getHora().getTime();
			if((tiempoActual-tiempoEstrella)>maxTiempo){
				quitarEstrellas(i);
				i--;//porque si no la siguiente estrella se la saltaria al comprobar 
				//ya que si se elimina la posicion n(i=n), la estrella en n+1 pasa a n
				//y el contador pasa a i+1
				contEstrellasquitar++;
			}
			else {
				estrellasMun.get(i).setGiro(10);//para que la estrella gire 10º
				estrellasMun.get(i).repaint();
				panel.repaint();
			}
		}
		return contEstrellasquitar;
	}
	public void quitarEstrellas(int posicion){
		panel.remove(estrellasMun.get(posicion));
		panel.repaint();
		estrellasMun.remove(posicion);
	}
	/** Calcula si hay choques del coche con alguna estrella (o varias). Se considera el choque si
	* se tocan las esferas lógicas del coche y la estrella. Si es así, las elimina.
	* @return Número de estrellas eliminadas
	*/
	public int choquesConEstrellas(CocheJuego coche){
		int choqueEstrellas=0;
		for(int i=0;i<estrellasMun.size();i++){
			if(hayChoqueEstrella(coche,i)){
				quitarEstrellas(i);
				choqueEstrellas++;
			}
		}
		return choqueEstrellas;
	}
	/** Calcula si hay choque en horizontal con los límites del mundo
	 * @param coche	Coche cuyo choque se comprueba con su posición actual
	 * @return	true si hay choque horizontal, false si no lo hay
	 */
	public boolean hayChoqueEstrella( CocheJuego coche,int posicion ) {
		boolean choque=false;
		//no cumple que sea justo con los borDes pero es la forma que mejor he encontraDo para que haga su
		//funcion
		Rectangle prueba=new Rectangle((int)coche.getPosX(),(int)coche.getPosY(),coche.getGrafico().RADIO_ESFERA_COCHE,coche.getGrafico().RADIO_ESFERA_COCHE);
		Rectangle prueba2=new Rectangle((int)estrellasMun.get(posicion).getX(),(int)estrellasMun.get(posicion).getY(),estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA,estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA);
		choque=prueba.intersects(prueba2);
		
		/*choque=((coche.getPosX()+coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getX()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA
				&&coche.getPosY()+coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getY()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA
				&&coche.getPosY()-coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getY()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA
				&&coche.getPosX()+coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getX()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				//primer caso posible choque
				||(coche.getPosY()-coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getY()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA
				&&coche.getPosX()+coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getX()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA
				&&coche.getPosX()-coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getX()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA
				&&coche.getPosY()-coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getY()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA));
		*/
		/*choque=((coche.getPosX()+coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getX()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				&&(coche.getPosX()+coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getX()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				&&(coche.getPosX()-coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getX()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				&&(coche.getPosX()-coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getX()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				&&(coche.getPosY()+coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getY()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				&&(coche.getPosY()+coche.getGrafico().RADIO_ESFERA_COCHE>estrellasMun.get(posicion).getY()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				&&(coche.getPosY()-coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getY()+estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				&&(coche.getPosY()-coche.getGrafico().RADIO_ESFERA_COCHE<estrellasMun.get(posicion).getY()-estrellasMun.get(posicion).RADIO_ESFERA_ESTRELLA)
				);*/
		
		return choque;
	}
}
