package laboratorio;

import laboratorio.usuario.*;
import laboratorio.utilidades.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CriarUsuarioPapup implements ActionListener {

    private final JPanel painel;
    private final Usuario usuario;

    public CriarUsuarioPapup(JPanel painel) {
        this.painel = painel;
        this.usuario = null;
    }

    public CriarUsuarioPapup(JPanel painel, Usuario usuario) {
        this.painel = painel;
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

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

        while (true) {
            int opcao = JOptionPane.showConfirmDialog(
                    painel, formulario, "Criar novo usuário", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                if (usuario == null) {
                    String senha = String.valueOf(senhaEntrada.getPassword());
                    String confirmarSenha = String.valueOf(confirmarSenhaEntrada.getPassword());

                    if (!senha.equals(confirmarSenha)) {
                        HelperDialog.popupErro("Senha inválida!", "As senhas não coincidem!");
                        confirmarSenhaEntrada.setText("");
                    } else {
                        Main.getMain().getGerenciadorUsuario().criarUsuario(
                                email.getText(), email.getText(), senha, true
                        );
                        painel.updateUI();
                        return;
                    }
                } else {
                    if (senhaAntiga.getPassword().length > 0) {
                        if(!String.valueOf(senhaAntiga.getPassword()).equals(usuario.getSenha())) {
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
                        } else {
                            usuario.setSenha(senha);
                        }
                    }
                    usuario.setNome(nome.getText());
                    usuario.setEmail(email.getText());
                    return;
                }
            } else {
                return;
            }
        }
    }
}
