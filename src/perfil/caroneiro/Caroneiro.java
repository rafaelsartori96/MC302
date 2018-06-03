package perfil.caroneiro;

import java.io.*;
import java.util.*;

import carona.Carona;
import perfil.Perfil;
import utilidades.*;

public class Caroneiro implements Salvavel, Comparable<Caroneiro> {

    private static int numCaroneiros = 0;

    private final ArrayList<CaronaCaroneiro> caronas = new ArrayList<>();

    // Para cálculo de avaliação após salvar
    private float somaAvaliacoesPassadas = 0;
    private int avaliacoesPassadas = 0;

    private String cartaoDeCredito;
    private boolean pagamentoEmDinheiro;
    private Perfil perfil;

    public Caroneiro(boolean pagamentoEmDinheiro) {
        this();
        this.pagamentoEmDinheiro = pagamentoEmDinheiro;
    }

    private Caroneiro(boolean pagamentoEmDinheiro, String cartaoDeCredito, int avaliacoesPassadas, float somaAvaliacoesPassadas) {
        this();
        this.pagamentoEmDinheiro = pagamentoEmDinheiro;
        this.cartaoDeCredito = cartaoDeCredito;
        this.avaliacoesPassadas = avaliacoesPassadas;
        this.somaAvaliacoesPassadas = somaAvaliacoesPassadas;
    }

    public Caroneiro() {
        numCaroneiros++;
    }

    public boolean pedirCarona(Carona carona) {
        CaronaCaroneiro caronaCaroneiro = new CaronaCaroneiro(this, carona);

        if (!carona.adicionarCaroneiro(caronaCaroneiro)) {
            return false; // Sem assento
        }

        caronas.add(caronaCaroneiro);
        return true;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getCartaoDeCredito() {
        return cartaoDeCredito;
    }

    public void setCartaoDeCredito(String cartaoDeCredito) {
        this.cartaoDeCredito = cartaoDeCredito;
    }

    public boolean isPagamentoEmDinheiro() {
        return pagamentoEmDinheiro;
    }

    public void setPagamentoEmDinheiro(boolean pagamentoEmDinheiro) {
        this.pagamentoEmDinheiro = pagamentoEmDinheiro;
    }

    public float getAvaliacao() {
        // A nota padrão é -1, o que significa que nenhuma nota foi atribuida, filtramos os valores para as notas válidas
        double[] avaliacoes = caronas.stream().mapToDouble(CaronaCaroneiro::getAvaliacao).filter(value -> value >= 0).toArray();
        // Somamos e dividimos pela quantidade
        return (float) ((Arrays.stream(avaliacoes).sum() + somaAvaliacoesPassadas) / (avaliacoes.length + avaliacoesPassadas));
    }

    public static int getNumCaroneiros() {
        return numCaroneiros;
    }

    public String toStringDesdeUsuario(int numeroEspacos) {
        return perfil.getUsuario().toString(numeroEspacos);
    }

    public String toString(int numeroEspacos) {
        String espacos = HelperFormatacao.criaEspacos(numeroEspacos);
        return String.format("%s| Caroneiro\n"
                        + "%s| - Numero do cartao de credito: %s\n"
                        + "%s| - Vai ser pago em dinheiro?: %s\n"
                        + "%s| - Numero de caroneiros no momento: %s\n",
                espacos, espacos, this.cartaoDeCredito, espacos, this.pagamentoEmDinheiro ? "Sim" : "Nao",
                espacos, Caroneiro.getNumCaroneiros());
    }

    @Override
    public String toString() {
        return toString(0);
    }

    // Será incluido pelo perfil

    @Override
    public void salvarParaArquivo(DataOutputStream dataOutputStream) throws IOException {
        if (cartaoDeCredito == null || cartaoDeCredito.length() == 0) {
            dataOutputStream.writeBoolean(false);
        } else {
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeUTF(cartaoDeCredito);
        }
        dataOutputStream.writeBoolean(pagamentoEmDinheiro);
        double[] avaliacoes = caronas.stream().mapToDouble(CaronaCaroneiro::getAvaliacao).filter(value -> value >= 0).toArray();
        dataOutputStream.writeInt(avaliacoesPassadas + avaliacoes.length);
        dataOutputStream.writeFloat(somaAvaliacoesPassadas + (float) Arrays.stream(avaliacoes).sum());
        dataOutputStream.flush();
    }

    public static Caroneiro carregar(DataInputStream inputStream) throws IOException {
        /* Verificamos se possui cartão de crédito */
        String cartaoDeCredito = null;
        if (inputStream.readBoolean()) {
            cartaoDeCredito = inputStream.readUTF();
        }

        /* Verificamos o pagamento em dinheiro */
        boolean pagamentoEmDinheiro = inputStream.readBoolean();
        int avaliacoes = inputStream.readInt();
        float somaAvaliacoes = inputStream.readFloat();

        return new Caroneiro(pagamentoEmDinheiro, cartaoDeCredito, avaliacoes, somaAvaliacoes);
    }

    @Override
    public int compareTo(Caroneiro caroneiro) {
        return Float.compare(getAvaliacao(), caroneiro.getAvaliacao());
    }
}
