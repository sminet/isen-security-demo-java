package fr.yncrea.cir3.securitydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.yncrea.cir3.securitydemo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsernameAndPassword(String username, String password);
}
