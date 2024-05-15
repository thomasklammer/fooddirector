package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Article;
import edu.mci.fooddirector.model.repositories.ArticleRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {

        this.articleRepository = articleRepository;
    }

    public Optional<Article> findFirst() {
       var articles = articleRepository.findAll();

       return articles.stream().findFirst();
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}