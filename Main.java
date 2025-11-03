package hotelRubroNegro;


import java.time.LocalDateTime;


public class Main {

	public static void main(String[] args) {
		
		
		Registro reg = Registro.getInstance();
		
		
		

		Reserva res = new Reserva();

		
	
		
		Quarto qua = new Quarto();
		Quarto qua2 = new Quarto();
		
		Cliente cli = new Cliente();
		Cliente cli2 = new Cliente();
		
		
		cli.setId(1);
		cli.setCpf("193");
		cli.setNome("Màrio");
		
		System.out.println(cli);
		
		LocalDateTime lt = LocalDateTime.of(2001, 10, 10, 14, 10);
		LocalDateTime lt1 = LocalDateTime.of(2001, 11, 10, 14, 10);
		qua.setDataCheckin(lt);
		qua.setDataCheckout(lt1);
		qua.setNum_quarto(1);
		qua.setCat(CAT_QUA.ECONOMICO);
		System.out.println("Teste entrada");
		//qua.setNum_quarto(entrada.nextInt());
		
		System.out.println(qua);
		
		res.inserir(cli, qua, STATUS.ATIVO);
		
		
		cli2.setId(2);
		cli2.setCpf("192");
		cli2.setNome("Sergio");
		
		lt = LocalDateTime.of(2001, 11, 10, 14, 10);
		lt1 = LocalDateTime.of(2001, 12, 10, 14, 10);
		
		qua2.setDataCheckin(lt);
		qua2.setDataCheckout(lt1);
		qua2.setNum_quarto(1);
		qua2.setCat(CAT_QUA.ECONOMICO);
		
		res.inserir(cli2, qua2, STATUS.ATIVO);
		
		res.exibirArvore();
		
		res.cancelarReserva(1);

		
	
		reg.mostrarArvore();
		
		res.exibirArvore();
		
        // Inserindo 10 reservas para atingir a capacidade
		for (int i = 1; i <= 10; i++) {
		    Cliente c = new Cliente();
		    c.setId(i);
		    c.setCpf("000" + i);
		    c.setNome("Cliente " + i);

		    Quarto q = new Quarto();
		    q.setNum_quarto(i); // quarto 1 a 10
		    q.setCat(CAT_QUA.ECONOMICO);

		    // check-in sempre antes do check-out (mesmo mês)
		    LocalDateTime checkin = LocalDateTime.of(2025, 11, i, 14, 0);
		    LocalDateTime checkout = checkin.plusDays(2); // checkout 2 dias depois

		    q.setDataCheckin(checkin);
		    q.setDataCheckout(checkout);

		    res.inserir(c, q, STATUS.ATIVO);
		}

		res.exibirArvore();
		// Tenta inserir a 11ª reserva (deve acionar aviso de capacidade cheia)
		Cliente cliExtra = new Cliente();
		cliExtra.setId(11);
		cliExtra.setCpf("00011");
		cliExtra.setNome("Cliente Extra");

		Quarto quaExtra = new Quarto();
		quaExtra.setNum_quarto(1);
		quaExtra.setCat(CAT_QUA.LUXO);

		LocalDateTime checkinExtra = LocalDateTime.of(2025, 12, 15, 14, 0);
		LocalDateTime checkoutExtra = checkinExtra.plusDays(3);

		quaExtra.setDataCheckin(checkinExtra);
		quaExtra.setDataCheckout(checkoutExtra);

		res.inserir(cliExtra, quaExtra, STATUS.ATIVO);

		// Mostra a árvore de reservas
		res.exibirArvore();
		reg.mostrarArvore();
		
		//registros
		
		int i = 1;
		for(int a : res.getCap_mes()) {
			System.out.println("mes: "+ i + " " + a + " Reservas");
			i++;
		}
		 i = 1;
		 System.out.println();
		for(int a : res.getCan_mes()) {
			System.out.println("mes: "+ i + " " + a + " Cancelamentos");
			i++;
		}
		
	}
}


