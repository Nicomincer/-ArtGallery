package com.nicol.backend;
import com.nicol.exceptions.NotaInvalidaException;
import java.io.Serializable;

public class Avaliacao implements Serializable{
    private String usuario;
    private int nota;
    private String comentario;
    public Avaliacao(String usuario, int nota, String comentario) throws NotaInvalidaException{
        if(nota >= 0 && nota <= 10) {
            this.usuario = usuario;
            this.nota = nota;
            this.comentario = comentario;
        }
        else{
            throw new NotaInvalidaException();
        }
    }

    public int mostrarNota(){
        return this.nota;
    }
}
