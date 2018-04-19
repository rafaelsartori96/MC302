import java.util.ArrayList;
import java.util.Collection;

public class Usuario {

    private final int id;
    private String nome, email, senha;
    private boolean status;

    private final ArrayList<Grupo> grupos = new ArrayList<>();
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
        getGrupos().addAll(grupos);
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

    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    public void adicionarGrupo(Grupo grupo) {
        if(!getGrupos().contains(grupo)) {
            getGrupos().add(grupo);
            grupo.adicionarUsuario(this);
        }
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        if(this.perfil != perfil) {
            if(perfil != null)
                perfil.setUsuario(this);
            if(this.perfil != null)
                this.perfil.setUsuario(null);
        }
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        String descricao = "Usuário id = " + getId() + "\n" +
                "Nome: " + getNome() + "\n" +
                "E-mail: " + getEmail() + "\n" +
                "Senha: " + getSenha() + "\n" + // privacidade zero mesmo
                "Status: " + getStatus() + "\n" +
                "Perfil: " + getPerfil().toString() + "\n" +
                (getGrupos().isEmpty() ? "Não possui grupos\n" : "Grupos:\n");
        for(Grupo grupo : getGrupos()) {
           descricao += grupo.toString();
        }
        return descricao;
    }
}
