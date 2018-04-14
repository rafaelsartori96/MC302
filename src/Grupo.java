import java.util.ArrayList;

public class Grupo {

    public static int testeStatic = 21;
    public int testeNaoStatic = 22;

    private final int id;
    private String nome, descricao;

    private final ArrayList<Usuario> membros = new ArrayList<>();

    private static int geradorId = 0;

    public Grupo(String nome, String descricao) {
        this.id = Grupo.geradorId++;
        setNome(nome);
        setDescricao(descricao);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Usuario> getMembros() {
        return membros;
    }

    public void adicionarUsuario(Usuario usuario) {
        if(!getMembros().contains(usuario)) {
            getMembros().add(usuario);
            usuario.adicionarGrupo(this);
        }
    }

    @Override
    public String toString() {
        String string = "Grupo id = " + getId() + ":\n" +
                "Nome: " + getNome() + "\n" +
                "Descrição: " + getDescricao() + "\n" +
                (getMembros().isEmpty() ? "Não há membros!\n" : "Usuários:\n");
        for(Usuario usuario : getMembros()) {
            string += usuario.getNome();
        }
        return string;
    }
}
