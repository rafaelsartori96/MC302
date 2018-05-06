public class GrupoPublico extends Grupo {

    public GrupoPublico(Usuario dono, String nome, String descricao) {
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
        return "Grupo público:\n" + super.toString();
    }
}
