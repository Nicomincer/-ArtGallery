package com.nicol.exceptions;

public class NotaInvalidaException extends Exception{
    @Override
    public String getMessage(){
        return "A nota precisa estar entre 0 e 10.";
    }
}
