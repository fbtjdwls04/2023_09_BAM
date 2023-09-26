package com.KoreaIT.java.BAM.Dao;

import java.util.ArrayList;

import com.KoreaIT.java.BAM.dto.Article;

public class ArticleDao extends Dao{
	ArrayList<Article> articles;
	
	public ArticleDao() {
		articles = new ArrayList<>();
	}
	public int size() {
		return articles.size();
	}
	public void add(Article article) {
		articles.add(article);
		lastId = article.id;
	}
	public ArrayList<Article> getArticles() {
		return articles;
	}
	public Article getArticleById(int id) {
		int index = getArticleIndexById(id);
		if(index == -1) {
			return null;
		}
		return articles.get(index);
	}
	private int getArticleIndexById(int id) {
		int index = -1;
		for(int i = 0; i < articles.size(); i++) {
			if(articles.get(i).id == id) {
				return i;
			}
		}
		return index;
	}
	public void remove(Article article) {
		articles.remove(article);
	}
	public ArrayList<Article> getFilterArticlesBySearchKeyword(String searchKeyword) {
		
		ArrayList<Article> filterArticles = new ArrayList<>();
		
		for(Article a : articles) {
			if(a.title.contains(searchKeyword)) {
				filterArticles.add(a);
			}
		}
		
		return filterArticles;
	}
}
