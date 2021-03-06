package laboratorio.grupo;

import laboratorio.usuario.GerenciadorUsuario;
import laboratorio.usuario.Usuario;
import laboratorio.utilidades.Salvavel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciadorGrupo implements Salvavel {

    private final ArrayList<Grupo> grupos = new ArrayList<>();

    public List<Grupo> getGrupos() {
        return Collections.unmodifiableList(grupos);
    }

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
        int numGrupos = dataInputStream.readInt();
        for (int i = 0; i < numGrupos; i++) {
            gerenciadorGrupo.grupos.add(Grupo.carregar(dataInputStream, gerenciadorUsuario));
        }

        return gerenciadorGrupo;
    }
}
