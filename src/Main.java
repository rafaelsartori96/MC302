import carona.*;
import perfil.caronante.*;
import perfil.caroneiro.*;
import grupo.*;
import perfil.*;
import usuario.*;
import utilidades.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] arguments) {
        /* Criar usuários */
        Usuario[] usuarios = new Usuario[]{
                new Usuario("Usuário 0 (u0)", "u0@u.com", "u0", true, new Perfil('m', "11/07/1995", "Araçatuba", "SP", "11", false, new Caronante(13, "forró", "GDF-9999", "11", "Tesla", "Burguês", 4), new Caroneiro(true), null)),
                new Usuario("Usuário 1 (u1)", "u1@u.com", "u1", true, new Perfil('f', "21/12/2000", "Araçatuba", "SP", "01", false, new Caronante(1, "forró", "GAS-9999", "33", "Tesla", "Burguês", 4), new Caroneiro(true), null)),
                new Usuario("Usuário 2 (u2)", "u2@u.com", "u2", true, new Perfil('m', "23/01/1984", "Araçatuba", "SP", "07", false, new Caronante(20, "forró", "FDF-9999", "22", "Tesla", "Burguês", 4), new Caroneiro(false), null)),
                new Usuario("Usuário 3 (u3)", "u3@u.com", "u3", true, new Perfil('f', "11/03/1992", "Araçatuba", "SP", "10", false, new Caronante(4, "forró", "HGF-9399", "44", "Tesla", "Burguês", 4), new Caroneiro(true), null)),
                new Usuario("Usuário 4 (u4)", "u4@u.com", "u4", true, new Perfil('m', "15/04/1997", "Araçatuba", "SP", "12", false, new Caronante(2, "forró", "ASD-5511", "55", "Tesla", "Burguês", 3), new Caroneiro(false), null))
        };

        /* Criar grupo público */
        GrupoPublico gpu = usuarios[0].criarGrupoPublico("gpu", "GTX 1080 a 80km ou GT 220 a 80km qual e mais rapido");
        // Grupo público pode ficar sem dono
        // gpu.alterarDono(usuarios[0], null); // Acredito que o teste 8 seja para testar isso

        /* Criar grupo privado */
        GrupoPrivado ec017 = usuarios[0].criarGrupoPrivado("EC017", "nada a dizer.. e digo mais: digo mais nada");

        /* Inserir u1 e u2 em ec017 com sucesso, falhar em u2 inserir u4 */
        try {
            System.out.println("\tEstado anterior do grupo:\n" + ec017.toStringMostraMembros(0) + "\n\n\n\n");
            usuarios[0].adicionarAGrupo(ec017, usuarios[1]);
            usuarios[0].adicionarAGrupo(ec017, usuarios[2]);
            System.out.println("\tEstado posterior do grupo:\n" + ec017.toStringMostraMembros(0) + "\n\n\n\n");
            usuarios[2].adicionarAGrupo(ec017, usuarios[4]);
        } catch (SistemaCaronaException e) {
            System.out.println("Algo de errado aconteceu na inserção no grupo: ");
            e.printStackTrace();
        } finally {
            System.out.println("\tEstado posterior às inserções:\n" + ec017.toStringMostraMembros(0) + "\n\n\n\n");
        }

        /* Fazer u2, u3 e u4 entrarem em gpu */
        usuarios[2].adicionarGrupo(gpu);
        usuarios[3].adicionarGrupo(gpu);
        usuarios[4].adicionarGrupo(gpu);

        /* u3 cria carona pública e posta em gpu */
        CaronaPublica caronaPublica = usuarios[3].getPerfil().getCaronante().oferecerCaronaPublica(2.2, 3.14, 3.21, 5.3, "você saberá a hora...", 2.30f);
        caronaPublica.adicionarGrupo(gpu);

        /* Remover u0 de gpu */
        usuarios[0].removerGrupo(gpu);
        System.out.println("\tEstado de gpu:\n" + gpu.toStringMostraMembros(0) + "\n\n\n\n");

        /* Criar carona de u2, que está no grupo ec017 */
        CaronaPrivada caronaPrivada2 = usuarios[2].getPerfil().getCaronante().oferecerCaronaPrivada(32.3, 312.2, 3, 21.6, "12h 12/06", 4.50f);
        System.out.println("\tTentando adicionar carona de u2 em ec017: " + caronaPrivada2.adicionarGrupo(ec017));
        System.out.println("\tEstado de carona de u2:\n" + caronaPrivada2 + "\n\n\n\n");

        /* Criar carona de u4, que não está no grupo ec017 */
        CaronaPrivada caronaPrivada4 = usuarios[4].getPerfil().getCaronante().oferecerCaronaPrivada(32.3, 312.2, 3, 21.6, "12h 12/06", 4.50f);
        System.out.println("\tTentando adicionar carona de u4 em ec017: " + caronaPrivada4.adicionarGrupo(ec017));
        System.out.println("\tEstado de carona de u4:\n" + caronaPrivada4 + "\n\n\n\n");

        /* Tentativas de carona, u0 e u1 devem conseguir */
        System.out.println("\tTentativa de u0 pedir carona de u2: " + usuarios[0].getPerfil().getCaroneiro().pedirCarona(caronaPrivada2));
        System.out.println("\tTentativa de u1 pedir carona de u2: " + usuarios[1].getPerfil().getCaroneiro().pedirCarona(caronaPrivada2));
        System.out.println("\tTentativa de u3 pedir carona de u2: " + usuarios[3].getPerfil().getCaroneiro().pedirCarona(caronaPrivada2));

        /* Criar lista de perfis de quem está na carona de u2 */
        // Como eu não adicionei Comparable em Perfil, mas em cada perfil Caronante e Caroneiro, não faz sentido incluir o Caronante (só tem um)
        // Então irei ignorar Caronante e só fazer a comparação dos perfis Caroneiros (o método é o mesmo, acredito que não há perda de generalidade)
        ArrayList<Caroneiro> caroneiros = new ArrayList<>();
        caroneiros.add(usuarios[0].getPerfil().getCaroneiro());
        caroneiros.add(usuarios[1].getPerfil().getCaroneiro());

        /* Avaliar a carona de u2 */
        caronaPrivada2.atribuirNotaCaroneiro(usuarios[0].getId(), 8.4f); // Nota maior primeiro (deverá ficar invertido)
        caronaPrivada2.atribuirNotaCaroneiro(usuarios[1].getId(), 5.6f);

        /* Imprimir a lista não ordenada de avaliações, ordenar e imprimir */
        System.out.println("\n\nAntes de ordenar:");
        for (Caroneiro caroneiro : caroneiros) {
            System.out.println("\tAvaliação: " + caroneiro.getAvaliacao());
        }
        System.out.println("\n\n");

        Collections.sort(caroneiros);

        System.out.println("\n\nApós ordenar:");
        for (Caroneiro caroneiro : caroneiros) {
            System.out.println("\tAvaliação: " + caroneiro.getAvaliacao());
        }
        System.out.println("\n\n");

        /* Imprimir o estado final */
        for (Usuario usuario : usuarios) {
            System.out.println("\n\n\n" + usuario);
        }

        System.out.println("\n\n\n" + gpu.toStringMostraMembros(0));
        System.out.println("\n\n\n" + ec017.toStringMostraMembros(0));

        System.out.println("\n\n\n" + caronaPrivada2);
        System.out.println("\n\n\n" + caronaPrivada4);

        /* Salvar no arquivo */
        try {
            File banco = new File("database.db");
            if(!banco.createNewFile()) {
                System.out.println("\nArquivo de banco de dados não existia, foi criado.");
            }

            FileOutputStream outputStream = new FileOutputStream(banco);
            // Quantidade de usuários
            outputStream.write(usuarios.length);
            for (Usuario usuario : usuarios) {
                usuario.salvarParaArquivo(outputStream);
            }
            // Quantidade de grupos
            outputStream.write(2);
            gpu.salvarParaArquivo(outputStream);
            ec017.salvarParaArquivo(outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
