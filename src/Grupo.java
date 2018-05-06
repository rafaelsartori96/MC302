import java.util.ArrayList;

public abstract class Grupo {

    private static int geradorId = 0;

    private final ArrayList<GrupoUsuario> membros = new ArrayList<>();

    private final int id;
    private GrupoUsuario dono;
    private String nome, descricao;


    public Grupo(Usuario dono, String nome, String descricao) {
        this.id = Grupo.geradorId++;
        setDono(dono);
        setNome(nome);
        setDescricao(descricao);
    }

    public int getId() {
        return id;
    }

    public GrupoUsuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = new GrupoUsuario(dono, this);
        this.dono.setDono(true);
        dono.adicionarGrupo(this);

        // Removemos o dono do grupo se está
        for (int i = 0; i < getMembros().size(); i++) {
            GrupoUsuario grupoUsuario = getMembros().get(i);
            if (grupoUsuario.equals(getDono())) {
                getMembros().remove(i);
            }
        }
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

    public ArrayList<GrupoUsuario> getMembros() {
        return membros;
    }

    public abstract void adicionarUsuario(Usuario usuario);

    public abstract void removeUsuario(Usuario usuario);

    @Override
    public String toString() {
        String string = "Grupo id = " + getId() + ":\n" +
                "Dono: " + getDono().getUsuario().getNome() + "\n" +
                "Nome: " + getNome() + "\n" +
                "Descrição: " + getDescricao() + "\n" +
                (getMembros().isEmpty() ? "Não há membros!\n" : "Usuários:\n");
        for (GrupoUsuario grupoUsuario : getMembros()) {
            string += grupoUsuario.getUsuario().getNome();
        }
        return string;
    }
}
