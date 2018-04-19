public class Perfil {

    private Usuario usuario;

    private char sexo;
    private String dataNascimento, cidade, estado, telefone;
    private boolean fumante;

    private Caronante caronante = null;
    private Caroneiro caroneiro = null;

    public Perfil(
            char sexo,
            String dataNascimento, String cidade, String estado, String telefone,
            boolean fumante,
            double somaAvaliacoes, int numAvaliacoes
    ) {
        setSexo(sexo);
        setDataNascimento(dataNascimento);
        setCidade(cidade);
        setEstado(estado);
        setTelefone(telefone);
        setFumante(fumante);
    }

    public Perfil(
            char sexo,
            String dataNascimento, String cidade, String estado, String telefone,
            boolean fumante,
            double somaAvaliacoes, int numAvaliacoes,
            Caroneiro caroneiro
    ) {
        this(sexo, dataNascimento, cidade, estado, telefone, fumante, somaAvaliacoes, numAvaliacoes);
        setCaroneiro(caroneiro);
    }

    public Perfil(
            char sexo,
            String dataNascimento, String cidade, String estado, String telefone,
            boolean fumante,
            double somaAvaliacoes, int numAvaliacoes,
            Caronante caronante
    ) {
        this(sexo, dataNascimento, cidade, estado, telefone, fumante, somaAvaliacoes, numAvaliacoes);
        setCaronante(caronante);
    }

    public Perfil(
            char sexo,
            String dataNascimento, String cidade, String estado, String telefone,
            boolean fumante,
            double somaAvaliacoes, int numAvaliacoes,
            Caroneiro caroneiro,
            Caronante caronante
    ) {
        this(sexo, dataNascimento, cidade, estado, telefone, fumante, somaAvaliacoes, numAvaliacoes);
        setCaroneiro(caroneiro);
        setCaronante(caronante);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if(this.usuario != usuario) {
            if(usuario != null)
                usuario.setPerfil(this);
            if(this.usuario != null)
                this.usuario.setPerfil(null);
        }
        this.usuario = usuario;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isFumante() {
        return fumante;
    }

    public void setFumante(boolean fumante) {
        this.fumante = fumante;
    }

    public Caronante getCaronante() {
        return caronante;
    }

    public void setCaronante(Caronante caronante) {
        if(this.caronante != caronante) {
            caronante.setPerfil(this);
            this.caronante = caronante;
        }
    }

    public Caroneiro getCaroneiro() {
        return caroneiro;
    }

    public void setCaroneiro(Caroneiro caroneiro) {
        if(this.caroneiro != caroneiro) {
            this.caroneiro = caroneiro;
            caroneiro.setPerfil(this);
        }
    }

    @Override
    public String toString() {
        return "Perfil:\n" +
                "Sexo: " + getSexo() + "\n" +
                "Data de nascimento: " + getDataNascimento() + "\n" +
                "Cidade, Estado: " + getCidade() + ", " + getEstado() + "\n" +
                "Telefone: " + getTelefone() + "\n" +
                "Fumante: " + isFumante() + "\n" +
                "Perfil caronante: " + (getCaronante() == null ? "null" : getCaronante().toString()) + "\n" +
                "Perfil caroneiro: " + (getCaroneiro() == null ? "null" : getCaroneiro().toString()) + "\n";
    }
}
