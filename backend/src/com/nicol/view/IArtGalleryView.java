package com.nicol.view;

import java.util.Vector;
import com.nicol.backend.Obra;

public interface IArtGalleryView {


    void exibirTela();


    void exibirMensagem(String mensagem);


    void exibirErro(String erro);

    void atualizarLista(Vector<Obra> listaObras);
}