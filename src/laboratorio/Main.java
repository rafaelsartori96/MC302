package laboratorio;

import laboratorio.grupo.GerenciadorGrupo;
import laboratorio.perfil.*;
import laboratorio.usuario.GerenciadorUsuario;
import laboratorio.usuario.Usuario;

import javax.swing.*;
import java.io.*;

/*
 * Rafael Santos (186154)
 *
 * As funcionalidades "básicas" desse programa exigem uma construção MUITO trabalhosa. Tive dificuldade durante as
 * primeiras horas, porém agora só está um *ENORME* trabalho maçante. ENORME TRABALHO MAÇANTE AAAAARGH
 * Mais de mil linhas...
 *
 * Observação:
 * A JanelaUsuario é a interface que atende todas as necessidades básicas exigidas no laboratório. Eu fiz a JanelaLogin
 * (que não era necessário no laboratório) antes dela, mas ficou razoavelmente curta.
 *
 * Observação 2:
 * Desculpem-me por utilizar JDK 10. Troquei as funções dos Collectors ao usar Stream para retornarem uma lista normal e
 * (não utilizei Collectors#toUnmodifiableList)
 *
 * Questões:
 * 1. Quais as principais diferenças entre Swing e AWT?
 *  Swing é uma "plataforma" que controla os objetos AWT, propondo um desenvolvimento mais "sustentável" (não variando
 *  com especificadades do sistema operacional) e de desenvolvimento mais rápido (por ter o suporte para eventos que
 *  envolvem mouse, teclado, de janelas fechando e abrindo).
 *
 * 2. Qual a vantagem de se usar um RootPaneContainer?
 *  O RootPaneContainer permite construir objetos de maior complexidade através de layouts e vários componentes ligados
 *  a ele. Inclusive outros containers, o que auxilia a construção de uma interface consistente e escalável em diversos
 *  sistemas diferentes.
 *
 * 3. Quando temos um JDialog visível o que ocorre com as outras janelas do sistema?
 *  Elas se tornam desativadas (mesmo que não visualmente). Não é possível utilizar o JFrame "mãe" do JDialog.
 *
 * 4. Explique as diferenças entre BorderLayout, BoxLayout, FlowLayout e GridLayout. Quais deles você usou na criação
 * das janelas? Por quê?
 *  BorderLayout permite a construção de uma estrutura orientada pela posição na janela (sul, norte, centro, oeste,
 *  leste).
 *  FlowLayout distribui horizontalmente os componentes. BoxLayout, verticalmente.
 *  GridLayout divide a janela em uma grid que tem dimensão variável mas número de colunas e linhas constante (constante
 *  na execução).
 *
 *
 * 5. Explique o que é uma função callback. Como são implementadas em Java? Não use código, explique com suas palavras.
 *  Uma função callback é um pedaço de código que será executado em algum momento. Em Java, pode ser feito com Listeners
 *  (muito usado em Swing), em Runnables para threads, com reflection e anotations (o método é marcado com uma anotação
 *  e em runtime é executado através de reflection, o que permite uma construção mais escalável inclusive com plugins -
 *  programas que são ligados ao programa principal)
 *
 * 6. Explique com suas palavras como cada nível do framework MVC seria tratado na sua implementação do sistema.
 *  O controller e a View infelizmente estão bastante misturados nas classes de janela. O modelo é em grande parte o que
 *  fizemos durante o semestre, como Usuario e Grupo, mas também o que foi feito nos últimos laboratórios, como
 *  GerenciadorUsuario e GerenciadorGrupo, fazendo uso dos dados, sem dirigir a interface gráfica mas sendo dirigido
 *  pelo controller.
 *
 */
public class Main {

    private static Main main;

    private final GerenciadorUsuario gerenciadorUsuario;
    private final GerenciadorGrupo gerenciadorGrupo;

    private JFrame janelaPrincipal = null;

    public Main() {
        GerenciadorUsuario gerenciadorUsuario_ = new GerenciadorUsuario();
        GerenciadorGrupo gerenciadorGrupo_ = new GerenciadorGrupo();

        /* Iniciamos o sistema, carregamos de arquivo se houver */
        try (DataInputStream dataInputStream =
                     new DataInputStream(new BufferedInputStream(new FileInputStream("database.db")))) {
            gerenciadorUsuario_ = GerenciadorUsuario.carregar(dataInputStream);
            gerenciadorGrupo_ = GerenciadorGrupo.carregar(dataInputStream, gerenciadorUsuario_);
        } catch (IOException e) {
            System.out.println("Não há banco de dados para leitura ou algum erro ocorreu.");
            /* Para criar o 'database.db' pela primeira vez */
            Usuario usuario = gerenciadorUsuario_.criarUsuario("Usuario 1", "u1@site.com", "senha", true);
            new Perfil(null, null, usuario, 'm', "14/02/2086", "Townsville", "Republicano", "12", false);
            gerenciadorUsuario_.criarUsuario("Usuario 2", "u2@site.com", "senha", true);
            gerenciadorUsuario_.criarUsuario("Usuario 3", "u3@site.com", "senha", true);
            gerenciadorUsuario_.criarUsuario("senha padrão é \"senha\"", "u2,5@site.com", "senha", true);
        }

        /* Definimos as variáveis final após tentar carregar o arquivo */
        this.gerenciadorUsuario = gerenciadorUsuario_;
        this.gerenciadorGrupo = gerenciadorGrupo_;
    }

    public static void main(String[] arguments) {
        // Alguma referência global para não termos que passar Main para todos os objetos
        main = new Main();
        main.setJanelaPrincipal(new JanelaLogin());
    }

    public void setJanelaPrincipal(JFrame janelaPrincipal) {
        if (this.janelaPrincipal != null) {
            this.janelaPrincipal.setVisible(false);
            this.janelaPrincipal.dispose();
        }
        this.janelaPrincipal = janelaPrincipal;
        this.janelaPrincipal.setVisible(true);
    }

    public static Main getMain() {
        return main;
    }

    public GerenciadorUsuario getGerenciadorUsuario() {
        return gerenciadorUsuario;
    }

    public GerenciadorGrupo getGerenciadorGrupo() {
        return gerenciadorGrupo;
    }

    /**
     * Define o usuário que está usando o serviço.
     *
     * @param usuario usuário que entrou com a senha correta
     */
    public void efetuarLogin(Usuario usuario) {
        if (usuario == null) {
            // Criamos a interface de login novamente
            setJanelaPrincipal(new JanelaLogin());
        } else {
            // Criamos a interface de um usuário logado
            setJanelaPrincipal(new JanelaUsuario(usuario, JanelaUsuario.Pagina.USUARIO));
        }
    }
}
