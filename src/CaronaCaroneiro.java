public class CaronaCaroneiro {

    private final Caroneiro caroneiro;
    private final Carona carona;

    private float avaliacao;

    public CaronaCaroneiro(Caroneiro caroneiro, Carona carona) {
        this.caroneiro = caroneiro;
        this.carona = carona;

        this.caroneiro.adicionarCarona(this);
    }

    public Caroneiro getCaroneiro() {
        return caroneiro;
    }

    public Carona getCarona() {
        return carona;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Carona <-> Caroneiro (" + getCaroneiro().toString() + ")";
    }
}
