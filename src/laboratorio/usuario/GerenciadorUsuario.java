package laboratorio.usuario;

import laboratorio.utilidades.Salvavel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciadorUsuario implements Salvavel {

    private final ArrayList<Usuario> usuarios = new ArrayList<>();

    public Usuario criarUsuario(String nome, String email, String senha, boolean status) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return null;
            }
        }

        return new Usuario(nome, email, senha, status);
    }

    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    public Usuario getUsuario(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void salvarParaArquivo(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(usuarios.size());
        for (Usuario usuario : usuarios) {
            usuario.salvarParaArquivo(dataOutputStream);
        }
        dataOutputStream.flush();
    }

    public static GerenciadorUsuario carregar(DataInputStream dataInputStream) throws IOException {
        GerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario();

        for (int i = 0; i < dataInputStream.readInt(); i++) {
            gerenciadorUsuario.usuarios.add(Usuario.carregar(dataInputStream));
        }

        return gerenciadorUsuario;
    }
}
