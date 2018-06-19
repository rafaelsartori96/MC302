package laboratorio.carona;

import java.util.ArrayList;

import laboratorio.perfil.caronante.CaronaCaronante;
import laboratorio.perfil.caroneiro.CaronaCaroneiro;
import laboratorio.grupo.GrupoPublico;
import laboratorio.usuario.*;

public class CaronaPublica extends Carona {

    private final ArrayList<GrupoPublico> grupos = new ArrayList<>();

    public CaronaPublica(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro,
                         double latitudeDestino, double longitudeDestino, String horaDiaEncontro, float valor) {
        super(Tipo.PUBLICA, caronante, latitudeEncontro, longitudeEncontro, latitudeDestino, longitudeDestino,
                horaDiaEncontro, valor);
    }

    public CaronaPublica(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro,
                         double latitudeDestino, double longitudeDestino, String horaDiaEncontro, float valor,
                         int ocupacaoMaxima) {
        super(Tipo.PUBLICA, latitudeEncontro, longitudeEncontro, latitudeDestino, longitudeDestino, horaDiaEncontro,
                valor, caronante, ocupacaoMaxima);
    }

    @Override
    public boolean adicionarCaroneiro(CaronaCaroneiro caroneiro) {
        // Se não há grupos definidos, todos podem entrar
        if (grupos.isEmpty()) {
            return super.adicionarCaroneiro(caroneiro);
        }

        // Se há, limitamos aos que estão no laboratorio.grupo
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
