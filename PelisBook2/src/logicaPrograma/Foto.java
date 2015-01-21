package logicaPrograma;
import java.util.ArrayList;


public class Foto {
	/*
	 * tituloFoto contiene el nombre correcto de la foto, lo inserta el usuario
	 * Palabras prohibidas contiene las palabras que si aparecen en los comentarios se bloquear�n para no permitir pistas
	 * nivel-> dependiendo del n�mero de intentos la foto se ver� de una forma, acertar con m�s nivel son m�s puntos
	 */
	private String tituloFoto=" ";
	private int numeroPalabrasProhibidas=10;
	private String[] palabrasProhibidas=new String[numeroPalabrasProhibidas];
	private String path=" ";
	private long fecha=0;
	private String emailUsuario=" ";
	private int numeroPersonasAdivinadas=0;
	private ArrayList <Comentario>comentarios= new ArrayList<Comentario>();
	public Foto(String tituloFoto,String [] palabrasProhibidas,String path,String emailUsuario, long fecha,int numeroPersonas){
		this.setTituloFoto(tituloFoto);
		for(int i=0;i<numeroPalabrasProhibidas;i++){
			this.getPalabrasProhibidas()[i]=palabrasProhibidas[i];
		}
		this.path=path;
		this.setEmailUsuario(emailUsuario);
		this.setFecha(fecha);
		this.numeroPersonasAdivinadas=numeroPersonas;
	}
	public Foto(String tituloFoto,String path,String emailUsuario, long fecha){
		this.setTituloFoto(tituloFoto);
		this.path=path;
		this.setEmailUsuario(emailUsuario);
		this.setFecha(fecha);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getNumeroPersonasAdivinadas() {
		return numeroPersonasAdivinadas;
	}
	public void setNumeroPersonasAdivinadas(int numeroPersonasAdivinadas) {
		this.numeroPersonasAdivinadas = numeroPersonasAdivinadas;
	}
	public String insertInto(){
		String insert="insert into foto values('"+this.getTituloFoto()+"', '"+getPalabrasProhibidas()[0]+"', '"+getPalabrasProhibidas()[1]+"', '"+
				getPalabrasProhibidas()[2]+"', '"+getPalabrasProhibidas()[3]+"', '"+getPalabrasProhibidas()[4]+"', '"+getPalabrasProhibidas()[5]+"'"
						+ ", '"+getPalabrasProhibidas()[6]+"', '"+getPalabrasProhibidas()[7]+"', '"+getPalabrasProhibidas()[8]+"'"
								+ ", '"+getPalabrasProhibidas()[9]+"', '"+this.path+"', '"+this.getEmailUsuario()+"', '"+0+"',"
										+ "'"+this.getFecha()+"')";
		return insert;
	}
	public String[] getPalabrasProhibidas() {
		return palabrasProhibidas;
	}
	public void setPalabrasProhibidas(String[] palabrasProhibidas) {
		this.palabrasProhibidas = palabrasProhibidas;
	}
	public ArrayList <Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(ArrayList <Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	public String getTituloFoto() {
		return tituloFoto;
	}
	public void setTituloFoto(String tituloFoto) {
		this.tituloFoto = tituloFoto;
	}
	//metodo para actualizar el numero de personas que han acertado una foto, cuando alguien acierta +1
	public String sumarAcierto(){
		this.numeroPersonasAdivinadas++;
		String sentencia="update foto set numeroPersonasAdivinadas='"+numeroPersonasAdivinadas+"'"+
		" where (path='"+this.path+"')";
		return sentencia;
	}
	public long getFecha() {
		return fecha;
	}
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}	
}