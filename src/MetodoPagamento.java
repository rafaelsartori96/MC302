public enum MetodoPagamento {

    CARTAO_DE_CREDITO,
    DINHEIRO,
    GRATIS;

    @Override
    public String toString() {
        return name();
    }
}
