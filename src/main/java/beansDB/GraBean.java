/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDB;

import beans.RejestracjaController;
import entitys.Gra;
import entitys.Uzytkownik;
import java.util.ArrayList;
import java.util.Date;
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
public class GraBean {

    @PersistenceContext
    private EntityManager manager;

    public void add(Gra gra) {
        manager.persist(gra);
    }

    public void dodajChetnego(Uzytkownik uzytkownik, Long id) {
        Gra temp = manager.find(Gra.class, id);
        List<Uzytkownik> lista = temp.getListaChetnych();
        lista.add(uzytkownik);
        temp.setListaChetnych(lista);
        manager.merge(temp);
    }

    public void usunChętnego(Uzytkownik uzytkownik, Long id) {
        Gra temp = manager.find(Gra.class, id);
        List<Uzytkownik> lista = temp.getListaChetnych();
        lista.remove(uzytkownik);
        temp.setListaChetnych(lista);
        manager.merge(temp);
    }

    public void zapiszUzytkownika(Uzytkownik uzytkownik, Long id, int wybor) {
        Gra temp = manager.find(Gra.class, id);
        List<Uzytkownik> lista;
        if (wybor == 1) {
            lista = temp.getListaMoze();
        } else {
            lista = temp.getListaNie();
        }

        lista.add(uzytkownik);
        temp.setListaChetnych(lista);
        manager.merge(temp);

    }

    public void usunDecyzje(Uzytkownik uzytkownik, Long id, int wybor) {
        Gra temp = manager.find(Gra.class, id);
        List<Uzytkownik> lista;
        if (wybor == 1) {
            lista = temp.getListaMoze();
            lista.remove(uzytkownik);
            temp.setListaMoze(lista);
            manager.merge(temp);
        } else {
            lista = temp.getListaNie();
            lista.remove(uzytkownik);
            temp.setListaNie(lista);
            manager.merge(temp);
        }
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

    public boolean sprawdzGracza(Long idGra, Long idUser) {
        Gra temp = manager.find(Gra.class, idGra);
        List<Uzytkownik> lista = temp.getListaChetnych();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == idUser) {
                return true;
            }
        }

        return false;
    }
}
