package it.unibs.ing;

import java.util.ArrayList;

public class Proposta {
	private CategoriaFoglia richiesta;
	private CategoriaFoglia offerta;
	private int durataRichiesta;
	private int durataOfferta;
	private StatoProposta stato;
	private Fruitore fruitore;
	
	private enum StatoProposta {CREATA, APERTA, CHIUSA}; 
	private static ArrayList<Proposta> listaProposte= new ArrayList<>(); 
	
	
	public Proposta(CategoriaFoglia richiesta, CategoriaFoglia offerta, int durataRichiesta, Fruitore fruitore) {
		super();
		this.richiesta = richiesta;
		this.offerta = offerta;
		this.durataRichiesta = durataRichiesta;
		this.stato = StatoProposta.CREATA;
		this.fruitore = fruitore;
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
	
	public void confermaProposta() {
		this.stato = StatoProposta.CHIUSA;
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


	public Fruitore getFruitore() {
		return fruitore;
	}


	
	
}
