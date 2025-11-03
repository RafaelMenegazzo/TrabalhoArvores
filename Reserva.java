package hotelRubroNegro;

import java.util.Scanner;

public class Reserva {
	
	private Nodo raiz;
	private Registro reg;
	private int capacidade = 10;
	private int[] cap_mes= new int[12];
	private int[] can_mes = new int[12];
	private int[] qua_res = new int[10];
	
	
	
	
	
	public int[] getQua_res() {
		return qua_res;
	}

	public void setQua_res(int[] qua_res) {
		this.qua_res = qua_res;
	}

	public int[] getCap_mes() {
		return cap_mes;
	}

	public void setCap_mes(int[] cap_mes) {
		this.cap_mes = cap_mes;
	}

	public int[] getCan_mes() {
		return can_mes;
	}

	public void setCan_mes(int[] can_mes) {
		this.can_mes = can_mes;
	}

	public Reserva() {
		this(true);
	}
	
	public Reserva(boolean iniciarRegistro) {
		this.raiz = null;
		
		if(iniciarRegistro) {
			reg = Registro.getInstance();
		}
	}
	
	
	private boolean verificarCapacidade(int mes) {
		
		//verifica a capacidade no array
		if(cap_mes[mes-1] == capacidade) {
			System.out.println("CAPACIDADE CHEIA");
			System.out.println("RESERVA DISPONIVEL SOMENTE PARA O PRÒXIMO MÊS");
			//retorno de false para poder realizar o re-agendamento
			return false;
			
		}
		//verifica se a capacidade é acima de setenta por cento e Ativa o Alerta
		//Ainda a reserva pode ser feita
		else if(cap_mes[mes-1] > capacidade * 0.7) {
			System.out.println("!ALERTA!");
			System.out.println("CAPACIDADE ACIMA DE 70% NO MẼS " + mes);
			return true;
		}
		
		return true;
		
	}
	
	public boolean verificarDisponibilidade(Nodo novoNodo){
		Nodo aux = raiz;
		
		return disponibilidade(aux, novoNodo);
		
	}
	
		private boolean disponibilidade(Nodo aux, Nodo novoNodo) {
			
			if(aux == null) {
				return true;
			}
			
			//verifica disponibilidade do quarto
			if(aux.getQuarto().getNum_quarto() == novoNodo.getQuarto().getNum_quarto()) {
				
				
				//Verifica se não há sobreposição. Se houver retorna falso
				if(!(novoNodo.getQuarto().getDataCheckout().isBefore(aux.getQuarto().getDataCheckin())
						|| novoNodo.getQuarto().getDataCheckin().isAfter(aux.getQuarto().getDataCheckout()))) {
					
					return false;
				}
			
			}
				
			boolean dispEsq = disponibilidade(aux.getEsq(), novoNodo);
			boolean dispDir = disponibilidade(aux.getDir(), novoNodo);
				
			
			
			return dispEsq && dispDir;
			
	}
		
		private Nodo reagendarData(boolean cap, Nodo nodo) {
			if (!cap) {
		        System.out.println("Capacidade cheia: reagendando automaticamente para o próximo mês...");
		        Quarto q = nodo.getQuarto();

		        // Reagenda +1 mês
		        q.setDataCheckout(q.getDataCheckout().plusMonths(1));
		        q.setDataCheckin(q.getDataCheckin().plusMonths(1));

		        // Se ainda não houver disponibilidade, muda o quarto automaticamente
		        if (!verificarDisponibilidade(nodo)) {
		            nodo = mudarQuarto(nodo, false, true);
		        }

		        System.out.println("Nova data reagendada: "
		                + q.getDataCheckin() + " até " + q.getDataCheckout());
		    }
		    return nodo;
		}
		
		private Nodo mudarQuarto(Nodo nodo, boolean disp, boolean auto) {

		    if (!disp) {
		        boolean[] quaPossiveis = new boolean[capacidade];

		        System.out.println("O quarto " + nodo.getQuarto().getNum_quarto() + " está ocupado.");

		        Quarto original = nodo.getQuarto();

		        for (int i = 1; i <= capacidade; i++) {
		           
		            Quarto teste = new Quarto();
		            teste.setNum_quarto(i);
		            teste.setCat(original.getCat());
		            teste.setDataCheckin(original.getDataCheckin());
		            teste.setDataCheckout(original.getDataCheckout());

		            Nodo temp = new Nodo(nodo.getCliente(), teste, nodo.getSituacao());
		            quaPossiveis[i - 1] = verificarDisponibilidade(temp);
		        }

		        if (auto) {
		            
		            for (int k = 0; k < capacidade; k++) {
		                if (quaPossiveis[k]) {
		                    nodo.getQuarto().setNum_quarto(k + 1);
		                    disp = true;
		                    break;
		                }
		            }
		        } else {
		            for (int k = 0; k < capacidade; k++) {
		                System.out.print("Quarto " + (k + 1));
		                if (quaPossiveis[k]) {
		                    System.out.println(" DISPONÍVEL");
		                } else {
		                    System.out.println(" OCUPADO");
		                }
		            }

		            System.out.println("Selecione o quarto: ");
		            Scanner entrada = Scan.getInstance();
		            int quarto;
		            do {
		                quarto = entrada.nextInt();
		            } while (quarto < 1 || quarto > capacidade || !quaPossiveis[quarto - 1]);

		            nodo.getQuarto().setNum_quarto(quarto);
		            disp = true;
		        }
		    }

		    return nodo;
		}


	public void inserir(Cliente cli, Quarto qua, STATUS sit) {
		
		Nodo novoNodo = new Nodo(cli, qua, sit);
		novoNodo.setCor(COR.VERMELHO);
		
		//verifica a capacidade máxima referente ao período
		boolean cap = verificarCapacidade(novoNodo.getQuarto().getDataCheckin().getMonthValue());
		
		novoNodo = reagendarData(cap, novoNodo);
		
		//verifica  a disponibilidade de quartos
		boolean disp = verificarDisponibilidade(novoNodo);
		
		novoNodo = mudarQuarto(novoNodo , disp, false);
		raiz = inserirNodo(raiz, novoNodo);
		corrigirInsercao(novoNodo);
		
		//adiciona  1 no array referente ao mês de reservado
		cap_mes[novoNodo.getQuarto().getDataCheckin().getMonthValue() -1]++;
		qua_res[novoNodo.getQuarto().getNum_quarto() - 1]++;

	}
	
	private Nodo inserirNodo(Nodo atual, Nodo novoNodo) {
		
		if(atual == null) {
			return novoNodo;
		}
		
		
		 if (novoNodo.quarto.getDataCheckin().isBefore(atual.quarto.getDataCheckin()) && novoNodo.getQuarto() != atual.getQuarto()) {
		        Nodo esq = inserirNodo(atual.getEsq(), novoNodo);
		        atual.setEsq(esq);
		        esq.setPai(atual);

		    } else if (novoNodo.quarto.getDataCheckin().isAfter(atual.quarto.getDataCheckin()) && novoNodo.getQuarto() != atual.getQuarto()) {
		        Nodo dir = inserirNodo(atual.getDir(), novoNodo);
		        atual.setDir(dir);
		        dir.setPai(atual);

		    } else {
			System.out.println("O quarto escolhido já têm uma reserva");
		}
		
		return atual;
		
		
	}
	
	private void corrigirInsercao(Nodo nodo) {
		Nodo pai, avo;
		
		while(nodo != raiz && nodo.getPai() != null && nodo.getPai().getCor() == COR.VERMELHO) {
			pai = nodo.getPai();
			avo = pai.getPai();
			
			if (avo == null) break;
			
			if(pai == avo.getEsq()) {
				Nodo tio = avo.getDir();
				if(tio != null && tio.getCor() == COR.VERMELHO) {
					pai.setCor(COR.PRETO);
					tio.setCor(COR.PRETO);
					avo.setCor(COR.VERMELHO);
					nodo = avo;
					

					
				} else {
					if(nodo == pai.getDir()){
						nodo = pai;
						rotacaoEsquerda(nodo);
						}
					
					pai.setCor(COR.PRETO);
					avo.setCor(COR.VERMELHO);
					rotacaoDireita(avo);
					
					}
			}	else {
				
				Nodo tio = avo.getEsq();
				if(tio != null && tio.getCor() == COR.VERMELHO) {
					pai.setCor(COR.PRETO);
					tio.setCor(COR.PRETO);
					avo.setCor(COR.VERMELHO);
					nodo = avo;
				} else {
					
					if(nodo == pai.getEsq()) {
					nodo = pai;
					rotacaoDireita(nodo);
					
					}
					
					pai.setCor(COR.PRETO);
					avo.setCor(COR.VERMELHO);
					rotacaoEsquerda(avo);
				}
			}
		}
		
		raiz.setCor(COR.PRETO);
	}
	
	private void rotacaoEsquerda(Nodo nodo) {
		Nodo novoNodo = nodo.getDir();
		nodo.setDir(novoNodo.getEsq());
		
		if(novoNodo.getEsq() != null) {
			novoNodo.getEsq().setPai(nodo);
		}
		
		novoNodo.setPai(nodo.getPai());
		
		if(nodo.getPai() == null) {
			raiz = novoNodo;
		}else if(nodo == nodo.getPai().getEsq()) {
			nodo.getPai().setEsq(novoNodo);
		} else {
			nodo.getPai().setDir(novoNodo);
		}
		
		novoNodo.setEsq(nodo);
		nodo.setPai(novoNodo);
		
	}
	
	private void rotacaoDireita(Nodo nodo) {
		Nodo novoNodo = nodo.getEsq();
		nodo.setEsq(novoNodo.getDir());
		
		if(novoNodo.getDir() != null) {
			novoNodo.getDir().setPai(nodo);
		}
		
		novoNodo.setPai(nodo.getPai());
		
		if(nodo.getPai() == null) {
			raiz = novoNodo;
		} else if(nodo == nodo.getPai().getDir()) {
			nodo.getPai().setPai(novoNodo);
		} else {
			nodo.getPai().setEsq(novoNodo);
		}
		
		novoNodo.setDir(nodo);
		nodo.getPai().setDir(novoNodo);
	
	}
	
	public void cancelarReserva(int id) { 
		removerReserva(id);
	}
	
	
	public void removerReserva(int id) {
	    Nodo alvo = buscarNodoPorId(raiz, id);

	    if (alvo == null) {
	        System.out.println("Nenhuma reserva encontrada para o ID " + id);
	        return;
	    }

	    int mesIndex = alvo.getQuarto().getDataCheckin().getMonthValue() - 1;

	    // Atualiza estatísticas mensais
	    can_mes[mesIndex]++;
	    if (cap_mes[mesIndex] > 0) cap_mes[mesIndex]--;

	    // Remove o nó da árvore e mantém balanceamento
	    removerNodo(alvo);

	    // Marca como cancelado e registra no histórico
	    alvo.setSituacao(STATUS.CANCELADO);
	    reg.inserir(alvo.getCliente(), alvo.getQuarto());

	    System.out.println("Reserva removida da árvore com sucesso!");
	}
	
	private Nodo buscarNodoPorId(Nodo atual, int id) {
	    if (atual == null) return null;

	    if (atual.getCliente().getId() == id) return atual;

	    Nodo esq = buscarNodoPorId(atual.getEsq(), id);
	    if (esq != null) return esq;

	    return buscarNodoPorId(atual.getDir(), id);
	}

	
	public void exibirArvore() {
	    System.out.println("\n===== Árvore Rubro-Negra =====");
	    exibirNodo(this.raiz, "", true);
	    exibirPorCheckin(raiz);
	}

	private void exibirNodo(Nodo nodo, String prefixo, boolean isEsquerda) {
	    if (nodo != null) {
	        System.out.println(prefixo + (isEsquerda ? "├── " : "└── ")
	        		+ "[" + nodo.getSituacao() + "] "
	                + "[" + nodo.quarto.getDataCheckin() + "] "
	                + "[ TIPO:" + nodo.quarto.getCat() + "] "
	                + "[ NUM: " + nodo.quarto.getNum_quarto() + "] "
	                + "(" + nodo.getCor() + ") "
	                + "Cliente: " + nodo.cliente.getNome()
	        		+ "CPF: " + nodo.cliente.getCpf() 
	        		+ "ID: " + nodo.cliente.getId());
	        exibirNodo(nodo.getEsq(), prefixo + (isEsquerda ? "│   " : "    "), true);
	        exibirNodo(nodo.getDir(), prefixo + (isEsquerda ? "│   " : "    "), false);
	    }
	}
	
	
	private void exibirPorCheckin(Nodo nodo) {
		
		Nodo aux = nodo;
		
		if(aux != null) {
			exibirPorCheckin(aux.getEsq());
			System.out.println("ID do Cliente:  " + aux.getCliente().getId() + "\nQuarto: " + aux.getQuarto().getNum_quarto() 
					+ " CheckIN: " + aux.getQuarto().getDataCheckin().getDayOfMonth() + "/" + aux.getQuarto().getDataCheckin().getMonthValue() + "/" 
					+ aux.getQuarto().getDataCheckin().getYear());
			exibirPorCheckin(aux.getDir());
		}
		

		
	}
	
	
	private void removerNodo(Nodo z) {
	    Nodo y = z;
	    COR corOriginal = y.getCor();

	    Nodo substituto;

	    // Caso 1: sem filho esquerdo
	    if (z.getEsq() == null) {
	        substituto = z.getDir();
	        transplantar(z, z.getDir());
	    }
	    // Caso 2: sem filho direito
	    else if (z.getDir() == null) {
	        substituto = z.getEsq();
	        transplantar(z, z.getEsq());
	    }
	    // Caso 3: dois filhos
	    else {
	        y = minimo(z.getDir());
	        corOriginal = y.getCor();
	        substituto = y.getDir();

	        if (y.getPai() == z) {
	            if (substituto != null)
	                substituto.setPai(y);
	        } else {
	            transplantar(y, y.getDir());
	            y.setDir(z.getDir());
	            if (y.getDir() != null)
	                y.getDir().setPai(y);
	        }

	        transplantar(z, y);
	        y.setEsq(z.getEsq());
	        if (y.getEsq() != null)
	            y.getEsq().setPai(y);
	        y.setCor(z.getCor());
	    }

	    if (corOriginal == COR.PRETO) {
	        corrigirRemocao(substituto);
	    }
	}
	
	
	private void corrigirRemocao(Nodo x) {
	    while (x != raiz && (x == null || x.getCor() == COR.PRETO)) {
	    	
	    	if(x == null ) return;
	        if (x == x.getPai().getEsq()) {
	            Nodo w = x.getPai().getDir();

	            if (w != null && w.getCor() == COR.VERMELHO) {
	                w.setCor(COR.PRETO);
	                x.getPai().setCor(COR.VERMELHO);
	                rotacaoEsquerda(x.getPai());
	                w = x.getPai().getDir();
	            }

	            if ((w == null) ||
	                ((w.getEsq() == null || w.getEsq().getCor() == COR.PRETO) &&
	                 (w.getDir() == null || w.getDir().getCor() == COR.PRETO))) {
	                if (w != null) w.setCor(COR.VERMELHO);
	                x = x.getPai();
	            } else {
	                if (w.getDir() == null || w.getDir().getCor() == COR.PRETO) {
	                    if (w.getEsq() != null) w.getEsq().setCor(COR.PRETO);
	                    w.setCor(COR.VERMELHO);
	                    rotacaoDireita(w);
	                    w = x.getPai().getDir();
	                }
	                w.setCor(x.getPai().getCor());
	                x.getPai().setCor(COR.PRETO);
	                if (w.getDir() != null) w.getDir().setCor(COR.PRETO);
	                rotacaoEsquerda(x.getPai());
	                x = raiz;
	            }
	        } else {
	            // Caso simétrico
	            Nodo w = x.getPai().getEsq();

	            if (w != null && w.getCor() == COR.VERMELHO) {
	                w.setCor(COR.PRETO);
	                x.getPai().setCor(COR.VERMELHO);
	                rotacaoDireita(x.getPai());
	                w = x.getPai().getEsq();
	            }

	            if ((w == null) ||
	                ((w.getDir() == null || w.getDir().getCor() == COR.PRETO) &&
	                 (w.getEsq() == null || w.getEsq().getCor() == COR.PRETO))) {
	                if (w != null) w.setCor(COR.VERMELHO);
	                x = x.getPai();
	            } else {
	                if (w.getEsq() == null || w.getEsq().getCor() == COR.PRETO) {
	                    if (w.getDir() != null) w.getDir().setCor(COR.PRETO);
	                    w.setCor(COR.VERMELHO);
	                    rotacaoEsquerda(w);
	                    w = x.getPai().getEsq();
	                }
	                w.setCor(x.getPai().getCor());
	                x.getPai().setCor(COR.PRETO);
	                if (w.getEsq() != null) w.getEsq().setCor(COR.PRETO);
	                rotacaoDireita(x.getPai());
	                x = raiz;
	            }
	        }
	    }

	    if (x != null)
	        x.setCor(COR.PRETO);
	}
	
	//método que corrige as ligações 
	private void transplantar(Nodo u, Nodo v) {
	    if (u.getPai() == null) {
	        raiz = v;
	    } else if (u == u.getPai().getEsq()) {
	        u.getPai().setEsq(v);
	    } else {
	        u.getPai().setDir(v);
	    }

	    if (v != null) {
	        v.setPai(u.getPai());
	    }
	}
	
	
	private Nodo minimo(Nodo nodo) {
	    while (nodo.getEsq() != null) {
	        nodo = nodo.getEsq();
	    }
	    return nodo;
	}
	
	

}
