package grupo;

import usuario.Usuario;

public class GrupoUsuario {

    private final Usuario usuario;
    private final Grupo grupo;

    public GrupoUsuario(Usuario usuario, Grupo grupo) {
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }
}
