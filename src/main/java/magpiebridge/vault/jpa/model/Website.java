package magpiebridge.vault.jpa.model;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import magpiebridge.vault.jpa.converters.CryptoConverter;

/**
 * Represents login credentials for web sites.
 * 
 *
 */
@Entity
public class Website {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User user;
	
	/**
	 * The following information will be encrypted when they are stored in the data base. 
	 */
	@Convert(converter = CryptoConverter.class)
	private String url;
	@Convert(converter = CryptoConverter.class)
	private String name;
	@Convert(converter = CryptoConverter.class)
	private String userName;
	@Convert(converter = CryptoConverter.class)
	private String password;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
}
