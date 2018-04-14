/*
 * Rafael Santos (RA: 186154)
 *
 * Questão 1:
 *  O benefício da herança é a fácil organização de informações repetidas. Não há mais a necessidade de reescrever
 *  tantos métodos. Não apenas facilidade ao escrever o código, há mais segurança, pois os tipos são organizados de
 *  forma segura, sem se misturarem. Em C, por exemplo, uma struct pode ser definida com o nome "Cidade", possuindo um
 *  ponteiro para vetor de caracteres (nome da cidade) e inteiro (população), outra strict definida como "Item" (para
 *  uma loja, por exemplo), possuir os mesmos "atributos" (nome e quantidade, por exemplo), mas o compilador permite
 *  você misturá-los pois os "atributos" são os mesmos na memória.
 *  Além disso, há a sobreescrita de métodos que não são compatíveis, o que torna o uso de herança muito conveniente
 *  para classes que não "combinam" tão bem.
 *
 * Questão 2:
 *  Uma classe final não pode possuir classes herdeiras. Sendo assim, o código não compila com a existência de
 *  GrupoPrivado e GrupoPublico quando há a classe Grupo é final. Quando adicionamos final às outras classes, nada muda,
 *  pois elas não são classes que são herdadas por outras.
 *
 * Questão 3:
 *  Em variáveis estáticas, prevalecem os atributos do tipo da variável. Ao contrário dos outros atributos de instância,
 *  para atributos de instancia que foram ocultados, prevalece o atributo ocultado quando o tipo da variável é da classe
 *  mãe ao invés da classe herdeira.
 *  Por padrão, sempre o método mais "especializado", sempre o da classe herdeira é utilizado, então se utilizassemos um
 *  getter, receberíamos o valor que "ocultou" o da classe mãe ao invés do que aconteceu com o "atributo livre". Ou
 *  seja, haveria mais segurança.
 *
 */


import java.util.Scanner;

public class Main {

    /* Laboratório 4 */
    public static void main(String[] arguments) {
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
    }

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
        carona.setHoraEncontro("13/03/2018 14:00");
        carona.setLatitudeDestino(3.1415d);
        carona.setLongitudeDestino(3.1415d);
        carona.setLatitudeEncontro(3.1415d + 1);
        carona.setLongitudeEncontro(3.1415d + 1);

        for (Caroneiro caroneiro : caroneiros) {
            carona.adicionarCaroneiro(caroneiro);
        }

        System.out.println(carona.toString());


        Carona caronaAL = new CaronaAL(caronante);
        caronaAL.setHoraEncontro("13/03/2018 14:00");
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
