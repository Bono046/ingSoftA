package it.unibs.ing;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Categoria implements Serializable {
    private String nome;
    private String campo;
    private HashMap<String, String> dominio;
    private HashMap<String, Categoria> sottocategorie;


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

    public void aggiungiSottocategoria(String valore_dominio, Categoria sottocategoria) {
        if (dominio.containsKey(valore_dominio)) {
                sottocategorie.put(valore_dominio, sottocategoria);
            } else {
                throw new IllegalArgumentException("Il nome della sottocategoria deve essere unico all'interno della gerarchia.");
            }
    }

    public HashMap<String, Categoria> getSottocategoria() {
        return sottocategorie;
    }

    public boolean isFoglia() {
        return false;
    }

    public void visualizzaGerarchia() {
        System.out.println("Categoria: " + nome);
        for (String valore : dominio.keySet()) {
            System.out.println("  Valore del dominio: " + valore + " (" + dominio.get(valore) + ")");
            if (sottocategorie.containsKey(valore)) {
                sottocategorie.get(valore).visualizzaGerarchia();
            }
        }
    }
    
    

    @Override
    public String toString() {
        return nome + "[campo=" + campo + ", dominio=" + dominio + ", sottocategorie="
                + sottocategorie + "]";
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