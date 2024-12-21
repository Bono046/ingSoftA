

package it.unibs.ing;

import java.io.IOException;
import java.util.*;

//

public class App {
    private Dati dati;
    private Scanner scanner = new Scanner(System.in);
    private Boolean loggedAsConfig = false;
    private Boolean loggedAsFruitore = false;
//dfwc

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
        Fruitore.setListaFruitori(dati.getFruitori());
        Proposta.setListaProposte(dati.getProposte());
    }

    public void start() {
    	 mostraMenuAutenticazione();
    }

    private void mostraMenuAutenticazione() {
        while (!loggedAsConfig && !loggedAsFruitore) {
            System.out.println("Menu Principale:");
            System.out.println("1. Primo accesso Configuratore");
            System.out.println("2. Autenticazione Configuratore");
            System.out.println("3. Primo accesso fruitore");	
            System.out.println("4. Autenticazione fruitore");
            System.out.println("0. Esci dal programma");
            int scelta = getInt();

            switch (scelta) {
                case 1:
                    registraConfiguratore();
                    break;
                case 2:
                	loggedAsConfig = autenticaConfiguratore();
                    mostraMenuPrincipaleConfig();
                    break;
                case 3:
                	registraFruitore();
                	break;
                case 4:
                    Fruitore userLogged = autenticaFruitore();
                	if(userLogged != null){
                		loggedAsFruitore = true;
                	 	mostraMenuPrincipaleFruitore(userLogged.getUsername());
                	}	
                	break;
                case 0: 
                	salvaDati();
                	System.out.println("Arrivederci!\n");
                	return;
                default:
                    System.out.println("Opzione non valida. Riprova" + "\n");
            }
        }
    }

    private void mostraMenuPrincipaleConfig() {
        while (loggedAsConfig) {
            System.out.println("1. Crea Comprensorio Geografico");
            System.out.println("2. Crea Gerarchia di Categorie");
            System.out.println("3. Stabilisci Fattore di Conversione");
            System.out.println("4. Visualizza Comprensori");
            System.out.println("5. Visualizza Gerarchie");
            System.out.println("6. Visualizza Fattori di Conversione");
            System.out.println("7. Visualizza proposte relative ad una categoria");
            System.out.println("0. Esci");
            System.out.print("Seleziona un'opzione: ");

            int scelta = getInt();

            switch (scelta) {
                case 1:
                    creaComprensorio();
                    break;
                case 2:
                	Categoria root = creaCategoria(true, null);
                	GerarchiaCategorie g = new GerarchiaCategorie(root);
                    creaGerarchia(g, g.getCategoriaRadice());
                    GerarchiaCategorie.addGerarchia(g);
                    salvaDati();
                    break;  	
                case 3:               	
                    setFattoriConversioneGerarchia(sceltaRadice());
                    break;        
                case 4:
                    visualizzaComprensori();
                    break;        
                case 5:
                    visualizzaGerarchie();
                    break;        
                case 6:
                	VisualizzaFattoriConversione(sceltaRadice());
                    break;
                case 7:
                	visualizzaProposteByFoglia();
                	break;
                case 0:
                    salvaDati();
                    loggedAsConfig=false;
                    System.out.println("Arrivederci! \n");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova" + "\n");
            }
        }
    }

    
  

	private void mostraMenuPrincipaleFruitore(String user) {
    	while (loggedAsFruitore) {
            System.out.println("1. Esplora gerarchie");
            System.out.println("2. Formula proposta scambio");
            System.out.println("3. Visualizza proposte di scambio");
            System.out.println("4. Ritira proposta di scambio");
            System.out.println("0. Esci");
            System.out.print("Seleziona un'opzione: ");

            int scelta = getInt();

            switch (scelta) {
                case 1:
                	GerarchiaCategorie g = sceltaRadice();
                	g.setCategoriaCorrente();
                	esploraGerarchia(g);
                    break;
                case 2: 
                	creaProposta(user);
                	break;
                case 3:
                	visualizzaProposteByUser(user);
                	break;
                case 4:
                	ritiraProposta(user);
                	break;
                case 0:
                    salvaDati();
                    loggedAsFruitore=false;
                    System.out.println("Arrivederci!\n");
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
    
    private String getNomeCategoriaValido(Categoria radice) {		
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
            }
        }
        salvaDati();
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
    
    
    private void registraFruitore() {
    	//scelta comprensorio
    	
    	try {
    		ArrayList<ComprensorioGeografico> listaComprensori = ComprensorioGeografico.getListaComprensori();
    		
    		if(listaComprensori.isEmpty()) 
	        	throw new IllegalArgumentException();
    		
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
	        } while (scelta <= 0 || scelta > listaComprensori.size());
	
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
		            salvaDati();
		            
		         }
		     }
    	} catch (Exception e) { 
    		System.out.println("Nessun comprensorio disponibile.\n"); 
    		}
    }
    
    
    private Fruitore autenticaFruitore() {
        System.out.println("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci password: ");
        String password = scanner.nextLine();

        Fruitore user = Fruitore.loginFruitore(username, password);
        if (user != null) {
            System.out.println("Autenticazione avvenuta con successo. Procedi con il seguente menu:");
            return user;
        }else
            System.out.println("Credenziali non valide. Riprova" + "\n");
        return null;
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
        salvaDati();
        
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
    	
    	if(isRadice) {	
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
    	String nome = getNomeCategoriaValido(radice); 		

        CategoriaFoglia categoria = new CategoriaFoglia(nome);
        System.out.println("Categoria foglia creata con successo." + "\n");
        return categoria;
    }

    private void creaGerarchia(GerarchiaCategorie g, Categoria padre) {			
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
        	
            StringBuilder result = new StringBuilder(indentazione + "- " + c.getNome() + " (" + c.getCampo() + "= [");
            List<String> coppie = new ArrayList<>();
            for (Map.Entry<String, String> dominio : c.getDominio().entrySet()) {
                String chiave = dominio.getKey();
                String valore = dominio.getValue();
                
                if (valore.isEmpty()) {
                    coppie.add(chiave);
                } else {
                    coppie.add(chiave + ": " + valore);
                }
            }

            result.append(String.join(", ", coppie));
            result.append("])");
            System.out.println(result.toString());

        } catch (NullPointerException e) {
            System.out.println(indentazione + "- " + c.getNome());
        }

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
    	salvaDati();
    }
    
    private void VisualizzaFattoriConversione(GerarchiaCategorie g) {
    	ArrayList<FattoreConversione> fattoriDaVisualizzare = new ArrayList<>();
    	
    	if(!GerarchiaCategorie.getListaRadici().isEmpty()) {   		
	    	System.out.println("inserisci il nome della categoria foglia ricercata: ");
	    	System.out.println(g.getListaFoglie().toString());
	    	
	    	String nome = scanner.nextLine();
	    	
	    	Boolean fogliaOk = false;		
	    	
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

    
 private Boolean esploraGerarchia(GerarchiaCategorie g) {
    	
    	Boolean ricerca = true;
    	do{ 
	    	Categoria c = g.getCategoriaCorrente();
	    	System.out.println("\nCategoria corrente: "+ c.getNome());
	    	
	    	Boolean foglia = false;
	    	for(CategoriaFoglia f: g.getListaFoglie()) {
	    		if(f.getNome().equals(c.getNome())) {
	    		System.out.println("Categoria foglia - Premi 0 per tornare indietro nella gerarchia o digita 'exit' per uscire");
	    		foglia = true;}
	    	}
	    	if(!foglia){
		    	System.out.println("Valori disponibili:");
	    		c.getSottocategorie().forEach((k,v) -> System.out.println(k + " -> " + v.getNome()));
		    	System.out.println("Inserisci il valore del campo per navigare nella sottocategoria (0 per tornare indietro nella gerarchia - 'exit per uscire)");
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
    	} while(ricerca);
    	return false;
    }
    
 
 private CategoriaFoglia getFogliaDaGerarchia(GerarchiaCategorie g) {
		ArrayList<CategoriaFoglia> foglie = g.getListaFoglie();
		boolean check = false;
		do{
			String input = getConsistentString(foglie.toString());
         for(CategoriaFoglia c : foglie) {
             if (input.equals(c.getNome())) {
             	check=true;
             	return c;  
             }
         }
         if(!check)
         	System.out.println("input non valido. Riprovare");
		}while(!check);
     return null;	
	}
 
 
 private void visualizzaProposteByFoglia() {
		String foglia = getFogliaDaGerarchia(sceltaRadice()).getNome();
		ArrayList<Proposta> lista = Proposta.getListaProposte();
		int count = 0;
		for(Proposta proposta:lista) {
			if(proposta.getOfferta().getNome().equals(foglia) || proposta.getRichiesta().getNome().equals(foglia)) {
				System.out.println(proposta.toString());
				count++;
			}
		}
		if(count==0) 
			System.out.println("Non esistono proposte legate alla categoria foglia selezionata");
		
	}
	
 
 
	
 private void creaProposta(String user) {
 	CategoriaFoglia richiesta = null;
 	CategoriaFoglia offerta = null;
 	boolean checkDiverso = false;
		do {
	    	System.out.println("Seleziona la categoria foglia richiesta\n"); 
	    	richiesta = getFogliaDaGerarchia(sceltaRadice());
	    	
	    	System.out.println("Seleziona la categoria foglia offerta\n"); 
	    	offerta = getFogliaDaGerarchia(sceltaRadice());

			if(richiesta.getNome().equals(offerta.getNome()))
				System.out.println("Non può essere selezionata la stessa categoria. Riprovare\n");
			else checkDiverso = true;

		} while (!checkDiverso);
		
		System.out.println("quante ore per la richiesta?");
		int durataRichiesta;
		do{
			durataRichiesta = getInt();
		} while(durataRichiesta < 0);
		
		Proposta proposta = new Proposta(richiesta, offerta, durataRichiesta, user);
		calcolaDurataOfferta(proposta);
		System.out.println(proposta.toString());
		System.out.println("vuoi confermare la proposta? s/n");
		String conferma = "";
		do {
			conferma = scanner.nextLine();
			if (conferma.equals("s")) {
				proposta.accettaProposta();
				Proposta.addProposta(proposta);
				System.out.println("Proposta confermata\n");
				proposta.verificaProposta();
			} else if(conferma.equals("n"))
				System.out.println("Proposta non confermata\n");
			else System.out.println("Input non valido. Riprovare");
		} while(!(conferma.equals("s") || conferma.equals("n")));
		salvaDati();
 }

	private void calcolaDurataOfferta(Proposta proposta) {
		int durataOfferta=0;
		try {
			FattoreConversione f = FattoreConversione.trovaFattore(proposta.getRichiesta().getNome(), proposta.getOfferta().getNome());
			durataOfferta = (int) Math.round(proposta.getDurataRichiesta() * f.getFattore());
			proposta.setDurataOfferta(durataOfferta);
		} catch (NullPointerException e) {
			System.out.println("non esiste questo fattore di conversione");
		};
	}
	
	
	
	
	private void ritiraProposta(String user) {
	    int scelta;
	    ArrayList<Proposta> list = Proposta.getListaProposteUser(user);

	    if (list.isEmpty()) {
	        System.out.println("Non sono presenti proposte da ritirare.");
	        return;
	    }

	    visualizzaProposteAperteByUser(user);
	    System.out.println("Seleziona il numero della proposta da ritirare:");

	    try {
	        scelta = scanner.nextInt();
	        scanner.nextLine();
	        
	        if (scelta >= 0 && scelta < list.size()) {
	            Proposta propostaDaRitirare = list.get(scelta);
	            propostaDaRitirare.ritiraProposta();
	            System.out.println("La proposta è stata ritirata con successo.");
	        } else {
	            System.out.println("Scelta non valida. Riprova.");
	        }
	    } catch (InputMismatchException e) {
	        System.out.println("Input non valido. Inserisci un numero.");
	        scanner.next(); 
	    }
	    salvaDati();
	}
	

	private void visualizzaProposteByUser(String user) {
	    ArrayList<Proposta> list = Proposta.getListaProposteUser(user);
	    if (list.isEmpty()) {
	        System.out.println("Non sono presenti proposte da visualizzare.");
	    } else {
	        for (int i = 0; i < list.size(); i++) {
	            System.out.println(i + ": " + list.get(i));
	        }
	    }
	    System.out.println("---------------------------------- \n");
	}
	
	private void visualizzaProposteAperteByUser(String user) {
	    ArrayList<Proposta> list = Proposta.getListaProposteUser(user);
	    if (list.isEmpty()) {
	        System.out.println("Non sono presenti proposte da visualizzare.");
	    } else {
	        for (int i = 1; i < list.size(); i++) {
	        	if(list.get(i).isAperto()) {
	        		System.out.println(i + ": " + list.get(i));
	        	}
	        }
	        	
	    }
	    System.out.println("---------------------------------------------------------------------------------------------------- \n");
	}  
	
	
	
	

    private void salvaDati() {
        dati.setConfiguratori(Configuratore.getListaConfiguratori());
        dati.setComprensori(ComprensorioGeografico.getListaComprensori());
        dati.setGerarchie(GerarchiaCategorie.getListaOggettiGerarchia());
        dati.setFattoriDiConversione(FattoreConversione.getListaFattori());
        dati.setFruitori(Fruitore.getListaFruitori());
        dati.setProposte(Proposta.getListaProposte());
        
        try {
            FileManager.salvaDati(dati);
            System.out.println("Dati salvati con successo.");
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }
}
