public class Caronante {

    private int tempoHabilitacao;
    private String generoMusicalFavorito;
    private String placaVeiculo;
    private String carteiraMotorista;
    private String modeloVeiculo;
    private String marcaVeiculo;
    private int assentosDisponiveis;

    private static int numCaroneiros = 0;


    public Caronante() {
        /* "A classe Caronante deverá necessita apenas de um construtor sem parâmetros"
        * No entanto, no exemplo do slide, a classe caronante está com um construtor. Acredito que a intenção final
        * seria a utilização dos setters.
        */
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

    public void setCarteiraMotorista(String carteiraMotorista) {
        this.carteiraMotorista = carteiraMotorista;
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

    @Override
    public String toString() {
        return "[DADOS DO CARONANTE]\n" +
                "tempoHabilitacao=" + getTempoHabilitacao() + "\n" +
                "generoMusicalPreferido=" + getGeneroMusicalFavorito() + "\n" +
                "placaVeiculo=" + getPlacaVeiculo() + "\n" +
                "carteiraMotorista=" + getCarteiraMotorista() + "\n" +
                "marcaVeiculo=" + getMarcaVeiculo() + "\n" +
                "modeloVeiculo=" + getModeloVeiculo() + "\n" +
                "assentosDisponiveis=" + getAssentosDisponiveis() + "\n";
    }

    public static int getNumCaroneiros() {
        return numCaroneiros;
    }
}
