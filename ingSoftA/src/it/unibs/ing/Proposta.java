package it.unibs.ing;

import java.util.ArrayList;

public class Proposta {
	private CategoriaFoglia richiesta;
	private CategoriaFoglia offerta;
	private int durataRichiesta;
	private int durataOfferta;
	private StatoProposta stato;
	private String username;
	private enum StatoProposta {CREATA, APERTA, CHIUSA, RITIRATA}; 
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


	public Boolean isAperto() {
		if(this.stato == stato.APERTA)
			return true;
		return false;
	}


	public void accettaProposta() {
		this.stato = StatoProposta.APERTA;
	}

	public StatoProposta getStato() {
		return stato;
	}
	
	public void chiudiProposta() {
		this.stato=StatoProposta.CHIUSA;
		
	}


	public String getUsername() {
		return username;
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

	public static ArrayList<Proposta> getProposteAperteFromUsers(ArrayList<String> listaUser) {
		ArrayList<Proposta> proposteFromComprensorio=new ArrayList<>();
		for(String user:listaUser) {
			for(Proposta p:listaProposte) {
				if(user.equals(p.getUsername()) && (p.isAperto())) {
					proposteFromComprensorio.add(p);
				}
			}
		}

		return proposteFromComprensorio;
	}

	
	
	
	public void verificaProposta() {
	    ComprensorioGeografico comprensorio = Fruitore.getComprensorioFromUser(this.getUsername());
	    ArrayList<String> userFruitoriFromComprensorio = Fruitore.getUserFruitoriFromComprensorio(comprensorio);
	    ArrayList<Proposta> proposteAperteFromComprensorio = Proposta.getProposteAperteFromUsers(userFruitoriFromComprensorio);

	    for (Proposta proposta : proposteAperteFromComprensorio) {
	        if (this.soddisfaRichiestaDi(proposta)) {
	            if (this.richiestaSoddisfattaDa(proposta)) {
	                this.chiudiProposta();
	                proposta.chiudiProposta();
	                break; 
	            } else {	              
	                ArrayList<Proposta> percorso = new ArrayList<>();
	                percorso.add(this);

	                if (verificaTransitivaCiclo(proposta, percorso, proposteAperteFromComprensorio)) {
	                    chiudiProposte(percorso);
	                    this.chiudiProposta();
	                    break; 
	                }
	            }
	        }
	    }
	}

	
	private boolean verificaTransitivaCiclo(Proposta propostaAperta, ArrayList<Proposta> catenaProposte, ArrayList<Proposta> elencoProposte) {
	    catenaProposte.add(propostaAperta);

	    if (catenaProposte.size() > 2 && propostaAperta.soddisfaRichiestaDi(this)) {
	        return true;
	        }

	    for (Proposta prossimaProposta : elencoProposte) {
	        if (!catenaProposte.contains(prossimaProposta) && propostaAperta.soddisfaRichiestaDi(prossimaProposta)) {
	            if (verificaTransitivaCiclo(prossimaProposta, catenaProposte, elencoProposte)) {
	                return true;
	            }
	        }
	    }
	    catenaProposte.remove(catenaProposte.size() - 1);
	    return false;
	}


	public boolean soddisfaRichiestaDi(Proposta altraProposta) {
	    return this.getOfferta().getNome().equals(altraProposta.getRichiesta().getNome())
	        && this.getDurataOfferta() == altraProposta.getDurataRichiesta();
	}

	public boolean richiestaSoddisfattaDa(Proposta altraProposta) {
	    return this.getRichiesta().getNome().equals(altraProposta.getOfferta().getNome())
	        && this.getDurataRichiesta() == altraProposta.getDurataOfferta();
	}

	
	private void chiudiProposte(ArrayList<Proposta> percorso) {
	    for (Proposta proposta : percorso) {
	        proposta.chiudiProposta();
	    }
	}
	

	
	
	
}