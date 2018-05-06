import java.util.ArrayList;
import java.util.Collection;

public class Usuario {

    private final int id;
    private String nome, email, senha;
    private boolean status;

    private final ArrayList<GrupoUsuario> grupos = new ArrayList<>();
    private Perfil perfil;

    private static int geradorId = 0;

    public Usuario(String nome, String email, String senha, boolean status) {
        this.id = Usuario.geradorId++;
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setStatus(status);
    }

    public Usuario(String nome, String email, String senha, boolean status, Collection<Grupo> grupos) {
        this(nome, email, senha, status);
        for (Grupo grupo : grupos) {
            adicionarGrupo(grupo);
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<GrupoUsuario> getGrupos() {
        return grupos;
    }

    public void adicionarGrupo(Grupo grupo) {
        grupo.adicionarUsuario(this);
        for (GrupoUsuario grupoUsuario : getGrupos()) {
            if (grupoUsuario.getGrupo().equals(grupo)) {
                return;
            }
        }
        for (GrupoUsuario grupoUsuario : grupo.getMembros()) {
            if (grupoUsuario.getUsuario().equals(this)) {
                grupos.add(grupoUsuario);
                return;
            }
        }
        if (grupo.getDono().getUsuario().equals(this)) {
            grupos.add(grupo.getDono());
        }
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        // Se está desatualizado
        if (this.perfil != perfil) {
            // Atualizamos o perfil antigo
            if (this.perfil != null)
                this.perfil.setUsuario(null);
            // Mudamos de perfil
            this.perfil = perfil;
            // Atualizamos o perfil se ele está desatualizado
            if (perfil != null && perfil.getUsuario() != this)
                perfil.setUsuario(this);
        }
    }

    private GrupoUsuario atualizarGrupo(int id) {
        for (GrupoUsuario grupoUsuario : getGrupos()) {
            if (grupoUsuario.getGrupo().getId() == id) {
                return grupoUsuario;
            }
        }
        return null;
    }

    public boolean atualizarGrupo(int id, String nome, String descricao) {
        GrupoUsuario grupoUsuario = atualizarGrupo(id);
        if (grupoUsuario == null || !grupoUsuario.isDono()) {
            return false;
        }

        grupoUsuario.getGrupo().setNome(nome);
        grupoUsuario.getGrupo().setDescricao(descricao);
        return true;
    }

    public boolean atualizarGrupo(int id, String descricao) {
        GrupoUsuario grupoUsuario = atualizarGrupo(id);
        if (grupoUsuario == null || !grupoUsuario.isDono()) {
            return false;
        }

        grupoUsuario.getGrupo().setDescricao(descricao);
        return true;
    }

    public void removerGrupo(int id) {
        for (int i = 0; i < getGrupos().size(); i++) {
            GrupoUsuario grupoUsuario = getGrupos().get(i);
            if (grupoUsuario.getGrupo().getId() == id) {
                // Apesar de não estar na figura, não sei outra maneira pra fazer isso de forma consistente (criando o
                // método que remove o usuário)
                grupoUsuario.getGrupo().removeUsuario(this);
                getGrupos().remove(i);
                return;
            }
        }
    }

    public void removerGrupo(Grupo grupo) {
        for (int i = 0; i < getGrupos().size(); i++) {
            GrupoUsuario grupoUsuario = getGrupos().get(i);
            if (grupoUsuario.getGrupo().equals(grupo)) {
                grupo.removeUsuario(this);
                getGrupos().remove(i);
                return;
            }
        }
    }

    @Override
    public String toString() {
        String descricao = "Usuário id = " + getId() + "\n" +
                "Nome: " + getNome() + "\n" +
                "E-mail: " + getEmail() + "\n" +
                "Senha: " + getSenha() + "\n" + // privacidade zero mesmo
                "Status: " + getStatus() + "\n" +
                "Perfil: " + getPerfil() + "\n" +
                (getGrupos().isEmpty() ? "Não possui grupos\n" : "Grupos:\n");
        for (GrupoUsuario grupoUsuario : getGrupos()) {
            descricao += grupoUsuario.getGrupo().getNome() + ", ";
        }
        return descricao;
    }
}
