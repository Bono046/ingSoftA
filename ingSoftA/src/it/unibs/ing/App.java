package it.unibs.ing;

import java.io.IOException;
import java.util.*;

public class App {
    private Dati dati;
    private Scanner scanner = new Scanner(System.in);
    private Boolean logged = false;

    private ArrayList<Categoria> listaGerarchie = new ArrayList<>();
    private ArrayList<ComprensorioGeografico> listaComprensori = new ArrayList<>();
    private ArrayList<Configuratore> listaConfiguratore = new ArrayList<>();
    private ArrayList<FattoreDiConversione> listaFattori = new ArrayList<>();
   

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
            System.out.println("5. Aggiungi Categoria Non Foglia");
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
                    
                    HashMap<String, Categoria> sottocategorie;
                    
                    do {
                    	
                    	System.out.println(" 1 - sottocategoria, 2 - categoria foglia");
                    	String choice = scanner.nextLine();
                    	
                    	if(choice.equals("1")) {
                    		
                    		aggiungiCategoria(root);
                    		
                    		sottocategorie = root.getSottocategoria();
                    		for(String s : sottocategorie.keySet()) {
                    			
                    		}
                    	}
                    	
                    //
                    
                    
                    }while(true ); //sottocategorie not empty
                    
                    
                   // break;
                case 5:
                    if (!listaGerarchie.isEmpty()) {
                        Categoria roo = sceltaRadice();
                        aggiungiCategoria(roo);
                        
                   
                    } else {
                        System.out.println("Non sono presenti Gerarchie - prima di proseguire definiscine una");
                        break;
                    }
                case 6:
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
        listaComprensori.add(comprensorio);
        System.out.println("Comprensorio creato con successo." + "\n");
    }
    
    private void visualizzaComprensori() {
        if (listaComprensori.isEmpty()) {
            System.out.println("Non ci sono comprensori da visualizzare.");
        } else {
            for (ComprensorioGeografico comprensorio : listaComprensori) {
                System.out.println("Comprensorio: " + comprensorio.getNome());
            }
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
        //ciclo per non accettare valori diversi da numeri interi - possibile miglioramento ocn minimo e massimo
        while(numValori == 0) {
        	numValori = getInteger(numValori);
        	if(numValori == 0)
        		System.out.println("scelta non valida. Riprovare");
        }	
        for (int i = 0; i < numValori; i++) {
            System.out.print("Inserisci valore del dominio: ");
            String valore = scanner.nextLine();
            System.out.print("Inserisci descrizione del valore: ");
            String descrizione = scanner.nextLine();
            dominio.put(valore, descrizione);
        }

        Categoria categoria = new Categoria(nome, campo, dominio);
        System.out.println("Categoria creata con successo." + "\n");
        return categoria;
    }
    
    private void aggiungiCategoria(Categoria padre) {
		
		ArrayList<String> listaDominio = new ArrayList<>(padre.getDominio().keySet());
		
		do {
			System.out.print("Inserisci valore del dominio a cui associare la sottocategoria: ");
			System.out.println("Valori disponibili: " + listaDominio.toString());
			String dom = scanner.nextLine();
			
			Categoria sottocat = creaCategoria();
			
			padre.aggiungiSottocategoria(dom, sottocat);
			System.out.println(padre.toString());
			
			
			listaDominio.remove(listaDominio.indexOf(dom));
		} while(!listaDominio.isEmpty());
	}

    
    private Categoria sceltaRadice() {
        System.out.println("Seleziona la gerarchia:");
        for (int i = 0; i < listaGerarchie.size(); i++) {
            System.out.println((i + 1) + ". " + listaGerarchie.get(i).getNome());
        }
        int scelta = scanner.nextInt();
        scanner.nextLine(); // Consumare la newline
        return listaGerarchie.get(scelta - 1);
    }

    
    
    private void sceltaCategoria(Categoria padre) {
    	
    	for(String dominio: padre.getDominio().keySet()) {
   // 		Categoria c = padre.getSottocategoria(dominio);
   // 		System.out.println(c.toString());
    		}
    	
    	
    	
    	
    }
    

	



    private void visualizzaGerarchie() {
        if (listaGerarchie.isEmpty()) {
            System.out.println("Non esiste alcuna gerarchia da visualizzare.");
        } else {
            for (Categoria gerarchia : listaGerarchie) {
                gerarchia.visualizzaGerarchia("");
            }
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
