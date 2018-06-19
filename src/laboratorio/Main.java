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
 * As funcionalidades "básicas" desse programa exigem uma construção muito trabalhosa. Tive dificuldade durante as
 * primeiras horas, porém agora só está um *ENORME* trabalho maçante. Apenas essa parte ficou mais de 600 linhas de
 * código.
 *
 * A JanelaUsuario é a interface que atende todas as necessidades básicas exigidas no laboratório. Eu fiz a JanelaLogin
 * (que não era necessário no laboratório) antes dela, ficou razoavelmente curta.
 */
public class Main {

    private static Main main;

    private final GerenciadorUsuario gerenciadorUsuario;
    private final GerenciadorGrupo gerenciadorGrupo;

    private Usuario usuario = null;

    private JFrame janelaPrincipal = null;

    public Main() {
        GerenciadorUsuario gerenciadorUsuario_ = new GerenciadorUsuario();
        GerenciadorGrupo gerenciadorGrupo_ = new GerenciadorGrupo();

        /* Iniciamos o sistema, carregamos de arquivo se houver */
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("database.db")))) {
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
            this.usuario = null;
            // Criamos a interface de login novamente
            setJanelaPrincipal(new JanelaLogin());
        } else {
            this.usuario = usuario;
            // Criamos a interface de um usuário logado
            setJanelaPrincipal(new JanelaUsuario(usuario));
        }
    }
}
