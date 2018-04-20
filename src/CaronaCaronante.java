public class CaronaCaronante {

    private final Caronante caronante;
    private final Carona carona;

    private float avaliacao;

    public CaronaCaronante(Caronante caronante, Carona carona) {
        this.caronante = caronante;
        this.carona = carona;
    }

    public Caronante getCaronante() {
        return caronante;
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
        return "Carona <-> Caronante (" + getCaronante().toString() + ")";
    }
}
