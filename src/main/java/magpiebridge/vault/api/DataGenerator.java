package magpiebridge.vault.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import magpiebridge.vault.jpa.model.User;
import magpiebridge.vault.jpa.model.Website;
import magpiebridge.vault.jpa.repository.WebsiteRepository;
import magpiebridge.vault.service.LoginUtils;
import magpiebridge.vault.service.UserDetailsServiceImpl;
/**
 * This is a data generator for demo, it generates two example users. No vulnerabilities in this class.
 * 
 *  
 */
@Component
public class DataGenerator implements CommandLineRunner{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private WebsiteRepository websiteRepo;
	
	@Autowired
	private LoginUtils loginUtils;

	@Override
	public void run(String... args) throws Exception {
		User user1 = userDetailsService.createUser("alice", "123456");
		User user2 = userDetailsService.createUser("bob", "abcdefg");

		
		loginUtils.loginDirectly("alice", "123456");
		
		Website website0 = new Website();
		website0.setName("Awesome site");
		website0.setUrl("www.awesomesite.com");
		website0.setPassword("veriSecureP455w0rd");
		website0.setUserName("iamalice");
		website0.setUser(user1);
		websiteRepo.save(website0);
		
		Website website1 = new Website();
    website1.setName("Even Better site");
    website1.setUrl("www.awesomesite2awesome4u.com");
    website1.setPassword("realliSecureP455w0rd");
    website1.setUserName("iamalice");
    website1.setUser(user1);
    websiteRepo.save(website1);
		
		loginUtils.loginDirectly("bob", "abcdefg");
		
		Website website2 = new Website();
		website2.setName("Bad site");
		website2.setUrl("www.badsite.com");
		website2.setPassword("veriUnSecureP455w0rd");
		website2.setUserName("iambob");
		website2.setUser(user2);
		websiteRepo.save(website2);
		
	  SecurityContextHolder.getContext().setAuthentication(null);
	}

	
	
}
