package com.nicol.backend;

public class ArteGenerativa extends Obra{
    private String algoritmo;
    private long seed;
    public ArteGenerativa(String titulo, String autor, String algoritmo, long seed){
        super(titulo, autor);
        this.algoritmo = algoritmo;
        this.seed = seed;
    }
    @Override
    public String exibirDetalhes(){
        return String.format("Titulo: %s\n Autor: %s\n Tipo: Arte Generativa\n Algoritmo: %s\n Seed: %d");
    }

}
