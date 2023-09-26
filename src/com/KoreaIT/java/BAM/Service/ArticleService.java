package com.KoreaIT.java.BAM.Service;

import java.util.ArrayList;

import com.KoreaIT.java.BAM.Container.Container;
import com.KoreaIT.java.BAM.Dao.ArticleDao;
import com.KoreaIT.java.BAM.dto.Article;

public class ArticleService {
	ArticleDao articleDao;
	public ArticleService() {
		articleDao = Container.articleDao;	
	}
	public void add(Article article) {
		articleDao.add(article);
	}
	public int size() {
		return articleDao.size();
	}
	public ArrayList<Article> getArticles(){
		return articleDao.getArticles();
	}
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}
	public void remove(Article article) {
		articleDao.remove(article);
	}
	public int getNewId() {
		return articleDao.getNewId();
	}
	public ArrayList<Article> getFilterArticlesBySearchKeyword(String searchKeyword) {
		return articleDao.getFilterArticlesBySearchKeyword(searchKeyword);
	}
}
