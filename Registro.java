	package hotelRubroNegro;
	
	public class Registro{
		
		
		public static Registro reg;
	private Reserva reservasCanceladas;
		
		private Registro() {
			this.reservasCanceladas = new Reserva(false);
		}
		
		public void inserir(Cliente cli, Quarto qua) {
			reservasCanceladas.inserir(cli, qua, STATUS.CANCELADO);
		}
		
		public void mostrarArvore() {
			reservasCanceladas.exibirArvore();
		}
		
		
		public static synchronized Registro getInstance() {
			if(reg == null) {
				reg = new Registro();
			
			}
		
	
				return reg;
	}
	
	
	}
