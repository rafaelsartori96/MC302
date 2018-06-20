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
        if (membros.isEmpty()) {
            return false;
        }

        for (Iterator<GrupoUsuario> iterator = membros.iterator(); iterator.hasNext(); ) {
            GrupoUsuario membro = iterator.next();
            if (membro.getUsuario().getId() == usuario.getId()) {
                // Para ficar consistente
                usuario.removerGrupoUsuario(membro);
                // Removemos utilizando o iterator
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
