package grupo;

import usuario.Usuario;

public class GrupoUsuario {
	private Usuario usuario;
	private Grupo grupo;
	
	public GrupoUsuario(Usuario usuario, Grupo grupo) {
		super();
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
