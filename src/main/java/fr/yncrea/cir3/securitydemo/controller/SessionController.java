package fr.yncrea.cir3.securitydemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.yncrea.cir3.securitydemo.domain.User;
import fr.yncrea.cir3.securitydemo.repository.UserRepository;

@Controller
@RequestMapping("/session")
public class SessionController {
	@Autowired
	private UserRepository users;
	
	@GetMapping("fixation")
	public String fixation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.getSession(true);
			return "redirect:/session/fixation";
		}
		
		model.addAttribute("users", users.findAll());
		
		return "session-fixation";
	}
	
	@GetMapping("fixation/login")
	public String fixation(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.getSession(true);
			return "redirect:/session/fixation/login";
		}

		return "session-fixation-login";
	}
	
	@GetMapping("fixation/logout")
	public String fixationLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		
		return "redirect:/session/fixation/login";
	}
	
	@PostMapping("fixation/login")
	public String fixationLogin(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) {
		User object = users.findByUsernameAndPassword(username, password);
		if (object == null) {
			return "redirect:/session/fixation/login?error";
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("userLogged", object.getUsername());
		
		return "redirect:/session/fixation/login";
	}
}
