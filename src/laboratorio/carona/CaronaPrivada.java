package laboratorio.carona;

import java.util.ArrayList;

import laboratorio.perfil.caronante.CaronaCaronante;
import laboratorio.perfil.caroneiro.CaronaCaroneiro;
import laboratorio.grupo.GrupoPrivado;
import laboratorio.usuario.*;

public class CaronaPrivada extends Carona {

    private final ArrayList<GrupoPrivado> grupos = new ArrayList<>();

    public CaronaPrivada(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
                         double longitudeDestino, String horaDiaEncontro, float valor) {
        super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino, longitudeDestino, horaDiaEncontro, valor);
    }

    public CaronaPrivada(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
                         double longitudeDestino, String horaDiaEncontro, float valor, int ocupacaoMaxima) {
        super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
                longitudeDestino, horaDiaEncontro, valor, ocupacaoMaxima);
    }

    @Override
    public boolean adicionarCaroneiro(CaronaCaroneiro caroneiro) {
        Usuario usuario = caroneiro.getCaroneiro().getPerfil().getUsuario();

        for (GrupoPrivado grupo : grupos) {
            // Só permitimos usuários que esteja em algum dos grupos
            if (grupo.checarPresenca(usuario)) {
                return super.adicionarCaroneiro(caroneiro);
            }
        }

        return false;
    }

    public boolean adicionarGrupo(GrupoPrivado grupo) {
        if (grupo.checarPresenca(getCaronante().getPerfil().getUsuario())) {
            grupos.add(grupo);
            return true;
        }
        return false;
    }
}
