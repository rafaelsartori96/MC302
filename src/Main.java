/*
 * Rafael Santos (RA: 186154)
 *
 * Questão 1:
 *  A vantagem de uma relação bi-direcional é a disponibilidade de atributos em ambos os lados, permitindo diversos
 *  recursos que poderiam ser impedidos caso a relação fosse unidirecional. As desvantagens é o controle complicado de
 *  dados caso não seja constante (declarado como final) (pode facilitar o processo pois não há a necessidade de
 *  atributos serem alterados para uma relação bidirecional fixa).
 *
 * Questão 2:
 *  Não há necessidade de criar uma classe associativa. É possível colocar esses atributos na classe Carona, por
 *  exemplo. A vantagem dessa abordagem é menor manipulação de novos objetos, menos tempo gasto pelo programa os
 *  criando. A desvantagem é que perderia a explicidade da relação, já que essa "interface" contém os atributos que
 *  convém à relação carona - caronante. Ou seja, colocando esses atributos na classe Carona, ficaria menos claro.
 *
 * Questão 3:
 *  Há como criar essa consistência, mas é necessário um estudo sobre o que o programa irá precisar fazer. Por exemplo,
 *  para a relação Usuário - Perfil, poderíamos utilizar uma relação 1:1 (da mesma forma que Carona - Caronante) e
 *  tornar o atributo final, deixando para alguma das classes (fazendo mais sentido na Usuário) cuidar de criar a outra
 *  durante a construção, de modo que há uma relação constante e clara durante toda a execução do programa.
 *
 *  No entanto, na maneira como as classes foram construídas atualmente e na maneira com que muitas aplicações
 *  funcionam, é possível encontrar um instante de tempo em que o usuário pode não tem um perfil criado, o que pode
 *  causar diversos erros na execução do programa. Neste laboratório, podemos notar que comparamos o identificador do
 *  usuário ao atribuir uma nota. Se, por conta de um mal planejamento do programa, um usuário conseguisse receber uma
 *  classe Caroneiro (o que é possível, já que essa relação não é bi-direcional), ao chegar no trecho da comparação,
 *  haveria um NullPointerException, já que esse usuário hipotético não possui perfil associado ao seu Usuário.
 *
 *  Isso pode ser corrigido de várias maneiras como, por exemplo, criando o Perfil junto com o usuário (como várias
 *  aplicações fazem), ou conferindo se o usuário possui perfil em qualquer acesso à getPerfil(), ou ainda criando uma
 *  relação que torne necessária a existência de um perfil para a existência das classes Caronante e Caroneiro.
 */


import java.util.Scanner;

public class Main {

    /* Laboratório 6 */
    public static void main(String[] arguments) {

    }

    /* Laboratório 5 */
    /*public static void main(String[] arguments) {
        Perfil perfil = new Perfil('M', "12/12/2012", "Marília", "São Paulo", "00 14321-1321", false, 33.0D, 5);
        Usuario usuario = new Usuario("Rafael", "rafael@email.com", "5555", true, perfil);
        Caronante caronante = new Caronante(3, "Qualquer", "aaa-5452", "1144225566", "Fusca", "Wolks", 12, perfil);

        Carona carona = new Carona(caronante);
        CaronaPrivada caronaPrivada = caronante.oferecerCaronaPrivada();
        CaronaPublica caronaPublica = caronante.oferecerCaronaPublica();

        Carona[] caronas = new Carona[] {carona, caronaPrivada, caronaPublica};

        for(Carona carona_ : caronas) {
            System.out.println(carona_);

            carona_.adicionarFormaPagamento(MetodoPagamento.CARTAO_DE_CREDITO);
            carona_.adicionarFormaPagamento(MetodoPagamento.DINHEIRO);

            System.out.println(carona_.caronaGratuita());
            System.out.println(carona_.checarFormaPagamento(MetodoPagamento.DINHEIRO));

            carona_.removerFormaPagamento(MetodoPagamento.DINHEIRO);
            carona_.adicionarFormaPagamento(MetodoPagamento.GRATIS);

            System.out.println(carona_.checarFormaPagamento(MetodoPagamento.DINHEIRO));
            System.out.println(carona_.caronaGratuita());

            System.out.println(carona_);
        }
    }*/

    /* Laboratório 4 */
    /*public static void main(String[] arguments) {
        Perfil perfil = new Perfil('M', "12/12/2012", "Marília", "São Paulo", "00 14321-1321", false, 33.0D, 5);
        Usuario usuario = new Usuario("Rafael", "rafael@email.com", "5555", true, perfil);
        Caronante caronante = new Caronante(3, "Qualquer", "aaa-5452", "1144225566", "Fusca", "Wolks", 12, perfil);
        Caroneiro caroneiro = new Caroneiro("12", perfil);

        System.out.println(usuario.toString()); // imprime todas as informações

        System.out.println(usuario.getPerfil() == perfil); // deve ser true
        System.out.println(usuario.getPerfil().getCaronante() == caronante); // deve ser true
        System.out.println(usuario.getPerfil().getCaroneiro() == caroneiro); // deve ser true
        System.out.println(caronante.getPerfil() == perfil); // deve ser true
        System.out.println(caroneiro.getPerfil() == perfil); // deve ser true
        // De fato, todas foram true

        Grupo grupo = new Grupo("Grupo massa", "mas não é spaghetti, é lasanha");
        System.out.println(grupo.toString());

        grupo.adicionarUsuario(usuario);

        System.out.println(usuario.toString());
        System.out.println(grupo.toString());


        Grupo grupo2 = new Grupo("Grupo massa v2.0", "spaghetti > lasanha"); // será?
        System.out.println(grupo2.toString());

        Usuario usuario2 = new Usuario("Rafael 2", "rafael@emailfalso.com", "5555+1", false, perfil);
        System.out.println(usuario2.toString());

        Grupo a = new Grupo("grupo que será atribuido com b", "kmdsak");
        GrupoPrivado b = new GrupoPrivado("grupo privado que será atribuido com c", "afas");
        GrupoPrivado c = new GrupoPrivado("grupo privado alterado e atribuido", "afasdas");

        c.testeNaoStatic++;

        a = b;
        b = c;

        c.testeNaoStatic++;

        System.out.println("Teste static: ");
        System.out.println("a=" + a.testeStatic); // deve ser 21
        System.out.println("b=" + b.testeStatic); // deve ser 32
        System.out.println("c=" + c.testeStatic); // deve ser 32
        // Ocorreu como esperado

        System.out.println("Teste não static: ");
        System.out.println("a=" + a.testeNaoStatic); // deve ser 33 pois é da classe grupoprivado
        System.out.println("b=" + b.testeNaoStatic); // deve ser 35, pois é da classe grupoprivado e foi alterado
        System.out.println("c=" + c.testeNaoStatic); // deve ser 35
        // 'a' imprimiu 22, o que é surpreendente => por conta do atributo ocultar na classe herdeira

        System.out.println(a.toString()); // todos os dados de b
        System.out.println(b.toString()); // todos os dados de c
        System.out.println(c.toString()); // todos os dados de c
        // como esperado
    }*/

    /* Laboratório 3 */
/*    public static void main(String[] arguments) {
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
        carona.setHoraDiaEncontro("13/03/2018 14:00");
        carona.setLatitudeDestino(3.1415d);
        carona.setLongitudeDestino(3.1415d);
        carona.setLatitudeEncontro(3.1415d + 1);
        carona.setLongitudeEncontro(3.1415d + 1);

        for (Caroneiro caroneiro : caroneiros) {
            carona.adicionarCaroneiro(caroneiro);
        }

        System.out.println(carona.toString());


        Carona caronaAL = new CaronaAL(caronante);
        caronaAL.setHoraDiaEncontro("13/03/2018 14:00");
        caronaAL.setLatitudeDestino(3.1415d);
        caronaAL.setLongitudeDestino(3.1415d);
        caronaAL.setLatitudeEncontro(3.1415d + 1);
        caronaAL.setLongitudeEncontro(3.1415d + 1);

        for (Caroneiro caroneiro : caroneiros) {
            caronaAL.adicionarCaroneiro(caroneiro);
        }

        System.out.println(caronaAL.toString());
    }*/

    /* Laboratório 2 */
/*    public static void main(String[] arguments) {
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
    }*/

    /* Laboratório 1 */
/*    public static void main(String[] arguments) {
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
    }*/

}
