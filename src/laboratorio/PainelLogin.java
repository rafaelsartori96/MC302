package laboratorio;

import laboratorio.usuario.*;
import laboratorio.utilidades.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class PainelLogin extends JPanel {

    private final JTable tabelaUsuarios;

    public PainelLogin(final JFrame janela) {
        super();

        /* Estrutura geral */
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /* Haverá primeiro um painel com a tabela de usuários */
        JPanel painelUsuarios = new JPanel(new BorderLayout());

        /* Construímos a tabela */
        ModeloTabela<Usuario> modeloTabela = new ModeloTabela<>() {
            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Nome";
                    case 1:
                        return "E-mail";
                    default:
                        return null;
                }
            }

            @Override
            public Object getColumn(Usuario object, int column) {
                switch (column) {
                    case 0:
                        return object.getNome();
                    case 1:
                        return object.getEmail();
                    default:
                        return null;
                }
            }

            @Override
            public List<Usuario> getList() {
                return Main.getMain().getGerenciadorUsuario().getUsuarios();
            }
        };
        tabelaUsuarios = new JTable(modeloTabela);
        tabelaUsuarios.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        // Adicionamos o header da tabela no topo
        painelUsuarios.add(tabelaUsuarios.getTableHeader(), BorderLayout.NORTH);

        JScrollPane tabelaScroll = new JScrollPane(
                tabelaUsuarios,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        // Adicionamos a tabela com scroll no centro
        painelUsuarios.add(tabelaScroll, BorderLayout.CENTER);
        /* Agora que construímos o painel de usuários, adicionamos todo o painel */
        add(painelUsuarios);

        /* Depois haverá o painel dos botões que irão interagir com usuários para entrar */
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton entrar = new JButton("Entrar");
        // Para removermos o login da janela
        entrar.addActionListener(event -> {
            Usuario usuario;
            if (tabelaUsuarios.getSelectedRow() < 0 ||
                    (usuario = modeloTabela.getList().get(tabelaUsuarios.getSelectedRow())) == null) {
                HelperDialog.popupErro(
                        "Erro!",
                        "É necessário selecionar um usuário para entrar!"
                );
            } else {
                String senha = HelperDialog.pedirSenha();
                if (senha == null) {
                    return;
                } else if (!senha.equals(usuario.getSenha())) {
                    HelperDialog.popupErro(
                            "Senha incorreta!",
                            "A senha entrada não corresponde com a senha do usuário."
                    );
                    return;
                }

                janela.remove(this);
                janela.setVisible(false);
                Main.getMain().efetuarLogin(usuario);
            }
        });
        painelBotoes.add(entrar);

        JButton criar = new JButton("Criar");
        criar.addActionListener(new CriarUsuarioPapup(this));
        painelBotoes.add(criar);

        JButton sair = new JButton("Salvar e sair");
        sair.addActionListener(event -> {
            /* Terminamos o sistema, salvamos para arquivo */
            System.out.println("Salvando database...");
            try (DataOutputStream dataOutputStream = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream("database.db")))) {
                Main.getMain().getGerenciadorUsuario().salvarParaArquivo(dataOutputStream);
                Main.getMain().getGerenciadorGrupo().salvarParaArquivo(dataOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            janela.dispose();
            System.exit(0);
        });
        painelBotoes.add(sair);

        /* Agora que construímos o painel de botões, adicionamos */
        add(painelBotoes);
        janela.pack();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (tabelaUsuarios != null) {
            tabelaUsuarios.updateUI();
        }
    }
}
