package caroneiro;

import java.util.ArrayList;

import carona.Carona;
import perfil.Perfil;
import utilidades.HelperFormatacao;

public class Caroneiro {
	private String cartaoDeCredito;
	private boolean pagamentoEmDinheiro;
	private static int numCaroneiros;
	private Perfil perfil;
	private final ArrayList<CaronaCaroneiro> caronas;
	
	public Caroneiro(boolean pagamentoEmDinheiro) {
		this();
		this.pagamentoEmDinheiro = pagamentoEmDinheiro;
	}
	
	public Caroneiro(String cartaoDeCredito) {
		this();
		this.cartaoDeCredito = cartaoDeCredito;
	}
	
	public Caroneiro() {
		numCaroneiros ++;
		caronas = new ArrayList<CaronaCaroneiro>();
	}
	
	public boolean pedirCarona(Carona carona) {		
		CaronaCaroneiro caronaCaroneiro = new CaronaCaroneiro(this, carona);		
		if(!carona.adicionarCaroneiro(caronaCaroneiro)) {
			return false; //Sem assento
		}
		caronas.add(caronaCaroneiro);
		return true;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public String getCartaoDeCredito() {
		return cartaoDeCredito;
	}
	public void setCartaoDeCredito(String cartaoDeCredito) {
		this.cartaoDeCredito = cartaoDeCredito;
	}
	public boolean isPagamentoEmDinheiro() {
		return pagamentoEmDinheiro;
	}
	public void setPagamentoEmDinheiro(boolean pagamentoEmDinheiro) {
		this.pagamentoEmDinheiro = pagamentoEmDinheiro;
	}
	public static int getNumCaroneiros() {
		return numCaroneiros;
	}
	public static void setNumCaroneiros(int numCaroneiros) {
		Caroneiro.numCaroneiros = numCaroneiros;
	}
	
	public String toStringDesdeUsuario(int numeroEspacos) {
		return perfil.getUsuario().toString(numeroEspacos);
	}
	
	public String toString(int numeroEspacos) {
		String espacos = HelperFormatacao.criaEspacos(numeroEspacos);
		return String.format("%s| Caroneiro\n"
				+ "%s| - Numero do cartao de credito: %s\n"
				+ "%s| - Vai ser pago em dinheiro?: %s\n"
				+ "%s| - Numero de caroneiros no momento: %s\n",
				espacos, espacos, this.cartaoDeCredito, espacos, this.pagamentoEmDinheiro ? "Sim" : "Nao",
				espacos, Caroneiro.getNumCaroneiros());
		//this.pagamentoEmDinheiro ? "Sim" : "Nao" é uma expressão ternária
	}
	
	@Override
	public String toString() {
		return toString(0);
	}
	
	
}