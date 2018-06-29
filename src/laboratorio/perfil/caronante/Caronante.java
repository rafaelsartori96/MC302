package laboratorio.perfil.caronante;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import laboratorio.carona.*;
import laboratorio.perfil.Perfil;
import laboratorio.utilidades.*;

public class Caronante implements Salvavel, Comparable<Caronante> {

    private static int numCaronantes = 0;

    private final ArrayList<CaronaCaronante> caronas = new ArrayList<>();
    private final String carteiraMotorista;

    // Para cálculo de avaliação média
    private float somaAvaliacoesPassadas = 0;
    private int avaliacoesPassadas = 0;

    private Perfil perfil;
    private int tempoHabilitacao;
    private int assentosDisponiveis;
    private String generoMusicalFavorito;
    private String placaVeiculo;
    private String marcaVeiculo;
    private String modeloVeiculo;

    private Caronante(String carteiraMotorista) {
        numCaronantes++;
        this.carteiraMotorista = carteiraMotorista;
    }

    public Caronante(int tempoHabilitacao, String generoMusicalFavorito, String placaVeiculo, String carteiraMotorista,
                     String marcaVeiculo, String modeloVeiculo, int assentosDisponiveis) {
        this(carteiraMotorista);
        this.tempoHabilitacao = tempoHabilitacao;
        this.generoMusicalFavorito = generoMusicalFavorito;
        this.placaVeiculo = placaVeiculo;
        this.marcaVeiculo = marcaVeiculo;
        this.modeloVeiculo = modeloVeiculo;
        this.assentosDisponiveis = assentosDisponiveis;
    }

    private Caronante(int tempoHabilitacao, String generoMusicalFavorito, String placaVeiculo, String carteiraMotorista,
                      String marcaVeiculo, String modeloVeiculo, int assentosDisponiveis, int avaliacoesPassadas,
                      float somaAvaliacoesPassadas) {
        this(tempoHabilitacao, generoMusicalFavorito, placaVeiculo, carteiraMotorista, marcaVeiculo, modeloVeiculo,
                assentosDisponiveis);
        this.avaliacoesPassadas = avaliacoesPassadas;
        this.somaAvaliacoesPassadas = somaAvaliacoesPassadas;
    }

    public CaronaPublica oferecerCaronaPublica(double latitudeEncontro, double longitudeEncontro,
                                               double latitudeDestino, double longitudeDestino, String horaDiaEncontro,
                                               float valor) {
        CaronaCaronante caronaCaronante = new CaronaCaronante(this, null);
        CaronaPublica carona = new CaronaPublica(caronaCaronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
                longitudeDestino, horaDiaEncontro, valor);
        caronaCaronante.setCarona(carona);
        this.caronas.add(caronaCaronante);
        return carona;
    }

    public CaronaPrivada oferecerCaronaPrivada(double latitudeEncontro, double longitudeEncontro,
                                               double latitudeDestino, double longitudeDestino, String horaDiaEncontro,
                                               float valor) {
        CaronaCaronante caronaCaronante = new CaronaCaronante(this, null);
        CaronaPrivada carona = new CaronaPrivada(caronaCaronante, latitudeEncontro, longitudeEncontro, latitudeDestino,
                longitudeDestino, horaDiaEncontro, valor);
        caronaCaronante.setCarona(carona);
        this.caronas.add(caronaCaronante);
        return carona;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        if (this.perfil != perfil) {
            this.perfil = perfil;
            if (perfil != null) {
                perfil.setCaronante(this);
            }
        }
    }

    public int getTempoHabilitacao() {
        return tempoHabilitacao;
    }

    public void setTempoHabilitacao(int tempoHabilitacao) {
        this.tempoHabilitacao = tempoHabilitacao;
    }

    public String getGeneroMusicalFavorito() {
        return generoMusicalFavorito;
    }

    public void setGeneroMusicalFavorito(String generoMusicalFavorito) {
        this.generoMusicalFavorito = generoMusicalFavorito;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getCarteiraMotorista() {
        return carteiraMotorista;
    }

    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public List<Carona> getCaronas() {
        return Collections.unmodifiableList(
                caronas.stream().map(CaronaCaronante::getCarona).collect(Collectors.toList()));
    }

    public float getAvaliacao() {
        // A nota padrão é -1, o que significa que nenhuma nota foi atribuida, filtramos os valores para as notas
        // válidas
        double[] avaliacoes = caronas.stream().mapToDouble(CaronaCaronante::getAvaliacao)
                .filter(value -> value >= 0).toArray();
        // Somamos e dividimos pela quantidade
        return (float) ((Arrays.stream(avaliacoes).sum() + somaAvaliacoesPassadas) /
                (avaliacoes.length + avaliacoesPassadas));
    }

    public static int getNumCaronantes() {
        return numCaronantes;
    }

    public String toStringDesdeUsuario(int numeroEspacos) {
        return perfil.getUsuario().toString(numeroEspacos);
    }

    public String toString(int numeroEspacos) {
        String espacos = HelperFormatacao.criaEspacos(numeroEspacos);
        return String.format("%s| Caronante\n"
                        + "%s| - Tempo de habilitacao em anos: %d\n"
                        + "%s| - Genero musical favorito: %s\n"
                        + "%s| - Placa do veiculo: %s\n"
                        + "%s| - Carteira de motorista: %s\n"
                        + "%s| - Marca do veiculo: %s\n"
                        + "%s| - Modelo do Veiculo: %s\n"
                        + "%s| - Assentos disponiveis: %d\n"
                        + "%s| - Numero de caronantes no momento: %d\n",
                espacos, espacos, this.tempoHabilitacao, espacos, this.generoMusicalFavorito, espacos,
                this.placaVeiculo, espacos, this.carteiraMotorista,
                espacos, this.marcaVeiculo, espacos, this.modeloVeiculo,
                espacos, this.assentosDisponiveis, espacos, Caronante.getNumCaronantes());
    }

    @Override
    public String toString() {
        return toString(0);
    }

    // Será incluido pelo perfil

    @Override
    public void salvarParaArquivo(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(carteiraMotorista);
        dataOutputStream.writeInt(tempoHabilitacao);
        dataOutputStream.writeInt(assentosDisponiveis);
        dataOutputStream.writeUTF(generoMusicalFavorito);
        dataOutputStream.writeUTF(placaVeiculo);
        dataOutputStream.writeUTF(marcaVeiculo);
        dataOutputStream.writeUTF(modeloVeiculo);
        double[] avaliacoes = caronas.stream().mapToDouble(CaronaCaronante::getAvaliacao)
                .filter(value -> value >= 0).toArray();
        dataOutputStream.writeInt(avaliacoesPassadas + avaliacoes.length);
        dataOutputStream.writeFloat(somaAvaliacoesPassadas + (float) Arrays.stream(avaliacoes).sum());
        dataOutputStream.flush();
    }

    public static Caronante carregar(DataInputStream inputStream) throws IOException {
        String carteiraMotorista = inputStream.readUTF();
        int tempoHabilitacao = inputStream.readInt();
        int assentosDisponiveis = inputStream.readInt();
        String generoMusicalFavorito = inputStream.readUTF();
        String placaVeiculo = inputStream.readUTF();
        String marcaVeiculo = inputStream.readUTF();
        String modeloVeiculo = inputStream.readUTF();
        int avaliacoes = inputStream.readInt();
        float somaAvaliacoes = inputStream.readFloat();

        return new Caronante(
                tempoHabilitacao,
                generoMusicalFavorito,
                placaVeiculo,
                carteiraMotorista,
                marcaVeiculo,
                modeloVeiculo,
                assentosDisponiveis,
                avaliacoes,
                somaAvaliacoes
        );
    }

    @Override
    public int compareTo(Caronante caronante) {
        return Float.compare(getAvaliacao(), caronante.getAvaliacao());
    }
}
