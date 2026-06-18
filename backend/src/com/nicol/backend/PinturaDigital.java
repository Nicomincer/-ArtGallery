package com.nicol.backend;

public class PinturaDigital extends Obra{
    private String resolucao;
    private String softwareUtilizado;

    public PinturaDigital(String titulo, String autor, String resolucao, String softwareUtilizado){
        super(titulo, autor);
        this.resolucao = resolucao;
        this.softwareUtilizado = softwareUtilizado;
    }

    @Override
    public String exibirDetalhes(){
        return String.format("Titulo: %s\n Autor: %s\n Tipo: Pintura Digital\n Resolução: %s\n Software: %s", this.getTitulo(), this.getAutor(), this.resolucao, this.softwareUtilizado);

    }
}
