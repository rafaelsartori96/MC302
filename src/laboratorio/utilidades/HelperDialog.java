package laboratorio.utilidades;

import laboratorio.perfil.*;
import laboratorio.perfil.caronante.*;

import javax.swing.*;

public abstract class HelperDialog {

    public static boolean isInvalid(String string) {
        return string == null || string.length() == 0;
    }

    public static void popupErro(String titulo, String mensagem) {
        JDialog dialog = new JOptionPane(mensagem, JOptionPane.ERROR_MESSAGE).createDialog(titulo);
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }

    public static void popupInformacao(String titulo, String mensagem) {
        JDialog dialog = new JOptionPane(mensagem, JOptionPane.INFORMATION_MESSAGE).createDialog(titulo);
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }

    public static Integer popupInteger(String mensagem) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(mensagem);
            if (isInvalid(entrada)) {
                return null;
            }

            try {
                return Integer.valueOf(entrada);
            } catch (NumberFormatException e) {
                popupErro("Entrada inválida!", "O número deve ser inteiro.");
            }
        }
    }

    public static String pedirSenha() {
        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setEchoChar('*');
        int opcao = JOptionPane.showConfirmDialog(
                null, campoSenha, "Digite a senha do usuário:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        /* Cancelamos caso não queira digitar a senha */
        if (opcao != 0) {
            // Voltamos ao painel de login
            return null;
        }
        return String.valueOf(campoSenha.getPassword());
    }

    public static String pedirConfirmarSenha() {
        String senha = null;
        boolean faltaConfirmar = true;
        do {
            JPasswordField campoSenha = new JPasswordField();
            campoSenha.setEchoChar('*');
            int opcao = JOptionPane.showConfirmDialog(
                    null, campoSenha, "Digite a nova senha do usuário:",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            /* Cancelamos caso não queira digitar a senha */
            if (opcao != 0) {
                // Voltamos ao painel de login
                return null;
            }

            if (senha == null) {
                senha = String.valueOf(campoSenha.getPassword());
            } else {
                // Confirmamos a senha
                faltaConfirmar = false;
                if (String.valueOf(campoSenha.getPassword()).equals(senha)) {
                    return senha;
                } else {
                    JDialog dialog = new JOptionPane(
                            "A senha entrada não coincide com a senha do usuário.",
                            JOptionPane.ERROR_MESSAGE
                    ).createDialog("Senha incorreta!");
                    dialog.setVisible(true);
                    dialog.setAlwaysOnTop(true);
                }
            }
        } while (faltaConfirmar);
        return null;
    }

    public static Perfil pedirPerfil() {
        char sexo;
        Object respostaSexo = JOptionPane.showInputDialog(
                null,
                "Qual seu sexo?",
                "Criando o perfil",
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{
                        "Masculino",
                        "Feminino"
                },
                null
        );
        if (respostaSexo == null) {
            return null;
        } else {
            sexo = respostaSexo.equals("Masculino") ? 'm' : 'f';
        }

        String dataNascimento = JOptionPane.showInputDialog(
                "Qual a sua data de nascimento? Utilize o formato 23/12/2018.");
        if (isInvalid(dataNascimento)) {
            return null;
        }

        String cidade = JOptionPane.showInputDialog("Qual a sua cidade?");
        if (isInvalid(cidade)) {
            return null;
        }

        String estado = JOptionPane.showInputDialog("Qual o seu estado?");
        if (isInvalid(estado)) {
            return null;
        }

        String telefone = JOptionPane.showInputDialog("Qual o seu telefone?");
        if (isInvalid(telefone)) {
            return null;
        }

        boolean fumante;
        Object respostaFumante = JOptionPane.showInputDialog(
                null,
                "Você fuma?",
                "Criando o perfil",
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{
                        "Sim",
                        "Não"
                },
                null
        );
        if (respostaFumante == null) {
            return null;
        } else {
            fumante = respostaFumante.equals("Sim");
        }

        /* Criamos o perfil associando-o a um usuário */
        return new Perfil(null, null, null, sexo, dataNascimento, cidade, estado, telefone, fumante);
    }
}
