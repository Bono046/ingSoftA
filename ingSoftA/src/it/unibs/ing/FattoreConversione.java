package it.unibs.ing;

import java.util.ArrayList;

public class FattoreConversione {

	private CategoriaFoglia c1;
	private CategoriaFoglia c2;
	double fattore;
	
	private static ArrayList<FattoreConversione> listaFattori = new ArrayList<>();
	
	
	
	public FattoreConversione(CategoriaFoglia c1, CategoriaFoglia c2, double fattore) {
		this.c1 = c1;
		this.c2 = c2;
		this.fattore = fattore;
	}


	public CategoriaFoglia getC1() {
		return c1;
	}

	public void setC1(CategoriaFoglia c1) {
		this.c1 = c1;
	}

	public CategoriaFoglia getC2() {
		return c2;
	}

	public void setC2(CategoriaFoglia c2) {
		this.c2 = c2;
	}

	public double getFattore() {
		return fattore;
	}

	public void setFattore(double fattore) {
		this.fattore = fattore;
	}
	
	public static ArrayList<FattoreConversione> getListaFattori() {
		return listaFattori;
	}

	public static void setListaFattori(ArrayList<FattoreConversione> listaFattori) {
		FattoreConversione.listaFattori = listaFattori;
	}
	
	public static void addFattore (CategoriaFoglia c1, CategoriaFoglia c3, double f13) {
		
		FattoreConversione fattore = new FattoreConversione(c1, c3, f13);
		FattoreConversione f_inverso = creaFattoreInverso(fattore);
		
		 ArrayList<FattoreConversione> fattoriDaAggiungere = new ArrayList<>();
		 double valoreFattore;
		 
		if(listaFattori.isEmpty()) {		//prima iterazione
			listaFattori.add(fattore);
			listaFattori.add(f_inverso);
		} else {
			for(FattoreConversione fact : listaFattori) {
				CategoriaFoglia c2 = fact.getC2();
				if(fact.getC1().getNome().equals(c1.getNome())) {
					if(c3.getNome().equals(c2.getNome()) == false) {
						valoreFattore = Math.round((f13 / fact.getFattore()) * 100.0) / 100.0;
						valoreFattore = Math.max(0.5, Math.min(2.0, valoreFattore));
						FattoreConversione newFattore = new FattoreConversione(c2, c3, valoreFattore);
						fattoriDaAggiungere.add(newFattore);
						fattoriDaAggiungere.add(creaFattoreInverso(newFattore));
					}
				}
			}
			listaFattori.add(fattore);
			listaFattori.add(f_inverso);
			listaFattori.addAll(fattoriDaAggiungere);
		}
	}
	
	
	public static Boolean esisteFattore(CategoriaFoglia c1, CategoriaFoglia c2) {
		
		for(FattoreConversione f: listaFattori) {
			String foglia1 = f.getC1().getNome();
			String foglia2 = f.getC2().getNome();
			
			if(foglia1.equals(c1.getNome()) && foglia2.equals(c2.getNome()))
				return true;
			if(foglia1.equals(c2.getNome()) && foglia2.equals(c1.getNome()))
				return true;
		}
		return false;
	}


	public static FattoreConversione creaFattoreInverso (FattoreConversione f) {
		CategoriaFoglia c1 = f.getC1();
		CategoriaFoglia c2 = f.getC2();
		Double f12 = f.getFattore();
		
		return(new FattoreConversione(c2, c1, Math.round((1/f12) * 100.0) / 100.0));
	}
	
	
	public static  ArrayList<FattoreConversione> trovaFattore(String s) {
		ArrayList<FattoreConversione> fattoriDaVisualizzare = new ArrayList<>();
		for(FattoreConversione f: listaFattori) {
			if( f.getC1().getNome().equals(s) || f.getC2().getNome().equals(s) )
				fattoriDaVisualizzare.add(f);
		}
		return fattoriDaVisualizzare;
	}
	
	
	public static FattoreConversione trovaFattore(String foglia1, String foglia2) {
		for(FattoreConversione f: listaFattori) {
			if( f.getC1().getNome().equals(foglia1) && f.getC2().getNome().equals(foglia2) )
				return f;
		}	
		return null;
	}
	
	
	@Override
	public String toString() {
		return "categoria: " + c1 + ", categoria: " + c2 + ", fattore: " + fattore;
	}
	
	

}
