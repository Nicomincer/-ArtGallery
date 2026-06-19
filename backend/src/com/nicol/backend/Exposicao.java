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

    public void removerObra(String titulo) {
        for (int i = 0; i < obras.size(); i++) {
            if (obras.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                obras.remove(i);
                return;
            }
        }
        
        throw new IllegalArgumentException("A obra '" + titulo + "' não está nesta exposição.");
    }
}
