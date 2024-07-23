package it.unibs.ing;
import java.io.Serializable;

public class Configuratore implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    
    private static final String USERNAME_PREDEFINITO = "user";
    private static final String PASSWORD_PREDEFINITO = "password";


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
