public class Caroneiro {

    private String cartaoDeCrediito;

    private Perfil perfil;

    public Caroneiro(String cartaoDeCrediito) {
        setCartaoDeCrediito(cartaoDeCrediito);
    }

    public Caroneiro(String cartaoDeCrediito, Perfil perfil) {
        this(cartaoDeCrediito);
        setPerfil(perfil);
    }

    public String getCartaoDeCrediito() {
        return cartaoDeCrediito;
    }

    public void setCartaoDeCrediito(String cartaoDeCrediito) {
        this.cartaoDeCrediito = cartaoDeCrediito;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        if(this.perfil != perfil) {
            this.perfil = perfil;
            perfil.setCaroneiro(this);
        }
    }

    @Override
    public String toString() {
        return "Caroneiro:\n" +
                "Cartão de crédito: " + getCartaoDeCrediito() + "\n"; // privacidade nula muahah
                // "Perfil: " + getPerfil().toString() + "\n"; // loop (perfil.toString() imprime caroneiro.toString())
    }
}
