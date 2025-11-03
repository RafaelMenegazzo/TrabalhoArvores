package hotelRubroNegro;

import java.util.Scanner;

public class Scan {
	
	public static Scanner entrada;
	
	
	public static Scanner getInstance() {
		
		if(entrada == null) {
			 entrada = new Scanner(System.in);
		}
		
		return entrada;
		
	}

}
