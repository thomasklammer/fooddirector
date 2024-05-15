package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Article;
import edu.mci.fooddirector.model.repositories.ArticleRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {

        this.articleRepository = articleRepository;
    }


    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {

        return articleRepository.findAll().stream()
                .filter(x -> !x.isDeleted())
                .collect(Collectors.toList());
    }

    public void deleteArticle(Article article) {
        article.setDeleted(true);
        articleRepository.save(article);
    }

}