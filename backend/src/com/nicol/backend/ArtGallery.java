package com.nicol.backend;

import java.util.Vector;

public class ArtGallery implements IArtGallery{

    private IRepositorioObra repositorio;
    private Vector<Exposicao> exposicoes;

    public ArtGallery(IRepositorioObra repositorio){
        this.repositorio = repositorio;
        this.exposicoes = new Vector<>();
    }

    public void publicarObra(Obra obra){

        repositorio.cadastrar(obra);

    }

    public void removerObra(String titulo){
        Obra obra = repositorio.buscar(titulo);
        if(obra != null && obra.isAtiva()){
            obra.setAtiva(false);
            repositorio.remover(titulo);

        }
    }

    public void avaliarObra(String titulo, Avaliacao avaliacao) {
        Obra obra = this.repositorio.buscar(titulo);

        // 1. Validando se ela existe
        if (obra == null) {
            throw new IllegalArgumentException("A obra '" + titulo + "' não existe no sistema!");
        }

        // 2. Validando se ela está ativa
        if (!obra.isAtiva()) {
            throw new IllegalStateException("A obra '" + titulo + "' está desativada e não pode ser avaliada!");
        }

        // 3. Se passou pelas validações, adiciona a avaliação com segurança
        obra.adicionarAvaliacao(avaliacao);
    }

    public Vector<Obra> listarObras(){
        Vector<Obra> obras_ativas = new Vector<>();
        for(Obra obra: repositorio.listar()){
            if(obra.isAtiva()){
                obras_ativas.add(obra);
            }
        }

        return obras_ativas;


    }

    public Vector<Obra> buscarPorAutor(String autor){
        Vector<Obra> obra_do_autor_x = new Vector<>();
        for(Obra obra: repositorio.listar()){
            if(obra.getAutor().equals(autor) && obra.isAtiva()){
                obra_do_autor_x.add(obra);
            }
        }

        return obra_do_autor_x;
    }

    public Vector<Obra> topObras(){
        Vector<Obra> obras_ordenadas = this.listarObras();

        // Ordena da maior média de avaliação para a menor
        obras_ordenadas.sort((obra1, obra2) ->
                Double.compare(obra2.mediaAvaliacoes(), obra1.mediaAvaliacoes())
        );

        return obras_ordenadas;
    }

    public Vector<Obra> obrasExpostas(String nomeExposicao){
        for (Exposicao exposicao : this.exposicoes) {
            if (exposicao.getNome().equals(nomeExposicao)) {
                return exposicao.listarObras();
            }
        }

        return new Vector<>();

    }
    public Obra buscarPorTitulo(String titulo) {
        Obra obra = repositorio.buscar(titulo);


        if (obra != null && obra.isAtiva()) {
            return obra;
        }


        return null;
    }

    public void adicionarExposicao(Exposicao exposicao) {
        this.exposicoes.add(exposicao);
    }

    public void adicionarObraNaExposicao(String nomeExposicao, String tituloObra) {
        // 1. Busca a obra usando o método que já criamos e filtra as inativas
        Obra obra_encontrada = this.buscarPorTitulo(tituloObra);

        if (obra_encontrada == null) {
            throw new IllegalArgumentException("Não foi possível adicionar: A obra não existe ou está desativada.");
        }

        // 2. Busca a exposição pelo nome
        for (Exposicao exposicao : this.exposicoes) {
            if (exposicao.getNome().equalsIgnoreCase(nomeExposicao)) {
                // 3. Adiciona a obra na exposição
                exposicao.adicionarObra(obra_encontrada);
                return;
            }
        }

        // 4. Se o loop terminar sem achar a exposição:
        throw new IllegalArgumentException("A exposição '" + nomeExposicao + "' não foi encontrada.");
    }

    public void removerObraDaExposicao(String nomeExposicao, String tituloObra) {
        // Busca a exposição pelo nome
        for (Exposicao exposicao : this.exposicoes) {
            if (exposicao.getNome().equalsIgnoreCase(nomeExposicao)) {
                // Se achar a exposição, manda ela remover a obra
                exposicao.removerObra(tituloObra);
                return;
            }
        }

        // Se não achar a exposição:
        throw new IllegalArgumentException("A exposição '" + nomeExposicao + "' não foi encontrada.");
    }

    public void ativarObra(String titulo) {

        Obra obra = this.repositorio.buscar(titulo);


        if (obra == null) {
            throw new IllegalArgumentException("A obra '" + titulo + "' não existe no sistema!");
        }


        if (obra.isAtiva()) {
            throw new IllegalStateException("A obra '" + titulo + "' já está ativa no sistema!");
        }


        obra.setAtiva(true);


        repositorio.atualizar(obra);
    }
}
