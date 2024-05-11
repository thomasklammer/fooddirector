package edu.mci.fooddirector.data.repositories;

import edu.mci.fooddirector.data.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
