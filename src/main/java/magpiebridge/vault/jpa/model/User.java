package magpiebridge.vault.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;
	
	private String userName;
	
	private String salt;
	private String saltEncrypted;

	private String encryptionKey;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public void setSaltEncrypted(String saltEncrypted) {
		this.saltEncrypted = saltEncrypted;
	}
	
	public String saltEncrypted() {
		return saltEncrypted;
	}
	
	public String encryptionKey() {
		return encryptionKey;
	}
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
	
	
}
