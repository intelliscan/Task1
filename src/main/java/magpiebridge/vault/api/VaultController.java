package magpiebridge.vault.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import magpiebridge.vault.jpa.repository.WebsiteRepository;

@Controller
public class VaultController {

  @Autowired
  private WebsiteRepository websiteRepo;

  @GetMapping("/")
  public String getWebsites(Model model) {
    model.addAttribute("websites", websiteRepo.findAll(Pageable.unpaged()));
    return "home";
  }
}
