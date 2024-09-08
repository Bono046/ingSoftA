package it.unibs.ing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComprensorioGeografico {
    
	private String nome;
    private Set<String> comuni;
    
    private static ArrayList<ComprensorioGeografico> listaComprensori = new ArrayList<>();
    

    public ComprensorioGeografico(String nome) {
        this.nome = nome;
        this.comuni = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Set<String> getComuni() {
        return comuni;
    }

    public void setComuni(Set<String> comuni) {
        this.comuni = comuni;
    }

    public void aggiungiComune(String comune) {
        comuni.add(comune);
    }

    public void rimuoviComune(String comune) {
        comuni.remove(comune);
    }

    public boolean contieneComune(String comune) {
        return comuni.contains(comune);
    }
    
    

    public static ArrayList<ComprensorioGeografico> getListaComprensori() {
		return listaComprensori;
	}

	public static void setListaComprensori(ArrayList<ComprensorioGeografico> lista) {
		for(ComprensorioGeografico c : lista) {
			listaComprensori.add(c);
		}
	}

	@Override
    public String toString() {
        return nome + " -> " +
                "comuni = " + comuni;
    }

	public static void addComprensorio(ComprensorioGeografico comprensorio) {
		listaComprensori.add(comprensorio);
		
	}
}
