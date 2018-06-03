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

    Grupo(Tipo tipo, int id, String nome, String descricao, Usuario dono) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
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

    public static Grupo carregar(DataInputStream dataInputStream, List<Usuario> usuarios) throws IOException {
        Tipo tipo = Tipo.fromOrdinal(dataInputStream.readInt());
        int id = dataInputStream.readInt();
        String nome = dataInputStream.readUTF();
        String descricao = dataInputStream.readUTF();

        /* Conferimos se há dono */
        Usuario dono = null;
        if (dataInputStream.readBoolean()) {
            int donoId = dataInputStream.readInt();
            for (Usuario usuario : usuarios) {
                if (donoId == usuario.getId()) {
                    dono = usuario;
                    break;
                }
            }
        }

        /* Criamos o grupo */
        Grupo grupo;
        if (tipo == Tipo.PRIVADO) {
            grupo = new GrupoPrivado(id, nome, descricao, dono);
        } else {
            grupo = new GrupoPublico(id, nome, descricao, dono);
        }

        /* Colocamos os usuários no grupo */
        int numMembros = dataInputStream.readInt();
        HashSet<Integer> idsMembros = new HashSet<>(numMembros);
        // Adicionamos todos os IDs dos usuários
        for (int i = 0; i < numMembros; i++) {
            idsMembros.add(dataInputStream.readInt());
        }
        // Procuramos em todos os usuários
        for (Usuario usuario : usuarios) {
            if (dono != null && usuario.getId() == dono.getId()) {
                continue;
            }
            if (idsMembros.contains(usuario.getId())) {
                grupo.adicionarMembro(usuario);
            }
        }

        return grupo;
    }

    public enum Tipo {

        PUBLICO,
        PRIVADO;

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
