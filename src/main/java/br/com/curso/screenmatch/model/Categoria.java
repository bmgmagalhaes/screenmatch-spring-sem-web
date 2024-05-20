package br.com.curso.screenmatch.model;

public enum Categoria {
    ACAO ("Action"),
    ROMANCE ("Romance"),
    COMEDIA ("Comedy"),
    DRAMA ("Drama"),
    CRIME ("Crime");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String texto){

        for (Categoria c: Categoria.values()){
            if (c.categoriaOmdb.equalsIgnoreCase(texto)){
                System.out.println("*********************************************************************");
                System.out.println("Imprimindo a categoria depois do Enum e dentro do IF");
                System.out.println(c);
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhum gênero encontrado.");
    }
}
