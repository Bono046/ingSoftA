package it.unibs.ing;

import java.util.ArrayList;

public class Proposta {
	private CategoriaFoglia richiesta;
	private CategoriaFoglia offerta;
	private int durataRichiesta;
	private int durataOfferta;
	private StatoProposta stato;
	private String username;
	
	private enum StatoProposta {CREATA, APERTA}; 
	
	 private static ArrayList<Proposta> listaProposte= new ArrayList<>(); 
	
	
	public Proposta(CategoriaFoglia richiesta, CategoriaFoglia offerta, int durataRichiesta, String username) {
		super();
		this.richiesta = richiesta;
		this.offerta = offerta;
		this.durataRichiesta = durataRichiesta;
		this.stato = StatoProposta.CREATA;
		this.username = username;
	}


	public CategoriaFoglia getRichiesta() {
		return richiesta;
	}


	public void setRichiesta(CategoriaFoglia richiesta) {
		this.richiesta = richiesta;
	}


	public CategoriaFoglia getOfferta() {
		return offerta;
	}


	public void setOfferta(CategoriaFoglia offerta) {
		this.offerta = offerta;
	}


	public int getDurataRichiesta() {
		return durataRichiesta;
	}


	public void setDurataRichiesta(int durataRichiesta) {
		this.durataRichiesta = durataRichiesta;
	}


	public int getDurataOfferta() {
		return durataOfferta;
	}


	public void setDurataOfferta(int durataOfferta) {
		this.durataOfferta = durataOfferta;
	}


	public StatoProposta isAperto() {
		return stato;
	}


	public void accettaProposta() {
		this.stato = StatoProposta.APERTA;
	}

	public StatoProposta getStato() {
		return stato;
	}


	public void setStato(StatoProposta stato) {
		this.stato = stato;
	}


	public String getUsername() {
		return username;
	}


	private void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		return "Proposta [richiesta=" + richiesta +", durata richiesta=" + durataRichiesta
				+ "], [offerta=" + offerta + ", durata offerta=" + durataOfferta + ", stato=" + stato + "]";
	}


	public static ArrayList<Proposta> getListaProposte() {
		return listaProposte;
	}
	
	public static void addProposta(Proposta p) {
		listaProposte.add(p);
	}


	public static void setListaProposte(ArrayList<Proposta> listaProposte) {
		for(Proposta p : listaProposte) {
			Proposta.listaProposte.add(p);
		}
	}
	
	public static ArrayList<Proposta> getListaProposteUser(String user) {
		ArrayList<Proposta> lista = new ArrayList<>();
		for(Proposta p : listaProposte) {
			if(p.getUsername().equals(user))
				lista.add(p);
		}
		return lista;
	}

	
	
	
	
	
}