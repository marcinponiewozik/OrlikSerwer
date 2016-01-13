/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beansDB.NewsBean;
import beansDB.UzytkownikBean;
import entitys.News;
import entitys.Uzytkownik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marcin
 */
@ManagedBean(name = "news")
@SessionScoped
public class NewsController  implements Serializable{

 
    private News news;
    private List<News> newsList;
    private News newsGlowny;
    @EJB
    private NewsBean request;
    @EJB
    private UzytkownikBean uzytkownikRequest;

    public News getNewsGlowny() {
        if(!request.getNewsList().isEmpty())
            newsGlowny=request.getNewsList().get(0);
        return newsGlowny;
    }

    public void setNewsGlowny(News newsGlowny) {
        this.newsGlowny = newsGlowny;
    }

    
    public NewsController() {
        news = new News();
    }

    public List<News> getNewsList() {
        newsList = new ArrayList<News>();
        newsList = request.getNewsList();
        if(!newsList.isEmpty())
            newsList.remove(0);
        return newsList;
    }


    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String addNews() {
        Uzytkownik temp = new Uzytkownik();
        temp=uzytkownikRequest.getUserById((Long) Session.getSession().getAttribute("userID"));
        news.setAutor(temp);
        news.setData(new Date());

        request.add(news);
        news = new News();
        return "aktualnosci";
    }

    public void aktualnosci() {
        newsList = new ArrayList<News>();
        newsList = request.getNewsList();
    }
}
