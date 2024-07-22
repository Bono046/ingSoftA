package it.unibs.ing;

//File: Persistenza.java
import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Persistenza {
 private static final String FILE_DATI = "dati.json";

 public static void salvaDati(Dati dati) throws IOException {
     Gson gson = new Gson();
     try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DATI))) {
         writer.write(gson.toJson(dati));
     }
 }

 public static Dati caricaDati() throws IOException {
     Gson gson = new Gson();
     File file = new File(FILE_DATI);
     if (!file.exists() || file.length() == 0) {
         // Se il file non esiste o è vuoto, restituisci un nuovo oggetto Dati
         return new Dati();
     }
     try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
         return gson.fromJson(reader, Dati.class);
     } catch (JsonSyntaxException e) {
         // Gestisci il caso in cui il file contenga dati non validi
         System.out.println("Errore: dati non validi nel file " + FILE_DATI);
         return new Dati();
     }
 }
}

