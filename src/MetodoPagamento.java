public enum MetodoPagamento {

    CARTAO_DE_CREDITO,
    DINHEIRO,
    GRATIS;

    public boolean possuiCusto() {
        switch (this) {
            case GRATIS:
                return false;
            default:
                return true;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}
