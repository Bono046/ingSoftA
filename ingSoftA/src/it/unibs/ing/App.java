
package it.unibs.ing;

import java.io.IOException;
import java.util.*;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class App {
    private Dati dati;
    private Scanner scanner = new Scanner(System.in);
    private Boolean logged = false;

    private ArrayList<Categoria> listaGerarchie = new ArrayList<>();
    private ArrayList<ComprensorioGeografico> listaComprensori = new ArrayList<>();
    private ArrayList<Configuratore> listaConfiguratore = new ArrayList<>();
    private ArrayList<FattoriManager> listaFattori = new ArrayList<>();
   

    public App() {
        try {
            dati = FileManager.caricaDati();
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dei dati: " + e.getMessage());
            dati = new Dati();
        }

        listaGerarchie = dati.getGerarchie();
        listaComprensori = dati.getComprensori();
        listaConfiguratore = dati.getConfiguratori();
        listaFattori = dati.getFattoriDiConversione();
        
        
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

            int scelta = -1;
            scelta = getInteger(scelta);

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
        while (true) {
            System.out.println("3. Crea Comprensorio Geografico");
            System.out.println("4. Crea Gerarchia di Categorie");
            System.out.println("5. Visualizza categorie foglia");
            System.out.println("6. Stabilisci Fattore di Conversione");
            System.out.println("7. Visualizza Comprensori");
            System.out.println("8. Visualizza Gerarchie");
            System.out.println("9. Visualizza Fattori di Conversione");
            System.out.println("0. Esci");
            System.out.print("Seleziona un'opzione: ");

            int scelta = -1;
            scelta = getInteger(scelta);

            switch (scelta) {
                case 3:
                    creaComprensorio();
                    break;
                    
                case 4:
                    Categoria root = creaCategoria();
                    listaGerarchie.add(root);
                    creaGerarchia(root);
                    break;             
                case 5 : 
                	stampaCategorieFoglia(sceltaRadice());
                	break;
                case 6:               	
                    setFattoriConversioneGerarchia(sceltaRadice());
                    break;
                case 7:
                    visualizzaComprensori();
                    break;
                case 8:
                    visualizzaGerarchie();
                    break;
                case 9:
              
                    break;
                case 0:
                    salvaDati();
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova" + "\n");
            }
        }
    }

    
    
    
  
    private int getInteger(int scelta) {
        try {
            scelta = scanner.nextInt();
        } catch (Exception e) {}
        scanner.nextLine();
        return scelta;
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
            System.out.println("Inserisci nuovo username: ");
            String username = scanner.nextLine();
            System.out.println("Inserisci nuova password: ");
            String password = scanner.nextLine();

            userValido = Configuratore.userValido(username, listaConfiguratore);
            if (!userValido) {
                System.out.println("Username già esistente. Riprova con un altro." + "\n");
            } else {
                Configuratore configuratore = new Configuratore(username, password);
                listaConfiguratore.add(configuratore);
                System.out.println("Configuratore registrato con successo." + "\n");
            }
        }
    }

    private Boolean autenticaConfiguratore() {
        System.out.println("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci password: ");
        String password = scanner.nextLine();

        Boolean check = Configuratore.loginConfiguratore(username, password, listaConfiguratore);
        if (check)
            System.out.println("Autenticazione avvenuta con successo. Procedi con il seguente menu:");
        else
            System.out.println("Credenziali non valide. Riprova" + "\n");
        return check;
    }

    private void creaComprensorio() {
        System.out.print("Inserisci nome comprensorio: ");
        String nome = scanner.nextLine();
        ComprensorioGeografico comprensorio = new ComprensorioGeografico(nome);
        String comune;
		do {
			System.out.print("Inserisci nome del comune da aggiungere. Premi 0 per uscire: ");
			comune = scanner.nextLine();
			if(!comune.equals("0"))
				comprensorio.aggiungiComune(comune);
		} while(!comune.equals("0"));
        listaComprensori.add(comprensorio);
        System.out.println("Comprensorio creato con successo." + "\n");
        
    }
    
    private void visualizzaComprensori() {
        if (listaComprensori.isEmpty()) {
            System.out.println("Non ci sono comprensori da visualizzare.");
        } else {
            for (ComprensorioGeografico comprensorio : listaComprensori) {
                System.out.println("Comprensorio: " + comprensorio.toString());
            }
        }
    }

    
    
    private void creaGerarchia(Categoria padre) {
    	System.out.println("categoria selezionata: " + padre.getNome());
    	System.out.println(" 1 - aggiungi sottocategoria, 2 - aggiungi categoria foglia");
    	String choice1 = scanner.nextLine();
    	
    	if(choice1.equals("1")) {
    		aggiungiCategoria(padre);
    		HashMap<String, Categoria> sottocategorie = padre.getSottocategorie();
      
    		for(String s : sottocategorie.keySet()) {
    			creaGerarchia(sottocategorie.get(s));
    		}
    	} else if (choice1.equals("2")) {
    		aggiungiCategoriaFoglia(padre);
    	}
   }
    
    
    
    private Categoria creaCategoria() {
        System.out.print("Inserisci nome categoria: ");
        String nome = scanner.nextLine();

        System.out.print("Inserisci nome del campo: ");
        String campo = scanner.nextLine();
        HashMap<String, String> dominio = new HashMap<>();
        System.out.print("Quanti valori di dominio vuoi inserire? ");
        int numValori = 0;
        // ciclo per non accettare valori diversi da numeri interi - possibile miglioramento ocn minimo e massimo
        while(numValori == 0) {
        	numValori = getInteger(numValori);
        	if(numValori == 0)
        		System.out.println("scelta non valida. Riprovare");
        }	
        for (int i = 0; i < numValori; i++) {
            System.out.print("Inserisci valore del dominio: ");
            String valore = scanner.nextLine();
            System.out.print("Inserisci descrizione del valore: "); //RENDERLA NON OBBLIGATORIA
            String descrizione = scanner.nextLine();
            dominio.put(valore, descrizione);
        }

        Categoria categoria = new Categoria(nome, campo, dominio);
        System.out.println("Categoria creata con successo." + "\n");
        return categoria;
    }
    
    private CategoriaFoglia creaCategoriaFoglia() {
        System.out.print("Inserisci nome categoria: ");
        String nome = scanner.nextLine();

       CategoriaFoglia categoria = new CategoriaFoglia(nome);
        System.out.println("Categoria foglia creata con successo." + "\n");
        return categoria;
    }
    
    
    private void aggiungiCategoria(Categoria padre) {
        ArrayList<String> listaDominio = new ArrayList<>(padre.getDominio().keySet());
        for (String dom : listaDominio) {
            System.out.println("Creazione sottocategoria per il dominio: " + dom);

            Categoria sottocat = creaCategoria();
            padre.aggiungiSottocategoria(dom, sottocat);
            System.out.println(padre.toString());
        }

        System.out.println("Livello completato!");
    }
    
    private void aggiungiCategoriaFoglia(Categoria padre) {
    	ArrayList<String> listaDominio = new ArrayList<>(padre.getDominio().keySet());
        for (String dom : listaDominio) {
            System.out.println("Creazione sottocategoria foglia per il dominio: " + dom);

            CategoriaFoglia sottocat = creaCategoriaFoglia();
            padre.aggiungiSottocategoria(dom, sottocat);
            System.out.println(padre.toString());
        }

        System.out.println("Livello completato!");
    }

    
    private Categoria sceltaRadice() {
        System.out.println("Seleziona la gerarchia:");

        if (listaGerarchie.isEmpty()) {
            System.out.println("Nessuna gerarchia disponibile.");
            return null; 
        }

       
        for (int i = 0; i < listaGerarchie.size(); i++) {
            System.out.println((i + 1) + ". " + listaGerarchie.get(i).getNome());
        }

        int scelta = -1;
        boolean sceltaValida = false;

       
        while (!sceltaValida) {
            try {
                System.out.print("Inserisci il numero della scelta: ");
                scelta = scanner.nextInt();
                scanner.nextLine(); 

                
                if (scelta > 0 && scelta <= listaGerarchie.size()) {
                    sceltaValida = true;
                } else {
                    System.out.println("Scelta non valida. Per favore, inserisci un numero tra 1 e " + listaGerarchie.size() + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input non valido. Per favore, inserisci un numero.");
                scanner.nextLine();
            }
        }

        return listaGerarchie.get(scelta - 1);
    }

    
    

    private void visualizzaGerarchie() {
        if (listaGerarchie.isEmpty()) {
            System.out.println("Non esiste alcuna gerarchia da visualizzare.");
        } else {
            for (Categoria gerarchia : listaGerarchie) {
                System.out.println(gerarchia.toString());
            }
        }
    }
    
    private void setFattoriConversioneGerarchia(Categoria gerarchia) {
    	FattoriManager conversionManager = new FattoriManager();
        Set<CategoriaFoglia> foglie = gerarchia.getCategorieFoglia();
        
        for (CategoriaFoglia c1 : foglie) {
            for (CategoriaFoglia c2 : foglie) {
                if (!c1.equals(c2)) {
                    System.out.println("Inserire il fattore di conversione da " + c1.getNome() + " a " + c2.getNome() + ":");
                    double factor = scanner.nextDouble();
                    scanner.nextLine(); 
                    conversionManager.setFattoreConversione(c1, c2, factor);
                }
            }
        }
    }
    
    private Set<CategoriaFoglia> getCategorieFoglia(Categoria root) {   // E RICORSIVO MA C'E UN PROBLEMA: IL METODO SI ASPETTA UNA CATEGORIA EPPURE PRIMA O POI DOVRA RICEVERE UNA CATEGORIAFOGLIA PER FUNZIONARE
        Set<CategoriaFoglia> foglie = new HashSet<>();
        HashMap<String, Categoria> sottocategorie = root.getSottocategorie();  
        
        if (root.isFoglia()) {
            foglie.add((CategoriaFoglia) root);
        } else {   
        	for(String s : sottocategorie.keySet()) {
        			getCategorieFoglia(sottocategorie.get(s)); 
        	}
            
        }
        return foglie;
    }
    
    private void stampaCategorieFoglia(Categoria root) {
        Set<CategoriaFoglia> foglie = getCategorieFoglia(root);
        if(foglie.isEmpty())
        	System.out.println("non sono presenti categorie foglia nella gerarchia: " +root.getNome());
        else 
        	for (CategoriaFoglia foglia : foglie) {
        		System.out.println(foglia.toString());
        	}
    }
    
	
    
    

    private void salvaDati() {
        dati.setGerarchie(listaGerarchie);
        dati.setComprensori(listaComprensori);
        dati.setConfiguratori(listaConfiguratore);
        dati.setFattoriDiConversione(listaFattori);

        try {
            FileManager.salvaDati(dati);
            System.out.println("Dati salvati con successo.");
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }
}
