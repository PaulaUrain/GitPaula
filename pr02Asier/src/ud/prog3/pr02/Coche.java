package ud.prog3.pr02;

/** Clase para definir instancias lógicas de coches con posición, dirección y velocidad.
 * @author Andoni Eguíluz
 * Facultad de Ingeniería - Universidad de Deusto (2014)
 */
public class Coche {
	protected double miVelocidad;  // Velocidad en pixels/segundo
	protected double miDireccionActual;  // Dirección en la que estoy mirando en grados (de 0 a 360)
	protected double posX;  // Posición en X (horizontal)
	protected double posY;  // Posición en Y (vertical)
	protected String piloto;  // Nombre de piloto
	private int masa=1;
	private int fuerzaAd=2000;// en kg*pixel/s^2
	private int fuerzaAt=1000;
	private double rozSuelo=15.5;
	private double rozAire=0.35;
	// Constructores
	
	public int getMasa() {
		return masa;
	}

	public void setMasa(int masa) {
		this.masa = masa;
	}

	public int getFuerzaAd() {
		return fuerzaAd;
	}

	public void setFuerzaAd(int fuerzaAd) {
		this.fuerzaAd = fuerzaAd;
	}

	public int getFuerzaAt() {
		return fuerzaAt;
	}

	public void setFuerzaAt(int fuerzaAt) {
		this.fuerzaAt = fuerzaAt;
	}

	public double getRozSuelo() {
		return rozSuelo;
	}

	public void setRozSuelo(double rozSuelo) {
		this.rozSuelo = rozSuelo;
	}

	public double getRozAire() {
		return rozAire;
	}

	public void setRozAire(double rozAire) {
		this.rozAire = rozAire;
	}

	public Coche() {
		miVelocidad = 0;
		miDireccionActual = 0;
		posX = 300;
		posY = 300;
	}
	
	/** Devuelve la velocidad actual del coche en píxeles por segundo
	 * @return	velocidad
	 */
	public double getVelocidad() {
		return miVelocidad;
	}

	/** Cambia la velocidad actual del coche
	 * @param miVelocidad
	 */
	public void setVelocidad( double miVelocidad ) {
		this.miVelocidad = miVelocidad;
	}

	public double getDireccionActual() {
		return miDireccionActual;
	}

	public void setDireccionActual( double dir ) {
		// if (dir < 0) dir = 360 + dir;
		if (dir > 360) dir = dir - 360;
		miDireccionActual = dir;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosicion( double posX, double posY ) {
		setPosX( posX );
		setPosY( posY );
	}
	
	public void setPosX( double posX ) {
		this.posX = posX; 
	}
	
	public void setPosY( double posY ) {
		this.posY = posY; 
	}
	
	public String getPiloto() {
		return piloto;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}


	/** Cambia la velocidad actual del coche
	 * @param aceleracion	Incremento/decremento de la velocidad en pixels/segundo
	 * @param tiempo	Tiempo transcurrido en segundos
	 */
	public void acelera( double aceleracion, double tiempo ) {
		miVelocidad = MundoJuego.calcVelocidadConAceleracion( miVelocidad, aceleracion, tiempo );
	}
	
	/** Cambia la dirección actual del coche
	 * @param giro	Angulo de giro a sumar o restar de la dirección actual, en grados (-180 a +180)
	 * 				Considerando positivo giro antihorario, negativo giro horario
	 */
	public void gira( double giro ) {
		setDireccionActual( miDireccionActual + giro );
	}
	
	/** Cambia la posición del coche dependiendo de su velocidad y dirección
	 * @param tiempoDeMovimiento	Tiempo transcurrido, en segundos
	 */
	public void mueve( double tiempoDeMovimiento ) {
		setPosX( posX + MundoJuego.calcMovtoX( miVelocidad, miDireccionActual, tiempoDeMovimiento ) );
		setPosY( posY + MundoJuego.calcMovtoY( miVelocidad, miDireccionActual, tiempoDeMovimiento ) );
	}
	
	public String toString() {
		return piloto + " (" + posX + "," + posY + ") - " +
			   "Velocidad: " + miVelocidad + " ## Dirección: " + miDireccionActual; 
	}
	/** Devuelve la fuerza de aceleración del coche, de acuerdo al motor definido en la práctica 2 
	 * @return Fuerza de aceleración en Newtixels 
	 */ 
	 public double fuerzaAceleracionAdelante() { 
	 if (miVelocidad<=-150) return fuerzaAd; 
	 else if (miVelocidad<=0) 
	 return fuerzaAd*(-miVelocidad/150*0.5+0.5); 
	 else if (miVelocidad<=250) 
	 return fuerzaAd*(miVelocidad/250*0.5+0.5); 
	 else if (miVelocidad<=750) 
	 return fuerzaAd; 
	 else return fuerzaAd*(-(miVelocidad-1000)/250); 
	 } 
	 public double fuerzaAceleracionAtras(){
		 if(miVelocidad<=-350)
			 return -fuerzaAt*((miVelocidad+500)/150);
		 else if(miVelocidad<=-200)
			 return -fuerzaAt;
		 else if(miVelocidad<=0)
			 return -fuerzaAt*(-0.7*miVelocidad/200+0.3);
		 else if(miVelocidad<=250)
			 return -fuerzaAt*((0.55*miVelocidad+75)/250);
		 else return -fuerzaAt*0.85;
	 }
}

