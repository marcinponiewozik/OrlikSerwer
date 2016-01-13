/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDB;

import entitys.Wiadomosc;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marcin
 */
@Stateless
public class WiadomosciBean {
@PersistenceContext
    private EntityManager manager;

    public void dodaj(Wiadomosc w) {
        manager.persist(w);
    }

    public void usun(Long id) {
        Wiadomosc w = manager.find(Wiadomosc.class, id);
        manager.remove(w);
    }

    public void usunN_Pierwszych(Long id, int N) {
        Query q = manager.createQuery("SELECT w FROM Wiadomosc w WHERE w.odbiorca.id =:id ", Wiadomosc.class);
        q.setParameter("id", id);
        List<Wiadomosc> wiadomosci = new ArrayList<Wiadomosc>();

        System.out.println("USUN WIADOMOSC");
        wiadomosci = q.getResultList();
        for (int i = 0; i < N; i++) {
            usun(wiadomosci.get(i).getId());
        }
    }
    
    public List<Wiadomosc> getWiadomosci(Long odbiorca){
        Query q = manager.createQuery("SELECT w FROM Wiadomosc w WHERE w.odbiorca.id =:id ", Wiadomosc.class);
        q.setParameter("id", odbiorca);
        List<Wiadomosc> wiadomosci = new ArrayList<Wiadomosc>();
    
        wiadomosci = q.getResultList();
        return wiadomosci;
    }
}
