package Pruebas;

public class PruebaSplit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dias = "Lunes Martes Miercoles Jueves Viernes Sabado Domingo";
	    String diaArray[] = dias.split(" ");
			
	    System.out.println("--Ejemplo 1--");
	    for(String dia : diaArray){
		System.out.println(dia);
	    }
	}

}
