
package it.unibs.ing;

import java.io.IOException;
import java.util.*;

//import javax.swing.plaf.synth.SynthSpinnerUI;

public class App {
    private Dati dati;
    private Scanner scanner = new Scanner(System.in);
    private Boolean loggedConfig = false;
    private Boolean loggedFruitore = false;

    
    //liste salvate e caricate su file
    private ArrayList<ComprensorioGeografico> listaComprensori = new ArrayList<>();
    private ArrayList<GerarchiaCategorie> listaOggettiGerarchia = new ArrayList<>();

    //liste derivate da oggetti salvati su file (non memorizzate direttamente)
    private ArrayList<CategoriaFoglia> listaFoglieTotali = new ArrayList<>();		//lista di tutte le foglie di tutte le gerarchie - serve per assegnare i fattori di conversione a gerarchie diverse
    private ArrayList<Categoria> listaRadici = new ArrayList<>();					//lista di utilità in vari metodi
    //liste resettate ad ogni gerarchia
    private ArrayList<CategoriaFoglia> listaFoglie = new ArrayList<>();				//lista che va a settare la corrispondente nell'oggetto Grarchia - resettata ad ogni g
    private ArrayList<String> listaNomi = new ArrayList<>(); 						//lista per verificare unicità nomi categorie nelle gerarchie
   

    public App() {
        try {
            dati = FileManager.caricaDati();
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dei dati: " + e.getMessage());
            dati = new Dati();
        }

        listaComprensori = dati.getComprensori();
        listaOggettiGerarchia = dati.getGerarchie();
        Configuratore.setListaConfiguratori(dati.getConfiguratori());
        FattoreConversione.setListaFattori(dati.getFattoriDiConversione());  //tutte le operazioni della lista vengono svolte nella classe FattoreConversione: si sarebbe reso necessario passare ogni volta la lista ad ogni metodo. Cosi si fa un set e un get per caricare e salvare i dati
        Fruitore.setListaFruitori(dati.getFruitori());

        
        // ciclo di inizializzazione delle liste radici e foglieTotali, non salvate su file direttamente ma facilmente derivabili dall'oggetto Gerarchia
        for(GerarchiaCategorie g: listaOggettiGerarchia) {
    		listaRadici.add(g.getCategoriaRadice());
    		listaFoglieTotali.addAll(g.getListaFoglie());
    	}
    }

    public void start() {
        mostraMenuAutenticazione();
        if(loggedConfig)
        	mostraMenuPrincipaleConfig();
        if(loggedFruitore)
        	mostraMenuPrincipaleFruitore();
    }

    private void mostraMenuAutenticazione() {
        while (!loggedConfig && !loggedFruitore) {
            System.out.println("Menu Principale:");
            System.out.println("1. Primo accesso Configuratore");
            System.out.println("2. Autenticazione Configuratore");
            System.out.println("3. Primo accesso fruitore");	
            System.out.println("4. Autenticazione fruitore");
            int scelta = getInt();

            switch (scelta) {
                case 1:
                    registraConfiguratore();
                    break;
                case 2:
                    loggedConfig = autenticaConfiguratore();
                    break;
                case 3:
                	registraFruitore();
                	break;
                case 4:
                	loggedFruitore = autenticaFruitore();
                	break;
              
                default:
                    System.out.println("Opzione non valida. Riprova" + "\n");
            }
        }
    }

    private void mostraMenuPrincipaleConfig() {
        while (loggedConfig) {
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
                	listaFoglie.clear();
                	listaNomi.clear();
                	
                	//ciclo per verificare l'unicità del nome per ogni gerarchia
                	Categoria root = null;
                	Boolean nomeValido=true;
                	do {
                		nomeValido=true;
                		root = creaCategoria();
                		
                		for(Categoria c: listaRadici) {
                			if(c.getNome().equals(root.getNome())){
                				System.out.println("Attenzione - nome non valido. Riprovare");
                				nomeValido=false;                	
                			}
                		}
                	} while(!nomeValido);
                
                    creaGerarchia(root);
                    
                    GerarchiaCategorie g = new GerarchiaCategorie(root);
                    
                    listaRadici.add(root);
                    listaOggettiGerarchia.add(g);
                    g.setListaFoglie(listaFoglie);

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
                    loggedConfig=false;
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova" + "\n");
            }
        }
    }

    private void mostraMenuPrincipaleFruitore() {
    	while (loggedFruitore) {
            System.out.println("3. Esplora gerarchie");
            System.out.println("4. ");
            System.out.println("5. ");
            System.out.println("0. Esci");
            System.out.print("Seleziona un'opzione: ");

            int scelta = getInt();

            switch (scelta) {
            // Esplora gerarchie
                case 3:
                    
                	GerarchiaCategorie g = sceltaRadice();
                	g.setCategoriaCorrente();
                	
                	esploraGerarchia(g);

                    break;
            // 
                case 4:

                    break;  
           //    
                case 5 : 

                	break;
           // Esci
                case 0:
                    salvaDati();
                    loggedFruitore=false;
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
    
    
    /*
     * Precondizioni: riceve una stringa in input
     * Postcondizioni: restituisce una stringe che abbia almeno un carattere valido
     */
    private String getConsistentString(String output) {		
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
    
    //Postcondizioni: assegna un nome alla categoria solo se unico all'interno della gerarchia di appartenenza
    private String getNome() {		
    	String nome = "";
    	Boolean nomeValido=false;
    	while(!nomeValido) {
    		nome = getConsistentString("Inserisci nome categoria: ");
    		
    		if(listaNomi.contains(nome))
    			System.out.println("Nome non valido. Riprovare");
    		else {
    			nomeValido=true;
    			listaNomi.add(nome);
    			return nome;
    		}
    	}
    	return nome;
    }

  //Postcondizioni: crea un configuratore con username non gia presente nell'elenco
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

    /*
     * Precondizioni: esiste un configuratore
     * Postcondizioni: manda al menu successivo
     */
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
    
    
    private void registraFruitore() {
    	//scelta comprensorio
    	try {
    		
    		System.out.println("Seleziona il comprensorio di appartenenza: ");
	        for (int i = 0; i < listaComprensori.size(); i++) {
	            System.out.println((i + 1) + ". " + listaComprensori.get(i).getNome());
	        }
	        int scelta = -1;
	        do {
	            System.out.print("\nInserisci il numero della scelta: ");
	            scelta = getInt();
	            if (scelta <= 0 || scelta > listaComprensori.size()) 
	                System.out.println("Scelta non valida. Per favore, inserisci un numero tra 1 e " + listaComprensori.size() + ".");
	        } while (scelta <= 0 || scelta > listaOggettiGerarchia.size());
	
	        ComprensorioGeografico comprensorio = listaComprensori.get(scelta - 1);
	       
	    	//registrazione credenziali
			Boolean userValido = false;
		    while (!userValido) {
		        String username = getConsistentString("Inserisci nuovo username: ");
		        String password = getConsistentString("Inserisci nuova password: ");
		        String mail = getConsistentString("Inserisci mail: ");
		
		        userValido = Fruitore.userValido(username);
		        if (!userValido) {
		            System.out.println("Username già esistente. Riprova con un altro." + "\n");
		        } else {
		            Fruitore fruitore = new Fruitore(username, password, comprensorio, mail);
		            Fruitore.addToListaConfiguratori(fruitore);
		            System.out.println("Fruitore registrato con successo." + "\n");
		         }
		     }
    	}catch (NullPointerException e) { System.out.println("Nessun comprensorio disponibile."); }
    }
    
    private Boolean autenticaFruitore() {
        System.out.println("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci password: ");
        String password = scanner.nextLine();

        Boolean check = Fruitore.loginFruitore(username, password);
        if (check) {
            System.out.println("Autenticazione avvenuta con successo. Procedi con il seguente menu:");
        	loggedFruitore= true;
        }else
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
        listaComprensori.add(comprensorio);
        System.out.println("Comprensorio creato con successo." + "\n");
    }
    
    private void visualizzaComprensori() {
        if (listaComprensori.isEmpty()) {
            System.out.println("Non ci sono comprensori da visualizzare.\n");
        } else {
            for (ComprensorioGeografico comprensorio : listaComprensori) {
                System.out.println("Comprensorio: " + comprensorio.toString());
            }
            System.out.println("");
        }
    }


    private Categoria creaCategoria() {
    	
    	String nome = getNome(); 	//metodo che verifica unicità del nome nella gerarchia
        String campo = getConsistentString("Inserisci nome del campo: ");
        HashMap<String, String> dominio = new HashMap<>();
       
        int numValori = -1;
        // ciclo per non accettare valori diversi da numeri interi
        while (numValori <= 0) {
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
            } while(!valido);
            
            dominio.put(valore, descrizione);
        }
        Categoria categoria = new Categoria(nome, campo, dominio);
        return categoria;
    }
    
    private CategoriaFoglia creaCategoriaFoglia() {
    	String nome = getNome(); 		//metodo che verifica unicità del nome nella gerarchia

        CategoriaFoglia categoria = new CategoriaFoglia(nome);
        System.out.println("Categoria foglia creata con successo." + "\n");
        return categoria;
    }

    /**
     * metodo ricorsivo per la creazioni di tutte le sottocategorie in una gerarchia
     * Precondizione: deve esistere un oggetto Categoria non nullo passato come parametro.
     * Postcondizione: la gerarchia deve essere aggiunta correttamente alla lista delle gerarchie e le foglie alla lista delle categoria foglia.
     * @param padre
     */
    private void creaGerarchia(Categoria padre) {			
    	System.out.println("categoria selezionata: " + padre.getNome());
    	
    	ArrayList<String> listaDominio = new ArrayList<>(padre.getDominio().keySet());
        
    	for (String dom : listaDominio) {
            System.out.println("Creazione sottocategoria per il dominio: " + dom);
            Boolean input=false;
            while(!input) {
		    	System.out.println(" 1 - aggiungi sottocategoria, 2 - aggiungi categoria foglia");
		    	int choice1 = getInt();
		    	
		    	switch(choice1) {
		    		case 1:
			    		Categoria sottocat = creaCategoria();
			            padre.aggiungiSottocategoria(dom, sottocat);
			    		HashMap<String, Categoria> sottocategorie = padre.getSottocategorie();
			    		creaGerarchia(sottocategorie.get(dom));
			    		input = true;
			    		break;
		    		case 2:
			    		CategoriaFoglia sottocatF = creaCategoriaFoglia();
			            padre.aggiungiSottocategoria(dom, sottocatF);
			            listaFoglie.add(sottocatF);
			            listaFoglieTotali.add(sottocatF);
			            input = true;
			            break;
			        default:
			        	System.out.println("input non valido. Riprovare");
		    	}
            }
    }
   }
    /*
     * Precondizioni: esiste almeno una gerarchia
     */
    private void visualizzaGerarchie() {
        if (listaRadici.isEmpty()) {
            System.out.println("Non esiste alcuna gerarchia da visualizzare.\n");
        } else {
            for (Categoria gerarchia : listaRadici) {
                gerarchia.stampaAlbero("");
                System.out.println("\n");
            }
        }
    }
    
    /**
     * restiuisce un oggetto gerarchia, da cui è possibile ottenere la radice con getCategoriaRadice
     * Precondizione: deve esistere almeno un oggetto Categoria non nullo salvato in listaRadici
     */
    private GerarchiaCategorie sceltaRadice() {				
    	if(listaRadici.isEmpty()) {
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


/**
 * Precondizione: esiste non nullo almeno un oggetto GerarchiaCategorie
 * Postcondizione: ogni coppia distinta di categorie foglia avrà un fattore di conversione compreso tra 0.5 e 2
 * @param gerarchia
 */
    private void setFattoriConversioneGerarchia(GerarchiaCategorie gerarchia) {
    	if(!listaRadici.isEmpty()) {
	    	ArrayList<CategoriaFoglia> foglie = gerarchia.getListaFoglie();
	    	
	    	double min = 0.5;
			double max = 2;
			double factor = -1;
			
	        for (CategoriaFoglia c1 : foglie) {
	            for (CategoriaFoglia c2 : listaFoglieTotali) {
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
	                }
	            }
	        }
	        // NUMERO FAT.CONV. = N.FOGLIE * (N.FOGLIE-1) -> COPPIE ORDINATE DISTINTE ==> POSSIBILE INVARIANTE DI CLASSSE
	        if(FattoreConversione.getListaFattori().size() == (listaFoglieTotali.size() * (listaFoglieTotali.size()-1)))
	        	System.out.println("Tutte le categorie foglia hanno già assegnato un fattore di conversione\n");
	    }
    }
    
    /**
     * Precondizione: esiste almeno un fattore di conversione
     * @param g
     */
    private void VisualizzaFattoriConversione(GerarchiaCategorie g) {
    	ArrayList<FattoreConversione> fattoriDaVisualizzare = new ArrayList<>();
    	
    	if(!listaRadici.isEmpty()) {   		
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
	    		System.out.println("");
	    	}
    	}
    }

    
    private Boolean esploraGerarchia(GerarchiaCategorie g) {
    	
    	Boolean ricerca = true;
    	do{ 
	    	Categoria c = g.getCategoriaCorrente();
	    	System.out.println("\nCategoria corrente:"+ c.getNome());
	    	
	    	Boolean foglia = false;
	    	for(CategoriaFoglia f: g.getListaFoglie()) {
	    		if(f.getNome().equals(c.getNome())) {
	    		System.out.println("Categoria foglia - Premi 0 per tornare indietro o digita 'exit' per uscire");
	    		foglia = true;}
	    	}
	    	if(!foglia){
		    	System.out.println("Valori disponibili:");
	    		c.getSottocategorie().forEach((k,v) -> System.out.println(k.toUpperCase() + "->" + v.getNome()));
		    	System.out.println("Inserisci il valore del campo per navigare nella sottocategoria (0 per tornare indietro - 'exit per uscire)");
	    	}
		    	String valore = scanner.nextLine();
	
		    	
		    	if(valore.equals("exit")) {
		    		ricerca = false;
		    		return ricerca;
		    		
		    	}else if(valore.equals("0")) 
		    		g.tornaIndietro();
		    	 else if(c.getSottocategorie().keySet().contains(valore)) 
		   			g.vaiASottocategoria(c.getSottocategorie().get(valore));
		    	else 
		    		System.out.println("Valore non valido. riprovare");
		    		
		    	ricerca = esploraGerarchia(g);
    	}while(ricerca);
    	return false;
    	

    	
    }
    
    
    // salva i dati inseriti in un file json 
    private void salvaDati() {
        dati.setConfiguratori(Configuratore.getListaConfiguratori());
        dati.setComprensori(listaComprensori);
        dati.setGerarchie(listaOggettiGerarchia);
        dati.setFattoriDiConversione(FattoreConversione.getListaFattori());
        dati.setFruitori(Fruitore.getListaFruitori());

        
        try {
            FileManager.salvaDati(dati);
            System.out.println("Dati salvati con successo.");
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }
}