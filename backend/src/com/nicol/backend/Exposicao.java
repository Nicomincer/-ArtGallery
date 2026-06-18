package com.nicol.backend;
import java.io.Serializable;
import java.util.Vector;

public class Exposicao implements Serializable{
    private String nome;
    private Vector<Obra> obras;

    public Exposicao(String nome) {
        this.nome = nome;
        this.obras = new Vector<>();

    }

    public String getNome(){
        return this.nome;
    }

    public void adicionarObra(Obra obra){
        this.obras.add(obra);
    }

    public Vector<Obra> listarObras(){
        return this.obras;
    }
}
