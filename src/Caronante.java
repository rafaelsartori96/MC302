public class Caronante {

    private int tempoHabilitacao;
    private String generoMusicalFavorito;
    private String placaVeiculo;
    private final String carteiraMotorista;
    private String modeloVeiculo;
    private String marcaVeiculo;
    private int assentosDisponiveis;

    private Perfil perfil;

    public Caronante(
            int tempoHabilitacao,
            String generoMusicalFavorito, String placaVeiculo,
            String carteiraMotorista,
            String modeloVeiculo, String marcaVeiculo,
            int assentosDisponiveis
    ) {
        setTempoHabilitacao(tempoHabilitacao);
        setGeneroMusicalFavorito(generoMusicalFavorito);
        setPlacaVeiculo(placaVeiculo);
        this.carteiraMotorista = carteiraMotorista;
        setModeloVeiculo(modeloVeiculo);
        setMarcaVeiculo(marcaVeiculo);
        setAssentosDisponiveis(assentosDisponiveis);
    }

    public Caronante(
            int tempoHabilitacao,
            String generoMusicalFavorito, String placaVeiculo,
            String carteiraMotorista,
            String modeloVeiculo, String marcaVeiculo,
            int assentosDisponiveis,
            Perfil perfil
    ) {
        this(
                tempoHabilitacao,
                generoMusicalFavorito,
                placaVeiculo, carteiraMotorista, modeloVeiculo, marcaVeiculo, assentosDisponiveis
        );
        setPerfil(perfil);
    }

    public int getTempoHabilitacao() {
        return tempoHabilitacao;
    }

    public void setTempoHabilitacao(int tempoHabilitacao) {
        this.tempoHabilitacao = tempoHabilitacao;
    }

    public String getGeneroMusicalFavorito() {
        return generoMusicalFavorito;
    }

    public void setGeneroMusicalFavorito(String generoMusicalFavorito) {
        this.generoMusicalFavorito = generoMusicalFavorito;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getCarteiraMotorista() {
        return carteiraMotorista;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        if(this.perfil != perfil) {
            this.perfil = perfil;
            perfil.setCaronante(this);
        }
    }

    public CaronaPublica oferecerCaronaPublica() {
        return new CaronaPublica(this);
    }

    public CaronaPrivada oferecerCaronaPrivada() {
        return new CaronaPrivada(this);
    }

    @Override
    public String toString() {
        return "Caronante:\n" +
                "Tempo de habilitação: " + getTempoHabilitacao() + "\n" +
                "Gênero músical favorito: " + getGeneroMusicalFavorito() + "\n" +
                "Placa: " + getPlacaVeiculo() + "\n" +
                "Número da carteira de motorista: " + getCarteiraMotorista() + "\n" +
                "Marca: " + getMarcaVeiculo() + "\n" +
                "Modelo: " + getModeloVeiculo() + "\n" +
                "Assentos disponíveis: " + getAssentosDisponiveis() + "\n";
                // "Perfil: " + getPerfil().toString() + "\n"; // loop => perfil.toString() utiliza caronante.toString()
    }
}
