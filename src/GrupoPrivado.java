public class GrupoPrivado extends Grupo {

    public static int testeStatic = 32;
    public int testeNaoStatic = 33;

    public GrupoPrivado(String nome, String descricao) {
        super(nome, descricao);
    }

    @Override
    public String toString() {
        return "Grupo privado:\n" + super.toString();
    }
}
