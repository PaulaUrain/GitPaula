import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** M�todos �tiles para base de datos.
 * Clase con m�todos est�ticos para gestionar una sola base de datos
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public class BaseDeDatos {

	// ------------------------------------
	// VALIDO PARA CUALQUIER BASE DE DATOS
	// ------------------------------------
	
	private static Connection connection = null;
	private static Statement statement = null;

	/** Inicializa una BD SQLITE y devuelve una conexi�n con ella. Debe llamarse a este 
	 * m�todo antes que ning�n otro, y debe devolver no null para poder seguir trabajando con la BD.
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexi�n con la base de datos indicada. Si hay alg�n error, se devuelve null
	 */
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    connection = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			statement = connection.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
		    return connection;
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}
	
	/** Cierra la conexi�n con la Base de Datos
	 */
	public static void close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** Devuelve la conexi�n si ha sido establecida previamente (#initBD()).
	 * @return	Conexi�n con la BD, null si no se ha establecido correctamente.
	 */
	public static Connection getConnection() {
		return connection;
	}
	
	/** Devuelve una sentencia para trabajar con la BD,
	 * si la conexi�n si ha sido establecida previamente (#initBD()).
	 * @return	Sentencia de trabajo con la BD, null si no se ha establecido correctamente.
	 */
	public static Statement getStatement() {
		return statement;
	}

	// ------------------------------------
	// PARTICULAR DEL CATALOGO MULTIMEDIA
	// ------------------------------------
	
	/** Crea una tabla de cat�logo multimedia en una base de datos, si no exist�a ya.
	 * Debe haberse inicializado la conexi�n correctamente.
	 */
	public static void crearTablaBD() {
		if (statement==null) return;
		try {
			statement.executeUpdate("create table usuario " +
				"(nombre string, apellido string, mail string" +
				", contrasenya string, privacidad int,acertadas int, conectado boolean)");
		} catch (SQLException e) {
			// Si hay excepci�n es que la tabla ya exist�a (lo cual es correcto)
			// e.printStackTrace();  
		}
		try {
			statement.executeUpdate("create table amigos " +
					"(mail1 string, mail2 string, confirmado boolean, fecha long)");
		} catch (SQLException e) {
			// Si hay excepci�n es que la tabla ya exist�a (lo cual es correcto)
			// e.printStackTrace();  
		}
		try {
			statement.executeUpdate("create table adivinar " +
					"(mail string, pathFoto string, adivinado boolean, numeroIntento int,fecha long)");	
		} catch (SQLException e) {
			// Si hay excepci�n es que la tabla ya exist�a (lo cual es correcto)
			// e.printStackTrace();  
		}
		try {
			statement.executeUpdate("create table registro_comentario " +
					"(mail string, pathFoto string, comentario string, fecha long)");
//ponerle fecha al comentario para ordenar, no lo comento para que salga error y acordarme
		} catch (SQLException e) {
			// Si hay excepci�n es que la tabla ya exist�a (lo cual es correcto)
			// e.printStackTrace();  
		}
//Creo que nos falta crear la tabla para registrar las fotos
		try {
			statement.executeUpdate("create table foto " +
					"(titulo string, palabraP1 string,palabraP2 string,palabraP3 string,"
					+ "palabraP4 string,palabraP5 string,palabraP6 string,palabraP7 string,palabraP8 string,palabraP9 string, "
					+ "palabraP10 string,path string, mailUsuario string, numeroPersonasAdivinadas int,"
					+ "fecha long)");	
		} catch (SQLException e) {
			// Si hay excepci�n es que la tabla ya exist�a (lo cual es correcto)
			// e.printStackTrace();  
		}
	}
	public static void insertInto(String insert){
		try {
			statement.executeUpdate(insert);	
		} catch (SQLException e) {
			// Si hay excepci�n es que la tabla ya exist�a (lo cual es correcto)
			 e.printStackTrace();  
		}
	}
}

