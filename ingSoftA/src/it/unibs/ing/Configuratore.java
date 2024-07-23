package it.unibs.ing;

// File: Configuratore.java


import java.io.Serializable;

public class Configuratore implements Serializable {
    private static String username;
    private static String password;
    
    private static final String USERNAME_PREDEFINITO = "user";
    private static final String PASSWORD_PREDEFINITO = "password";

    // Costruttore
    public Configuratore(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    public static Boolean verificaPrimoAccesso(String user, String pass) {
    	
    	if (user.equals(USERNAME_PREDEFINITO) && pass.equals(PASSWORD_PREDEFINITO)) {    	
    	return true;
    	}
    	else return false;
    }
    
    public static Boolean login(String user, String pass) {
   
    	if (user.equals(username) && pass.equals(password)) {    	
    	return true;
    	}
    	else return false;
    }






    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
