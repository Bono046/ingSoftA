package it.unibs.ing;

//File: Dati.java
import java.io.Serializable;
import java.util.List;

public class Dati implements Serializable {
 private List<Configuratore> configuratori;
 private List<ComprensorioGeografico> comprensori;
 private List<Categoria> gerarchie;
 private List<FattoreDiConversione> fattoriDiConversione;



 public List<Configuratore> getConfiguratori() {
     return configuratori;
 }

 public void setConfiguratori(List<Configuratore> configuratori) {
     this.configuratori = configuratori;
 }

 public List<ComprensorioGeografico> getComprensori() {
     return comprensori;
 }

 public void setComprensori(List<ComprensorioGeografico> comprensori) {
     this.comprensori = comprensori;
 }

 public List<Categoria> getGerarchie() {
     return gerarchie;
 }

 public void setGerarchie(List<Categoria> gerarchie) {
     this.gerarchie = gerarchie;
 }

 public List<FattoreDiConversione> getFattoriDiConversione() {
     return fattoriDiConversione;
 }

 public void setFattoriDiConversione(List<FattoreDiConversione> fattoriDiConversione) {
     this.fattoriDiConversione = fattoriDiConversione;
 }
}

