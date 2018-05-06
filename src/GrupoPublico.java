import java.util.*;

public class GrupoPublico extends Grupo {

    private final ArrayList<CaronaPublica> caronas = new ArrayList<>();

    public GrupoPublico(Usuario dono, String nome, String descricao) {
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

    public void adicionarCaronaPublica(CaronaPublica carona) {
        for (CaronaPublica caronaPublica : caronas) {
            if (caronaPublica.equals(carona)) {
                return;
            }
        }
        this.caronas.add(carona);
    }

    @Override
    public String toString() {
        return "Grupo público:\n" + super.toString();
    }
}
