package com.nicol.view;

import java.util.Vector;
import com.nicol.backend.Obra;

public interface IArtGalleryView {

    // Abre e torna a janela visível
    void exibirTela();

    // Exibe mensagens informativas de sucesso
    void exibirMensagem(String mensagem);

    // Exibe mensagens de erro (exceções capturadas)
    void exibirErro(String erro);

    // Atualiza o JTextArea ou tabela com a lista de obras filtradas
    void atualizarLista(Vector<Obra> listaObras);
}