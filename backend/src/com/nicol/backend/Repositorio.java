package com.nicol.backend;
import com.nicol.exceptions.ObraJaCadastradaException;
import com.nicol.exceptions.ObraNaoEncontradaException;
import java.io.*; // Importe as ferramentas de arquivo do Java
import java.util.Vector;

public class Repositorio implements IRepositorioObra {
    private Vector<Obra> obras;

    // Nome do arquivo que vai ser criado na pasta do seu projeto
    private final String NOME_ARQUIVO = "banco_de_obras.dat";

    public Repositorio(){
        // 1. Quando o repositório nascer, ele tenta carregar o arquivo!
        carregarDados();

        // Se o arquivo não existir (primeira vez rodando), ele cria um Vector vazio
        if (this.obras == null) {
            this.obras = new Vector<>();
        }
    }

    // --- MÉTODOS DE PERSISTÊNCIA (A MÁGICA ACONTECE AQUI) ---

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
        // Lê o arquivo e reconstrói o Vector
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            this.obras = (Vector<Obra>) in.readObject();
        } catch (FileNotFoundException e) {
            // Ignora: o arquivo não existe na primeira vez que você rodar o programa.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados do arquivo: " + e.getMessage());
        }
    }

    // --- AGORA VOCÊ SÓ PRECISA CHAMAR O SALVARDADOS() QUANDO HOUVER ALTERAÇÕES ---

    @Override
    public void cadastrar(Obra obra){
        for(Obra o : obras) {
            if(o.getTitulo().equalsIgnoreCase(obra.getTitulo()) && o.getAutor().equalsIgnoreCase(obra.getAutor())) {
                throw new ObraJaCadastradaException("Já existe uma obra com este título e autor.");
            }
        }
        obras.add(obra);

        salvarDados(); // SALVA O ARQUIVO APÓS CADASTRAR!
    }

    @Override
    public void atualizar(Obra obra){
        for(int i = 0; i < obras.size(); i++){
            if(obras.get(i).getTitulo().equalsIgnoreCase(obra.getTitulo())){
                obras.set(i, obra);

                salvarDados(); // SALVA O ARQUIVO APÓS ATUALIZAR! (Ex: quando receber nova avaliação)
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

                salvarDados(); // SALVA O ARQUIVO APÓS DESATIVAR!
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