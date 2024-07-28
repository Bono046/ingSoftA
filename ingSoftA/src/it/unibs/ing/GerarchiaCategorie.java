package it.unibs.ing;

import java.util.ArrayList;
import java.util.List;

public class GerarchiaCategorie {
	 private List<Categoria> categorie;
	 private String nome;

	    public GerarchiaCategorie() {
	        this.categorie = new ArrayList<>();
	        this.nome = nome;
	    }

	    public void aggiungiCategoriaRadice(Categoria categoria) {
	        this.categorie.add(categoria);
	    }

	    public List<Categoria> getCategorie() {
	        return categorie;
	    }

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public void setCategorie(List<Categoria> categorie) {
			this.categorie = categorie;
		}

}
