package it.unibs.ing;

//File: ComprensorioGeografico.java
import java.io.Serializable;

public class ComprensorioGeografico implements Serializable {
 private String nome;
 private String descrizione;

 // Costruttore
 public ComprensorioGeografico(String nome, String descrizione) {
     this.nome = nome;
     this.descrizione = descrizione;
 }

 public String getNome() {
     return nome;
 }

 public void setNome(String nome) {
     this.nome = nome;
 }

 public String getDescrizione() {
     return descrizione;
 }

 public void setDescrizione(String descrizione) {
     this.descrizione = descrizione;
 }
}
