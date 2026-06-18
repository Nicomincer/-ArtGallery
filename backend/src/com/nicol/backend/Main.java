package com.nicol.backend;

import javax.swing.SwingUtilities;
import com.nicol.view.IArtGalleryView;
import com.nicol.view.ArtGalleryGUI;

public class Main {
    public static void main(String[] args) {
        IRepositorioObra repositorio = new Repositorio();
        ArtGallery galeria = new ArtGallery(repositorio);

        SwingUtilities.invokeLater(() -> {
            IArtGalleryView tela = new ArtGalleryGUI(galeria);
            tela.exibirTela();
        });
    }
}