/*
 * Rafael Santos (RA: 186154)
 *
 * Questão 1:
 *  Não foi possível alterar o valor de alguma variável final, o código não é compilado.
 *
 * Questão 2:
 *  Sim, foi possível. É possível pois a variável final apenas impede escrita direta na variável, ou seja, proíbiria,
 *  por exemplo, a atribuição de outro Caronante à variável.
 *
 * Questão 3:
 *  O comportamendo do programa não mudou pois, da maneira que tratávamos a variável, não havia a necessidade da
 *  variável não ser final. Afinal, a ArrayList redimensionaria a array interna, não necessitando reatribuir à
 *  caronantes alguma outra ArrayList. Da mesma forma, para o array, como há um máximo de ocupantes, não há a
 *  necessidade (senão economizar memória) de diminuir ou aumentar o vetor, o que dependeria de uma reatribuição também.
 *
 * Questão 4:
 *  Como não há alteração no número de ocupantes, os dois funcionariam eficientemente bem. A vantagem do ArrayList é
 *  redimensionar o vetor baseado no número de objetos presentes (para um ônibus vazio, por exemplo, não haveria um
 *  gasto excessivo de memória, pois enquanto a array teria todos espaços reservados, a ArrayList teria uma quantidade
 *  menor conforme desejasse o programador -- já que ArrayList permite a alteração de tamanho na construção e execução
 *  através de métodos desta coleção).
 *
 */


import java.util.Scanner;

public class Main {

    public static void main(String[] arguments) {
        Caroneiro caroneiros[] = new Caroneiro[4];
        for (int i = 0; i < caroneiros.length; i++) {
            caroneiros[i] = new Caroneiro(true);
        }

        Caronante caronante = new Caronante("31415");
        caronante.setAssentosDisponiveis(3);
        caronante.setModeloVeiculo("Fusca");
        caronante.setMarcaVeiculo("Volkswagem");
        caronante.setPlacaVeiculo("aaa-0000");
        caronante.setTempoHabilitacao(99);
        caronante.setGeneroMusicalFavorito("Qualquer");

        Carona carona = new Carona(caronante);
        carona.setHoraEncontro("13/03/2018 14:00");
        carona.setLatitudeDestino(3.1415d);
        carona.setLongitudeDestino(3.1415d);
        carona.setLatitudeEncontro(3.1415d + 1);
        carona.setLongitudeEncontro(3.1415d + 1);

        for (Caroneiro caroneiro : caroneiros) {
            carona.adicionarCaroneiro(caroneiro);
        }

        System.out.println(carona.toString());


        CaronaAL caronaAL = new CaronaAL(caronante);
        caronaAL.setHoraEncontro("13/03/2018 14:00");
        caronaAL.setLatitudeDestino(3.1415d);
        caronaAL.setLongitudeDestino(3.1415d);
        caronaAL.setLatitudeEncontro(3.1415d + 1);
        caronaAL.setLongitudeEncontro(3.1415d + 1);

        for (Caroneiro caroneiro : caroneiros) {
            caronaAL.adicionarCaroneiro(caroneiro);
        }

        System.out.println(caronaAL.toString());
    }

    public static void main_lab02(String[] arguments) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("[DADOS DO CARONANTE]");
        Caronante caronante = new Caronante("32");

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
