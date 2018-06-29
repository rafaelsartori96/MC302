package laboratorio;

import laboratorio.carona.*;
import laboratorio.grupo.*;
import laboratorio.pagamento.*;
import laboratorio.perfil.*;
import laboratorio.perfil.caronante.*;
import laboratorio.perfil.caroneiro.*;
import laboratorio.usuario.*;
import laboratorio.utilidades.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class JanelaUsuario extends JFrame {

    private final Usuario usuario;
    private final JTabbedPane painel;

    private ModeloCarona modeloCaronaConcluidas;
    private JTable tabelaCaronasConcluidas;
    private ModeloCarona modeloCaronasDisponiveis;
    private JTable tabelaCaronasDisponiveis;

    private ModeloCarona modeloCaronaCaronante;
    private JTable tabelaCaronasCaronante;

    private ModeloTabela<Grupo> modeloGrupos;
    private JTable tabelaGrupos;

    public JanelaUsuario(final Usuario usuario, Pagina pagina) {
        super();

        setTitle("Sistema de caronas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.usuario = usuario;
        this.painel = new JTabbedPane();

        /* Adicionamos as informações do usuário */
        criarPainelUsuario();

        /* Adicionamos as informações de perfil */
        criarPainelPerfil();

        painel.setSelectedIndex(pagina.getIndex());

        add(painel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void criarPainelUsuario() {
        /* Painel principal do usuário */
        JPanel painelUsuario = new JPanel();
        painelUsuario.setLayout(new BoxLayout(painelUsuario, BoxLayout.Y_AXIS));

        /* Adicionamos o painel de informações do usuário */
        painelUsuario.add(criarPaineisTexto(
                "Nome: " + usuario.getNome(),
                "E-mail: " + usuario.getEmail()
        ));

        /* Criamos um painel com a lista de grupos a que o usuário pertence */
        JPanel painelGrupos = new JPanel(new BorderLayout());

        /* Criamos a tabela */
        modeloGrupos = new ModeloTabela<>() {
            @Override
            public int getColumnCount() {
                return 5;
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Nome";
                    case 1:
                        return "Descrição";
                    case 2:
                        return "Número de membros";
                    case 3:
                        return "Tipo";
                    case 4:
                        return "Dono";
                    default:
                        return null;
                }
            }

            @Override
            public Object getColumn(Grupo object, int column) {
                switch (column) {
                    case 0:
                        return object.getNome();
                    case 1:
                        return object.getDescricao();
                    case 2:
                        return object.getMembros().size();
                    case 3:
                        return object.getTipo().toString();
                    case 4:
                        return object.getDono() != null ? object.getDono().getNome() : "-";
                    default:
                        return null;
                }
            }

            @Override
            public List<Grupo> getList() {
                return usuario.getGrupos();
            }
        };
        tabelaGrupos = new JTable(modeloGrupos);
        tabelaGrupos.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        /* Adicionamos o header da tabela */
        painelGrupos.add(tabelaGrupos.getTableHeader(), BorderLayout.NORTH);

        /* Criamos um scroll para a tabela */
        JScrollPane scrollTabela = new JScrollPane(
                tabelaGrupos,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        painelGrupos.add(scrollTabela, BorderLayout.CENTER);

        /* Adicionamos o painel de grupos na interface do usuário */
        painelUsuario.add(painelGrupos);

        /* Criamos o painel para os botões dos grupos */
        JPanel opcoesGrupos = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton criarGrupo = new JButton("Criar grupo");
        criarGrupo.addActionListener(new CriarGrupoPopup(false));
        opcoesGrupos.add(criarGrupo);

        JButton entrarGrupo = new JButton("Entrar em grupo");
        entrarGrupo.addActionListener(new EntrarGrupoPopup());
        opcoesGrupos.add(entrarGrupo);

        JButton modificarGrupo = new JButton("Modificar grupo");
        modificarGrupo.addActionListener(new CriarGrupoPopup(true));
        opcoesGrupos.add(modificarGrupo);

        JButton adicionarAGrupo = new JButton("Adicionar a grupo");
        adicionarAGrupo.addActionListener(new AdicionarAGrupoPopup());
        opcoesGrupos.add(adicionarAGrupo);

        JButton sairDeGrupo = new JButton("Sair de grupo");
        sairDeGrupo.addActionListener(new SairGrupoPopup());
        opcoesGrupos.add(sairDeGrupo);

        /* Adicionamos as opções de grupo ao painel de grupos */
        painelUsuario.add(opcoesGrupos);

        /* Criamos um painel com botões de controle de usuário */
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton alterarSenha = new JButton("Alterar senha");
        alterarSenha.addActionListener(event -> {
            String senha = HelperDialog.pedirConfirmarSenha();
            if (senha != null) {
                usuario.setSenha(senha);
                HelperDialog.popupInformacao("Senha alterada!", "Sua senha foi alterada com sucesso! #sucesso");
            }
        });
        painelBotoes.add(alterarSenha);

        JButton alterarUsuario = new JButton("Modificar usuário");
        alterarUsuario.addActionListener(new CriarUsuarioPopup(this, usuario) {
            @Override
            public void redesenhar() {
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.USUARIO));
            }
        });
        painelBotoes.add(alterarUsuario);

        JButton sair = new JButton("Log out");
        sair.addActionListener(event -> Main.getMain().efetuarLogin(null));
        painelBotoes.add(sair);

        /* Adicionamos o painel de botões ao painel do usuário */
        painelUsuario.add(painelBotoes);

        /* Criamos a aba usuário com este painel */
        painel.insertTab("Usuário", null, painelUsuario, "Controle do usuário", Pagina.USUARIO.getIndex());
    }

    private void criarPainelPerfil() {
        /* Painel principal do perfil */
        JPanel painelPerfil = new JPanel();

        Perfil perfil = usuario.getPerfil();
        if (perfil == null) {
            painelPerfil.setLayout(new BorderLayout());

            /* Caso não exista perfil, criamos um botão único no centro do painel do perfil */
            JButton criarPerfil = new JButton("Criar perfil");
            criarPerfil.addActionListener(new CriarPerfilPopup());

            /* Adicionamos o botão ao centro do painel de perfil */
            painelPerfil.add(criarPerfil, BorderLayout.CENTER);

            /* Adicionamos a aba de perfil */
            painel.insertTab("Perfil", null, painelPerfil, "Controle do perfil", Pagina.PERFIL.getIndex());
        } else {
            painelPerfil.setLayout(new BoxLayout(painelPerfil, BoxLayout.Y_AXIS));

            /* Adicionamos o painel que contém as informações do perfil */
            painelPerfil.add(criarPaineisTexto(
                    "Sexo: " + (Character.toLowerCase(perfil.getSexo()) == 'f' ? "feminino" : "masculino"),
                    "Fumante: " + (perfil.isFumante() ? "sim" : "não"),
                    "Data de nascimento: " + perfil.getDataNascimento(),
                    "Telefone: " + perfil.getTelefone(),
                    "Localização: " + perfil.getCidade() + "/" + perfil.getEstado()
            ));

            /* Adicionar botões de controle de perfil */
            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton modificarPerfil = new JButton("Modificar perfil");
            modificarPerfil.addActionListener(new CriarPerfilPopup());
            painelBotoes.add(modificarPerfil);

            /* Adicionamos o painel de botões ao painel do perfil */
            painelPerfil.add(painelBotoes);

            /* Criamos a aba de perfil */
            painel.insertTab("Perfil", null, painelPerfil, "Controle do perfil", Pagina.PERFIL.getIndex());

            /* Criamos as abas de Caronante e Caroneiro, já que o perfil existe */
            criarPainelCaronante(perfil);
            criarPainelCaroneiro(perfil);
        }
    }

    private void criarPainelCaronante(Perfil perfil) {
        /* Painel principal do perfil de Caronante */
        JPanel painelCaronante = new JPanel();

        /* Criamos o painel de botões de controle do perfil */
        if (perfil.getCaronante() == null) {
            painelCaronante.setLayout(new BorderLayout());

            /* Adicionamos um painel que contém apenas o botão no centro */
            JButton criarCaronante = new JButton("Criar caronante");
            criarCaronante.addActionListener(new CriarCaronantePopup());
            painelCaronante.add(criarCaronante, BorderLayout.CENTER);

            /* Deixamos para o fim do método para criar a aba */
        } else {
            Caronante caronante = perfil.getCaronante();
            painelCaronante.setLayout(new BoxLayout(painelCaronante, BoxLayout.Y_AXIS));

            /* Adicionamos painel com informações */
            painelCaronante.add(criarPaineisTexto(
                    "Carteira de motorista: " + caronante.getCarteiraMotorista(),
                    "Avaliação média: " + caronante.getAvaliacao(),
                    "Gênero musical favorito: " + caronante.getGeneroMusicalFavorito(),
                    "Veículo: " + caronante.getMarcaVeiculo() + " " + caronante.getModeloVeiculo() + " placa " +
                            caronante.getPlacaVeiculo(),
                    "Assentos disponíveis: " + caronante.getAssentosDisponiveis(),
                    "Tempo habilitado: " + caronante.getTempoHabilitacao()
            ));

            /* Criamos o painel da tabela de caronas */
            JPanel painelTabela = new JPanel();
            painelTabela.setLayout(new BorderLayout());

            /* Criamos a tabela de carona */
            modeloCaronaCaronante = new ModeloCarona(caronante.getCaronas());
            tabelaCaronasCaronante = new JTable(modeloCaronaCaronante);
            tabelaCaronasCaronante.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
            /* Adicionamos o header da tabela */
            painelTabela.add(tabelaCaronasCaronante.getTableHeader(), BorderLayout.NORTH);
            JScrollPane scrollTabelaCarona = new JScrollPane(
                    tabelaCaronasCaronante,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
            scrollTabelaCarona.setPreferredSize(new Dimension(400, 200));
            /* Adicionamos a tabela com um scroll massa */
            painelTabela.add(scrollTabelaCarona, BorderLayout.CENTER);

            /* Adicionamos a tabela ao painel principal */
            painelCaronante.add(painelTabela);
            /* Criamos um painel para botões */
            JPanel opcoesCaronas = new JPanel(new FlowLayout(FlowLayout.CENTER));

            /* Adicionamos um botão para adicionar a carona a algum grupo */
            JButton adicionarGrupo = new JButton("Adicionar grupo");
            adicionarGrupo.addActionListener(new AdicionarCaronaGrupoPopup());
            opcoesCaronas.add(adicionarGrupo);

            /* Adicionamos um botão para modificar a carona selecionada */
            JButton modificarCarona = new JButton("Modificar carona");
            modificarCarona.addActionListener(new CriarCaronaPopup(true));
            opcoesCaronas.add(modificarCarona);

            /* Adicionamos um botão para avaliar caroneiros da carona selecionada */
            JButton avaliarCaronantes = new JButton("Avaliar caroneiros");
            avaliarCaronantes.addActionListener(new AvaliarCaroneiroPopup());
            opcoesCaronas.add(avaliarCaronantes);

            /* Adicionamos os botões das caronas */
            painelCaronante.add(opcoesCaronas);

            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton alterarPerfil = new JButton("Alterar caronante");
            alterarPerfil.addActionListener(new CriarCaronantePopup());
            painelBotoes.add(alterarPerfil);

            JButton oferecerCarona = new JButton("Oferecer carona");
            oferecerCarona.addActionListener(new CriarCaronaPopup(false));
            painelBotoes.add(oferecerCarona);

            /* Adicionamos o painel de botões ao painel do perfil */
            painelCaronante.add(painelBotoes);

            /* Deixamos para o fim do método para adicionar a aba */
        }

        /* Adicionamos a aba */
        painel.insertTab(
                "Caronante", null, painelCaronante, "Perfil para oferecer caronas", Pagina.CARONANTE.getIndex());
    }

    private void criarPainelCaroneiro(Perfil perfil) {
        /* Criamos o painel do perfil */
        JPanel painelCaroneiro = new JPanel();

        if (perfil.getCaroneiro() == null) {
            painelCaroneiro.setLayout(new BorderLayout());

            /* Criamos apenas um botão para criar o perfil */
            JButton criarCaroneiro = new JButton("Criar caroneiro");
            criarCaroneiro.addActionListener(new CriarCaroneiroPopup());

            /* Adicionamos o painel para o painel do perfil */
            painelCaroneiro.add(criarCaroneiro, BorderLayout.CENTER);

            /* Deixamos para o fim do método para adicionar a aba */
        } else {
            Caroneiro caroneiro = perfil.getCaroneiro();
            painelCaroneiro.setLayout(new BoxLayout(painelCaroneiro, BoxLayout.Y_AXIS));

            /* Adicionamos o painel de informações */
            painelCaroneiro.add(criarPaineisTexto(
                    "Cartão de crédito: " + (caroneiro.getCartaoDeCredito() == null ? "não registrado" :
                            caroneiro.getCartaoDeCredito()),
                    "Paga em dinheiro: " + (caroneiro.isPagamentoEmDinheiro() ? "sim" : "não"),
                    "Avaliação: " + caroneiro.getAvaliacao()
            ));

            /* Adicionamos a lista de caronas que o caronante está */
            JPanel painelCaronasConcluidas = new JPanel();
            painelCaronasConcluidas.setLayout(new BoxLayout(painelCaronasConcluidas, BoxLayout.Y_AXIS));

            JPanel painelTabelaConcluidas = new JPanel();
            painelTabelaConcluidas.setLayout(new BorderLayout());

            /* Criamos a tabela de carona */
            modeloCaronaConcluidas = new ModeloCarona(caroneiro.getCaronas());
            tabelaCaronasConcluidas = new JTable(modeloCaronaConcluidas);
            tabelaCaronasConcluidas.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
            /* Adicionamos o header da tabela */
            painelTabelaConcluidas.add(tabelaCaronasConcluidas.getTableHeader(), BorderLayout.NORTH);
            JScrollPane scrollTabelaCaronasConcluidas = new JScrollPane(
                    tabelaCaronasConcluidas,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
            scrollTabelaCaronasConcluidas.setPreferredSize(new Dimension(400, 200));
            /* Adicionamos a tabela com um scroll massa */
            painelTabelaConcluidas.add(scrollTabelaCaronasConcluidas, BorderLayout.CENTER);

            /* Adicionamos toda a tabela */
            painelCaronasConcluidas.add(painelTabelaConcluidas);

            /* Botões das caronas concluídas */
            JPanel painelBotoesConcluidas = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton avaliarCaronante = new JButton("Avaliar caronante");
            avaliarCaronante.addActionListener(new AvaliarCaronantePopup());
            painelBotoesConcluidas.add(avaliarCaronante);

            JButton sairCarona = new JButton("Sair da carona");
            sairCarona.addActionListener(new SairCaronaPopup());
            painelBotoesConcluidas.add(sairCarona);

            painelCaronasConcluidas.add(painelBotoesConcluidas);

            /* Adicionamos as informações das caronas concluídas */
            painelCaroneiro.add(painelCaronasConcluidas);

            /* Adicionamos a lista de caronas que o caronante pode entrar */
            JPanel painelCaronasDisponiveis = new JPanel();
            painelCaronasDisponiveis.setLayout(new BoxLayout(painelCaronasDisponiveis, BoxLayout.Y_AXIS));

            JPanel painelTabelaDisponiveis = new JPanel();
            painelTabelaDisponiveis.setLayout(new BorderLayout());

            /* Criamos a tabela de carona */
            modeloCaronasDisponiveis = new ModeloCarona(caroneiro.getCaronasDisponiveis());
            tabelaCaronasDisponiveis = new JTable(modeloCaronasDisponiveis);
            tabelaCaronasDisponiveis.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
            /* Adicionamos o header da tabela */
            painelTabelaDisponiveis.add(tabelaCaronasDisponiveis.getTableHeader(), BorderLayout.NORTH);
            JScrollPane scrollTabelaCaronaDisponiveis = new JScrollPane(
                    tabelaCaronasDisponiveis,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
            scrollTabelaCaronaDisponiveis.setPreferredSize(new Dimension(400, 200));
            /* Adicionamos a tabela com um scroll massa */
            painelTabelaDisponiveis.add(scrollTabelaCaronaDisponiveis, BorderLayout.CENTER);

            /* Adicionamos toda a tabela */
            painelCaronasDisponiveis.add(painelTabelaDisponiveis);

            /* Botões das caronas concluídas */
            JPanel painelBotoesDisponiveis = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton entrarEmCarona = new JButton("Entrar em carona");
            entrarEmCarona.addActionListener(new EntrarCaronaPopup());
            painelBotoesDisponiveis.add(entrarEmCarona);

            painelCaronasDisponiveis.add(painelBotoesDisponiveis);

            /* Adicionamos as informações das caronas concluídas */
            painelCaroneiro.add(painelCaronasDisponiveis);

            /* Criamos o painel de botões de controle de perfil */
            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton alterarPerfil = new JButton("Alterar caroneiro");
            alterarPerfil.addActionListener(new CriarCaroneiroPopup());
            painelBotoes.add(alterarPerfil);

            /* Adicionamos o painel de botões */
            painelCaroneiro.add(painelBotoes);

            /* Deixamos para o fim do método para adicionar a aba */
        }

        painel.insertTab("Caroneiro", null, painelCaroneiro, "Perfil para buscar caronas", Pagina.CARONEIRO.getIndex());
    }

    private class CriarGrupoPopup implements ActionListener {

        private final boolean modificar;

        private CriarGrupoPopup(boolean modificar) {
            this.modificar = modificar;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel formulario = new JPanel();
            formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

            /* Criamos um painel horizontal para cada pergunta */
            Grupo grupo = null;
            if (modificar) {
                if (tabelaGrupos.getSelectedRow() < 0) {
                    HelperDialog.popupErro("Selecione um grupo!", "É necessário selecionar um grupo!");
                    return;
                }
                grupo = modeloGrupos.getObject(tabelaGrupos.getSelectedRow());

                if (usuario != grupo.getDono()) {
                    HelperDialog.popupErro("Erro!", "Você não é dono do grupo!");
                    return;
                }
            }

            /* Não é possível modificar o estado de um grupo após criação */
            JCheckBox publico = null;
            if (grupo == null) {
                publico = new JCheckBox("Público");
                formulario.add(publico);
            }

            JPanel painelNome = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelNome.add(new JLabel("Nome:"));
            JTextField nome = new JTextField(16);
            nome.setToolTipText("Entre com o nome do grupo.");
            painelNome.add(nome);
            formulario.add(painelNome);

            JPanel painelDescricao = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelDescricao.add(new JLabel("Descrição do grupo:"));
            JTextArea descricao = new JTextArea(4, 30);
            descricao.setToolTipText("Descreva brevemente o grupo.");
            descricao.setLineWrap(true);
            descricao.setWrapStyleWord(true);
            JScrollPane scrollDescricao = new JScrollPane(descricao);
            scrollDescricao.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            painelDescricao.add(scrollDescricao);
            formulario.add(painelDescricao);

            /* Adicionamos informações do caroneiro caso exista perfil */
            if (grupo != null) {
                nome.setText(grupo.getNome());
                descricao.setText(grupo.getDescricao());
            }

            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, formulario, "Dados do grupo", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                /* Criamos o perfil ou atualizamos seus atributos */
                if (grupo == null) {
                    if (publico.isSelected()) {
                        usuario.criarGrupoPublico(nome.getText(), descricao.getText());
                    } else {
                        usuario.criarGrupoPrivado(nome.getText(), descricao.getText());
                    }
                } else {
                    grupo.setNome(nome.getText());
                    grupo.setDescricao(descricao.getText());
                }

                /* Reconstruímos a UI */
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.USUARIO));
            }
        }
    }

    private class AdicionarAGrupoPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            /* Conferimos se há grupo selecionado */
            if (tabelaGrupos.getSelectedRow() < 0) {
                HelperDialog.popupErro("Selecione um grupo!", "É necessário selecionar um grupo!");
                return;
            }

            Grupo grupo = modeloGrupos.getObject(tabelaGrupos.getSelectedRow());
            if (grupo.getTipo() == Grupo.Tipo.PUBLICO || !(grupo instanceof GrupoPrivado)) {
                HelperDialog.popupErro("Erro!", "O grupo é público e todos podem entrar!");
                return;
            }

            /* Criamos o painel principal */
            JPanel painelTabela = new JPanel(new BorderLayout());

            ModeloTabela<Usuario> modeloUsuarios = new ModeloTabela<>() {
                @Override
                public int getColumnCount() {
                    return 3;
                }

                @Override
                public String getColumnName(int column) {
                    switch (column) {
                        case 0:
                            return "Nome";
                        case 1:
                            return "E-mail";
                        case 2:
                            return "Número de grupos";
                        default:
                            return null;
                    }
                }

                @Override
                public Object getColumn(Usuario object, int column) {
                    switch (column) {
                        case 0:
                            return object.getNome();
                        case 1:
                            return object.getEmail();
                        case 2:
                            return object.getGrupos().size();
                        default:
                            return null;
                    }
                }

                @Override
                public List<Usuario> getList() {
                    return Main.getMain().getGerenciadorUsuario().getUsuarios();
                }
            };
            JTable tabelaUsuarios = new JTable(modeloUsuarios);
            tabelaUsuarios.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
            painelTabela.add(tabelaUsuarios.getTableHeader(), BorderLayout.NORTH);

            JScrollPane scrollUsuarios = new JScrollPane(tabelaUsuarios);
            scrollUsuarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            painelTabela.add(scrollUsuarios, BorderLayout.CENTER);

            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, painelTabela, "Lista de usuários", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                /* Avisamos caso não tenha sido selecionado */
                if (tabelaUsuarios.getSelectedRow() < 0) {
                    HelperDialog.popupErro("Erro!", "É necessário escolher ao menos um usuário!");
                    return;
                }

                Usuario usuarioSelecionado = modeloUsuarios.getObject(tabelaUsuarios.getSelectedRow());

                /* Tentamos adicionar o usuário ao grupo */
                try {
                    usuario.adicionarAGrupo((GrupoPrivado) grupo, usuarioSelecionado);
                } catch (SistemaCaronaException e) {
                    HelperDialog.popupErro("Erro!", e.getMessage());
                }

                /* Reconstruímos a UI */
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.USUARIO));
            }
        }
    }

    private class EntrarGrupoPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            /* Criamos o painel principal */
            JPanel painelTabela = new JPanel(new BorderLayout());

            ModeloTabela<Grupo> modeloGrupos = new ModeloTabela<>() {
                @Override
                public int getColumnCount() {
                    return 5;
                }

                @Override
                public String getColumnName(int column) {
                    switch (column) {
                        case 0:
                            return "ID";
                        case 1:
                            return "Nome";
                        case 2:
                            return "Tipo";
                        case 3:
                            return "Descrição";
                        case 4:
                            return "Dono";
                        default:
                            return null;
                    }
                }

                @Override
                public Object getColumn(Grupo object, int column) {
                    switch (column) {
                        case 0:
                            return object.getId();
                        case 1:
                            return object.getNome();
                        case 2:
                            return object.getTipo().toString();
                        case 3:
                            return object.getDescricao();
                        case 4:
                            return object.getDono() != null ? object.getDono().getNome() : "-";
                        default:
                            return null;
                    }
                }

                @Override
                public List<Grupo> getList() {
                    return Main.getMain().getGerenciadorGrupo().getGrupos();
                }
            };
            JTable tabelaGrupos = new JTable(modeloGrupos);
            tabelaGrupos.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
            painelTabela.add(tabelaGrupos.getTableHeader(), BorderLayout.NORTH);

            JScrollPane scrollGrupos = new JScrollPane(tabelaGrupos);
            scrollGrupos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            painelTabela.add(scrollGrupos, BorderLayout.CENTER);

            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, painelTabela, "Lista de grupos", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                /* Avisamos caso não tenha sido selecionado */
                if (tabelaGrupos.getSelectedRow() < 0) {
                    HelperDialog.popupErro("Erro!", "É necessário escolher ao menos um grupo!");
                    return;
                }

                Grupo grupo = modeloGrupos.getObject(tabelaGrupos.getSelectedRow());

                /* Verificamos se o grupo é válido */
                if (grupo.getTipo() == Grupo.Tipo.PRIVADO) {
                    HelperDialog.popupErro("Erro!", "Não é possível entrar em grupos privados, apenas em públicos.");
                    return;
                }

                /* Tentamos adicionar o usuário ao grupo */
                usuario.adicionarGrupo((GrupoPublico) grupo);

                /* Reconstruímos a UI */
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.USUARIO));
            }
        }
    }

    private class SairGrupoPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            /* Criamos um painel horizontal para cada pergunta */
            if (tabelaGrupos.getSelectedRow() < 0) {
                HelperDialog.popupErro("Selecione um grupo!", "É necessário selecionar um grupo!");
                return;
            }
            Grupo grupo = modeloGrupos.getObject(tabelaGrupos.getSelectedRow());

            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this,
                    "Você tem certeza que quer sair de " + grupo.getNome() + "?",
                    "Sair de grupo",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (opcao == 0) {
                /* Saímos do grupo */
                try {
                    usuario.removerGrupo(grupo);
                } catch (SistemaCaronaException e) {
                    HelperDialog.popupErro("Erro!", e.getMessage());
                }

                /* Reconstruímos a UI */
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.USUARIO));
            }
        }
    }

    private class CriarPerfilPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel formulario = new JPanel();
            formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

            /* Criamos um painel horizontal para cada pergunta */

            JPanel painelCidadeEstado = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelCidadeEstado.add(new JLabel("Cidade, estado:"));
            JTextField cidade = new JTextField(13);
            cidade.setToolTipText("Entre com o nome da cidade.");
            painelCidadeEstado.add(cidade);
            JTextField estado = new JTextField(16);
            estado.setToolTipText("Entre com o nome do estado.");
            painelCidadeEstado.add(estado);
            formulario.add(painelCidadeEstado);

            JPanel painelDataNascimento = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelDataNascimento.add(new JLabel("Data de nascimento:"));
            JTextField dataNascimento = new JTextField(13);
            dataNascimento.setToolTipText("Entre com sua data de nascimento. Por exemplo, 24/11/1995.");
            painelDataNascimento.add(dataNascimento);
            formulario.add(painelDataNascimento);

            JPanel painelTelefone = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelTelefone.add(new JLabel("Telefone:"));
            JTextField telefone = new JTextField(13);
            telefone.setToolTipText("Entre com o seu número de telefone.");
            painelTelefone.add(telefone);
            formulario.add(painelTelefone);

            JPanel painelSexo = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelSexo.add(new JLabel("Sexo:"));
            JComboBox<String> sexo = new JComboBox<>(new String[]{"Masculino", "Feminino"});
            painelSexo.add(sexo);
            formulario.add(painelSexo);

            JCheckBox fumante = new JCheckBox("Fumante");
            formulario.add(fumante);

            Perfil perfil = usuario.getPerfil();
            /* Adicionamos informações do caroneiro caso exista perfil */
            if (perfil != null) {
                cidade.setText(perfil.getCidade());
                estado.setText(perfil.getEstado());
                dataNascimento.setText(perfil.getDataNascimento());
                fumante.setSelected(perfil.isFumante());
                sexo.setSelectedIndex(Character.toLowerCase(perfil.getSexo()) == 'm' ? 0 : 1);
                telefone.setText(perfil.getTelefone());
            }

            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, formulario, "Dados do perfil", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                /* Criamos o perfil ou atualizamos seus atributos */
                if (perfil == null) {
                    usuario.setPerfil(new Perfil(
                            null,
                            null,
                            null,
                            sexo.getSelectedIndex() == 0 ? 'm' : 'f',
                            dataNascimento.getText(),
                            cidade.getText(),
                            estado.getText(),
                            telefone.getText(),
                            fumante.isSelected()
                    ));
                } else {
                    perfil.setCidade(cidade.getText());
                    perfil.setEstado(estado.getText());
                    perfil.setDataNascimento(dataNascimento.getText());
                    perfil.setSexo(sexo.getSelectedIndex() == 0 ? 'm' : 'f');
                    perfil.setFumante(fumante.isSelected());
                    perfil.setTelefone(telefone.getText());
                }

                /* Reconstruímos a UI */
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.PERFIL));
            }
        }
    }

    private class CriarCaronaPopup implements ActionListener {

        private final boolean modificar;

        private CriarCaronaPopup(boolean modificar) {
            this.modificar = modificar;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel formulario = new JPanel();
            formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

            /*
             * Para cada linha do formulário, fazemos um layout horizontal com label e entradas de texto
             */

            Carona carona = null;
            if (modificar) {
                if (tabelaCaronasCaronante.getSelectedRow() < 0) {
                    HelperDialog.popupErro("Selecione uma carona!", "É necessário selecionar uma carona!");
                    return;
                }
                carona = modeloCaronaCaronante.getObject(tabelaCaronasCaronante.getSelectedRow());
            }

            JCheckBox caronaPublica = null;
            if (carona == null) {
                JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.CENTER));
                caronaPublica = new JCheckBox("Carona pública");
                caronaPublica.setToolTipText("Caso não, será privada.");
                painelTipo.add(caronaPublica);
                formulario.add(painelTipo);
            }

            JPanel painelEncontro = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelEncontro.add(new JLabel("Latitude e longitude do encontro:"));
            JTextField latitudeEncontro = new JTextField(10);
            latitudeEncontro.setToolTipText("51,23456");
            painelEncontro.add(latitudeEncontro);
            JTextField longitudeEncontro = new JTextField(10);
            longitudeEncontro.setToolTipText("14,14583");
            painelEncontro.add(longitudeEncontro);
            formulario.add(painelEncontro);

            JPanel painelDestino = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelDestino.add(new JLabel("Latitude e longitude do destino:"));
            JTextField latitudeDestino = new JTextField(10);
            latitudeDestino.setToolTipText("51,23456");
            painelDestino.add(latitudeDestino);
            JTextField longitudeDestino = new JTextField(10);
            longitudeDestino.setToolTipText("14,14583");
            painelDestino.add(longitudeDestino);
            formulario.add(painelDestino);

            JPanel painelHoraData = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelHoraData.add(new JLabel("Hora e data de encontro:"));
            JTextField horaDataEncontro = new JTextField(16);
            horaDataEncontro.setToolTipText("18h 15/12/2012");
            painelHoraData.add(horaDataEncontro);
            formulario.add(painelHoraData);

            JPanel painelValor = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelValor.add(new JLabel("Valor da carona em reais:"));
            JTextField valorCarona = new JTextField(5);
            valorCarona.setToolTipText("17,99 ou 0,00");
            painelValor.add(valorCarona);
            formulario.add(painelValor);

            JPanel painelMoedas = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JCheckBox dinheiro = new JCheckBox("Dinheiro");
            dinheiro.setToolTipText("Se a carona irá aceitar dinheiro como forma de pagamento.");
            painelMoedas.add(dinheiro);
            JCheckBox cartaoDeCredito = new JCheckBox("Cartão de crédito");
            cartaoDeCredito.setToolTipText("Se a carona irá aceitar cartão de crédito como forma de pagamento.");
            painelMoedas.add(cartaoDeCredito);
            formulario.add(painelMoedas);

            if (carona != null) {
                latitudeEncontro.setText(String.valueOf(carona.getLatitudeEncontro()));
                longitudeEncontro.setText(String.valueOf(carona.getLongitudeEncontro()));
                latitudeDestino.setText(String.valueOf(carona.getLatitudeDestino()));
                longitudeDestino.setText(String.valueOf(carona.getLongitudeDestino()));
                horaDataEncontro.setText(carona.getHoraDiaEncontro());
                valorCarona.setText(String.valueOf(carona.getValor()));
                if (carona.checarExistenciaFormaPagamento(MetodoPagamento.CARTAO_DE_CREDITO)) {
                    cartaoDeCredito.setSelected(true);
                }
                if (carona.checarExistenciaFormaPagamento(MetodoPagamento.DINHEIRO)) {
                    dinheiro.setSelected(true);
                }
                if (carona.checarExistenciaFormaPagamento(MetodoPagamento.GRATIS)) {
                    cartaoDeCredito.setSelected(false);
                    dinheiro.setSelected(false);
                    valorCarona.setText("0");
                }
            }

            /* Mostramos ao usuário esse painel */
            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, formulario, "Dados da carona", JOptionPane.OK_CANCEL_OPTION
            );

            /* Se o usuário apertou OK... */
            if (opcao == 0) {
                try {
                    Float valor = Float.valueOf(valorCarona.getText().replace(",", "."));

                    /* Criamos uma carona pública ou privada dependendo da opção selecionada */
                    if (carona == null) {
                        if (caronaPublica.isSelected()) {
                            carona = usuario.getPerfil().getCaronante().oferecerCaronaPublica(
                                    Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                    Double.valueOf(longitudeEncontro.getText().replace(",", ".")),
                                    Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                    Double.valueOf(longitudeDestino.getText().replace(",", ".")),
                                    horaDataEncontro.getText(),
                                    valor
                            );
                        } else {
                            carona = usuario.getPerfil().getCaronante().oferecerCaronaPrivada(
                                    Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                    Double.valueOf(longitudeEncontro.getText().replace(",", ".")),
                                    Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                    Double.valueOf(longitudeDestino.getText().replace(",", ".")),
                                    horaDataEncontro.getText(),
                                    valor
                            );
                        }
                    } else {
                        carona.setLatitudeEncontro(Double.valueOf(latitudeEncontro.getText()));
                        carona.setLongitudeEncontro(Double.valueOf(longitudeEncontro.getText()));
                        carona.setLatitudeDestino(Double.valueOf(latitudeDestino.getText()));
                        carona.setLongitudeDestino(Double.valueOf(longitudeDestino.getText()));
                        carona.setHoraDiaEncontro(horaDataEncontro.getText());
                        carona.setValor(valor);
                    }

                    /* Em ambas as caronas, adicionamos os métodos de pagamento desejados */
                    boolean metodoPagamentoPresente = false;
                    if (dinheiro.isSelected() && valor > 0) {
                        carona.adicionarFormaPagamento(MetodoPagamento.DINHEIRO);
                        metodoPagamentoPresente = true;
                    }
                    if (cartaoDeCredito.isSelected() && valor > 0) {
                        carona.adicionarFormaPagamento(MetodoPagamento.CARTAO_DE_CREDITO);
                        metodoPagamentoPresente = true;
                    }

                    if (valor == 0 || !metodoPagamentoPresente) {
                        carona.adicionarFormaPagamento(MetodoPagamento.GRATIS);
                        carona.setValor(0);
                    }

                    /* Reconstruímos a UI */
                    Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONANTE));
                } catch (NumberFormatException e) {
                    HelperDialog.popupErro(
                            "Formatação inválida!", "Os números não respeitam o formato 4,32 ou 4.32!");
                }
            }
        }
    }

    private class CriarCaronantePopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel formulario = new JPanel();
            formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

            /* De maneira análoga ao outro popup */

            JPanel painelTempoHabilitacao = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelTempoHabilitacao.add(new JLabel("Tempo de habilitação em anos:"));
            JTextField tempoHabilitacao = new JTextField(3);
            tempoHabilitacao.setToolTipText("Por exemplo, 5");
            painelTempoHabilitacao.add(tempoHabilitacao);
            formulario.add(painelTempoHabilitacao);

            JPanel painelGeneroMusical = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelGeneroMusical.add(new JLabel("Gênero músical favorito:"));
            JTextField generoMusicalFavorito = new JTextField(26);
            generoMusicalFavorito.setToolTipText("Por exemplo, o seu gênero músical favorito");
            painelGeneroMusical.add(generoMusicalFavorito);
            formulario.add(painelGeneroMusical);

            JPanel painelCarro = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelCarro.add(new JLabel("Marca:"));
            JTextField marcaVeiculo = new JTextField(20);
            marcaVeiculo.setToolTipText("Por exemplo, Chevrolet");
            painelCarro.add(marcaVeiculo);
            painelCarro.add(new JLabel("Modelo:"));
            JTextField modeloVeiculo = new JTextField(20);
            modeloVeiculo.setToolTipText("Por exemplo, Prisma");
            painelCarro.add(modeloVeiculo);
            formulario.add(painelCarro);

            JPanel painelJuridico = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelJuridico.add(new JLabel("Assentos disponíveis:"));
            JTextField assentosDisponiveis = new JTextField(3);
            assentosDisponiveis.setToolTipText("Por exemplo, 4");
            painelJuridico.add(assentosDisponiveis);
            painelJuridico.add(new JLabel("Placa do veículo:"));
            JTextField placaVeiculo = new JTextField(9);
            placaVeiculo.setToolTipText("ABC-1234");
            painelJuridico.add(placaVeiculo);

            Caronante caronante = usuario.getPerfil().getCaronante();
            /* Permitimos a escrita de carteira de motorista apenas para o usuário que está se registrando */
            JTextField carteiraMotorista = null;

            /* Adicionamos informações do caroneiro caso exista perfil */
            if (caronante != null) {
                tempoHabilitacao.setText(String.valueOf(caronante.getTempoHabilitacao()));
                generoMusicalFavorito.setText(caronante.getGeneroMusicalFavorito());
                marcaVeiculo.setText(caronante.getMarcaVeiculo());
                modeloVeiculo.setText(caronante.getModeloVeiculo());
                placaVeiculo.setText(caronante.getPlacaVeiculo());
                assentosDisponiveis.setText(String.valueOf(caronante.getAssentosDisponiveis()));
            } else {
                // Atributo não pode ser alterado
                painelJuridico.add(new JLabel("Carteira de motorista:"));
                carteiraMotorista = new JTextField(14);
                painelJuridico.add(carteiraMotorista);
            }

            formulario.add(painelJuridico);

            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, formulario, "Dados do caronante", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                try {
                    /* Criamos o perfil ou atualizamos seus atributos */
                    if (caronante == null) {
                        usuario.getPerfil().setCaronante(new Caronante(
                                Integer.valueOf(tempoHabilitacao.getText()),
                                generoMusicalFavorito.getText(),
                                placaVeiculo.getText(),
                                carteiraMotorista.getText(),
                                marcaVeiculo.getText(),
                                modeloVeiculo.getText(),
                                Integer.valueOf(assentosDisponiveis.getText())
                        ));
                    } else {
                        caronante.setTempoHabilitacao(Integer.valueOf(tempoHabilitacao.getText()));
                        caronante.setGeneroMusicalFavorito(generoMusicalFavorito.getText());
                        caronante.setMarcaVeiculo(marcaVeiculo.getText());
                        caronante.setModeloVeiculo(modeloVeiculo.getText());
                        caronante.setAssentosDisponiveis(Integer.valueOf(assentosDisponiveis.getText()));
                    }

                    /* Reconstruímos a UI */
                    Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONANTE));
                } catch (NumberFormatException exception) {
                    HelperDialog.popupErro(
                            "Formatação inválida!", "O número deve ser inteiro!");
                }
            }
        }
    }

    private class CriarCaroneiroPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel formulario = new JPanel();
            formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

            /* De maneira análoga ao outro popup */

            JPanel painelCartaoCredito = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelCartaoCredito.add(new JLabel("Cartão de crédito:"));
            JTextField cartaoDeCredito = new JTextField(26);
            cartaoDeCredito.setToolTipText("Preencha apenas se houver");
            painelCartaoCredito.add(cartaoDeCredito);
            formulario.add(painelCartaoCredito);

            JCheckBox pagamentoEmDinheiro = new JCheckBox("Pagamento em dinheiro");
            formulario.add(pagamentoEmDinheiro);

            Caroneiro caroneiro = usuario.getPerfil().getCaroneiro();

            /* Adicionamos informações do caroneiro caso exista perfil */
            if (caroneiro != null) {
                if (caroneiro.getCartaoDeCredito() != null) {
                    cartaoDeCredito.setText(caroneiro.getCartaoDeCredito());
                }
                pagamentoEmDinheiro.setSelected(caroneiro.isPagamentoEmDinheiro());
            }

            int opcao = JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, formulario, "Dados do caroneiro", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                try {
                    /* Criamos o perfil ou atualizamos seus atributos */
                    if (caroneiro == null) {
                        caroneiro = new Caroneiro(pagamentoEmDinheiro.isSelected());
                        if (cartaoDeCredito.getText().length() > 0) {
                            caroneiro.setCartaoDeCredito(cartaoDeCredito.getText());
                        }
                        usuario.getPerfil().setCaroneiro(caroneiro);
                    } else {
                        caroneiro.setCartaoDeCredito(cartaoDeCredito.getText());
                        caroneiro.setPagamentoEmDinheiro(pagamentoEmDinheiro.isSelected());
                    }

                    /* Reconstruímos a UI */
                    Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONEIRO));
                } catch (NumberFormatException exception) {
                    HelperDialog.popupErro("Formatação inválida!", "O número de cartão deve ser inteiro!");
                }
            }
        }

    }

    class AvaliarCaronantePopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tabelaCaronasConcluidas.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(
                        JanelaUsuario.this,
                        "É necessário selecionar uma carna da lista de caronas que possui",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Carona carona = modeloCaronaConcluidas.getObject(tabelaCaronasConcluidas.getSelectedRow());
            try {
                carona.atribuirNotaCaronante(Float.valueOf(JOptionPane.showInputDialog("Entre a nota:")));
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(
                        JanelaUsuario.this, "O número deve ser inteiro", "Erro!", JOptionPane.ERROR_MESSAGE);
            }

            /* Reconstruímos a UI */
            Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONEIRO));
        }
    }

    class EntrarCaronaPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tabelaCaronasDisponiveis.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(
                        JanelaUsuario.this,
                        "É necessário selecionar uma carona para entrar!",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    JanelaUsuario.this,
                    usuario.getPerfil().getCaroneiro().pedirCarona(
                            modeloCaronasDisponiveis.getObject(tabelaCaronasDisponiveis.getSelectedRow())) ?
                            "Entrou na carona com sucesso!" :
                            "Carona recusou sua entrada",
                    "Situação de carona",
                    JOptionPane.INFORMATION_MESSAGE
            );

            /* Reconstruímos a UI */
            Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONEIRO));
        }
    }

    class SairCaronaPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tabelaCaronasConcluidas.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(
                        JanelaUsuario.this,
                        "É necessário selecionar uma carona para sair!",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    JanelaUsuario.this,
                    modeloCaronaConcluidas.getObject(tabelaCaronasConcluidas.getSelectedRow())
                            .removerCaroneiro(usuario.getPerfil().getCaroneiro()) ?
                            "Saiu da carona com sucesso!" :
                            "Erro ao sair da carona",
                    "Situação de carona",
                    JOptionPane.INFORMATION_MESSAGE
            );

            /* Reconstruímos a UI */
            Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONEIRO));
        }
    }

    class AvaliarCaroneiroPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaCaronasCaronante.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(
                        JanelaUsuario.this, "Selecione uma carona!", "Erro!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Carona carona = modeloCaronaCaronante.getObject(selectedRow);
            if (carona.caronaVazia()) {
                JOptionPane.showMessageDialog(
                        JanelaUsuario.this, "Carona está vazia!", "Erro!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel avaliacao = new JPanel();
            avaliacao.setLayout(new BoxLayout(avaliacao, BoxLayout.Y_AXIS));

            JPanel selecaoCaroneiro = new JPanel(new FlowLayout(FlowLayout.CENTER));
            selecaoCaroneiro.add(new JLabel("Selecione o caroneiro:"));
            ModeloComboBox<Caroneiro> modeloCaroneiro = new ModeloComboBox<>(carona.getCaroneiros()) {
                @Override
                public String getElementAt(int index) {
                    return list.get(index).getPerfil().getUsuario().getNome();
                }
            };
            JComboBox<String> caroneiros = new JComboBox<>(modeloCaroneiro);
            selecaoCaroneiro.add(caroneiros);
            avaliacao.add(selecaoCaroneiro);

            JPanel painelNota = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelNota.add(new JLabel("Entre a nota:"));
            JTextField nota = new JTextField(3);
            painelNota.add(nota);
            avaliacao.add(painelNota);

            if (JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, avaliacao, "Avalie caroneiros", JOptionPane.OK_CANCEL_OPTION) == 0) {
                try {
                    Caroneiro caroneiro = modeloCaroneiro.getAt(caroneiros.getSelectedIndex());
                    carona.atribuirNotaCaroneiro(
                            caroneiro.getPerfil().getUsuario().getId(), Float.valueOf(nota.getText()));
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(
                            JanelaUsuario.this,
                            "Número deve ser real! Utilize ponto como divisor decimal.",
                            "Erro!",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                /* Reconstruímos a UI */
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONANTE));
            }
        }
    }

    class AdicionarCaronaGrupoPopup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaCaronasCaronante.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(
                        JanelaUsuario.this, "Selecione uma carona!", "Erro!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Carona carona = modeloCaronaCaronante.getObject(selectedRow);

            JPanel selecaoGrupo = new JPanel(new FlowLayout(FlowLayout.CENTER));
            selecaoGrupo.add(new JLabel("Selecione o grupo:"));
            ModeloComboBox<Grupo> modeloGrupos = new ModeloComboBox<>(
                    carona.getTipo() == Carona.Tipo.PUBLICA ?
                            usuario.getGrupos().stream()
                                    .filter(grupo -> grupo.getTipo() == Grupo.Tipo.PUBLICO)
                                    .collect(Collectors.toList()) :
                            usuario.getGrupos().stream()
                                    .filter(grupo -> grupo.getTipo() == Grupo.Tipo.PRIVADO)
                                    .collect(Collectors.toList())
            ) {
                @Override
                public String getElementAt(int index) {
                    return list.get(index).getNome();
                }
            };
            JComboBox<String> grupos = new JComboBox<>(modeloGrupos);
            selecaoGrupo.add(grupos);

            if (JOptionPane.showConfirmDialog(
                    JanelaUsuario.this, selecaoGrupo, "Escolha o grupo", JOptionPane.OK_CANCEL_OPTION) == 0) {
                if (carona.getTipo() == Carona.Tipo.PUBLICA) {
                    ((CaronaPublica) carona).adicionarGrupo(
                            (GrupoPublico) modeloGrupos.getAt(grupos.getSelectedIndex())
                    );
                } else {
                    ((CaronaPrivada) carona).adicionarGrupo(
                            (GrupoPrivado) modeloGrupos.getAt(grupos.getSelectedIndex())
                    );
                }

                /* Reconstruímos a UI */
                Main.getMain().setJanelaPrincipal(new JanelaUsuario(usuario, Pagina.CARONANTE));
            }
        }
    }

    abstract class ModeloComboBox<T> extends DefaultComboBoxModel<String> {

        protected final List<T> list;

        ModeloComboBox(List<T> caroneiros) {
            this.list = caroneiros;
        }

        @Override
        public abstract String getElementAt(int index);

        @Override
        public int getSize() {
            return list.size();
        }

        public T getAt(int index) {
            return list.get(index);
        }
    }

    public enum Pagina {
        USUARIO(0),
        PERFIL(1),
        CARONANTE(2),
        CARONEIRO(3);

        private final int index;

        Pagina(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    class ModeloCarona extends ModeloTabela<Carona> {

        private final List<Carona> caronas;

        ModeloCarona(List<Carona> caronas) {
            this.caronas = caronas;
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Encontro";
                case 1:
                    return "Destino";
                case 2:
                    return "Hora e data";
                case 3:
                    return "Assentos disponíveis";
                case 4:
                    return "Tipo";
                case 5:
                    return "Valor";
                default:
                    return null;
            }
        }

        @Override
        public Object getColumn(Carona object, int column) {
            switch (column) {
                case 0:
                    return object.getLatitudeEncontro() + ", " + object.getLongitudeEncontro();
                case 1:
                    return object.getLatitudeDestino() + ", " + object.getLongitudeDestino();
                case 2:
                    return object.getHoraDiaEncontro();
                case 3:
                    return object.getAssentosDisponiveis();
                case 4:
                    return object.getTipo().toString();
                case 5:
                    return object.getValor();
                default:
                    return null;
            }
        }

        @Override
        public List<Carona> getList() {
            return this.caronas;
        }
    }

    /**
     * Cria um painel com todas as linhas
     *
     * @param textos linhas de texto
     * @return um painel que contém todos os textos
     */
    private static JPanel criarPaineisTexto(String... textos) {
        JPanel painelEsquerda = new JPanel(new BorderLayout());
        JPanel linhas = new JPanel();
        linhas.setLayout(new BoxLayout(linhas, BoxLayout.Y_AXIS));
        for (String texto : textos) {
            linhas.add(new JLabel(texto));
        }
        painelEsquerda.add(linhas);
        return painelEsquerda;
    }
}
