package it.unibs.ing;

import java.util.ArrayList;

public class Fruitore {

	private String username;
    private String password;
    private ComprensorioGeografico comprensiorio;
    private String mail;
    
    private static ArrayList<Fruitore> listaFruitori = new ArrayList<>();
    
    
	public Fruitore(String username, String password, ComprensorioGeografico comprensiorio, String mail) {
        this.username = username;
        this.password = password;
        this.comprensiorio = comprensiorio;
        this.mail = mail;
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
    
   
    public ComprensorioGeografico getComprensiorio() {
		return comprensiorio;
	}


	public void setComprensiorio(ComprensorioGeografico comprensiorio) {
		this.comprensiorio = comprensiorio;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public static ArrayList<Fruitore> getListaFruitori() {
		return listaFruitori;
	}


	public static void setListaFruitori(ArrayList<Fruitore> listaFruitori) {
		Fruitore.listaFruitori = listaFruitori;
	}

	public static void addToListaConfiguratori(Fruitore f) {
		listaFruitori.add(f);
	}

	public static boolean userValido(String username) {
        for (Fruitore user : listaFruitori) {
            if (user.getUsername().equals(username)) 
                return false;
        }
        for (Configuratore user : Configuratore.getListaConfiguratori()) {
            if (user.getUsername().equals(username)) 
                return false;
        }
        return true;
    }
    
    
	public static Fruitore loginFruitore(String username, String password) {
	    for (Fruitore user : listaFruitori) {
	        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
	            return user;
	        }
	    }
	    return null;
	}

	public static ComprensorioGeografico getComprensorioFromUser(String user) {
		for(Fruitore f : listaFruitori) {
			if(f.getUsername().equals(user))
				return f.getComprensiorio();
		}
		return null;
	}
	
	public static ArrayList<String> getUserFruitoriFromComprensorio(ComprensorioGeografico c){
		ArrayList<String> lista = new ArrayList<>();
		
		for(Fruitore f : listaFruitori) {
			if(f.getComprensiorio().getNome().equals(c.getNome()))
				lista.add(f.getUsername());				
		}
		return lista;
	}
	

}
