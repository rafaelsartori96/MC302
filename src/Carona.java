import java.util.ArrayList;
import java.util.EnumSet;

// Transformei a classe em abstrata pois acredito que não haja uma carona diferente de publica ou privada
// Destransformei ela de abstrata apesar de não ver sentido
public class Carona {

    private final ArrayList<Caroneiro> caroneiros = new ArrayList<>();
    private final Caronante caronante;
    private String horaDiaEncontro;
    private double latitudeEncontro, longitudeEncontro;
    private double latitudeDestino, longitudeDestino;
    private int ocupacaoMaxima;
    private float valor;
    // EnumSet são vetores binários para um enum, economiza memória, usei porque achei interessante na biblioteca :)
    // Set somente implica que não há duplicatas, o que é de se esperar, sobretudo para este fim.
    private final EnumSet<MetodoPagamento> metodoPagamentos;

    public Carona(Caronante caronante) {
        // Não sei se essa era a ideia do construtor ou se ele devia ser mais completo..
        this.caronante = caronante;
        this.ocupacaoMaxima = caronante.getAssentosDisponiveis();
        this.metodoPagamentos = EnumSet.noneOf(MetodoPagamento.class);
    }

    public ArrayList<Caroneiro> getCaroneiros() {
        return caroneiros;
    }

    public Caronante getCaronante() {
        return caronante;
    }

    public String getHoraDiaEncontro() {
        return horaDiaEncontro;
    }

    public void setHoraDiaEncontro(String horaDiaEncontro) {
        this.horaDiaEncontro = horaDiaEncontro;
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
        return this.caroneiros.isEmpty();
    }

    public boolean adicionarCaroneiro(Caroneiro caroneiro) {
        if(this.caroneiros.size() >= this.ocupacaoMaxima) {
            return false;
        }

        this.caroneiros.add(caroneiro);
        return true;
    }

    public boolean removerCaroneiro(Caroneiro caroneiro) {
        return this.caroneiros.remove(caroneiro);
    }

    /* Errei no laboratório 3 por conta do erro de digitação da Esther no enunciado, o laboratório 4 contém esse erro
    * pois a correção foi divulgada mais tarde (na semana do laboratório 5), espero que não se incomodem com essa
    * "propagação de erro". Enfim, o correto é dizer quantos usuários estão na carona. */
    public int verificaOcupacao() {
        return this.caroneiros.size();
    }

    public boolean adicionarFormaPagamento(MetodoPagamento metodoPagamento) {
        return this.metodoPagamentos.add(metodoPagamento); // Por ser um set, não precisamos conferir por duplicatas
    }

    public boolean removerFormaPagamento(MetodoPagamento metodoPagamento) {
        return this.metodoPagamentos.remove(metodoPagamento);
    }

    public boolean checarFormaPagamento(MetodoPagamento metodoPagamento) {
        return this.metodoPagamentos.contains(metodoPagamento);
    }

    public boolean caronaGratuita() {
        return this.metodoPagamentos.contains(MetodoPagamento.GRATIS);
    }

    @Override
    public String toString() {
        return "Carona (" +
                "vazio=" + caronaVazia() +
                ", caronante=" + caronante +
                ", horaDiaEncontro='" + horaDiaEncontro + "'" +
                ", latitudeEncontro=" + latitudeEncontro +
                ", longitudeEncontro=" + longitudeEncontro +
                ", latitudeDestino=" + latitudeDestino +
                ", longitudeDestino=" + longitudeDestino +
                ", ocupacaoMaxima=" + ocupacaoMaxima +
                ", metodosPagamento=" + metodoPagamentos.toString() +
                ", caroneiros=" + caroneiros.toString() + ")";
    }
}
