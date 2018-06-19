package laboratorio;

import laboratorio.carona.*;
import laboratorio.pagamento.*;
import laboratorio.perfil.*;
import laboratorio.perfil.caronante.*;
import laboratorio.perfil.caroneiro.*;
import laboratorio.usuario.*;
import laboratorio.utilidades.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PainelUsuario extends JPanel {

    private final JFrame janela;
    private final Usuario usuario;
    private boolean construido = false;

    public PainelUsuario(final JFrame janela, final Usuario usuario) {
        super();
        this.janela = janela;
        this.usuario = usuario;

        construir();
    }

    private void construir() {
        /* Estrutura geral */
        if (!construido) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        } else {
            removeAll();
        }

        /* Adicionamos as informações do usuário */
        criarPainelUsuario();

        /* Adicionamos as informações de perfil */
        criarPainelPerfil();

        if (!construido) {
            construido = true;
        }
        janela.pack();
    }

    private void criarPainelUsuario() {
        add(criarPaineisTexto(
                "Nome: " + usuario.getNome(),
                "E-mail: " + usuario.getEmail()
        ));

        /* Adicionar botões de controle de usuário */
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton alterarSenha = new JButton("Alterar senha");
        alterarSenha.addActionListener(event -> {
            String senha = HelperDialog.pedirConfirmarSenha();
            if (senha != null && senha.length() > 0) {
                usuario.setSenha(senha);
                HelperDialog.popupInformacao("Senha alterada!", "Sua senha foi alterada com sucesso! #sucesso");
            }
        });
        painelBotoes.add(alterarSenha);

        JButton alterarUsuario = new JButton("Modificar usuário");
        alterarUsuario.addActionListener(new CriarUsuarioPapup(this, usuario));
        painelBotoes.add(alterarUsuario);

        JButton sair = new JButton("Log out");
        sair.addActionListener(event -> {
            janela.remove(this);
            janela.setVisible(false);
            Main.getMain().efetuarLogin(null);
        });
        painelBotoes.add(sair);

        add(painelBotoes, CENTER_ALIGNMENT);
    }

    private void criarPainelPerfil() {
        Perfil perfil = usuario.getPerfil();
        if (perfil == null) {
            JPanel painelPerfil = new JPanel();
            painelPerfil.setLayout(new FlowLayout(FlowLayout.CENTER));

            JButton criarPerfil = new JButton("Criar perfil");
            criarPerfil.addActionListener(event -> {
                Perfil perfilPreenchido = HelperDialog.pedirPerfil();
                if (perfilPreenchido != null) {
                    perfilPreenchido.setUsuario(usuario);
                } else {
                    return;
                }

                // Recriamos esta seção no programa
                construir();
            });
            painelPerfil.add(criarPerfil);

            add(painelPerfil);
        } else {
            add(criarPaineisTexto(
                    "Sexo: " + (Character.toLowerCase(perfil.getSexo()) == 'f' ? "feminino" : "masculino"),
                    "Fumante? " + (perfil.isFumante() ? "sim" : "não"),
                    "Data de nascimento: " + perfil.getDataNascimento(),
                    "Telefone: " + perfil.getTelefone(),
                    "Localização: " + perfil.getCidade() + "/" + perfil.getEstado()
            ));

            /* Adicionar botões de controle de perfil */
            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton modificarPerfil = new JButton("Modificar perfil");
            modificarPerfil.addActionListener(event -> {
                Perfil perfilPreenchido = HelperDialog.pedirPerfil();
                if (perfilPreenchido != null) {
                    /* Substituímos no usuário */
                    perfilPreenchido.setUsuario(usuario);

                    /* Utilizamos a referência antes da mudança para passar os perfis de Caroneiro e Caronante para o
                     * novo perfil
                     */
                    if (perfil.getCaronante() != null) {
                        perfil.getCaronante().setPerfil(perfilPreenchido);
                    }

                    if (perfil.getCaroneiro() != null) {
                        perfil.getCaroneiro().setPerfil(perfilPreenchido);
                    }
                } else {
                    return;
                }

                /* Redesenhamos a janela */
                construir();
            });
            painelBotoes.add(modificarPerfil);

            add(painelBotoes, CENTER_ALIGNMENT);

            criarPainelCaronante(perfil);
            criarPainelCaroneiro(perfil);
        }
    }

    private void criarPainelCaronante(Perfil perfil) {
        Caronante caronante = perfil.getCaronante();
        /* Adicionar botões de controle de perfil */
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        if (caronante == null) {
            JButton criarCaronante = new JButton("Criar caronante");
            criarCaronante.addActionListener(new CriarCaronantePopup(janela, perfil));
            painelBotoes.add(criarCaronante);
        } else {
            add(criarPaineisTexto(
                    "Carteira de motorista: " + caronante.getCarteiraMotorista(),
                    "Avaliação média: " + caronante.getAvaliacao(),
                    "Gênero musical favorito: " + caronante.getGeneroMusicalFavorito(),
                    "Veículo: " + caronante.getMarcaVeiculo() + " " + caronante.getModeloVeiculo() + " placa " +
                            caronante.getPlacaVeiculo(),
                    "Assentos disponíveis: " + caronante.getAssentosDisponiveis(),
                    "Tempo habilitado: " + caronante.getTempoHabilitacao()
            ));

            /* Adicionamos a lista de caronas */


            JButton alterarPerfil = new JButton("Alterar caronante");
            alterarPerfil.addActionListener(new CriarCaronantePopup(janela, perfil));
            painelBotoes.add(alterarPerfil);

            JButton oferecerPrivada = new JButton("Oferecer carona");
            oferecerPrivada.addActionListener(new CriarCaronaPopup(janela, perfil));
            painelBotoes.add(oferecerPrivada);
        }
        add(painelBotoes);
    }

    private void criarPainelCaroneiro(Perfil perfil) {
        Caroneiro caroneiro = perfil.getCaroneiro();
        /* Adicionar botões de controle de perfil */
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        if (caroneiro == null) {
            JButton criarCaroneiro = new JButton("Criar caroneiro");
            criarCaroneiro.addActionListener(event -> {

            });
            painelBotoes.add(criarCaroneiro);
        } else {
            add(criarPaineisTexto(
                    "Cartão de crédito: " + (caroneiro.getCartaoDeCredito() == null ? "não registrado" :
                            caroneiro.getCartaoDeCredito()),
                    "Paga em dinheiro: " + (caroneiro.isPagamentoEmDinheiro() ? "sim" : "não")
            ));

            JButton alterarPerfil = new JButton("Alterar caroneiro");
            alterarPerfil.addActionListener(event -> {
            });
            painelBotoes.add(alterarPerfil);
        }
        add(painelBotoes);
    }

    private class CriarCaronaPopup implements ActionListener {

        private final JFrame janela;
        private final Perfil perfil;

        private CriarCaronaPopup(JFrame janela, Perfil perfil) {
            this.janela = janela;
            this.perfil = perfil;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel formulario = new JPanel();
            formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

            JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JCheckBox caronaPublica = new JCheckBox("Carona pública");
            caronaPublica.setToolTipText("Caso não, será privada.");
            painelTipo.add(caronaPublica);
            formulario.add(painelTipo);

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

            int opcao = JOptionPane.showConfirmDialog(
                    janela, formulario, "Dados da carona", JOptionPane.OK_CANCEL_OPTION
            );

            if (opcao == 0) {
                try {
                    Float valor = Float.valueOf(valorCarona.getText().replace(",", "."));
                    Carona carona;
                    if (caronaPublica.isSelected()) {
                        carona = perfil.getCaronante().oferecerCaronaPublica(
                                Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                Double.valueOf(longitudeEncontro.getText().replace(",", ".")),
                                Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                Double.valueOf(longitudeDestino.getText().replace(",", ".")),
                                horaDataEncontro.getText(),
                                valor
                        );
                    } else {
                        carona = perfil.getCaronante().oferecerCaronaPrivada(
                                Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                Double.valueOf(longitudeEncontro.getText().replace(",", ".")),
                                Double.valueOf(latitudeDestino.getText().replace(",", ".")),
                                Double.valueOf(longitudeDestino.getText().replace(",", ".")),
                                horaDataEncontro.getText(),
                                valor
                        );
                    }

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
                    }
                } catch (NumberFormatException e) {
                    HelperDialog.popupErro(
                            "Formatação inválida!", "Os números não respeitam o formato 4,32 ou 4.32!");
                }
            }
        }
    }

    private class CriarCaronantePopup implements ActionListener {

        private final JFrame janela;
        private final Perfil perfil;

        CriarCaronantePopup(JFrame janela, Perfil perfil) {
            this.janela = janela;
            this.perfil = perfil;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel formulario = new JPanel();
            formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

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

            Caronante caronante = perfil.getCaronante();
            JTextField carteiraMotorista = null;

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
                    janela, formulario, "Dados do caronante", JOptionPane.OK_CANCEL_OPTION);

            if (opcao == 0) {
                try {
                    if (caronante == null) {
                        perfil.setCaronante(new Caronante(
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

                    // Reconstruímos a UI
                    construir();
                } catch (NumberFormatException exception) {
                    HelperDialog.popupErro(
                            "Formatação inválida!", "O número deve ser inteiro!");
                }
            }
        }
    }

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
