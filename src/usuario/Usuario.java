package usuario;

import java.io.*;
import java.util.ArrayList;

import grupo.*;
import perfil.Perfil;
import utilidades.*;

public class Usuario implements Salvavel {

    private static int geradorId = 0;

    private final ArrayList<GrupoUsuario> grupos = new ArrayList<>();
    private final int id;

    private Perfil perfil;
    private String nome;
    private String email;
    private String senha;
    private boolean status;

    public Usuario(String nome, String email, String senha, boolean status, Perfil perfil) {
        this.id = Usuario.geradorId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
        this.perfil = perfil;
        if (perfil != null) perfil.setUsuario(this);
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
        this.perfil = perfil;
    }

    public void adicionarGrupo(GrupoPublico grupo) {
        grupo.adicionarMembro(this);
    }

    // Já é garantido que o dono do grupo não possa sair sem alterar o dono
    public boolean removerGrupo(int id) {
        for (GrupoUsuario grupoUsuario : grupos) {
            if (grupoUsuario.getGrupo().getId() == id) {
                // Conferimos o grupo apenas para privado (aparentemente, grupos públicos podem ficar sem dono)
                if (grupoUsuario.getGrupo().getTipo() == Grupo.Tipo.PRIVADO &&
                        grupoUsuario.getGrupo().getDono().getId() == getId()) {
                    return false;
                }

                grupos.remove(grupoUsuario);
                grupoUsuario.getGrupo().removerMembro(this);
                return true;
            }
        }
        return false;
    }

    public boolean removerGrupo(Grupo grupo) {
        return removerGrupo(grupo.getId());
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
            throw new SistemaCaronaException("Não é possível adicionar alguém ao grupo privado não sendo o dono.");
        }

        grupo.adicionarMembro(usuario);
    }

    public GrupoPublico criarGrupoPublico(String nome, String descricao) {
        return new GrupoPublico(nome, descricao, this);
    }

    public GrupoPrivado criarGrupoPrivado(String nome, String descricao) {
        return new GrupoPrivado(nome, descricao, this);
    }

    public String toString(int numeroEspacos) {
        String espacos = HelperFormatacao.criaEspacos(numeroEspacos);

        String stringGrupos = espacos + "* Sem grupo associado *\n";

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
    public void salvarParaArquivo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        if (perfil != null) {
            dataOutputStream.writeBoolean(true);
            perfil.salvarParaArquivo(outputStream);
        } else {
            dataOutputStream.writeBoolean(false);
        }

        dataOutputStream.writeInt(id);
        dataOutputStream.writeChars(nome);
        dataOutputStream.writeChars(email);
        dataOutputStream.writeChars(senha);
        dataOutputStream.writeBoolean(status);
    }
}