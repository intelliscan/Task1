package magpiebridge.vault.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import magpiebridge.vault.jpa.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
	
	User findByUserName(String userName);

}
