package com.nicol.backend;
import com.nicol.exceptions.ObraJaCadastradaException;
import com.nicol.exceptions.ObraNaoEncontradaException;
import java.io.*;
import java.util.Vector;

public class Repositorio implements IRepositorioObra {
    private Vector<Obra> obras;


    private final String NOME_ARQUIVO = "banco_de_obras.dat";

    public Repositorio(){

        carregarDados();

        if (this.obras == null) {
            this.obras = new Vector<>();
        }
    }



    private void salvarDados() {
        // Pega o Vector inteiro e "escreve" no arquivo
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            out.writeObject(this.obras);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            this.obras = (Vector<Obra>) in.readObject();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados do arquivo: " + e.getMessage());
        }
    }



    @Override
    public void cadastrar(Obra obra){
        for(Obra o : obras) {
            if(o.getTitulo().equalsIgnoreCase(obra.getTitulo()) && o.getAutor().equalsIgnoreCase(obra.getAutor())) {
                throw new ObraJaCadastradaException("Já existe uma obra com este título e autor.");
            }
        }
        obras.add(obra);

        salvarDados();
    }

    @Override
    public void atualizar(Obra obra){
        for(int i = 0; i < obras.size(); i++){
            if(obras.get(i).getTitulo().equalsIgnoreCase(obra.getTitulo())){
                obras.set(i, obra);

                salvarDados();
                return;
            }
        }
        throw new ObraNaoEncontradaException("Obra não encontrada.");
    }

    @Override
    public void remover(String titulo){
        for(Obra obra: obras){
            if(obra.getTitulo().equalsIgnoreCase(titulo)){
                obra.setAtiva(false);


                salvarDados();
                return;
            }
        }
    }

    @Override
    public Obra buscar(String titulo){
        for(Obra obra: obras){
            if(obra.getTitulo().equalsIgnoreCase(titulo)){
                return obra;
            }
        }
        return null;
    }

    @Override
    public Vector<Obra> listar(){
        return this.obras;
    }
}