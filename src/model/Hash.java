package model;

import org.mindrot.jbcrypt.BCrypt;

public class Hash {
	
    public String hashPassword(String password){
    	// Hash a password for the first time
    	String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

    	// gensalt's log_rounds parameter determines the complexity
    	// the work factor is 2**log_rounds, and the default is 10
    	String candidate = BCrypt.hashpw(password, BCrypt.gensalt(12));

    	// Check that an unencrypted password matches one that has
    	// previously been hashed
    	if (BCrypt.checkpw(candidate, hashed))
    		System.out.println("It matches");
    	else
    		System.out.println("It does not match");
    	
    	return candidate;
    }
}
