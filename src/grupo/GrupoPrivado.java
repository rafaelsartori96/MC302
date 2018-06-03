package grupo;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import carona.CaronaPrivada;
import usuario.Usuario;

public class GrupoPrivado extends Grupo {

    private final ArrayList<GrupoUsuario> membros = new ArrayList<>();
    private final ArrayList<CaronaPrivada> caronas = new ArrayList<>();

    public GrupoPrivado(String nome, String descricao, Usuario dono) {
        super(Tipo.PRIVADO, nome, descricao, dono);
        adicionarMembro(dono);
    }

    GrupoPrivado(int id, String nome, String descricao, Usuario dono) {
        super(Tipo.PRIVADO, id, nome, descricao, dono);
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
