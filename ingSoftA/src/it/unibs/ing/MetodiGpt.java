package it.unibs.ing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetodiGpt {
	
	public void gpt() {


	//METODI CLASSE APP
	/*
	private void creaGerarchia() {
		System.out.print("Inserisci nome radice gerarchia: ");
		String nomeRadice = scanner.nextLine();
		Categoria radice = new Categoria(nomeRadice, false, null, null, null);
		dati.getGerarchie().add(radice);
	}

	private void aggiungiCategoriaNonFoglia() {
		System.out.print("Inserisci nome della categoria non foglia: ");
		String nome = scanner.nextLine();
		System.out.print("Inserisci campo caratteristico: ");
		String campo = scanner.nextLine();
		System.out.print("Inserisci dominio (valori separati da virgola): ");
		String[] dominioArray = scanner.nextLine().split(",");
		List<String> dominio = new ArrayList<>(Arrays.asList(dominioArray));
		Map<String, String> descrizioni = new HashMap<>();
		for (String valore : dominio) {
			System.out.print("Inserisci descrizione per " + valore + ": ");
			descrizioni.put(valore, scanner.nextLine());
		}
		System.out.print("Inserisci nome della gerarchia a cui aggiungere la categoria: ");
		String nomeGerarchia = scanner.nextLine();
		for (Categoria gerarchia : dati.getGerarchie()) {
			if (gerarchia.getNome().equals(nomeGerarchia)) {
				Categoria categoria = new Categoria(nome, false, campo, dominio, descrizioni);
				gerarchia.getSottocategorie().add(categoria);
				break;
			}
		}
	}

	private void stabilisciFattoreConversione() {
		System.out.print("Inserisci nome della prima categoria foglia: ");
		String nomeFoglia1 = scanner.nextLine();
		Categoria foglia1 = trovaCategoriaFoglia(nomeFoglia1);
		if (foglia1 == null) {
			System.out.println("Categoria foglia non trovata.");
			return;
		}
		System.out.print("Inserisci nome della seconda categoria foglia: ");
		String nomeFoglia2 = scanner.nextLine();
		Categoria foglia2 = trovaCategoriaFoglia(nomeFoglia2);
		if (foglia2 == null) {
			System.out.println("Categoria foglia non trovata.");
			return;
		}
		System.out.print("Inserisci valore del fattore di conversione: ");
		double valore = scanner.nextDouble();
		scanner.nextLine(); // Consuma il newline
		FattoreDiConversione fattore = new FattoreDiConversione(foglia1, foglia2, valore);
		dati.getFattoriDiConversione().add(fattore);
	}

	private Categoria trovaCategoriaFoglia(String nome) {
		for (Categoria gerarchia : dati.getGerarchie()) {
			Categoria foglia = trovaCategoriaFogliaInSottocategorie(gerarchia, nome);
			if (foglia != null) {
				return foglia;
			}
		}
		return null;
	}

	private Categoria trovaCategoriaFogliaInSottocategorie(Categoria categoria, String nome) {
		if (categoria.isFoglia() && categoria.getNome().equals(nome)) {
			return categoria;
		}
		for (Categoria sottocategoria : categoria.getSottocategorie()) {
			Categoria foglia = trovaCategoriaFogliaInSottocategorie(sottocategoria, nome);
			if (foglia != null) {
				return foglia;
			}
		}
		return null;
	}

	

	private void visualizzaGerarchie() {
		for (Categoria gerarchia : dati.getGerarchie()) {
			System.out.println("Gerarchia: " + gerarchia.getNome());
			visualizzaCategorie(gerarchia, "  ");
		}
	}

	private void visualizzaCategorie(Categoria categoria, String indent) {
		System.out.println(indent + "Categoria: " + categoria.getNome());
		if (!categoria.isFoglia()) {
			for (Categoria sottocategoria : categoria.getSottocategorie()) {
				visualizzaCategorie(sottocategoria, indent + "  ");
			}
		}
	}

	private void visualizzaFattoriDiConversione() {
		System.out.print("Inserisci nome della categoria foglia: ");
		String nomeFoglia = scanner.nextLine();
		Categoria foglia = trovaCategoriaFoglia(nomeFoglia);
		if (foglia == null) {
			System.out.println("Categoria foglia non trovata.");
			return;
		}
		for (FattoreDiConversione fattore : dati.getFattoriDiConversione()) {
			if (fattore.getFoglia1().equals(foglia)) {
				System.out.println("Da " + fattore.getFoglia1().getNome() + " a " + fattore.getFoglia2().getNome() + ": " + fattore.getValore());
			}
		}
	}


		// CLASSE CATEGORIA
		 * public class Categoria implements Serializable {
 private String nome;
 private boolean isFoglia;
 private String campoCaratteristico;
 private List<String> dominio;
 private Map<String, String> descrizioni;
 private List<Categoria> sottocategorie;

 // Costruttore
 public Categoria(String nome, boolean isFoglia, String campoCaratteristico, List<String> dominio, Map<String, String> descrizioni) {
     this.nome = nome;
     this.isFoglia = isFoglia;
     this.campoCaratteristico = campoCaratteristico;
     this.dominio = dominio;
     this.descrizioni = descrizioni;
     this.sottocategorie = new ArrayList<>();
 }

 public String getNome() {
     return nome;
 }

 public void setNome(String nome) {
     this.nome = nome;
 }

 public boolean isFoglia() {
     return isFoglia;
 }

 public void setFoglia(boolean isFoglia) {
     this.isFoglia = isFoglia;
 }

 public String getCampoCaratteristico() {
     return campoCaratteristico;
 }

 public void setCampoCaratteristico(String campoCaratteristico) {
     this.campoCaratteristico = campoCaratteristico;
 }

 public List<String> getDominio() {
     return dominio;
 }

 public void setDominio(List<String> dominio) {
     this.dominio = dominio;
 }

 public Map<String, String> getDescrizioni() {
     return descrizioni;
 }

 public void setDescrizioni(Map<String, String> descrizioni) {
     this.descrizioni = descrizioni;
 }

 public List<Categoria> getSottocategorie() {
     return sottocategorie;
 }

 public void setSottocategorie(List<Categoria> sottocategorie) {
     this.sottocategorie = sottocategorie;
 }


*/	
	}




	   // METODO CreaGerarchia con STACK
	// DA RIVEDERE PER CREARE LA NAVIGAZIONE DELL'ALBERO IN V2
	
    private void creaGerarchia(GerarchiaCategorie gerarchia) {
    /*	
    	Categoria root = gerarchia.getCategoriaCorrente();
    	Set<String> listaDominio;
    
    	
    	do {
    		root.attraversaAlbero("");
    		Categoria padre = gerarchia.getCategoriaCorrente();
    
        	listaDominio = padre.getDominio().keySet();
    		
    		System.out.println("categoria selezionata: " + padre.getNome() + padre.getDominio().toString());
	    	System.out.println("Valori campo disponibili: " + listaDominio.toString());
	    	
	  // DA AGGIUNGERE VERIFICA CORRETTEZZA
    		System.out.println("Scegli il valore del campo a cui associare una nuova categoria");
	    	String valoreDominio = scanner.nextLine();
	    		
	    	System.out.println(" 1 - aggiungi sottocategoria, 2 - aggiungi categoria foglia, 3 - torna al nodo precedente" );
	    	String choice1 = scanner.nextLine();
	    	
	    	if(choice1.equals("1")) {
	    		Categoria sottocat = creaCategoria();
	            gerarchia.getCategoriaCorrente().aggiungiSottocategoria(valoreDominio, sottocat);
	            gerarchia.vaiASottocategoria(sottocat);
	        	    
	    		
	    	} else if (choice1.equals("2")) {
	    		CategoriaFoglia foglia = creaCategoriaFoglia();
	            gerarchia.getCategoriaCorrente().aggiungiSottocategoria(valoreDominio, foglia);

	    	} else if(choice1.equals("3"))
	    		gerarchia.tornaIndietro();
	    	
	    	
	    	
	    } while(true);
   }
    
    */
    }
    

    
	


	





}