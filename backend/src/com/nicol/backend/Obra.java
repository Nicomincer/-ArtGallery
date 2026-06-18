package com.nicol.backend;
import com.nicol.backend.Avaliacao;
import java.util.Vector;

public abstract class Obra {
    private Vector<Avaliacao> avaliacoes;
    private boolean ativa;
    private String titulo;
    private String autor;

    public Obra(String titulo, String autor){
        this.titulo = titulo;
        this.autor = autor;
        this.ativa = true;
        this.avaliacoes = new Vector<>();

    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getAutor(){
        return this.autor;
    }

    public boolean isAtiva(){
        return this.ativa;
    }

    public void setAtiva(boolean ativa){
        this.ativa = ativa;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao){
            avaliacoes.add(avaliacao);
    }

    public double mediaAvaliacoes(){
        int soma = 0;
        int qtd_avaliacoes=0;
        double media;
        for(Avaliacao avaliacao : avaliacoes){
            soma += avaliacao.mostrarNota();
            qtd_avaliacoes++;
        }
        if(qtd_avaliacoes != 0) {
            media = (double) soma / qtd_avaliacoes;
            return media;
        }else{
            return 0.0;
        }
    }
    public abstract String exibirDetalhes();

}
