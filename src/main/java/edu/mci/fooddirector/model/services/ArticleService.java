package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Article;
import edu.mci.fooddirector.model.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {

        this.articleRepository = articleRepository;
    }

    public Optional<Article> findFirst() {
        return articleRepository.findById(1L);
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }


}