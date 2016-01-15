/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDB;

import entitys.Dzial;
import entitys.Komentarz;
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
public class KomenatrzBean {

    
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager manager;
    
    public void dodaj(Komentarz k){
        manager.persist(k);
    }
    
    public void usun(Long id){
        Komentarz k = manager.find(Komentarz.class, id);
        manager.remove(k);
    }
    
    public void zamien(Komentarz k){
        manager.merge(k);
    }
    public Komentarz get(Long id){
        Komentarz k = manager.find(Komentarz.class, id);
        return k;
    }
    public List<Komentarz> getAllByDzial(Dzial dzial,Long idDzialu){
        Query q = manager.createQuery("SELECT k FROM Komentarz k WHERE k.dzial =:dzial AND k.idDzialu =:idDzialu ORDER BY k.data DESC", Komentarz.class);
        q.setParameter("dzial", dzial);
        q.setParameter("idDzialu", idDzialu);
        List<Komentarz> komentarze = new ArrayList<Komentarz>();
        komentarze = q.getResultList();
        return komentarze;
    }
    public int getAllByDzialLicz(Dzial dzial,Long idDzialu){
        Query q = manager.createQuery("SELECT k FROM Komentarz k WHERE k.dzial =:dzial AND k.idDzialu =:idDzialu", Komentarz.class);
        q.setParameter("dzial", dzial);
        q.setParameter("idDzialu", idDzialu);
        List<Komentarz> komentarze = new ArrayList<Komentarz>();
        komentarze = q.getResultList();
        return komentarze.size();
    }
    
    public void removeAllByNews(Dzial dzial,Long id){
        Query q = manager.createQuery("SELECT k FROM Komentarz k WHERE k.dzial =:dzial AND k.idDzialu =:idDzialu", Komentarz.class);
        q.setParameter("dzial", dzial);
        q.setParameter("idDzialu", id);
        List<Komentarz> komentarze = new ArrayList<Komentarz>();
        komentarze = q.getResultList();
        
        for(Komentarz k : komentarze){
            manager.remove(k);
        }
        
    }
}
