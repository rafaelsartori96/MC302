package laboratorio.grupo;

import java.util.*;
import java.util.stream.Collectors;

import laboratorio.carona.CaronaPrivada;
import laboratorio.usuario.Usuario;

public class GrupoPrivado extends Grupo {

    private final ArrayList<GrupoUsuario> membros = new ArrayList<>();
    private final ArrayList<CaronaPrivada> caronas = new ArrayList<>();

    GrupoPrivado(Usuario dono, String nome, String descricao) {
        super(Tipo.PRIVADO, dono, nome, descricao);
        adicionarMembro(dono);
    }

    GrupoPrivado(int id, Usuario dono, String nome, String descricao) {
        super(Tipo.PRIVADO, id, dono, nome, descricao);
        adicionarMembro(dono);
    }

    @Override
    public void adicionarMembro(Usuario usuario) {
        GrupoUsuario grupoUsuario = new GrupoUsuario(usuario, this);
        usuario.adicionarGrupoUsuario(grupoUsuario);
        membros.add(grupoUsuario);
    }

    @Override
    public boolean removerMembro(Usuario usuario) {
        Iterator<GrupoUsuario> iterator = membros.iterator();
        if (!iterator.hasNext()) {
            return false;
        }

        for (GrupoUsuario membro = iterator.next(); iterator.hasNext(); membro = iterator.next()) {
            if (membro.getUsuario().getId() == usuario.getId()) {
                // Removemos utilizando o iterator (for-loop com remoção dá erros)
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Usuario> getMembros() {
        return this.membros.stream().map(GrupoUsuario::getUsuario).collect(Collectors.toUnmodifiableList());
    }
}
