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
        if (membros.isEmpty()) {
            return false;
        }

        if (getDono() != null && getDono().getId() == usuario.getId()) {
            // Altera o dono para nulo antes de remover
            alterarDono(usuario, null);
        }

        for (Iterator<GrupoUsuario> iterator = membros.iterator(); iterator.hasNext(); ) {
            GrupoUsuario membro = iterator.next();

            if (membro.getUsuario().getId() == usuario.getId()) {
                // Para ficar consistente
                membro.getUsuario().removerGrupoUsuario(membro);
                // Removemos utilizando o iterator
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Usuario> getMembros() {
        return Collections.unmodifiableList(
                this.membros.stream().map(GrupoUsuario::getUsuario).collect(Collectors.toList()));
    }

    public List<CaronaPublica> getCaronas() {
        return Collections.unmodifiableList(caronas);
    }

    public void adicionarCarona(CaronaPublica caronaPublica) {
        this.caronas.add(caronaPublica);
    }
}
