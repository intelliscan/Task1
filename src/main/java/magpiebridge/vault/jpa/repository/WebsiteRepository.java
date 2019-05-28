package magpiebridge.vault.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import magpiebridge.vault.jpa.model.Website;

@RepositoryRestResource(collectionResourceRel = "websites", path ="websites")
public interface WebsiteRepository extends PagingAndSortingRepository<Website, Long>{

  
	@Override
	@Query("select w from Website w where w.user.userName = ?#{principal.username}")
	Page<Website> findAll(Pageable pageable);
	
	
	@Query("select w from Website w where w.user.userName = ?#{principal.username} and w.url = :url")
	List<Website> findByUrl(@Param("url") String url);
}
