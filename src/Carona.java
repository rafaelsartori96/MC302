import java.util.Arrays;

public class Carona {

    private final Caroneiro[] caroneiros;
    private final Caronante caronante;
    private String horaEncontro;
    private double latitudeEncontro, longitudeEncontro;
    private double latitudeDestino, longitudeDestino;
    private int ocupacaoMaxima;

    private int ocupacaoAtual = 0;

    public Carona(Caronante caronante) {
        // Não sei se essa era a ideia do construtor ou se ele devia ser mais completo..
        this.caronante = caronante;
        this.ocupacaoMaxima = caronante.getAssentosDisponiveis();
        this.caroneiros = new Caroneiro[ocupacaoMaxima];
    }

    public Caroneiro[] getCaroneiros() {
        return caroneiros;
    }

    public Caronante getCaronante() {
        return caronante;
    }

    public String getHoraEncontro() {
        return horaEncontro;
    }

    public void setHoraEncontro(String horaEncontro) {
        this.horaEncontro = horaEncontro;
    }

    public double getLatitudeEncontro() {
        return latitudeEncontro;
    }

    public void setLatitudeEncontro(double latitudeEncontro) {
        this.latitudeEncontro = latitudeEncontro;
    }

    public double getLongitudeEncontro() {
        return longitudeEncontro;
    }

    public void setLongitudeEncontro(double longitudeEncontro) {
        this.longitudeEncontro = longitudeEncontro;
    }

    public double getLatitudeDestino() {
        return latitudeDestino;
    }

    public void setLatitudeDestino(double latitudeDestino) {
        this.latitudeDestino = latitudeDestino;
    }

    public double getLongitudeDestino() {
        return longitudeDestino;
    }

    public void setLongitudeDestino(double longitudeDestino) {
        this.longitudeDestino = longitudeDestino;
    }

    public int getOcupacaoMaxima() {
        return ocupacaoMaxima;
    }

    public boolean caronaVazia() {
        return this.ocupacaoAtual == 0;
    }

    public boolean adicionarCaroneiro(Caroneiro caroneiro) {
        if(this.ocupacaoAtual >= this.ocupacaoMaxima) {
            return false;
        }

        this.caroneiros[this.ocupacaoAtual++] = caroneiro;
        return true;
    }

    public boolean verificaOcupacao() {
        return this.ocupacaoAtual > 0;
    }

    @Override
    public String toString() {
        return "Carona (" +
                "vazio=" + caronaVazia() +
                ", caronante=" + caronante +
                ", horaEncontro='" + horaEncontro + "'" +
                ", latitudeEncontro=" + latitudeEncontro +
                ", longitudeEncontro=" + longitudeEncontro +
                ", latitudeDestino=" + latitudeDestino +
                ", longitudeDestino=" + longitudeDestino +
                ", ocupacaoMaxima=" + ocupacaoMaxima +
                ", caroneiros=" + Arrays.toString(caroneiros) + ")";
    }
}
