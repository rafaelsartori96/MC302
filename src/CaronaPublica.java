import java.util.ArrayList;

public class CaronaPublica extends Carona {

    private final ArrayList<GrupoPublico> grupos = new ArrayList<>();

    public CaronaPublica(Caronante caronante) {
        super(caronante);
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
