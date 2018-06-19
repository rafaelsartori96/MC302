package laboratorio;

import laboratorio.grupo.GerenciadorGrupo;
import laboratorio.perfil.*;
import laboratorio.usuario.GerenciadorUsuario;
import laboratorio.usuario.Usuario;
import laboratorio.utilidades.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

/*
 * Rafael Santos, RA 186154
 */
public class Main {

    private static Main main;

    private final GerenciadorUsuario gerenciadorUsuario;
    private final GerenciadorGrupo gerenciadorGrupo;

    private Usuario usuario = null;

    private JFrame janela;

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

        /* Iniciamos a janela principal do nosso programa */
        janela = new JFrame();
        janela.setMinimumSize(new Dimension(600, 500));
        janela.setTitle("Sistema de caronas");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
    }

    public static void main(String[] arguments) {
        // Alguma referência global para não termos que passar Main para todos os objetos
        main = new Main();
        main.prepararLogin();
    }

    private void prepararLogin() {
        janela.add(new PainelLogin(janela));
        janela.setVisible(true);
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
            prepararLogin();
        } else {
            this.usuario = usuario;
            // Criamos a interface de um usuário logado
            janela.add(new PainelUsuario(janela, usuario));
            janela.setVisible(true);
        }
    }
}
