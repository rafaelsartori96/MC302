public class Perfil {

    private char sexo;
    private String dataNascimento, cidade, estado, telefone;
    private boolean fumante;
    private double somaAvaliacoes;
    private int numAvaliacoes;

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
        this.somaAvaliacoes = somaAvaliacoes;
        this.numAvaliacoes = numAvaliacoes;
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

    public double getAvaliacao() {
        if(numAvaliacoes > 0)
            return (double) somaAvaliacoes / (double) numAvaliacoes;
        else
            return -1.0D;
    }

    public void setAvaliacao(double avaliacao) {
        this.somaAvaliacoes += avaliacao;
        this.numAvaliacoes++;
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
                "Avaliação: " + getAvaliacao() + "\n" +
                "Perfil caronante: " + (getCaronante() == null ? "null" : getCaronante().toString()) + "\n" +
                "Perfil caroneiro: " + (getCaroneiro() == null ? "null" : getCaroneiro().toString()) + "\n";
    }
}
