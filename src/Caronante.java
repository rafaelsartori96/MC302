import java.util.ArrayList;

public class Caronante {

    private int tempoHabilitacao;
    private String generoMusicalFavorito;
    private String placaVeiculo;
    private final String carteiraMotorista;
    private String modeloVeiculo;
    private String marcaVeiculo;
    private int assentosDisponiveis;

    private Perfil perfil;

    private final ArrayList<CaronaCaronante> caronas = new ArrayList<>();

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

    public float getAvaliacao() {
        if(this.caronas.isEmpty()) {
            return -1.0F;
        }

        float soma = 0.0F;
        for(CaronaCaronante caronaCaronante : this.caronas) {
            soma = caronaCaronante.getAvaliacao();
        }

        return soma / this.caronas.size();
    }

    public CaronaPublica oferecerCaronaPublica() {
        CaronaPublica caronaPublica = new CaronaPublica(this);
        this.caronas.add(caronaPublica.getCaronante());
        return caronaPublica;
    }

    public CaronaPrivada oferecerCaronaPrivada() {
        CaronaPrivada caronaPrivada = new CaronaPrivada(this);
        this.caronas.add(caronaPrivada.getCaronante());
        return caronaPrivada;
    }

    @Override
    public String toString() {
        return "Caronante:\n" +
                "Tempo de habilitação: " + getTempoHabilitacao() + "\n" +
                "Gênero músical favorito: " + getGeneroMusicalFavorito() + "\n" +
                "Placa: " + getPlacaVeiculo() + "\n" +
                "Avaliação: " + getAvaliacao() + "\n" +
                "Número da carteira de motorista: " + getCarteiraMotorista() + "\n" +
                "Marca: " + getMarcaVeiculo() + "\n" +
                "Modelo: " + getModeloVeiculo() + "\n" +
                "Assentos disponíveis: " + getAssentosDisponiveis() + "\n";
    }
}
