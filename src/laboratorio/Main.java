package laboratorio;

import laboratorio.grupo.GerenciadorGrupo;
import laboratorio.usuario.GerenciadorUsuario;
import laboratorio.usuario.Usuario;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

/*
 * Rafael Santos, RA 186154
 */
public class Main {

    public static GerenciadorUsuario gerenciadorUsuario;
    public static GerenciadorGrupo gerenciadorGrupo;

    public static void main(String[] arguments) {
        /* Iniciamos o sistema, carregamos de arquivo se houver */
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("database.db")))) {
            gerenciadorUsuario = GerenciadorUsuario.carregar(dataInputStream);
            gerenciadorGrupo = GerenciadorGrupo.carregar(dataInputStream, gerenciadorUsuario);
        } catch (IOException e) {
            System.out.println("Não há banco de dados para leitura.");
            gerenciadorUsuario = new GerenciadorUsuario();
            gerenciadorGrupo = new GerenciadorGrupo();
        }

        JFrame janela = new JFrame();
        janela.setTitle("Exemplo");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        janela.add(painel);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;

        JTable jTable = new JTable();

        JButton entrar = new JButton("Entrar");
        entrar.addActionListener(event -> {

        });
        painel.add(entrar, gridBagConstraints);

        JButton sair = new JButton("Salvar e sair");
        sair.addActionListener(event -> {
            /* Terminamos o sistema, salvamos para arquivo */
            System.out.println("Salvando database...");
            try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("database.db")))) {
                gerenciadorUsuario.salvarParaArquivo(dataOutputStream);
                gerenciadorGrupo.salvarParaArquivo(dataOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            janela.dispose();
        });
        painel.add(sair, gridBagConstraints);

        janela.pack();
        janela.setVisible(true);
    }

    private static class UsuarioTabela extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return gerenciadorUsuario.getUsuarios().size();
        }

        @Override
        public int getColumnCount() {
            // id, nome e e-mail
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Usuario usuario = gerenciadorUsuario.getUsuarios().get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return usuario.getId();
                case 1:
                    return usuario.getNome();
                default:
                    return usuario.getEmail();
            }
        }
    }
}
