/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDB;

import entitys.Gra;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marcin
 */
@Stateless
public class GraBean {

    @PersistenceContext
    private EntityManager manager;

    public void add(Gra gra) {
        manager.persist(gra);
    }




    public Gra getGra(Long id) {
        return manager.find(Gra.class, id);
    }

    public Gra wezGraByData(Date data) {
        Query q = manager.createQuery("SELECT g FROM Gra g WHERE g.data =:data", Gra.class);
        q.setParameter("data", data);

        Gra gra = new Gra();
        gra = (Gra) q.getResultList().get(0);
        return gra;
    }

    public boolean sprawdzCzyGraja(Date data) {
        Query query = manager.createQuery("SELECT g FROM Gra g WHERE g.data =:data", Gra.class);
        query.setParameter("data",data);

        if (query.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}
