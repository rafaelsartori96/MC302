package laboratorio.grupo;

import laboratorio.usuario.GerenciadorUsuario;
import laboratorio.usuario.Usuario;
import laboratorio.utilidades.Salvavel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GerenciadorGrupo implements Salvavel {

    private final ArrayList<Grupo> grupos = new ArrayList<>();

    public GrupoPrivado criarGrupoPrivado(Usuario dono, String nome, String descricao) {
        GrupoPrivado grupoPrivado = new GrupoPrivado(dono, nome, descricao);
        grupos.add(grupoPrivado);
        return grupoPrivado;
    }

    public GrupoPublico criarGrupoPublico(Usuario dono, String nome, String descricao) {
        GrupoPublico grupoPublico = new GrupoPublico(dono, nome, descricao);
        grupos.add(grupoPublico);
        return grupoPublico;
    }

    @Override
    public void salvarParaArquivo(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(grupos.size());

        for (Grupo grupo : grupos) {
            grupo.salvarParaArquivo(dataOutputStream);
        }

        dataOutputStream.flush();
    }

    public static GerenciadorGrupo carregar(DataInputStream dataInputStream,
                                            GerenciadorUsuario gerenciadorUsuario) throws IOException {
        GerenciadorGrupo gerenciadorGrupo = new GerenciadorGrupo();

        for (int i = 0; i < dataInputStream.readInt(); i++) {
            Grupo.carregar(dataInputStream, gerenciadorUsuario);
        }

        return gerenciadorGrupo;
    }
}
