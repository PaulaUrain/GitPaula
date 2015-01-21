package logicaPrograma;
import java.util.ArrayList;


public class Usuario {
//declaración de las variables que definen la clase y que serán utiles despues
	private String nombre=" ";
	private String apellido=" ";
	private String mail=" ";
	private String contrasenya=" ";
	private String fotoPerfil=" ";
	//el array que guarda una lista con todos los amigos que tiene el usuario
	private ArrayList <Usuario> amigos=new ArrayList<Usuario>();
//el array que guarda las fotos que ha ido subiendo el usuario
private ArrayList <Foto> fotos=new ArrayList<Foto>();
//en usuario habrá que guardar también un arraylist de intentos para aunque sea poder poner que fotos ya ha adivinado
private ArrayList <IntentoFoto> intentos=new ArrayList<IntentoFoto>();
//para indicar el grado de pricacidad de la cuenta, habrá tres estados 0=publico, 1=amigos de mis amigos, 2= solo amigos
	private int privacidad=0;
//guarda cuantas acertadas
	private int acertadas=0;
//indica si se esta conectado o no 
	private boolean conectado=false;
	
//para inicializar el usuario se le pasaran por parametro los identificadores
	public Usuario(String nombre,String apellido, String mail, String contrasenya,String fotoPerfil){
		this.nombre=nombre;
		this.apellido=apellido;
		this.mail=mail;
		this.contrasenya=contrasenya;
		this.fotoPerfil=fotoPerfil;
	}
//para cuando sea crear un usuario para la lista de amigos de un usuario, no nos interesa recuperar la contrasenya
	public Usuario(String nombre,String apellido, String mail,String fotoPerfil,int acertadas){
		this.nombre=nombre;
		this.apellido=apellido;
		this.mail=mail;
		this.fotoPerfil=fotoPerfil;
		this.acertadas=acertadas;
	}
//getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String email) {
		this.mail = email;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public ArrayList<Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(ArrayList<Usuario> amigos) {
		this.amigos = amigos;
	}

	public int getPrivacidad() {
		return privacidad;
	}

	public void setPrivacidad(int privacidad) {
		this.privacidad = privacidad;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}
	// metodo para añadir una foto
	public void anyadirFoto(Foto fot){
		this.fotos.add(fot);
	}
	//se añade un amigo al array de amigos
	public void anyadirAmigo(Usuario amigo){
		amigos.add(amigo);
	}
	
	public int getAcertadas() {
		return acertadas;
	}
	public void setAcertadas(int acertadas) {
		this.acertadas = acertadas;
	}
	//eliminas un amigo concreto
	public void eliminarAmigo(Usuario eliminar){
		for(int i=0;i<amigos.size();i++){
			if(amigos.get(i).getMail().equals(eliminar.getMail())){
				amigos.remove(i);
			}
		}
	}
	//eliminar una foto del array
	public void eliminarFoto(Foto eliminar){
		for(int i=0;i<getFotos().size();i++){
			if(getFotos().get(i).getPath().equals(eliminar.getPath())){
				getFotos().remove(i);
			}
		}
	}
	public String insertInto(){
	String insert=	"insert into usuario values('"+this.nombre+"', '"+this.apellido+"', '"+this.mail+"', '"+
				this.contrasenya+"', '"+this.privacidad+"', '"+this.acertadas+"', '"+this.conectado+"','"+this.fotoPerfil+"')";
	return insert;
	}
	public ArrayList <Foto> getFotos() {
		return fotos;
	}
	public void setFotos(ArrayList <Foto> fotos) {
		this.fotos = fotos;
	}
	public ArrayList <IntentoFoto> getIntentos() {
		return intentos;
	}
	//actualizamos el numero de intentos que ha hecho un usuario para una foto
	public void setIntentos(ArrayList <IntentoFoto> intentos) {
		this.intentos = intentos;
		acertadas=0;
		for(int i=0;i<intentos.size();i++){
			if(intentos.get(i).isAdivinado()){
				acertadas++;
			}
		}
	}
	public String toString(){
		return nombre+" "+mail;
		
	}
	public String getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
}

