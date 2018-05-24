package caronante;

import java.util.ArrayList;

import carona.Carona;
import carona.CaronaPrivada;
import carona.CaronaPublica;
import perfil.Perfil;
import utilidades.HelperFormatacao;

public class Caronante {
	
	private int tempoHabilitacao;	
	private String generoMusicalFavorito;	
	private String placaVeiculo;	
	private final String carteiraMotorista;	
	private String marcaVeiculo;	
	private String modeloVeiculo;	
	private int assentosDisponiveis;	
	private static int numCaronantes;
	private Perfil perfil;
	private ArrayList<CaronaCaronante> caronas;
	
	public Caronante(String carteiraMotorista) {
		numCaronantes ++;
		this.carteiraMotorista = carteiraMotorista;
	}

	public Caronante(int tempoHabilitacao, String generoMusicalFavorito, String placaVeiculo, String carteiraMotorista,
			String marcaVeiculo, String modeloVeiculo, int assentosDisponiveis) {
		this(carteiraMotorista);
		this.tempoHabilitacao = tempoHabilitacao;
		this.generoMusicalFavorito = generoMusicalFavorito;
		this.placaVeiculo = placaVeiculo;
		this.marcaVeiculo = marcaVeiculo;
		this.modeloVeiculo = modeloVeiculo;
		this.assentosDisponiveis = assentosDisponiveis;
		this.caronas = new ArrayList<CaronaCaronante>();
	}
	
	public CaronaPublica oferecerCaronaPublic(double latitudeEncontro, double longitudeEncontro,
			double latitudeDestino, double longitudeDestino, String horaDiaEncontro, float valor) {
		CaronaCaronante caronaCaronante = new CaronaCaronante(this, null);
		CaronaPublica carona =  new CaronaPublica(caronaCaronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
				longitudeDestino, horaDiaEncontro, valor);
		this.caronas.add(caronaCaronante);
		return carona;
	}
	
	public CaronaPrivada oferecerCaronaPrivada(double latitudeEncontro, double longitudeEncontro,
			double latitudeDestino, double longitudeDestino, String horaDiaEncontro, float valor) {
		CaronaCaronante caronaCaronante = new CaronaCaronante(this, null);
		CaronaPrivada carona =  new CaronaPrivada(caronaCaronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
				longitudeDestino, horaDiaEncontro, valor);
		this.caronas.add(caronaCaronante);
		return carona;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public int getTempoHabilitacao() {
		return tempoHabilitacao;
	}

	public void setTempoHabilitacao(int tempoHabilitacao) {
		this.tempoHabilitacao = tempoHabilitacao;
	}

	public String getGeneroMusicalFavorito() {
		return generoMusicalFavorito;
	}

	public void setGeneroMusicalFavorito(String generoMusicalFavorito) {
		this.generoMusicalFavorito = generoMusicalFavorito;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getCarteiraMotorista() {
		return carteiraMotorista;
	}

	public String getMarcaVeiculo() {
		return marcaVeiculo;
	}

	public void setMarcaVeiculo(String marcaVeiculo) {
		this.marcaVeiculo = marcaVeiculo;
	}

	public String getModeloVeiculo() {
		return modeloVeiculo;
	}

	public void setModeloVeiculo(String modeloVeiculo) {
		this.modeloVeiculo = modeloVeiculo;
	}

	public int getAssentosDisponiveis() {
		return assentosDisponiveis;
	}

	public void setAssentosDisponiveis(int assentosDisponiveis) {
		this.assentosDisponiveis = assentosDisponiveis;
	}

	public static int getNumCaronantes() {
		return numCaronantes;
	}

	public static void setNumCaronantes(int numCaronantes) {
		Caronante.numCaronantes = numCaronantes;
	}
	
	public String toStringDesdeUsuario(int numeroEspacos) {
		return perfil.getUsuario().toString(numeroEspacos);
	}

	public String toString(int numeroEspacos) {
		String espacos = HelperFormatacao.criaEspacos(numeroEspacos);
		return String.format("%s| Caronante\n"
				+ "%s| - Tempo de habilitacao em anos: %d\n"
				+ "%s| - Genero musical favorito: %s\n"
				+ "%s| - Placa do veiculo: %s\n"
				+ "%s| - Carteira de motorista: %s\n"
				+ "%s| - Marca do veiculo: %s\n"
				+ "%s| - Modelo do Veiculo: %s\n"
				+ "%s| - Assentos disponiveis: %d\n"
				+ "%s| - Numero de caronantes no momento: %d\n",
				espacos, espacos, this.tempoHabilitacao, espacos, this.generoMusicalFavorito, espacos,
				this.placaVeiculo, espacos, this.carteiraMotorista,
				espacos, this.marcaVeiculo, espacos, this.modeloVeiculo,
				espacos, this.assentosDisponiveis, espacos, Caronante.getNumCaronantes());
	}
	
	@Override
	public String toString() {
		return toString(0);
	}
		

}
