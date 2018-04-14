public class GrupoPublico extends Grupo {

    public GrupoPublico(String nome, String descricao) {
        super(nome, descricao);
    }

    @Override
    public String toString() {
        return "Grupo público:\n" + super.toString();
    }
}
