package grupo;

import java.util.ArrayList;
import java.util.stream.Collectors;

import carona.CaronaPrivada;
import usuario.Usuario;

public class GrupoPrivado extends Grupo{
	
	private final ArrayList<GrupoUsuario> membros = new ArrayList<GrupoUsuario>();
	private final ArrayList<CaronaPrivada> caronas = new ArrayList<CaronaPrivada>();

	public GrupoPrivado(String nome, String descricao, Usuario dono) {
		super(nome, descricao, dono);
		adicionarMembro(dono);
	}
	
	public void adicionarMembro(Usuario usuario) {
		GrupoUsuario grupoUsuario = new GrupoUsuario(usuario, this);
		usuario.adicionarGrupoUsuario(grupoUsuario);
		membros.add(grupoUsuario);
	}
	
	public boolean removerMembro(Usuario usuario) {
		for(GrupoUsuario membro: membros) {
			if (membro.getUsuario().getId() == usuario.getId()) {
				membros.remove(membro);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Usuario> getMembros() {
		//Novamente streams do java 8. Nesse caso ele mapeia um stream de GrupoUsuario para um de Usuarios,
		//e então transforma em uma lista. Há boatos que até 2050 java terá um stream.toList()
		return this.membros.stream().map(GrupoUsuario::getUsuario).collect(Collectors.toCollection(ArrayList::new));
	}

}
