	package hotelRubroNegro;
	
	import java.time.LocalDateTime;
	import java.util.Scanner;
	
	public class Quarto {
		
		private int num_quarto;
		private CAT_QUA cat;
		private LocalDateTime DataCheckin;
		private LocalDateTime DataCheckout;
		private Scanner entrada = Scan.getInstance();
		
		
		public int getNum_quarto() {
			return num_quarto;
		}
		public void setNum_quarto(int num_quarto) {
			
			while(num_quarto < 1 || num_quarto > 10) {
				Scanner  entrada = Scan.getInstance();
				System.out.println("Digite novamente: ");
				num_quarto = entrada.nextInt();
				
			}
			
			this.num_quarto = num_quarto;
		}
		public CAT_QUA getCat() {
			return cat;
		}
		public void setCat(CAT_QUA cat) {
			this.cat = cat;
		}
		public LocalDateTime getDataCheckin() {
			return DataCheckin;
		}
		public void setDataCheckin(LocalDateTime dataCheckin) {
			
			while(this.DataCheckout != null && dataCheckin.isAfter(this.DataCheckout)) {
				System.out.println("Data de Checkin não pode ser depois da data de checkout. Digite novamente");
				
				dataCheckin = Arrumarhoras();
				
			} 
			
			this.DataCheckin = dataCheckin;
			
		}
		public LocalDateTime getDataCheckout() {
			return DataCheckout;
		}
		public void setDataCheckout(LocalDateTime dataCheckout) {
			
			
			while(dataCheckout.isBefore(DataCheckin)) {
				System.out.println("Data de checkout não pode ser antes da Data de checkin. Digite novamente");
				System.out.println("CheckIn -> " + DataCheckin);
				dataCheckout = Arrumarhoras();
			}
			
			this.DataCheckout = dataCheckout;
			                                                                              
		}
		
		
		private LocalDateTime Arrumarhoras() {
			
			System.out.println("Dia: ");
			int dia = entrada.nextInt();
			while(dia < 1 || dia > 31) {
				System.out.println("Digite novamente: ");
				dia = entrada.nextInt();
			}
			System.out.println("Mês: ");
			int mes = entrada.nextInt();
			while(mes < 1 || dia > 12) {
				System.out.println("Digite novamente: ");
				mes = entrada.nextInt();
			}
			System.out.println("Ano: ");
			int ano = entrada.nextInt();
			
			System.out.println("Hora: ");
			int hora = entrada.nextInt();
			while(hora < 0 || hora > 24) {
				System.out.println("Digite novamente: ");
				hora = entrada.nextInt();
			}
			System.out.println("minuto: ");
			int minuto = entrada.nextInt();
			while(minuto < 0 || dia > 59) {
				System.out.println("Digite novamente: ");
				minuto = entrada.nextInt();
			}
			
			LocalDateTime lt = LocalDateTime.of(ano, mes, dia, hora, minuto);
			return lt;
			
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Quarto [num_quarto=");
			builder.append(num_quarto);
			builder.append(", cat=");
			builder.append(cat);
			builder.append(", DataCheckin=");
			builder.append(DataCheckin);
			builder.append(", DataCheckout=");
			builder.append(DataCheckout);
			builder.append("]");
			return builder.toString();
		}
		
		
		
		
	
	}
