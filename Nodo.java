package hotelRubroNegro;

public class Nodo {
	
	private Nodo esq;
	private Nodo dir;
	private Nodo pai;
	protected Cliente cliente;
	protected Quarto quarto;
	private STATUS situacao;
	private COR cor;
	
	
	public Nodo(Cliente cli, Quarto qua, STATUS sit) {
		
		this.cliente = cli;
		this.quarto = qua;
		this.cor = COR.VERMELHO;
		this.situacao = sit;
		this.esq = this.dir = this.pai = null;
				
	}
	
	public Nodo getEsq() {
		return esq;
	}
	public void setEsq(Nodo esq) {
		this.esq = esq;
	}
	public Nodo getDir() {
		return dir;
	}
	public void setDir(Nodo dir) {
		this.dir = dir;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Quarto getQuarto() {
		return quarto;
	}
	public void setQuarto(Quarto quarto) {
		this.quarto = quarto;
	}
	
	public COR getCor() {
		return cor;
	}
	public void setCor(COR cor) {
		this.cor = cor;
	}

	public Nodo getPai() {
		return pai;
	}

	public void setPai(Nodo pai) {
		this.pai = pai;
	}

	public STATUS getSituacao() {
		return situacao;
	}

	public void setSituacao(STATUS situacao) {
		this.situacao = situacao;
	}
	
	
	
	
	

}
