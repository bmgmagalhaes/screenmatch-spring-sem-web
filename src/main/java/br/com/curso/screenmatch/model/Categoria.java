package br.com.curso.screenmatch.model;

public enum Categoria {
    ACAO ("Action", "Ação"),
    ROMANCE ("Romance","Romance"),
    COMEDIA ("Comedy","Comédia"),
    DRAMA ("Drama","Drama"),
    BIOGRAFIA ("Biography","Biografia"),
    CRIME ("Crime","Crime");

    private String categoriaOmdb;
    private String categoriaPtbr;

    Categoria(String categoriaOmdb, String categoriaPtbr) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPtbr = categoriaPtbr;
    }

    public static Categoria fromString(String texto){

        for (Categoria c: Categoria.values()){
            if (c.categoriaOmdb.equalsIgnoreCase(texto)){
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhum gênero encontrado.");
    }

    public static Categoria fromPtbr(String texto){
        for (Categoria c: Categoria.values()){
            if (c.categoriaPtbr.equalsIgnoreCase(texto)){
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhum gênero encontrado");
    }
}
