package it.unibs.ing;

//File: App.java
import java.io.IOException;
import java.util.*;

public class App {
	
    private Dati dati;
    private Scanner scanner = new Scanner(System.in);
    // da verificare se usata in un solo metodo o in piu metodi
    private Boolean logged = false;
    
    //inizializzazione liste
    // invece di chiamare la classe dati, passeremo le liste al metodo corrispondente
    // nella classe di riferimento, che applicher� la logica necessaria.
    // nel salvataggio finale utilizziamo ancora un oggetto dati, settato con queste liste modificate
    // lungo l'applicazione
    private ArrayList<Categoria> listaCategorie = new ArrayList<>();
    private ArrayList<ComprensorioGeografico> listaComprensori = new ArrayList<>();
    private ArrayList<Configuratore> listaConfiguratore = new ArrayList<>();
    private ArrayList<FattoreDiConversione> listaFattori  = new ArrayList<>();
    
    

    public App() {
        
    	try {
            dati = FileManager.caricaDati();
           
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dei dati: " + e.getMessage());
            dati = new Dati();
        }
          	
     	listaCategorie = dati.getGerarchie();
    	listaComprensori = dati.getComprensori();
    	listaConfiguratore = dati.getConfiguratori();
    	listaFattori = dati.getFattoriDiConversione();
    	
    	
    	
    	//inizializzaListeVuote();
    }

//	metodo probabilmente non piu utile - eliminare dopo test resistenza caricamento dati    
//    private void inizializzaListeVuote() {
//        if (dati.getConfiguratori() == null) {dati.setConfiguratori(new ArrayList<>());
//        }
//        if (dati.getComprensori() == null) {dati.setComprensori(new ArrayList<>());
//        }
//        if (dati.getGerarchie() == null) {dati.setGerarchie(new ArrayList<>());
//        }
//        if (dati.getFattoriDiConversione() == null) {dati.setFattoriDiConversione(new ArrayList<>());
//        }}
    
	

	public void start() {
		//while (!logged)  VERICARE UTILITA' CICLO
			mostraMenuAutenticazione();
			mostraMenuPrincipale();
	}
	
	

	private void mostraMenuAutenticazione() {
		
		while (!logged) {
			System.out.println("Menu Principale:");
			System.out.println("1. Primo accesso Configuratore");
			System.out.println("2. Autenticazione Configuratore");
			
			int scelta = -1;
			scelta = getInput(scelta);
			
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

			// try catch per gestire input non numerici
			int scelta = -1;
			scelta = getInput(scelta);
			

			switch (scelta) {
			
			case 3:
				creaComprensorio();
				break;
			case 4:
	//			creaGerarchia();
				break;
			case 5:
	//			aggiungiCategoriaNonFoglia();
				break;
			case 6:
	//			stabilisciFattoreConversione();
				break;
			case 7:
				visualizzaComprensori();
				break;
			case 8:
	//			visualizzaGerarchie();
				break;
			case 9:
	//			visualizzaFattoriDiConversione();
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

	
	private int getInput(int scelta) {

		try {
			scelta = scanner.nextInt();
		} catch(Exception e) {}
		
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
	            System.out.println("Username gi� esistente. Riprova con un altro." + "\n");
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
		if(check)
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
		}	while(!comune.equals("0"));
		listaComprensori.add(comprensorio);
	}

	
	private void visualizzaComprensori() {
		if(listaComprensori.isEmpty())
			System.out.println("Non esiste alcun comprensorio da visualizzare");
		else
			for (ComprensorioGeografico comprensorio : listaComprensori) {
				System.out.println(comprensorio.toString());
			}
	}
	
	
	
/*	
*	
		GPT DA IMPLEMENTARE
*	
*/	
	
	
	
	
	private void salvaDati() {

		
		dati.setConfiguratori(listaConfiguratore);
		dati.setComprensori(listaComprensori);
//		dati.setFattoriDiConversione(listaFattori);
//		dati.setGerarchie(listaCategorie);
		
		
		
		try {
			FileManager.salvaDati(dati);
		} catch (IOException e) {
			System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
		}
	}
}
