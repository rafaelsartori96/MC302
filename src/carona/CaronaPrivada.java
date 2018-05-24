package carona;

import java.util.ArrayList;

import caronante.CaronaCaronante;
import caroneiro.CaronaCaroneiro;
import grupo.GrupoPrivado;

public class CaronaPrivada extends Carona {
	private ArrayList<GrupoPrivado> grupos;

	public CaronaPrivada(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
			double longitudeDestino, String horaDiaEncontro, float valor) {
		super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino, longitudeDestino, horaDiaEncontro, valor);
		grupos = new ArrayList<GrupoPrivado>();
	}
	
	public CaronaPrivada(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
			double longitudeDestino, String horaDiaEncontro,  float valor, int ocupacaoMaxima) {
		super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
				longitudeDestino, horaDiaEncontro, valor, ocupacaoMaxima);
		grupos = new ArrayList<GrupoPrivado>();
		
	}
	
	public boolean adicionarCaroneiro(CaronaCaroneiro caroneiro) {
		return super.adicionarCaroneiro(caroneiro);
	}
	
	public boolean adicionarGrupo(GrupoPrivado grupo) {
		grupos.add(grupo);
		return true;
	}

}
