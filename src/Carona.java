import java.util.ArrayList;
import java.util.EnumSet;

public abstract class Carona {

    private final ArrayList<CaronaCaroneiro> caroneiros = new ArrayList<>();
    private final EnumSet<MetodoPagamento> metodoPagamentos = EnumSet.noneOf(MetodoPagamento.class);
    private final CaronaCaronante caronante;

    private String horaDiaEncontro;
    private double latitudeEncontro, longitudeEncontro;
    private double latitudeDestino, longitudeDestino;
    private int ocupacaoMaxima;
    private float valor;

    public Carona(Caronante caronante) {
        this.caronante = new CaronaCaronante(caronante, this);
        this.ocupacaoMaxima = caronante.getAssentosDisponiveis();
    }

    public ArrayList<CaronaCaroneiro> getCaroneiros() {
        return caroneiros;
    }

    public CaronaCaronante getCaronante() {
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

    public void setOcupacaoMaxima(int ocupacaoMaxima) {
        this.ocupacaoMaxima = ocupacaoMaxima;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean caronaVazia() {
        return this.caroneiros.isEmpty();
    }

    public abstract boolean adicionarCaroneiro(Caroneiro caroneiro);

    public boolean removerCaroneiro(Caroneiro caroneiro) {
        for (int i = 0; i < getCaroneiros().size(); i++) {
            CaronaCaroneiro caronaCaroneiro = getCaroneiros().get(i);
            if (caronaCaroneiro.getCaroneiro().equals(caroneiro)) {
                caroneiro.removerCarona(caronaCaroneiro);
                getCaroneiros().remove(i);
                return true;
            }
        }
        return false;
    }

    public int verificaOcupacao() {
        return this.caroneiros.size();
    }

    /*
     * Havia assumido no laboratório 5 que uma carona pode ser de graça porém ainda aceitar outros métodos de pagamento,
     * como se fosse uma carona de pagamento opcional, mas está errado.
     */
    public boolean adicionarFormaPagamento(MetodoPagamento metodoPagamento) {
        if(metodoPagamento.possuiCusto()) {
            removerFormaPagamento(MetodoPagamento.GRATIS);
        }
        return this.metodoPagamentos.add(metodoPagamento); // Por ser um set, não precisamos conferir por duplicatas
    }

    public boolean removerFormaPagamento(MetodoPagamento metodoPagamento) {
        return this.metodoPagamentos.remove(metodoPagamento);
    }

    public boolean checarFormaPagamento(MetodoPagamento metodoPagamento) {
        return this.metodoPagamentos.contains(metodoPagamento);
    }

    public boolean caronaGratuita() {
        return this.metodoPagamentos.isEmpty() || this.metodoPagamentos.contains(MetodoPagamento.GRATIS);
    }

    public boolean atribuirNotaCaroneiro(int id_caroneiro, float avaliacao) {
        for(CaronaCaroneiro caronaCaroneiro : this.caroneiros) {
            if(caronaCaroneiro.getCaroneiro().getPerfil().getUsuario().getId() == id_caroneiro) {
                caronaCaroneiro.setAvaliacao(avaliacao);
                return true;
            }
        }
        return false;
    }

    public void atribuirNotaCaronante(float avaliacao) {
        /* Sempre temos um caronante */
        this.caronante.setAvaliacao(avaliacao);
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
