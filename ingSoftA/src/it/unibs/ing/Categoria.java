package it.unibs.ing;

//File: Categoria.java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Categoria implements Serializable {
 private String nome;
 private boolean isFoglia;
 private String campoCaratteristico;
 private List<String> dominio;
 private Map<String, String> descrizioni;
 private List<Categoria> sottocategorie;

 // Costruttore
 public Categoria(String nome, boolean isFoglia, String campoCaratteristico, List<String> dominio, Map<String, String> descrizioni) {
     this.nome = nome;
     this.isFoglia = isFoglia;
     this.campoCaratteristico = campoCaratteristico;
     this.dominio = dominio;
     this.descrizioni = descrizioni;
     this.sottocategorie = new ArrayList<>();
 }

 public String getNome() {
     return nome;
 }

 public void setNome(String nome) {
     this.nome = nome;
 }

 public boolean isFoglia() {
     return isFoglia;
 }

 public void setFoglia(boolean isFoglia) {
     this.isFoglia = isFoglia;
 }

 public String getCampoCaratteristico() {
     return campoCaratteristico;
 }

 public void setCampoCaratteristico(String campoCaratteristico) {
     this.campoCaratteristico = campoCaratteristico;
 }

 public List<String> getDominio() {
     return dominio;
 }

 public void setDominio(List<String> dominio) {
     this.dominio = dominio;
 }

 public Map<String, String> getDescrizioni() {
     return descrizioni;
 }

 public void setDescrizioni(Map<String, String> descrizioni) {
     this.descrizioni = descrizioni;
 }

 public List<Categoria> getSottocategorie() {
     return sottocategorie;
 }

 public void setSottocategorie(List<Categoria> sottocategorie) {
     this.sottocategorie = sottocategorie;
 }
}
