/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDB;

import entitys.News;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author Marcin
 */
@Stateless
public class NewsBean {

    
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager manager;

    public void add(News news) {
        manager.persist(news);
    }
    public News getById(Long id){
        News n = new News();
        n=manager.find(News.class,id);
        return n;
    }
    public List<News> getNewsList(){
        List<News> newsList = new ArrayList<News>();
        newsList=manager.createQuery("SELECT c From News c ORDER BY c.data DESC", News.class).getResultList();
        return newsList;
    }
    public List<News> getByUzytkownikId(Long id){
        Query q = manager.createQuery("SELECT n FROM News n WHERE n.autor.id =:id ORDER BY n.data DESC", News.class);
        q.setParameter("id", id);
        List<News> lista = new ArrayList<News>();
        lista = q.getResultList();
        return lista;
        
    }
    
    public void removeNews(Long id){
        News n= new News();
        n=manager.find(News.class, id);
        manager.remove(n);
    }
    
    public void zamien(News news){
        manager.merge(news);
    }
    
}
