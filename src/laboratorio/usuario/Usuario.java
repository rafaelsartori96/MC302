package laboratorio.usuario;

import java.io.*;
import java.util.ArrayList;

import laboratorio.Main;
import laboratorio.grupo.*;
import laboratorio.perfil.Perfil;
import laboratorio.utilidades.*;

public class Usuario implements Salvavel {

    private static int geradorId = 0;

    private final ArrayList<GrupoUsuario> grupos = new ArrayList<>();
    private final int id;

    private Perfil perfil;
    private String nome;
    private String email;
    private String senha;
    private boolean status;

    Usuario(String nome, String email, String senha, boolean status) {
        this.id = Usuario.geradorId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
    }

    private Usuario(int id, String nome, String email, String senha, boolean status, Perfil perfil) {
        this.id = id;
        // Subimos o gerador para o valor que temos certeza
        if (this.id >= geradorId) {
            geradorId = this.id + 1;
        }
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
        setPerfil(perfil);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    // Adiciona do lado de cá e considera que o relacionamento foi feito do outro lado
    // (o jeito de separar é que esse adiciona um GrupoUsuario explicitamente)

    public void adicionarGrupoUsuario(GrupoUsuario grupo) {
        grupos.add(grupo);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        if (this.perfil != perfil) {
            this.perfil = perfil;
            if (perfil != null) {
                perfil.setUsuario(this);
            }
        }
    }

    public void adicionarGrupo(GrupoPublico grupo) {
        grupo.adicionarMembro(this);
    }

    // Já é garantido que o dono do laboratorio.grupo não possa sair sem alterar o dono

    public void removerGrupo(int id) throws SistemaCaronaException {
        for (GrupoUsuario grupoUsuario : grupos) {
            if (grupoUsuario.getGrupo().getId() == id) {
                // Conferimos o laboratorio.grupo apenas para privado (aparentemente, grupos públicos podem ficar sem dono)
                if (grupoUsuario.getGrupo().getTipo() == Grupo.Tipo.PRIVADO &&
                        grupoUsuario.getGrupo().getDono().getId() == getId()) {
                    throw new SistemaCaronaException("Grupo privado não pode ficar sem dono!");
                }

                grupos.remove(grupoUsuario);
                grupoUsuario.getGrupo().removerMembro(this);
            }
        }
        throw new SistemaCaronaException("O usuário não pertence a este laboratorio.grupo!");
    }

    public void removerGrupo(Grupo grupo) throws SistemaCaronaException {
        removerGrupo(grupo.getId());
    }

    public boolean atualizarGrupo(int id, String nome, String descricao) {
        for (GrupoUsuario grupoUsuario : grupos) {
            if (grupoUsuario.getGrupo().getId() == id) {
                if (grupoUsuario.getGrupo().getDono().getId() != this.id) {
                    return false;
                }
                if (descricao != null) grupoUsuario.getGrupo().setDescricao(descricao);
                if (nome != null) grupoUsuario.getGrupo().setNome(nome);
                return true;
            }
        }
        return false;
    }

    public boolean atualizarGrupo(int id, String nome) {
        return atualizarGrupo(id, nome, null);
    }

    public void adicionarAGrupo(GrupoPrivado grupo, Usuario usuario) throws SistemaCaronaException {
        if (grupo.getDono().getId() != getId()) {
            throw new SistemaCaronaException("Não é possível adicionar alguém ao laboratorio.grupo privado não sendo o dono.");
        }

        grupo.adicionarMembro(usuario);
    }

    public GrupoPublico criarGrupoPublico(String nome, String descricao) {
        return Main.getMain().getGerenciadorGrupo().criarGrupoPublico(this, nome, descricao);
    }

    public GrupoPrivado criarGrupoPrivado(String nome, String descricao) {
        return Main.getMain().getGerenciadorGrupo().criarGrupoPrivado(this, nome, descricao);
    }

    public String toString(int numeroEspacos) {
        String espacos = HelperFormatacao.criaEspacos(numeroEspacos);

        String stringGrupos = espacos + "* Sem laboratorio.grupo associado *\n";

        if (!grupos.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (GrupoUsuario grupo : grupos) {
                stringBuilder.append(grupo.getGrupo().toString(numeroEspacos + 1));
            }

            stringGrupos = stringBuilder.toString();
        }

        return String.format("%s| Usuario\n"
                        + "%s| - Nome: %s\n"
                        + "%s| - Id: %s\n"
                        + "%s| - Email: %s\n"
                        + "%s| - Senha: %s\n"
                        + "%s| - Status: %s\n"
                        + "%s| - Perfil: \n%s"
                        + "%s| - Dados dos grupos: \n%s",
                espacos,
                espacos, this.nome,
                espacos, this.id,
                espacos, this.email,
                espacos, this.senha,
                espacos, this.status ? "Ativo" : "Inativo",
                espacos, this.perfil.toString(numeroEspacos + 1),
                espacos, stringGrupos);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    @Override
    public void salvarParaArquivo(DataOutputStream dataOutputStream) throws IOException {
        if (perfil != null) {
            dataOutputStream.writeBoolean(true);
            perfil.salvarParaArquivo(dataOutputStream);
        } else {
            dataOutputStream.writeBoolean(false);
        }

        dataOutputStream.writeInt(id);
        dataOutputStream.writeUTF(nome);
        dataOutputStream.writeUTF(email);
        dataOutputStream.writeUTF(senha);
        dataOutputStream.writeBoolean(status);

        dataOutputStream.flush();
    }

    static Usuario carregar(DataInputStream inputStream) throws IOException {
        // Verificamos se há laboratorio.perfil
        Perfil perfil = null;
        if (inputStream.readBoolean()) {
            perfil = Perfil.carregar(inputStream);
        }

        int id = inputStream.readInt();
        String nome = inputStream.readUTF();
        String email = inputStream.readUTF();
        String senha = inputStream.readUTF();
        boolean status = inputStream.readBoolean();

        return new Usuario(id, nome, email, senha, status, perfil);
    }
}
