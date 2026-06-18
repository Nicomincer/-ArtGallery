package com.nicol.view;

import com.nicol.backend.ArtGallery;
import com.nicol.backend.Obra;
import com.nicol.backend.Avaliacao;
import com.nicol.backend.Exposicao;
import com.nicol.exceptions.ObraJaCadastradaException;
import com.nicol.exceptions.NotaInvalidaException;
import com.nicol.backend.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

// Adicionado o "implements IArtGalleryView"
public class ArtGalleryGUI extends JFrame implements IArtGalleryView {

    private ArtGallery galleria;
    private JTextArea txt_resultados;

    public ArtGalleryGUI(ArtGallery galleria) {
        this.galleria = galleria;

        setTitle("ArtGallery - Sistema de Curadoria");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Obras (Cadastrar/Remover)", criarAbaObras());
        abas.addTab("Avaliações", criarAbaAvaliacoes());
        abas.addTab("Consultas e Listagens", criarAbaConsultas());

        add(abas);
    }

    // --- IMPLEMENTAÇÃO DOS MÉTODOS DA INTERFACE ---

    @Override
    public void exibirTela() {
        this.setVisible(true);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void exibirErro(String erro) {
        JOptionPane.showMessageDialog(this, erro, "Erro no Sistema", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void atualizarLista(Vector<Obra> listaObras) {
        txt_resultados.setText("");
        if (listaObras.isEmpty()) {
            txt_resultados.setText("Nenhuma obra encontrada para esta consulta.");
            return;
        }

        for (Obra obra : listaObras) {
            // Late binding e Polimorfismo acontecendo na prática aqui
            txt_resultados.append(obra.exibirDetalhes());
            txt_resultados.append("\nMédia de Avaliações: " + obra.mediaAvaliacoes());
            txt_resultados.append("\n----------------------------------------\n");
        }
    }

    // --- MÉTODOS AUXILIARES DAS ABAS ---

    private JPanel criarAbaObras() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form_cadastro = new JPanel(new GridLayout(6, 2, 5, 5));
        form_cadastro.setBorder(BorderFactory.createTitledBorder("Cadastrar Nova Obra"));

        JTextField txt_titulo = new JTextField();
        JTextField txt_autor = new JTextField();
        JComboBox<String> cb_tipo = new JComboBox<>(new String[]{"Pintura Digital", "Modelagem 3D", "Arte Generativa"});
        JTextField txt_info1 = new JTextField();
        JTextField txt_info2 = new JTextField();

        form_cadastro.add(new JLabel("Título da Obra:"));
        form_cadastro.add(txt_titulo);
        form_cadastro.add(new JLabel("Autor/Artista:"));
        form_cadastro.add(txt_autor);
        form_cadastro.add(new JLabel("Tipo de Obra:"));
        form_cadastro.add(cb_tipo);
        form_cadastro.add(new JLabel("Atributo Extra 1:"));
        form_cadastro.add(txt_info1);
        form_cadastro.add(new JLabel("Atributo Extra 2:"));
        form_cadastro.add(txt_info2);

        JButton btn_cadastrar = new JButton("Publicar Obra");
        form_cadastro.add(new JLabel(""));
        form_cadastro.add(btn_cadastrar);

        btn_cadastrar.addActionListener(e -> {
            try {
                String titulo = txt_titulo.getText();
                String autor = txt_autor.getText();
                String tipo = (String) cb_tipo.getSelectedItem();

                Obra nova_obra = null;

                if (tipo.equals("Pintura Digital")) {
                    nova_obra = new PinturaDigital(titulo, autor, txt_info1.getText(), txt_info2.getText());
                } else if (tipo.equals("Modelagem 3D")) {
                    int poligonos = Integer.parseInt(txt_info1.getText());
                    nova_obra = new Modelagem3D(titulo, autor, poligonos, txt_info2.getText());
                } else if (tipo.equals("Arte Generativa")) {
                    long seed = Long.parseLong(txt_info2.getText());
                    nova_obra = new ArteGenerativa(titulo, autor, txt_info1.getText(), seed);
                }

                galleria.publicarObra(nova_obra);
                exibirMensagem("Obra cadastrada com sucesso!"); // Uso do método da interface

                txt_titulo.setText(""); txt_autor.setText(""); txt_info1.setText(""); txt_info2.setText("");
            } catch (ObraJaCadastradaException ex) {
                exibirErro(ex.getMessage()); // Uso do método da interface
            } catch (Exception ex) {
                exibirErro("Erro nos dados informados. Verifique os campos.");
            }
        });

        JPanel form_remover = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form_remover.setBorder(BorderFactory.createTitledBorder("Desativar/Remover Obra"));
        JTextField txt_remover_titulo = new JTextField(20);
        JButton btn_remover = new JButton("Remover");

        form_remover.add(new JLabel("Título da Obra:"));
        form_remover.add(txt_remover_titulo);
        form_remover.add(btn_remover);

        btn_remover.addActionListener(e -> {
            galleria.removerObra(txt_remover_titulo.getText());
            exibirMensagem("Se a obra existia, ela foi desativada!");
            txt_remover_titulo.setText("");
        });

        painel.add(form_cadastro, BorderLayout.CENTER);
        painel.add(form_remover, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaAvaliacoes() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txt_obra_titulo = new JTextField();
        JTextField txt_usuario = new JTextField();
        JTextField txt_nota = new JTextField();
        JTextField txt_comentario = new JTextField();
        JButton btn_avaliar = new JButton("Enviar Avaliação");

        painel.add(new JLabel("Título da Obra:"));
        painel.add(txt_obra_titulo);
        painel.add(new JLabel("Seu Nome:"));
        painel.add(txt_usuario);
        painel.add(new JLabel("Nota (0 a 10):"));
        painel.add(txt_nota);
        painel.add(new JLabel("Comentário:"));
        painel.add(txt_comentario);
        painel.add(new JLabel(""));
        painel.add(btn_avaliar);

        btn_avaliar.addActionListener(e -> {
            try {
                String titulo = txt_obra_titulo.getText();
                String usuario = txt_usuario.getText();
                int nota = Integer.parseInt(txt_nota.getText());
                String comentario = txt_comentario.getText();

                Avaliacao avaliacao = new Avaliacao(usuario, nota, comentario);

                // Se a obra não existir, o método vai "jogar" o erro para cá
                galleria.avaliarObra(titulo, avaliacao);

                // Só vai chegar aqui se a obra existir e estiver ativa!
                exibirMensagem("Avaliação adicionada com sucesso!");

                // Limpa os campos
                txt_obra_titulo.setText(""); txt_usuario.setText(""); txt_nota.setText(""); txt_comentario.setText("");

            } catch (IllegalArgumentException | IllegalStateException ex) {
                // Captura o erro do backend e mostra o aviso exato na tela!
                exibirErro(ex.getLocalizedMessage());
            } catch (Exception ex) {
                exibirErro("Erro ao processar avaliação. Verifique se a nota é válida.");
            }
        });

        return painel;
    }

    private JPanel criarAbaConsultas() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painel_botoes = new JPanel(new FlowLayout());
        JButton btn_listar_ativas = new JButton("Listar Ativas");
        JButton btn_top_obras = new JButton("Top Obras");
        JTextField txt_busca_autor = new JTextField(10);
        JButton btn_buscar_autor = new JButton("Buscar Autor");

        painel_botoes.add(btn_listar_ativas);
        painel_botoes.add(btn_top_obras);
        painel_botoes.add(new JLabel(" | Artista:"));
        painel_botoes.add(txt_busca_autor);
        painel_botoes.add(btn_buscar_autor);

        txt_resultados = new JTextArea();
        txt_resultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(txt_resultados);

        painel.add(painel_botoes, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        btn_listar_ativas.addActionListener(e -> atualizarLista(galleria.listarObras()));
        btn_top_obras.addActionListener(e -> atualizarLista(galleria.topObras()));
        btn_buscar_autor.addActionListener(e -> atualizarLista(galleria.buscarPorAutor(txt_busca_autor.getText())));

        return painel;
    }
}