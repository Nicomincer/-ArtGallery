package com.nicol.backend;
import java.util.Vector;

public interface IRepositorioObra{
    public void cadastrar(Obra obra);

    public void atualizar(Obra obra);

    public void remover(String titulo);

   public Vector<Obra> listar();
}
