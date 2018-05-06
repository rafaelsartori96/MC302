public class GrupoPrivado extends Grupo {

    public GrupoPrivado(Usuario dono, String nome, String descricao) {
        super(dono, nome, descricao);
    }

    @Override
    public void adicionarUsuario(Usuario usuario) {

    }

    @Override
    public void removeUsuario(Usuario usuario) {

    }

    @Override
    public String toString() {
        return "Grupo privado:\n" + super.toString();
    }
}
