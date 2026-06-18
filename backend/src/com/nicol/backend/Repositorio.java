package com.nicol.backend;

import java.util.Vector;

public class Repositorio implements IRepositorioObra{
    private Vector<Obra> obras;
    public Repositorio(){
        this.obras = new Vector<>();
    }

    public Obra buscar(String titulo){
        for(Obra obra: obras){
            if(obra.)
        }
    }

    public void cadastrar(Obra obra){
        obras.add(obra);
    }

    public void atualizar(Obra obra){}

    public void remover(String titulo){
        for(Obra obra: obras){
            if(obra.getTitulo().equals(titulo)){
                obra.setAtiva(false);
            }
        }
    }

    public Vector<Obra> listar(){}
}
