package carona;

import java.util.ArrayList;

import perfil.caronante.CaronaCaronante;
import perfil.caroneiro.CaronaCaroneiro;
import grupo.GrupoPublico;
import usuario.*;

public class CaronaPublica extends Carona {

    private final ArrayList<GrupoPublico> grupos = new ArrayList<>();

    public CaronaPublica(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
                         double longitudeDestino, String horaDiaEncontro, float valor) {
        super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino, longitudeDestino, horaDiaEncontro, valor);
    }

    public CaronaPublica(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro, double latitudeDestino,
                         double longitudeDestino, String horaDiaEncontro, float valor, int ocupacaoMaxima) {
        super(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
                longitudeDestino, horaDiaEncontro, valor, ocupacaoMaxima);
    }

    @Override
    public boolean adicionarCaroneiro(CaronaCaroneiro caroneiro) {
        // Se não há grupos definidos, todos podem entrar
        if (grupos.isEmpty()) {
            return super.adicionarCaroneiro(caroneiro);
        }

        // Se há, limitamos aos que estão no grupo
        Usuario usuario = caroneiro.getCaroneiro().getPerfil().getUsuario();
        for (GrupoPublico grupo : grupos) {
            if (grupo.checarPresenca(usuario)) {
                return super.adicionarCaroneiro(caroneiro);
            }
        }
        return false;
    }

    public boolean adicionarGrupo(GrupoPublico grupo) {
        if (grupo.checarPresenca(getCaronante().getPerfil().getUsuario())) {
            grupos.add(grupo);
            return true;
        }
        return false;
    }
}