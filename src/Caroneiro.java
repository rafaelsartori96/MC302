public class Caroneiro {

    private String cartaoDeCrediito;
    private boolean pagamentoEmDinheiro;

    private static int numCaronantes = 0;


    public Caroneiro(String cartaoDeCrediito, boolean pagamentoEmDinheiro) {
        this.cartaoDeCrediito = cartaoDeCrediito;
        this.pagamentoEmDinheiro = pagamentoEmDinheiro;
    }

    public Caroneiro(boolean pagamentoEmDinheiro) {
        this.pagamentoEmDinheiro = pagamentoEmDinheiro;
    }

    public Caroneiro() {
        this.pagamentoEmDinheiro = false;
    }

    public String getCartaoDeCrediito() {
        return cartaoDeCrediito;
    }

    public void setCartaoDeCrediito(String cartaoDeCrediito) {
        this.cartaoDeCrediito = cartaoDeCrediito;
    }

    public boolean isPagamentoEmDinheiro() {
        return pagamentoEmDinheiro;
    }

    public void setPagamentoEmDinheiro(boolean pagamentoEmDinheiro) {
        this.pagamentoEmDinheiro = pagamentoEmDinheiro;
    }

    @Override
    public String toString() {
        return "[DADOS DO CARONEIRO]\n" +
                "cartaoDeCredito=" + getCartaoDeCrediito() + "\n" +
                "pagamentoEmDinheiro=" + isPagamentoEmDinheiro() + "\n";
    }

    public static int getNumCaronantes() {
        return numCaronantes;
    }
}
