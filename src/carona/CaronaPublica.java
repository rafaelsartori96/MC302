package carona;

import java.util.ArrayList;

import caronante.CaronaCaronante;
import caroneiro.CaronaCaroneiro;
import grupo.GrupoPublico;

public class CaronaPublica extends Carona {
	private ArrayList<GrupoPublico> grupos;

	public CaronaPublica(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
			double longitudeDestino, String horaDiaEncontro, float valor) {
		super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino, longitudeDestino, horaDiaEncontro, valor);
		grupos = new ArrayList<GrupoPublico>();
	}
	
	public CaronaPublica(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
			double longitudeDestino, String horaDiaEncontro, float valor, int ocupacaoMaxima) {
		super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
				longitudeDestino, horaDiaEncontro, valor, ocupacaoMaxima);
		grupos = new ArrayList<GrupoPublico>();
	}
	
	public boolean adicionarCaroneiro(CaronaCaroneiro caroneiro) {
		return super.adicionarCaroneiro(caroneiro);
	}
	
	public boolean adicionarGrupo(GrupoPublico grupo) {
		grupos.add(grupo);
		return true;
	}

}
