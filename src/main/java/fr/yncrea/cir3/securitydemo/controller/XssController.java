package fr.yncrea.cir3.securitydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.yncrea.cir3.securitydemo.domain.Article;
import fr.yncrea.cir3.securitydemo.repository.ArticleRepository;

@Controller
@RequestMapping("/xss")
public class XssController {
	@Autowired
	private ArticleRepository articles;
	
	@GetMapping("/reflected")
	public String reflected(Model model, @RequestParam(value = "q", required = false) String query) {
		model.addAttribute("q", query);
		
		if (query != null) {
			model.addAttribute("matches", articles.findByContentContaining(query));
		}
		
		return "xss-reflected";
	}
	
	@GetMapping("/stored")
	public String stored(Model model) {
		model.addAttribute("articles", articles.findAll());
		return "xss-stored";
	}
	
	@PostMapping("/stored")
	public String stored(@RequestParam("title") String title, @RequestParam("content") String content) {
		Article object = new Article();
		object.setTitle(title);
		object.setContent(content);
		articles.save(object);
		
		return "redirect:/xss/stored";
	}
}
