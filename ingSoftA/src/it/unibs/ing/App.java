package it.unibs.ing;

//File: App.java
import java.io.IOException;
import java.util.*;

public class App {
	private Dati dati;
	private Scanner scanner = new Scanner(System.in);

	public App() {
		try {
			dati = FileManager.caricaDati();
		} catch (IOException e) {
			System.out.println("Errore nel caricamento dei dati: " + e.getMessage());
			dati = new Dati();
		}
		inizializzaListeVuote();
	}


	// metodo di inizializzazione e di controllo in caso di file json esistente ma vuoto o parziale
	private void inizializzaListeVuote() {
		if (dati.getConfiguratori() == null) {dati.setConfiguratori(new ArrayList<>());
		}
		if (dati.getComprensori() == null) {dati.setComprensori(new ArrayList<>());
		}
		if (dati.getGerarchie() == null) {dati.setGerarchie(new ArrayList<>());
		}
		if (dati.getFattoriDiConversione() == null) {dati.setFattoriDiConversione(new ArrayList<>());
		}
	}

	public void start() {
		mostraMenuAutenticazione();
		mostraMenuPrincipale();
		
	}

	private void mostraMenuAutenticazione() {
		while (true) {
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
				autenticaConfiguratore();
				break;
			default:
				System.out.println("Opzione non valida. Riprova.");
			}
			// condizione di uscita
			break;
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
				creaGerarchia();
				break;
			case 5:
				aggiungiCategoriaNonFoglia();
				break;
			case 6:
				stabilisciFattoreConversione();
				break;
			case 7:
				visualizzaComprensori();
				break;
			case 8:
				visualizzaGerarchie();
				break;
			case 9:
				visualizzaFattoriDiConversione();
				break;
			case 0:
				salvaDati();
				System.out.println("Arrivederci!");
				return;
			default:
				System.out.println("Opzione non valida. Riprova.");
			}
		}
	}

	
	private int getInput(int scelta) {

		try {
			scelta = scanner.nextInt();

		} catch(Exception e) {
			System.out.println("riprova");
		}

		// Consuma il newline
		scanner.nextLine();
		return scelta;
	}
	
	
	private void registraConfiguratore() {
		Boolean check = false;
		while(!check) {
			System.out.print("Inserisci username predefinito: ");
			String usernamePredefinito = scanner.nextLine();
			System.out.print("Inserisci password predefinita: ");
			String passwordPredefinita = scanner.nextLine();
			
			// Verifica credenziali predefinite (da implementare)
			check = Configuratore.verificaPrimoAccesso(usernamePredefinito, passwordPredefinita);
			if(!check) { System.out.println("errore, riprova");
			}
		}
		System.out.print("Inserisci nuovo username: ");
		String username = scanner.nextLine();
		System.out.print("Inserisci nuova password: ");
		String password = scanner.nextLine();
		// logica per verificare unicità username
		// ..
		Configuratore configuratore = new Configuratore(username, password);
		dati.getConfiguratori().add(configuratore);
	}

	private void autenticaConfiguratore() {
		System.out.print("Inserisci username: ");
		String username = scanner.nextLine();
		System.out.print("Inserisci password: ");
		String password = scanner.nextLine();
		Configuratore configuratore = new Configuratore(username, password);
		Boolean logged = configuratore.login(username, password);
	}

	private void creaComprensorio() {
		System.out.print("Inserisci nome comprensorio: ");
		String nome = scanner.nextLine();
		System.out.print("Inserisci descrizione comprensorio: ");
		String descrizione = scanner.nextLine();
		ComprensorioGeografico comprensorio = new ComprensorioGeografico(nome, descrizione);
		dati.getComprensori().add(comprensorio);
	}

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

	private void visualizzaComprensori() {
		for (ComprensorioGeografico comprensorio : dati.getComprensori()) {
			System.out.println("Nome: " + comprensorio.getNome() + ", Descrizione: " + comprensorio.getDescrizione());
		}
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

	private void salvaDati() {
		try {
			FileManager.salvaDati(dati);
		} catch (IOException e) {
			System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
		}
	}
}
