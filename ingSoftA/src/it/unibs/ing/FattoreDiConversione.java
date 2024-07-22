package it.unibs.ing;

//File: FattoreDiConversione.java
import java.io.Serializable;

public class FattoreDiConversione implements Serializable {
 private Categoria foglia1;
 private Categoria foglia2;
 private double valore;

 // Costruttore
 public FattoreDiConversione(Categoria foglia1, Categoria foglia2, double valore) {
     this.foglia1 = foglia1;
     this.foglia2 = foglia2;
     this.valore = valore;
 }

 public Categoria getFoglia1() {
     return foglia1;
 }

 public void setFoglia1(Categoria foglia1) {
     this.foglia1 = foglia1;
 }

 public Categoria getFoglia2() {
     return foglia2;
 }

 public void setFoglia2(Categoria foglia2) {
     this.foglia2 = foglia2;
 }

 public double getValore() {
     return valore;
 }

 public void setValore(double valore) {
     this.valore = valore;
 }
}

