public class GrupoPrivado extends Grupo {

    public GrupoPrivado(String nome, String descricao) {
        super(nome, descricao);
    }

    @Override
    public String toString() {
        return "Grupo privado:\n" + super.toString();
    }
}
