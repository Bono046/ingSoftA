
package it.unibs.ing;

import java.io.IOException;
import java.util.*;


public class App {
    private Dati dati;
    private Scanner scanner = new Scanner(System.in);
    private Boolean logged = false;


    public App() {
        try {
            dati = FileManager.caricaDati();
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dei dati: " + e.getMessage());
            dati = new Dati();
        }

        ComprensorioGeografico.setListaComprensori(dati.getComprensori());
        GerarchiaCategorie.setListaOggettiGerarchia(dati.getGerarchie());
        Configuratore.setListaConfiguratori(dati.getConfiguratori());
        FattoreConversione.setListaFattori(dati.getFattoriDiConversione());  
    }

    public void start() {
        mostraMenuAutenticazione();
        mostraMenuPrincipale();
    }

    private void mostraMenuAutenticazione() {
        while (!logged) {
            System.out.println("Menu Principale:");
            System.out.println("1. Primo accesso Configuratore");
            System.out.println("2. Autenticazione Configuratore");

            int scelta = getInt();

            switch (scelta) {
                case 1:
                    registraConfiguratore();
                    break;
                case 2:
                    logged = autenticaConfiguratore();
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova" + "\n");
            }
        }
    }

    private void mostraMenuPrincipale() {
        while (logged) {
            System.out.println("1. Crea Comprensorio Geografico");
            System.out.println("2. Crea Gerarchia di Categorie");
            System.out.println("3. Stabilisci Fattore di Conversione");
            System.out.println("4. Visualizza Comprensori");
            System.out.println("5. Visualizza Gerarchie");
            System.out.println("6. Visualizza Fattori di Conversione");
            System.out.println("0. Esci");
            System.out.print("Seleziona un'opzione: ");

            int scelta = getInt();

            switch (scelta) {
            // Crea Comprensorio Geografico
                case 1:
                    creaComprensorio();
                    break;
            // Crea Gerarchia di Categorie
                case 2:
                	Categoria root = creaCategoria(true, null);
                	GerarchiaCategorie g = new GerarchiaCategorie(root);
                	
                    creaGerarchia(g, g.getCategoriaRadice());
                    GerarchiaCategorie.addGerarchia(g);

                    break;  
           // Stabilisci Fattore di Conversione	
                case 3:               	
                    setFattoriConversioneGerarchia(sceltaRadice());
                    break;
           // Visualizza Comprensori         
                case 4:
                    visualizzaComprensori();
                    break;
           // Visualizza Gerarchie         
                case 5:
                    visualizzaGerarchie();
                    break;
           //Visualizza Fattori di Conversione         
                case 6:
                	VisualizzaFattoriConversione(sceltaRadice());
                    break;
           // Esci
                case 0:
                    salvaDati();
                    logged=false;
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova" + "\n");
            }
        }
    }

    
    private int getInt() {
    	int input = -1;
        try {
            input = scanner.nextInt();
        } catch (Exception e) {}
        scanner.nextLine();
        return input;
    }
    
    private double getDouble() {
    	double input = -1;
        try {
        	input = scanner.nextDouble();
        } catch (Exception e) {}
        scanner.nextLine();
        return Math.round(input * 100.0) / 100.0;
    }
    
    private String getConsistentString(String output) {		//verifica che la stringe inserita abbia almeno un carattere valido
    	System.out.println(output);
    	
    	Boolean valido = false;
    	String input = "";
    	
    	while(!valido) {
    		input = scanner.nextLine();
    		if(input.equals(" ") || input.isEmpty())
    			System.out.println("Inserimento non valido. Inserire almeno un carattere");
    		else valido = true;  		
    	}
    	return input;
    }
    
    private String getNomeCategoriaValido(Categoria radice) {		//metodo di controllo per l'unicità del nome nella stessa gerarchia
    	String nome = "";
    	Boolean nomeValido=false;
    
    	while(!nomeValido) {
    		nome = getConsistentString("Inserisci nome categoria: ");
    		nomeValido = radice.isNomeUnivoco(nome);
    		if(!nomeValido) 
    			System.out.println("Nome non valido. Riprovare");
    	}
    	return nome;
    }

    
    private void registraConfiguratore() {
        Boolean check = false;
        while (!check) {
            System.out.println("Inserisci username predefinito: ");
            String usernamePredefinito = scanner.nextLine();
            System.out.println("Inserisci password predefinita: ");
            String passwordPredefinita = scanner.nextLine();

            check = Configuratore.verificaPrimoAccesso(usernamePredefinito, passwordPredefinita);
            if (!check)
                System.out.println("Credenziali predefinite errate. Riprova" + "\n");
        }

        Boolean userValido = false;
        while (!userValido) {
            String username = getConsistentString("Inserisci nuovo username: ");
            String password = getConsistentString("Inserisci nuova password: ");

            userValido = Configuratore.userValido(username);
            if (!userValido) {
                System.out.println("Username già esistente. Riprova con un altro." + "\n");
            } else {
                Configuratore configuratore = new Configuratore(username, password);
                Configuratore.addToListaConfiguratori(configuratore);
                System.out.println("Configuratore registrato con successo." + "\n");
            }
        }
    }

    private Boolean autenticaConfiguratore() {
        System.out.println("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci password: ");
        String password = scanner.nextLine();

        Boolean check = Configuratore.loginConfiguratore(username, password);
        if (check)
            System.out.println("Autenticazione avvenuta con successo. Procedi con il seguente menu:");
        else
            System.out.println("Credenziali non valide. Riprova" + "\n");
        return check;
    }
    
    
    private void creaComprensorio() {
        String nome = getConsistentString("Inserisci nome comprensorio: ");
        ComprensorioGeografico comprensorio = new ComprensorioGeografico(nome);
        String comune;
		do {
			comune = getConsistentString("Inserisci nome del comune da aggiungere. Premi 0 per uscire: ");
			if(!comune.equals("0"))
				comprensorio.aggiungiComune(comune);
		} while(!comune.equals("0"));
        ComprensorioGeografico.addComprensorio(comprensorio);
        System.out.println("Comprensorio creato con successo." + "\n");
        
    }
    
    private void visualizzaComprensori() {
    	ArrayList<ComprensorioGeografico> listaComprensori = ComprensorioGeografico.getListaComprensori();
        if (listaComprensori.isEmpty()) {
            System.out.println("Non ci sono comprensori da visualizzare.\n");
        } else {
            for (ComprensorioGeografico comprensorio : listaComprensori) {
                System.out.println("Comprensorio: " + comprensorio.toString());
            }
            System.out.println("");
        }
    }


    private Categoria creaCategoria(boolean isRadice, Categoria radice) {
    	
    	Boolean nomeValido=true;
    	String nome = "";
    	
    	if(isRadice) {	// controllo unicità nomi radici
    		do {
    			
    			nome= getConsistentString("Inserisci nome categoria");
    			nomeValido=GerarchiaCategorie.checkNomeGerarchia(nome);
        			if(!nomeValido) 
        				System.out.println("Nome non valido. Riprovare");
        	} while(!nomeValido);
    		
    	}else 
    		nome = getNomeCategoriaValido(radice);
    	
        String campo = getConsistentString("Inserisci nome del campo: ");
        HashMap<String, String> dominio = new HashMap<>();
       
        int numValori = -1;
        // ciclo per non accettare valori diversi da numeri interi
        while(numValori <= 0) {
        	System.out.print("Quanti valori di dominio vuoi inserire? ");
        	numValori = getInt();
        	if(numValori <= 0)
        		System.out.println("scelta non valida. Riprovare");
        }	
        // ciclo per inserire i valori del dominio - la descrizione è resa facoltativa lasciando la stringa vuota - ciclo do-while per evitare duplicati
        for (int i = 0; i < numValori; i++) {
        	String valore;
        	String descrizione;
        	Boolean valido = false;
            do {
	        	valore = getConsistentString("Inserisci valore del dominio: ");
	            System.out.print("Inserisci descrizione (premere invio per lasciarla vuota): "); 
	            descrizione = scanner.nextLine();
	            if(dominio.containsKey(valore))
	            	System.out.println("Valore duplicato. Riprovare");
	            else valido = true;
            }while(!valido);
            
            dominio.put(valore, descrizione);
        }
        Categoria categoria = new Categoria(nome, campo, dominio);
        return categoria;
    }
    
    private CategoriaFoglia creaCategoriaFoglia(Categoria radice) {
    	String nome = getNomeCategoriaValido(radice); 		//metodo che verifica unicità del nome nella gerarchia

        CategoriaFoglia categoria = new CategoriaFoglia(nome);
        System.out.println("Categoria foglia creata con successo." + "\n");
        return categoria;
    }

    private void creaGerarchia(GerarchiaCategorie g, Categoria padre) {			// metodo ricorsivo per la creazioni di tutte le sottocategorie in una gerarchia
    	System.out.println("categoria selezionata: " + padre.getNome());
    	
    	ArrayList<String> listaDominio = new ArrayList<>(padre.getDominio().keySet());
        Categoria radice = g.getCategoriaRadice();
    	
    	for (String dom : listaDominio) {
            System.out.println("Creazione sottocategoria per il dominio: " + dom);
            Boolean input=false;
            while(!input) {
		    	System.out.println(" 1 - aggiungi sottocategoria, 2 - aggiungi categoria foglia");
		    	int choice1 = getInt();
		    	
		    	switch(choice1) {
		    		case 1:
			    		Categoria sottocat = creaCategoria(false, radice);
			            padre.aggiungiSottocategoria(dom, sottocat);
			    		HashMap<String, Categoria> sottocategorie = padre.getSottocategorie();
			    		creaGerarchia(g, sottocategorie.get(dom));
			    		input = true;
			    		break;
		    		case 2:
			    		CategoriaFoglia sottocatF = creaCategoriaFoglia(radice);
			            padre.aggiungiSottocategoria(dom, sottocatF);
			            g.addToListaFoglie(sottocatF);
			            input = true;
			            break;
			        default:
			        	System.out.println("input non valido. Riprovare");
		    	}
            }
    }
   }
    
    public void stampaAlbero(String indentazione, Categoria c) {
    	try {
    	   System.out.println(indentazione + "- " + c.getNome() + "\t(dominio: " + c.getDominio().keySet() + ")");
       } catch (NullPointerException e) {
    	   System.out.println(indentazione + "- " + c.getNome() + "\t(foglia)");
       }

        // Se ci sono sottocategorie, le visitiamo ricorsivamente
        if (c.getSottocategorie() != null) {
            for (Categoria sottocategoria : c.getSottocategorie().values()) {
                stampaAlbero(indentazione + "  ", sottocategoria);
            }
        }
    }
    
    private void visualizzaGerarchie() {
        if (GerarchiaCategorie.getListaRadici().isEmpty()) {
            System.out.println("Non esiste alcuna gerarchia da visualizzare.\n");
        } else {
            for (Categoria gerarchia : GerarchiaCategorie.getListaRadici()) {
                stampaAlbero("", gerarchia);
                System.out.println("");
            }
        }
    }
    

    
    
    private GerarchiaCategorie sceltaRadice() {				// modificato -> restiuisce un oggetto gerarchia, da cui è possibile ottenere la radice con getCategoriaRadice
    	
    	ArrayList<GerarchiaCategorie> listaOggettiGerarchia = GerarchiaCategorie.getListaOggettiGerarchia();
    	
    	if(GerarchiaCategorie.getListaRadici().isEmpty()) {
    		System.out.println("Non esiste alcuna gerarchia da visualizzare.\n");
    		return null;
    	} else {
	    	try {
		        System.out.println("Seleziona la gerarchia:");
		       
		        for (int i = 0; i < listaOggettiGerarchia.size(); i++) {
		            System.out.println((i + 1) + ". " + listaOggettiGerarchia.get(i).getCategoriaRadice().getNome());
		        }
		        int scelta = -1;
		        do {
		            System.out.print("\nInserisci il numero della scelta: ");
		            scelta = getInt();
		            
		            if (scelta <= 0 || scelta > listaOggettiGerarchia.size()) 
		                System.out.println("Scelta non valida. Per favore, inserisci un numero tra 1 e " + listaOggettiGerarchia.size() + ".");
		        } while (scelta <= 0 || scelta > listaOggettiGerarchia.size());
		        
		        return listaOggettiGerarchia.get(scelta - 1);
		    
	    	} catch(NullPointerException e) {
	            System.out.println("Nessuna gerarchia disponibile.");
	            return null;
	        }
    	}
    }    



    private void setFattoriConversioneGerarchia(GerarchiaCategorie gerarchiaOfferta) {
    	if(!GerarchiaCategorie.getListaRadici().isEmpty()) {
	    	ArrayList<CategoriaFoglia> foglie = gerarchiaOfferta.getListaFoglie();
	    	
	    	boolean aggiuntoFattore = false;
	    	double min = 0.5;
			double max = 2;
			double factor = -1;
			
			System.out.println("Seleziona la gerarchia di categoria foglia per la richiesta");
			GerarchiaCategorie gerarchiaRichiesta = sceltaRadice();
			
	        for (CategoriaFoglia c1 : foglie) {
	            for (CategoriaFoglia c2 : gerarchiaRichiesta.getListaFoglie()) {
	                if ( (!c1.getNome().equals(c2.getNome())) && (!FattoreConversione.esisteFattore(c1, c2)) ) {		//if(c1 diversa da s2)&(Fattore(c1,c2) non già esistente)
	                	Boolean valido= false;
	                	while(!valido) {
	                		System.out.println("Inserire il fattore di conversione da " + c1.getNome().toUpperCase() + " a " + c2.getNome().toUpperCase() + ":");
	                		
	                			factor = getDouble();
	                			if(factor < min || factor > max)
	                				System.out.println("Il fattore deve avere range = ["+min+","+max+"]");
	                			else valido = true;
	                	}
	                    FattoreConversione.addFattore(c1, c2, factor);
	                    aggiuntoFattore = true;
	                }
	            }
	        }
	        if(!aggiuntoFattore)
	        	System.out.println("Tutte le categorie foglia hanno già assegnato un fattore di conversione\n");
	    }
    }
    
    private void VisualizzaFattoriConversione(GerarchiaCategorie g) {
    	ArrayList<FattoreConversione> fattoriDaVisualizzare = new ArrayList<>();
    	
    	if(!GerarchiaCategorie.getListaRadici().isEmpty()) {   		
	    	System.out.println("inserisci il nome della categoria foglia ricercata: ");
	    	System.out.println(g.getListaFoglie().toString());
	    	
	    	String nome = scanner.nextLine();
	    	
	    	Boolean fogliaOk = false;		// variabile per stampare l'errore giusto tra foglia mancante e fattore mancante
	    	
	    	for(int i=0; i < g.getListaFoglie().size(); i++) {
	    		if(nome.equals(g.getListaFoglie().get(i).getNome())) {
	    			fattoriDaVisualizzare = FattoreConversione.trovaFattore(nome);
	    			fogliaOk = true;
	    			break;
	    		}
	    	}
	    	
	    	if(!fogliaOk) 
	    		System.out.println("Categoria foglia non trovata. Riprovare\n");
	    	else if(fattoriDaVisualizzare.isEmpty()) 
	    		System.out.println("Fattore di conversione non trovato\n");
	    	else {
	    		for (FattoreConversione f : fattoriDaVisualizzare) {
	    			System.out.println(f.toString());
	    		}
	    	}
    	}
    }


    private void salvaDati() {
        dati.setConfiguratori(Configuratore.getListaConfiguratori());
        dati.setComprensori(ComprensorioGeografico.getListaComprensori());
        dati.setGerarchie(GerarchiaCategorie.getListaOggettiGerarchia());

        dati.setFattoriDiConversione(FattoreConversione.getListaFattori());

        try {
            FileManager.salvaDati(dati);
            System.out.println("Dati salvati con successo.");
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }
}