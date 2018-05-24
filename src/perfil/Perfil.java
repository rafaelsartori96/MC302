package perfil;

import caronante.Caronante;
import caroneiro.Caroneiro;
import usuario.Usuario;
import utilidades.HelperFormatacao;

public class Perfil {
	private char sexo;
	private String dataNascimento;
	private String cidade;
	private String estado;
	private String telefone;
	private boolean fumante;
	private float avaliacao; 
	private Caroneiro caroneiro;
	private Caronante caronante;
	private Usuario usuario;
	
	public Perfil(char sexo, String dataNascimento, String cidade, String estado, String telefone, boolean fumante,
			Caronante caronante, Caroneiro caroneiro, Usuario usuario) {
		super();
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.cidade = cidade;
		this.estado = estado;
		this.telefone = telefone;
		this.fumante = fumante;
		this.avaliacao = 0; //Nos outros labs será melhor definido
		this.caronante = caronante;
		this.caroneiro = caroneiro;
		if (caronante != null) caronante.setPerfil(this);
		if (caroneiro != null) caroneiro.setPerfil(this);
		if (usuario != null) usuario.setPerfil(this);
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
		
	public float getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(float avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Caroneiro getCaroneiro() {
		return caroneiro;
	}

	public void setCaroneiro(Caroneiro caroneiro) {
		this.caroneiro = caroneiro;
	}

	public Caronante getCaronante() {
		return caronante;
	}

	public void setCaronante(Caronante caronante) {
		this.caronante = caronante;
	}

	public char getSexo() {
		return sexo;
	}
	
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public boolean isFumante() {
		return fumante;
	}
	
	public void setFumante(boolean fumante) {
		this.fumante = fumante;
	}
	
	public String toString(int numeroEspacos) {		
		String espacos = HelperFormatacao.criaEspacos(numeroEspacos);
		return String.format("%s| Perfil\n"
				+ "%s| - Sexo: %s\n"
				+ "%s| - Data de nascimento: %s\n"
				+ "%s| - Cidade: %s\n"
				+ "%s| - Estado: %s\n"
				+ "%s| - Telefone: %s\n"
				+ "%s| - Fumante: %s\n"
				+ "%s| - Dados como caroneiro: \n%s"
				+ "%s| - Dados como caronante: \n%s",
				espacos, espacos, this.sexo, espacos, this.dataNascimento, espacos, this.cidade,
				espacos, this.estado, espacos, this.telefone, espacos, this.fumante,
				espacos, this.caroneiro != null ? this.caroneiro.toString(numeroEspacos + 1) : espacos + "*Sem dado como caroneiro*\n",
				espacos, this.caronante != null ? this.caronante.toString(numeroEspacos + 1) : espacos + "*Sem dado como caronante*\n");
	}
	
	@Override
	public String toString() {
		return toString(0);
	}	
	

}
