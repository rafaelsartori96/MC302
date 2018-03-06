public class Usuario {

    private int id;
    private String nome, email, senha;
    private boolean status;

    public Usuario(int id, String nome, String email, String senha, boolean status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return String.format(
                "%s (id: %d)\nEmail: %s\nSenha: %s\nStatus: %b\n",
                getNome(),
                getId(),
                getEmail(),
                getSenha(),
                getStatus()
        );
    }
}
