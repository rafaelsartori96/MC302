/*
 * Rafael Santos (RA: 186154)
 *
 * Questão 1:
 *  O impacto é a quebra do código, pois é impossível alterar um dado 'private' fora do escopo da classe.
 *
 * Questão 2:
 *  O problema poderá ser tratado através do setter de 'nome' na classe Usuario. O pró é o encapsulamento da variável,
 *  a estruturação do programa de tal forma que as mudanças internas não afetem o acesso externo desses dados. Os
 *  contras são as chamadas de funções para coisas simples e direta algumas vezes, outra é necessidade de estruturar as
 *  classes.
 *
 */


public class Main {

    public static void main(String[] arguments) {
        Usuario usuario1 = new Usuario(1, "Rafael Sartori",  "rafael.sartori96@gmail.com", "12345", false);
        Usuario usuario2 = new Usuario(2, "Yoda",  "yodaiam@gmail.com", "13452", true);

        Perfil perfil1 = new Perfil('m', "13/12/1996", "Campinas", "São Paulo", "00 00000-0000", false, 3);
        Perfil perfil2 = new Perfil('m', "12/12/2012", "Araraquara", "São Paulo", "00 00000-0001", false, 32);

        // Erro de compilação: private
        // usuario1.nome = "Teste";
        // Programa executa perfeitamente
        usuario2.setNome("daYo");

        System.out.println("Usuário #1: " + usuario1.toString());
        System.out.println("Usuário #2: " + usuario2.toString());

        System.out.println("Perfil #1: " + perfil1.toString());
        System.out.println("Perfil #2: " + perfil2.toString());
    }

}
