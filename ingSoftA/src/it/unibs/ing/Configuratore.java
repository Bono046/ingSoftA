package it.unibs.ing;
import java.util.ArrayList;

public class Configuratore {
    
    private static final String USERNAME_PREDEFINITO = "user";
    private static final String PASSWORD_PREDEFINITO = "password";
    
	private String username;
    private String password;
    
    private static ArrayList<Configuratore> listaConfiguratori = new ArrayList<>();

	public Configuratore(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    public static Boolean verificaPrimoAccesso(String user, String pass) {
    	
    	if (user.equals(USERNAME_PREDEFINITO) && pass.equals(PASSWORD_PREDEFINITO)) {    	
    		return true;
    	} else {
    		return false;
    	}
    }


    @Override
	public String toString() {
		return "Configuratore [username=" + username + ", password=" + password + "]";
	}


	public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
    
   
    public static ArrayList<Configuratore> getListaConfiguratori() {
		return listaConfiguratori;
	}


	public static void setListaConfiguratori(ArrayList<Configuratore> listaConfiguratori) {
		Configuratore.listaConfiguratori = listaConfiguratori;
	}
	
	public static void addToListaConfiguratori(Configuratore c) {
		listaConfiguratori.add(c);
	}

	public static boolean userValido(String user) {
        for (Configuratore conf : listaConfiguratori) {
            if (conf.getUsername().equals(user)) 
                return false;
        }
        return true;
    }
    
    
	public static boolean loginConfiguratore(String username, String password) {
	    for (Configuratore conf : listaConfiguratori) {
	        if (conf.getUsername().equals(username) && conf.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;
	}

	
    
    
    
    
    
    
    
}