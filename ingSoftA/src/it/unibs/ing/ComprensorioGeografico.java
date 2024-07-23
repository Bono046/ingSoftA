package it.unibs.ing;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ComprensorioGeografico implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private String nome;
    private Set<String> comuni;

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

    @Override
    public String toString() {
        return "ComprensorioGeografico{" +
                "nome='" + nome + '\'' +
                ", comuni=" + comuni +
                '}';
    }
}
