package grupo;

import java.util.ArrayList;

import caroneiro.Caroneiro;
import usuario.Usuario;
import utilidades.HelperFormatacao;

public abstract class Grupo {
	private int id;
	private String nome;
	private String descricao;
	static private int geradorId = 0;
	private Usuario dono;
	
	public Grupo(String nome, String descricao, Usuario dono) {
		this.id = Grupo.geradorId++;
		this.nome = nome;
		this.descricao = descricao;
		this.dono = dono;
	}
	
	public Grupo(String nome, String descricao) {
		this.id = Grupo.geradorId++;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public abstract ArrayList<Usuario> getMembros();

	public int getId() {
		return id;
	}

	public Usuario getDono() {
		return dono;
	}

	public abstract void adicionarMembro(Usuario usuario);
	
	public abstract boolean removerMembro(Usuario usuario);
	
	//Considera que o dono ainda será membro
	public boolean alterarDono(Usuario anterior, Usuario atual) {
		if (anterior.getId() != dono.getId()) {
			return false;
		}
		
		//Insere o usuario como membro, também (com o cuidado de verificar a unicidade - mais fácil remover antes)
		this.removerMembro(atual);
		this.adicionarMembro(atual);
		
		dono = atual;
		return true;
	}
	
	public String toStringMostraMembros(int numeroEspacos) {
		String espacos = HelperFormatacao.criaEspacos(numeroEspacos);
		
		StringBuilder stringBuilder = new StringBuilder();
		for(Usuario usuario: this.getMembros()) {
			stringBuilder.append(usuario.toString(numeroEspacos + 1));
		}		
		
		String stringMembros = stringBuilder.toString();
		
		return toString(numeroEspacos) 
				+ String.format("%s| - Membros: \n%s",
					espacos, stringMembros);
	}
	
	public String toString(int numeroEspacos) {
		String espacos = HelperFormatacao.criaEspacos(numeroEspacos);

		
		return String.format("%s| Grupo\n"
				+ "%s| - Id: %d\n"
				+ "%s| - Nome: %s\n"
				+ "%s| - Descricao: %s\n",
				espacos,
				espacos, this.id,
				espacos, this.nome,
				espacos, this.descricao);
	}
	
	@Override
	public String toString() {
		return toString(0);
	}
	

}
