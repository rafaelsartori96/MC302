/*
 * Rafael Santos (RA: 186154)
 *
 * Questão 1:
 *  Sim, é possível escrever uma classe sem definir qualquer construtor, pois existe um construtor padrão vazio.
 *
 * Considerando que tudo é alcançável pelo escopo atual:
 * Questão 2:
 *  Um método estático só pode acessar um atributo não estático se acessá-lo através de um parâmetro do método. Não é
 *  possível acessar sem algum parâmetro dado, já que pode haver uma classe com método estático sem qualquer objeto ter
 *  sido criado.
 *
 * Questão 3:
 *  Sim, um método não estático pode acessar um atributo estático da classe, seja utilizando o acesso através do nome da
 *  da classe, seja através do "this.".
 *
 */


import java.util.Scanner;

public class Main {

    public static void main(String[] arguments) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("[DADOS DO CARONANTE]");
        Caronante caronante = new Caronante();

        System.out.print("Há quanto tempo o caronante está habilitado? ");
        caronante.setTempoHabilitacao(scanner.nextInt());
        // Pulamos o '\n' que ficará sobrando ao pressionar enter no inteiro
        scanner.skip("\n");
        System.out.print("Qual o gênero musical favorito? ");
        caronante.setGeneroMusicalFavorito(scanner.nextLine());
        System.out.print("Qual a placa do veículo? ");
        caronante.setPlacaVeiculo(scanner.nextLine());
        System.out.print("E o modelo? ");
        caronante.setModeloVeiculo(scanner.nextLine());
        System.out.print("E a marca? ");
        caronante.setMarcaVeiculo(scanner.nextLine());
        System.out.print("Número de assentos disponíveis? ");
        caronante.setAssentosDisponiveis(scanner.nextInt());

        System.out.println("\nPronto!");
        System.out.println(caronante.toString());

        System.out.println("[DADOS DO CARONEIRO]");

        System.out.print("O caroneiro pagará com cartão de crédito? (true/false) ");
        boolean possuiCartao = scanner.nextBoolean();
        scanner.skip("\n");

        String cartaoDeCredito = null;
        if(possuiCartao) {
            System.out.print("Qual o número do cartão? ");
            cartaoDeCredito = scanner.nextLine();
        }

        System.out.print("O caroneiro pagará em dinheiro? (true/false) ");
        boolean pagarEmDinheiro = scanner.nextBoolean();

        Caroneiro caroneiro = possuiCartao ?
                new Caroneiro(cartaoDeCredito, pagarEmDinheiro) : new Caroneiro(pagarEmDinheiro);

        System.out.println("\nPronto!");
        System.out.println(caroneiro.toString());
    }

    public static void main_lab01(String[] arguments) {
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
