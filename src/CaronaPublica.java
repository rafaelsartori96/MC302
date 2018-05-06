import java.util.ArrayList;

public class CaronaPublica extends Carona {

    private final ArrayList<GrupoPublico> grupos = new ArrayList<>();

    public CaronaPublica(Caronante caronante) {
        super(caronante);
    }

    @Override
    public boolean adicionarCaroneiro(Caroneiro caroneiro) {
        if(getCaroneiros().size() >= getOcupacaoMaxima()) {
            return false;
        }

        for(CaronaCaroneiro caronaCaroneiro : getCaroneiros()) {
            if(caronaCaroneiro.getCaroneiro() == caroneiro) {
                return false;
            }
        }

        // Adicionamos apenas se o usuário possui acesso à carona através dos grupos aos quais a carona pertence
        for (GrupoPublico grupo : grupos) {
            for (GrupoUsuario grupoUsuario : grupo.getMembros()) {
                if (grupoUsuario.getUsuario().equals(caroneiro.getPerfil().getUsuario())) {
                    CaronaCaroneiro caronaCaroneiro = new CaronaCaroneiro(caroneiro, this);
                    getCaroneiros().add(caronaCaroneiro);
                    caroneiro.adicionarCarona(caronaCaroneiro);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean adicionarGrupo(GrupoPublico grupo) {
        if(this.grupos.contains(grupo)) {
            return false;
        }

        this.grupos.add(grupo);
        return true;
    }

    @Override
    public String toString() {
        return "CaronaPublica:\nGrupos: " + grupos.toString() + ", " + super.toString();
    }
}
