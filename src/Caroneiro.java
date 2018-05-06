import java.util.ArrayList;

public class Caroneiro {

    private final ArrayList<CaronaCaroneiro> caronas = new ArrayList<>();

    private String cartaoDeCrediito = null;
    private Perfil perfil = null;

    public Caroneiro() {
    }

    public Caroneiro(Perfil perfil) {
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
        if (this.perfil != perfil) {
            this.perfil = perfil;
            perfil.setCaroneiro(this);
        }
    }

    public boolean pedirCarona(Carona carona) {
        return carona.adicionarCaroneiro(this);
    }

    public void adicionarCarona(CaronaCaroneiro caronaCaroneiro) {
        // Deixamos a carona cuidar da lógica
        this.caronas.add(caronaCaroneiro);
    }

    public void removerCarona(CaronaCaroneiro caronaCaroneiro) {
        this.caronas.remove(caronaCaroneiro);
    }

    public float getAvaliacao() {
        if (this.caronas.isEmpty()) {
            return -1.0F;
        }

        float soma = 0.0F;
        for (CaronaCaroneiro caronaCaroneiro : this.caronas) {
            soma += caronaCaroneiro.getAvaliacao();
        }

        return soma / this.caronas.size();
    }

    @Override
    public String toString() {
        return "Caroneiro:\n" +
                "Cartão de crédito: " + getCartaoDeCrediito() + "\n" +
                "Avaliação: " + getAvaliacao() + "\n";
    }
}
