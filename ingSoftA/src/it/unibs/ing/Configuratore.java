package it.unibs.ing;
import java.io.Serializable;
import java.util.ArrayList;

public class Configuratore implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    private static final String USERNAME_PREDEFINITO = "user";
    private static final String PASSWORD_PREDEFINITO = "password";
    
	private String username;
    private String password;
    



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


	private String getUsername() {
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
    
   
    public static boolean userValido(String user, ArrayList<Configuratore> list) {
        for (Configuratore conf : list) {
            if (conf.getUsername().equals(user)) 
                return false;
        }
        return true;
    }
    
    
	public static boolean loginConfiguratore(String username, String password, ArrayList<Configuratore> list) {
	    for (Configuratore conf : list) {
	        if (conf.getUsername().equals(username) && conf.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;
	}

	
    
    
    
    
    
    
    
}
