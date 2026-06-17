package com.nicol.backend;

public class Avaliacao {
    private String usuario;
    private int nota;
    private String comentario;
    public Avaliacao(String usuario, int nota, String comentario){
        this.usuario = usuario;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int mostrarNota(){
        if(nota >= 0 && nota <= 10){
            return nota;
        }
        else{
            throw new NotaInvalidaException("Nota invalida, tente novamente.");
        }
    }
}
