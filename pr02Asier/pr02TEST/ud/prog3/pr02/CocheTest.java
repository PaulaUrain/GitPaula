package ud.prog3.pr02;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CocheTest {
	private Coche cochePrueba;
	private static Logger logger = Logger.getLogger( Coche.class.getName() );
	@Before
	public void setUp(){
		cochePrueba=new Coche();
	}
	@After
	public void test() {
	//	fail("Not yet implemented");
	}
	@Test
	public void acelAtras(){
				double[] tablaVel = { -500, -425,-300, -250, -200, -100, 0, 125, 250, 500,1100 };
				double[] tablaFuerza = { 0, 0.5, 1, 1, 1, 0.65, 0.3, 0.575,0.85,0.85,0.85};
				for (int i=0;i<tablaVel.length;i++) {
				cochePrueba.setVelocidad(tablaVel[i] );
				logger.log(Level.INFO, tablaFuerza[i]*cochePrueba.getFuerzaAt()+" "+cochePrueba.fuerzaAceleracionAtras());
				assertEquals(tablaFuerza[i]*cochePrueba.getFuerzaAt(), cochePrueba.fuerzaAceleracionAtras(),0.00001);
				}
	}

}
