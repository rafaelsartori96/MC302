public class GrupoUsuario {

    private final Usuario usuario;
    private final Grupo grupo;

    private boolean dono = false;

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

    public boolean isDono() {
        return dono;
    }

    public void setDono(boolean dono) {
        this.dono = dono;
    }

    @Override
    public String toString() {
        return "Grupo " + getGrupo().getId() + " <-> " + getUsuario().getNome();
    }
}
