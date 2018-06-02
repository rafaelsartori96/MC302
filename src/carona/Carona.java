package carona;

import java.util.ArrayList;
import java.util.stream.Collectors;

import perfil.caronante.*;
import perfil.caroneiro.CaronaCaroneiro;
import perfil.caroneiro.Caroneiro;
import pagamento.MetodoPagamento;
import utilidades.HelperFormatacao;

public abstract class Carona {

    private final ArrayList<CaronaCaroneiro> caroneiros = new ArrayList<>();
    private final ArrayList<MetodoPagamento> metodoPagamentos = new ArrayList<>();

    private final CaronaCaronante caronante;

    private double latitudeEncontro;
    private double longitudeEncontro;
    private double latitudeDestino;
    private double longitudeDestino;
    private String horaDiaEncontro;
    private int ocupacaoMaxima;
    private float valor;

    public Carona(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro,
                  double latitudeDestino, double longitudeDestino, String horaDiaEncontro, float valor) {
        this.ocupacaoMaxima = caronante.getCaronante().getAssentosDisponiveis();
        this.caronante = caronante;
        this.latitudeEncontro = latitudeEncontro;
        this.longitudeEncontro = longitudeEncontro;
        this.latitudeDestino = latitudeDestino;
        this.longitudeDestino = longitudeDestino;
        this.horaDiaEncontro = horaDiaEncontro;
        this.valor = valor;
    }

    public Carona(CaronaCaronante caronante, double latitudeEncontro, double longitudeEncontro,
                  double latitudeDestino, double longitudeDestino, String horaDiaEncontro, float valor, int ocupacaoMaxima) {
        this(caronante, latitudeEncontro, longitudeEncontro, latitudeDestino, longitudeDestino, horaDiaEncontro, valor);
        this.ocupacaoMaxima = ocupacaoMaxima;
    }

    public Caronante getCaronante() {
        return caronante.getCaronante();
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
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

    public String getHoraDiaEncontro() {
        return horaDiaEncontro;
    }

    public void setHoraDiaEncontro(String horaDiaEncontro) {
        this.horaDiaEncontro = horaDiaEncontro;
    }

    public int getOcupacaoMaxima() {
        return ocupacaoMaxima;
    }

    public int getAssentosDisponiveis() {
        return ocupacaoMaxima - caroneiros.size();
    }

    public int verificaOcupacao() {
        return caroneiros.size();
    }

    public boolean caronaVazia() {
        return caroneiros.isEmpty();
    }

    // Sim, no exercicio está escrito que é pra criar aqui a classe associativa.
    // Mas no próximo lab vai ser pedido pra existir o pedirCarona em Caroneiro.
    // Honestamente, faz mais sentido ligar lá, e aqui só adicionar :). Mas tanto faz a escolha, precisa ter
    // um método de cada lado.
    public boolean adicionarCaroneiro(CaronaCaroneiro caronaCaroneiro) {
        if (getAssentosDisponiveis() == 0) {
            return false;
        }

        caroneiros.add(caronaCaroneiro);

        return true;
    }

    public boolean removerCaroneiro(Caroneiro caroneiro) {
        // Precisa fazer isso pra remover por id. É feio, e dá pra melhorar fazendo sobrescrita do Equals
        // pra considerar id. Mas não entra no escopo da matéria até o momento, então assim é o melhor por agora
        for (CaronaCaroneiro caroneiroBuscado : caroneiros) {
            if (caroneiroBuscado.getCaroneiro().getPerfil().getUsuario().getId()
                    == caroneiro.getPerfil().getUsuario().getId()) {
                caroneiros.remove(caroneiroBuscado);
                return true; // Importante sair porque, se não estou enganado, remover um elemento invalida o iterador
            }
        }
        return false;
    }

    public boolean adicionarFormaPagamento(MetodoPagamento metodoPagamento) {
        if (metodoPagamentos.contains(metodoPagamento)) {
            return false;
        }

        metodoPagamentos.add(metodoPagamento);
        return true;
    }

    public boolean removerFormaPagamento(MetodoPagamento metodoPagamento) {
        return metodoPagamentos.remove(metodoPagamento);
    }

    public boolean checarExistenciaFormaPagamento(MetodoPagamento metodoPagamento) {
        return metodoPagamentos.contains(metodoPagamento);
    }

    public boolean caronaGratuita() {
        return metodoPagamentos.contains(MetodoPagamento.GRATIS);
    }

    public boolean atribuirNotaCaroneiro(int id, float avaliacao) {
        for (CaronaCaroneiro cc : caroneiros) {
            if (cc.getCaroneiro().getPerfil().getUsuario().getId() == id) {
                cc.setAvaliacao(avaliacao);
                return true;
            }
        }
        return false;
    }

    public boolean atribuirNotaCaronante(float avaliacao) {
        if (caronante == null) return false;
        caronante.setAvaliacao(avaliacao);
        return true;
    }

    public String toString(int numeroEspacos) {
        String espacos = HelperFormatacao.criaEspacos(numeroEspacos);
        String stringCaroneiros = espacos + "*Nao ha caroneiros nessa carona*\n";
        if (!caroneiros.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            String espacos2 = HelperFormatacao.criaEspacos(numeroEspacos + 1);
            for (CaronaCaroneiro caroneiro : caroneiros) {
                stringBuilder.append(caroneiro.getCaroneiro().toStringDesdeUsuario(numeroEspacos + 1));
                stringBuilder.append(espacos2).append("| - Avaliacao dada para a carona: ").append(caroneiro.getAvaliacao()).append("\n");
            }

            stringCaroneiros = stringBuilder.toString();
        }

        String stringFormaPag = espacos + "*Nao ha forma de pagamento cadastrado*\n";
        if (!metodoPagamentos.isEmpty()) {
            // Um exemplo com stream. Nesse caso ele faz um stream (simplificando, nesse caso é como se fosse uma lista)
            // com as formas de pagamento, e então transforma cada elemento MetodoPagamento para uma string que
            // tem o retorno do método "name" (que retorna o string correspondente ao enum) do enum.
            // Finalmente, dá um join com ", "
            stringFormaPag = metodoPagamentos.stream()
                    .map(MetodoPagamento::name)
                    .collect(Collectors.joining(", "));
        }

        return String.format("%s| Carona\n"
                        + "%s| - Coordenadas do encontro: (%f, %f)\n"
                        + "%s| - Coordenadas do destino: (%f, %f)\n"
                        + "%s| - Hora e dia do encontro: %s\n"
                        + "%s| - Ocupacao maxima: %d\n"
                        + "%s| - Numero de assentos disponiveis: %d\n"
                        + "%s| - Dados do caronante: \n%s"
                        + "%s| - Avaliacao dada pelo caronante: %s\n"
                        + "%s| - Dados dos caroneiros: \n%s"
                        + "%s| - Formas de pagamento aceitas: %s\n",
                espacos, espacos, this.latitudeEncontro, this.longitudeEncontro,
                espacos, this.latitudeDestino, this.longitudeDestino,
                espacos, this.horaDiaEncontro,
                espacos, this.ocupacaoMaxima,
                espacos, this.getAssentosDisponiveis(),
                espacos, this.caronante.getCaronante().toStringDesdeUsuario(numeroEspacos + 1),
                espacos, this.caronante.getAvaliacao(),
                espacos, stringCaroneiros,
                espacos, stringFormaPag);
    }

    @Override
    public String toString() {
        return toString(0);
    }


}
