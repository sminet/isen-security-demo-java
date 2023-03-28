package fr.yncrea.cir3.securitydemo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.yncrea.cir3.securitydemo.dto.CardValidation;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fr.yncrea.cir3.securitydemo.domain.Article;
import fr.yncrea.cir3.securitydemo.domain.User;
import fr.yncrea.cir3.securitydemo.dto.Message;
import fr.yncrea.cir3.securitydemo.repository.ArticleRepository;

@Controller
@RequestMapping("/injection")
public class InjectionController {
	@Autowired
	private JdbcTemplate db;
	
	@Autowired
	private ArticleRepository articles;
	
	@GetMapping("/login")
	public String login() {
		return "injection-login";
	}
	
	@PostMapping("/login")
	public String loginPerform(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
		// generate query
		String sql = "SELECT id, username, password FROM users WHERE username='" + username + "' AND password='" + password + "'";
		
		// load user from database (null if not found)
		User u = db.query(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User object = new User();
				object.setId(rs.getLong(1));
				object.setUsername(rs.getString(2));
				object.setPassword(rs.getString(3));
				return object;
			}
		}).stream().findFirst().orElse(null);
		
		if (u == null) {
			model.addAttribute("message", new Message("Incorrect credentials", "danger"));
			return "injection-login";
		}
		
		model.addAttribute("message", new Message("Welcome " + u.getUsername() + ", you have successfully logged in.", "success"));
		return "injection-login";
	}
	
	@GetMapping("/advanced")
	public String advancedWelcome(Model model) {
		model.addAttribute("articles", articles.findAll());
		return "injection-advanced";
	}
	
	@GetMapping("/article")
	public String advancedWelcome(Model model, @RequestParam("id") String id) {
		String sql = "SELECT id, title, content FROM articles WHERE id=" + id;
		
		// load article from database (null if not found)
		Article a = db.query(sql, new RowMapper<Article>() {
			@Override
			public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
				Article object = new Article();
				object.setId(rs.getLong(1));
				object.setTitle(rs.getString(2));
				object.setContent(rs.getString(3));
				return object;
			}
		}).stream().findFirst().orElseThrow(() -> new ObjectNotFoundException(id, Article.class.getCanonicalName()));
		
		model.addAttribute("article", a);
		return "injection-advanced-article";
	}

	@GetMapping("/blind")
	public String blindWelcome() {
		return "injection-blind";
	}

	@PostMapping("/blind/ajax")
	public ResponseEntity<Message> blindWelcome(@RequestBody CardValidation card) {
		String sql = "SELECT count(1) FROM cards WHERE username = '" + card.getUsername() + "' AND number = '" + card.getNumber() + "'";
		Long count = db.queryForObject(sql, Long.class);

		return ResponseEntity.ok(new Message(count <= 0 ? "KO" : "OK", "info"));
	}
}
