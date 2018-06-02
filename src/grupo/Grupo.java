package grupo;

import java.io.*;
import java.util.*;

import usuario.Usuario;
import utilidades.*;

public abstract class Grupo implements Salvavel {

    private static int geradorId = 0;

    private final int id;
    private final Tipo tipo;

    private String nome;
    private String descricao;
    private Usuario dono;

    public Grupo(Tipo tipo, String nome, String descricao, Usuario dono) {
        this.tipo = tipo;
        this.id = Grupo.geradorId++;
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
    }

    public Grupo(Tipo tipo, String nome, String descricao) {
        this.tipo = tipo;
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

    public abstract List<Usuario> getMembros();

    public int getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Usuario getDono() {
        return dono;
    }

    public boolean checarPresenca(Usuario usuario) {
        return getMembros().contains(usuario);
    }

    public abstract void adicionarMembro(Usuario usuario);

    public abstract boolean removerMembro(Usuario usuario);

    // Considera que o dono ainda será membro, é necessário anterior e atual para verificar se não é um usuário comum
    // que quer alterar o dono do grupo
    public boolean alterarDono(Usuario anterior, Usuario atual) {
        if (anterior.getId() != dono.getId()) {
            return false;
        }

        // É possível possuir um grupo sem dono (público)
        if (tipo == Tipo.PRIVADO) {
            // Insere o usuario como membro, também (com o cuidado de verificar a unicidade - mais fácil remover antes)
            this.removerMembro(atual);
            this.adicionarMembro(atual);
        }

        this.dono = atual;
        return true;
    }

    public String toStringMostraMembros(int numeroEspacos) {
        String espacos = HelperFormatacao.criaEspacos(numeroEspacos);

        StringBuilder stringBuilder = new StringBuilder();
        for (Usuario usuario : this.getMembros()) {
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

    @Override
    public void salvarParaArquivo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(tipo.ordinal());
        dataOutputStream.writeInt(id);
        dataOutputStream.writeChars(nome);
        dataOutputStream.writeChars(descricao);

        if (dono != null) {
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeInt(dono.getId());
        } else {
            dataOutputStream.writeBoolean(false);
        }

        List<Usuario> membros = getMembros();
        dataOutputStream.writeInt(membros.size());
        for (Usuario usuario : membros) {
            dataOutputStream.writeInt(usuario.getId());
        }

        dataOutputStream.flush();
    }

    public enum Tipo {
        PUBLICO,
        PRIVADO
    }
}
