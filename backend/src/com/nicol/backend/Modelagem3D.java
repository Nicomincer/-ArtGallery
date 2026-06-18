package com.nicol.backend;


public class Modelagem3D extends Obra{
    private int numeroPoligonos;
    private String engine;
    public Modelagem3D(String titulo, String autor, int numeroPoligonos, String engine){
        super(titulo, autor);
        this.numeroPoligonos = numeroPoligonos;
        this.engine = engine;
    }
    @Override
    public String exibirDetalhes(){
        return String.format("Titulo: %s\n Autor: %s\n Tipo: Modelagem 3D\n Poligonos: %d\n Engine: %s", this.getTitulo(), this.getAutor(), this.numeroPoligonos, this.engine);
    }

}
