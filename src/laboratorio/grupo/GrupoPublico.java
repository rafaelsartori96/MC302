package laboratorio.grupo;

import java.util.*;
import java.util.stream.Collectors;

import laboratorio.carona.CaronaPublica;
import laboratorio.usuario.Usuario;

public class GrupoPublico extends Grupo {

    private final ArrayList<GrupoUsuario> membros = new ArrayList<>();
    private final ArrayList<CaronaPublica> caronas = new ArrayList<>();

    GrupoPublico(Usuario dono, String nome, String descricao) {
        super(Tipo.PUBLICO, dono, nome, descricao);
        adicionarMembro(dono);
    }

    GrupoPublico(int id, Usuario dono, String nome, String descricao) {
        super(Tipo.PUBLICO, id, dono, nome, descricao);
        if (dono != null) adicionarMembro(dono);
    }

    @Override
    public void adicionarMembro(Usuario usuario) {
        GrupoUsuario grupoUsuario = new GrupoUsuario(usuario, this);
        usuario.adicionarGrupoUsuario(grupoUsuario);
        membros.add(grupoUsuario);
    }

    // Deve ser chamado através de usuário
    @Override
    public boolean removerMembro(Usuario usuario) {
        /* "Garanta que o método removerGrupo só remova o usuário do laboratorio.grupo caso ele não seja o dono (no caso de ser um
         * laboratorio.grupo privado)."
         * Faz sentido o laboratorio.grupo público poder não possuir dono.. então vou remover o dono caso o usuário dono saia do
         * laboratorio.grupo (o que seria impedido no laboratorio.grupo privado). Em laboratorio.grupo privado, a chamada em usuário retorna false antes
         * de chegar nesse método de Grupo (e o sobreescrito em GrupoPrivado)
         */
        if (getDono() != null && getDono().getId() == usuario.getId()) {
            alterarDono(usuario, null);
        }

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
