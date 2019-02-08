package fr.yncrea.cir3.securitydemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.yncrea.cir3.securitydemo.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findByContentContaining(String query);
}
