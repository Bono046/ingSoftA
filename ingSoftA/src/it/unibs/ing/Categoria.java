package it.unibs.ing;

import java.util.HashMap;


class Categoria  {
    protected String nome;
    private String campo;
    private HashMap<String, String> dominio;
    protected HashMap<String, Categoria> sottocategorie;


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

    public HashMap<String, Categoria> getSottocategorie() {
        return sottocategorie;
    }

    public boolean isFoglia() {
        return false;
    }    

    @Override
    public String toString() {
        return nome + "campo: " + campo + ", dominio: " + dominio + ", sottocategorie: "
                + sottocategorie;
    	}
	


	public boolean isNomeUnivoco(String nomeSottocategoria) {
	    if (this.nome.equals(nomeSottocategoria)) {
	        return false;  
	    }
	    if(sottocategorie != null) {
		    for (Categoria sottocategoria : sottocategorie.values()) {
		        if (!sottocategoria.isNomeUnivoco(nomeSottocategoria)) {
		            return false; 
		        }
		    }}
	    return true;
	}

}


class CategoriaFoglia extends Categoria {
    public CategoriaFoglia(String nome) {
        super(nome, null, null);
        this.sottocategorie = null;
    }

    
	@Override
	public boolean isFoglia() {
	    return true;
	}
	
	
	// Override del metodo per evitare l'aggiunta di sottocategorie
	@Override
	public void aggiungiSottocategoria(String nome, Categoria categoria) {
	    throw new UnsupportedOperationException("Le foglie non possono avere sottocategorie.");
	}
	
	@Override
	public String getCampo() {
	    return null; // Campo non applicabile per le foglie
	}
	
	@Override
	public HashMap<String, String> getDominio() {
	    return null; // Dominio non applicabile per le foglie
	}
	
    @Override
    public String toString() {
        return nome;
    }
	
	
}
