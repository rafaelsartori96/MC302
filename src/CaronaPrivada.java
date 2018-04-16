import java.util.ArrayList;

public class CaronaPrivada extends Carona {

    private final ArrayList<GrupoPrivado> grupos = new ArrayList<>();

    public CaronaPrivada(Caronante caronante) {
        super(caronante);
    }

    public boolean adicionarGrupo(GrupoPrivado grupo) {
        if(this.grupos.contains(grupo)) {
            return false;
        }

        this.grupos.add(grupo);
        return true;
    }

    @Override
    public String toString() {
        return "CaronaPrivada:\nGrupos: " + grupos.toString() + ", " + super.toString();
    }
}
