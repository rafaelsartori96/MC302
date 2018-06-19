package laboratorio.utilidades;

import laboratorio.*;
import laboratorio.usuario.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class CriarUsuarioPopup implements ActionListener {

    private final JFrame janela;
    private final Usuario usuario;

    public CriarUsuarioPopup(JFrame janela) {
        this.janela = janela;
        this.usuario = null;
    }

    public CriarUsuarioPopup(JFrame janela, Usuario usuario) {
        this.janela = janela;
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

        /*
         * Para cada questão, adicionamos uma linha com label e caixa de entrada
         */

        JPanel painelNome = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelNome.add(new JLabel("Nome:"));
        JTextField nome = new JTextField(20);
        painelNome.add(nome);
        formulario.add(painelNome);

        JPanel painelEmail = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelEmail.add(new JLabel("E-mail:"));
        JTextField email = new JTextField(20);
        painelEmail.add(email);
        formulario.add(painelEmail);

        JPasswordField senhaAntiga = null;
        if (usuario != null) {
            JPanel painelSenhaAntiga = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelSenhaAntiga.add(new JLabel("Senha antiga:"));
            painelSenhaAntiga.setToolTipText("Digite apenas se quiser alterar a senha.");
            senhaAntiga = new JPasswordField(14);
            painelSenhaAntiga.add(senhaAntiga);
            formulario.add(painelSenhaAntiga);

            // Definimos informações antigas
            nome.setText(usuario.getNome());
            email.setText(usuario.getEmail());
        }

        JPanel painelSenha = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelSenha.add(new JLabel(usuario == null ? "Senha:" : "Senha nova:"));
        JPasswordField senhaEntrada = new JPasswordField(14);
        painelSenha.add(senhaEntrada);
        formulario.add(painelSenha);

        JPanel painelConfirmar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelConfirmar.add(new JLabel("Confirmar senha:"));
        JPasswordField confirmarSenhaEntrada = new JPasswordField(14);
        painelConfirmar.add(confirmarSenhaEntrada);
        formulario.add(painelConfirmar);

        /* Enquanto o usuário digita a senha errada, repetimos */
        while (true) {
            int opcao = JOptionPane.showConfirmDialog(
                    janela,
                    formulario,
                    usuario == null ? "Criar novo usuário" : "Modificar usuário",
                    JOptionPane.OK_CANCEL_OPTION
            );

            /* Se o usuário apertou Ok... */
            if (opcao == 0) {

                /*
                 * Se o usuário não existe, é necessário que senha e confirmar senha sejam iguais
                 */

                if (usuario == null) {
                    String senha = String.valueOf(senhaEntrada.getPassword());
                    String confirmarSenha = String.valueOf(confirmarSenhaEntrada.getPassword());

                    if (!senha.equals(confirmarSenha)) {
                        HelperDialog.popupErro("Senha inválida!", "As senhas não coincidem!");
                        confirmarSenhaEntrada.setText("");
                    } else {
                        if (Main.getMain().getGerenciadorUsuario().criarUsuario(
                                email.getText(), email.getText(), senha, true
                        ) == null) {
                            HelperDialog.popupErro("Erro!", "Falha ao criar usuário!");
                        }
                        redesenhar();
                        return;
                    }
                } else {
                    /*
                     * Se usuário já existe, só conferimos a troca de senha caso o usuário queira trocar de senha
                     */

                    if (senhaAntiga.getPassword().length > 0) {
                        if (!String.valueOf(senhaAntiga.getPassword()).equals(usuario.getSenha())) {
                            HelperDialog.popupErro("Senha inválida!", "A senha antiga é inválida!");
                            confirmarSenhaEntrada.setText("");
                            // Não mudamos o nome ou e-mail
                            continue;
                        }

                        String senha = String.valueOf(senhaEntrada.getPassword());
                        String confirmarSenha = String.valueOf(confirmarSenhaEntrada.getPassword());

                        if (!senha.equals(confirmarSenha)) {
                            HelperDialog.popupErro("Senha inválida!", "As senhas não coincidem!");
                            confirmarSenhaEntrada.setText("");
                            // Não mudamos o nome ou e-mail
                            continue;
                        } else if (senha.length() == 0) {
                            HelperDialog.popupErro("Senha inválida!", "A nova senha é muito curta!");
                            confirmarSenhaEntrada.setText("");
                            // Não mudamos o nome ou e-mail
                            continue;
                        } else {
                            /* Mudamos a senha apenas se a nova senha foi definida e coincidem */
                            usuario.setSenha(senha);
                        }
                    }

                    /* Trocamos nome e senha de qualquer forma */
                    usuario.setNome(nome.getText());
                    usuario.setEmail(email.getText());

                    /* Redesenhamos o sistema */
                    redesenhar();
                    return;
                }
            } else {
                return;
            }
        }
    }

    public abstract void redesenhar();
}
