import java.util.*;

public class GrupoPrivado extends Grupo {

    private final ArrayList<CaronaPrivada> caronas = new ArrayList<>();

    public GrupoPrivado(Usuario dono, String nome, String descricao) {
        super(dono, nome, descricao);
    }

    @Override
    public void adicionarUsuario(Usuario usuario) {
        if (getDono().getUsuario().equals(usuario)) {
            return;
        }
        for (GrupoUsuario grupoUsuario : getMembros()) {
            if (grupoUsuario.getUsuario().equals(usuario)) {
                return;
            }
        }
        getMembros().add(new GrupoUsuario(usuario, this));
        usuario.adicionarGrupo(this);
    }

    @Override
    public void removeUsuario(Usuario usuario) {
        if (getDono().getUsuario().equals(usuario)) {
            return;
        }
        usuario.removerGrupo(this);
        for (int i = 0; i < getMembros().size(); i++) {
            GrupoUsuario grupoUsuario = getMembros().get(i);
            if (grupoUsuario.getUsuario().equals(usuario)) {
                getMembros().remove(i);
                return;
            }
        }
    }

    public void adicionarCaronaPrivada(CaronaPrivada carona) {
        for (CaronaPrivada caronaPrivada : this.caronas) {
            if (caronaPrivada.equals(carona)) {
                return;
            }
        }
        this.caronas.add(carona);
    }

    @Override
    public String toString() {
        return "Grupo privado:\n" + super.toString();
    }
}
