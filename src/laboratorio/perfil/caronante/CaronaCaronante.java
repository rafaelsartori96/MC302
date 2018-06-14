package laboratorio.perfil.caronante;

import laboratorio.carona.Carona;

public class CaronaCaronante {

    private Caronante caronante;
    private Carona carona;

    private float avaliacao = -1.0f;

    public CaronaCaronante(Caronante caroneiro, Carona carona) {
        this.caronante = caroneiro;
        this.carona = carona;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Caronante getCaronante() {
        return caronante;
    }

    public Carona getCarona() {
        return carona;
    }

    public void setCaronante(Caronante caronante) {
        this.caronante = caronante;
    }

    public void setCarona(Carona carona) {
        this.carona = carona;
    }
}
