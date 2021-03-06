package laboratorio.grupo;

import java.io.*;
import java.util.*;

import laboratorio.usuario.GerenciadorUsuario;
import laboratorio.usuario.Usuario;
import laboratorio.utilidades.*;

public abstract class Grupo implements Salvavel {

    private static int geradorId = 0;

    private final int id;
    private final Tipo tipo;

    private String nome;
    private String descricao;
    private Usuario dono;

    Grupo(Tipo tipo, Usuario dono, String nome, String descricao) {
        this.tipo = tipo;
        this.id = Grupo.geradorId++;
        this.dono = dono;
        this.nome = nome;
        this.descricao = descricao;
    }

    Grupo(Tipo tipo, int id, Usuario dono, String nome, String descricao) {
        this.id = id;
        if (id >= geradorId) {
            geradorId = id + 1;
        }
        this.tipo = tipo;
        this.dono = dono;
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

    /*
     * Considera que o dono ainda será membro, é necessário anterior e atual para verificar se não é um usuário comum
     * que quer alterar o dono do grupo
     */
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
    public void salvarParaArquivo(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(tipo.ordinal());
        dataOutputStream.writeInt(id);
        dataOutputStream.writeUTF(nome);
        dataOutputStream.writeUTF(descricao);

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

    static Grupo carregar(DataInputStream dataInputStream, GerenciadorUsuario gerenciadorUsuario) throws IOException {
        Tipo tipo = Tipo.fromOrdinal(dataInputStream.readInt());
        int id = dataInputStream.readInt();
        String nome = dataInputStream.readUTF();
        String descricao = dataInputStream.readUTF();

        /* Conferimos se há dono */
        Usuario dono = null;
        if (dataInputStream.readBoolean()) {
            dono = gerenciadorUsuario.getUsuario(dataInputStream.readInt());
        }

        /* Criamos o grupo */
        Grupo grupo;
        if (tipo == Tipo.PRIVADO) {
            grupo = new GrupoPrivado(id, dono, nome, descricao);
        } else {
            grupo = new GrupoPublico(id, dono, nome, descricao);
        }

        /* Colocamos os usuários no grupo */
        int numMembros = dataInputStream.readInt();
        // Adicionamos todos os usuários
        for (int i = 0; i < numMembros; i++) {
            int idUsuario = dataInputStream.readInt();
            // O dono (se existiu) já foi adicionado
            if (dono != null && idUsuario == dono.getId()) {
                continue;
            }
            grupo.adicionarMembro(gerenciadorUsuario.getUsuario(idUsuario));
        }

        return grupo;
    }

    public enum Tipo {

        PUBLICO("Público"),
        PRIVADO("Privado");

        private final String nomeDisplay;

        Tipo(String nomeDisplay) {
            this.nomeDisplay = nomeDisplay;
        }

        @Override
        public String toString() {
            return nomeDisplay;
        }

        public static Tipo fromOrdinal(int ordinal) {
            for (Tipo tipo : values()) {
                if (tipo.ordinal() == ordinal) {
                    return tipo;
                }
            }

            return null;
        }
    }
}
