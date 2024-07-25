package it.unibs.ing;

//File: Categoria.java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;


class Categoria {
    private String nome;		// nome categoria
    private String campo;		// nome campo
    private HashMap<String, String> dominio; 	//dominio-descrizione
    private HashMap<String, Categoria> sottocategorie;		//dominio - categoria

    public Categoria(String nome, String campo, HashMap<String, String> dominio) {
        this.nome = nome;
        this.campo = campo;
        this.dominio = dominio;
        this.sottocategorie = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCampo() {
        return campo;
    }

    public HashMap<String, String> getDominio() {
        return dominio;
    }

    public void aggiungiSottocategoria(String valore, Categoria sottocategoria) {
        if (dominio.containsKey(valore)) {
            sottocategorie.put(valore, sottocategoria);
        } else {
            throw new IllegalArgumentException("Valore non presente nel dominio.");
        }
    }

    public Categoria getSottocategoria(String valore) {
        return sottocategorie.get(valore);
    }

    public boolean isFoglia() {
        return false;
    }
}

class CategoriaFoglia extends Categoria {
    public CategoriaFoglia(String nome) {
        super(nome, null, null);
    }

    @Override
    public boolean isFoglia() {
        return true;
    }




}
