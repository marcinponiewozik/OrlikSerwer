/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDB;

import entitys.Gra;
import entitys.GraUser;
import entitys.Uzytkownik;
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
public class GraUserBean {

    @PersistenceContext
    private EntityManager manager;

    public void dodaj(GraUser graUser) {
        manager.persist(graUser);
    }

    public void usun(Long id) {
        manager.remove(manager.find(GraUser.class, id));
    }

    public void zamien(GraUser graUser) {
        manager.merge(graUser);
    }

    public void zapiszDecyzje(Gra gra, Uzytkownik uzytkownik, int decyzja) {
        GraUser graUser = new GraUser();
        graUser.setDecyzja(decyzja);
        graUser.setGra(gra);
        graUser.setUzytkownik(uzytkownik);
        manager.persist(graUser);
    }

    public void zmienDecyzja(Gra gra, Uzytkownik uzytkownik, int decyzja) {
        Query q = manager.createQuery("SELECT g FROM GraUser g  WHERE g.uzytkownik.id=:idUser AND g.gra.id=:idGra", GraUser.class);
        q.setParameter("idUser", uzytkownik.getId());
        q.setParameter("idGra", gra.getId());
        GraUser graUser = new GraUser();
        graUser = (GraUser) q.getResultList().get(0);
        graUser.setDecyzja(decyzja);
        manager.merge(graUser);

    }

    public List<Uzytkownik> wszystkieDecyzje(Gra g, int decyzja) {
        Query q = manager.createQuery("SELECT g FROM GraUser g  WHERE g.gra.id=:idGra AND g.decyzja =:decyzja", GraUser.class);
        q.setParameter("idGra", g.getId());
        q.setParameter("decyzja", decyzja);

        List<GraUser> graUsers = new ArrayList<GraUser>();
        graUsers = q.getResultList();
        List<Uzytkownik> lista = new ArrayList<Uzytkownik>();
        Uzytkownik u;
        if (graUsers.size() > 0) {
            for (GraUser graUser : graUsers) {
                lista.add(graUser.getUzytkownik());
            }
        }
        return lista;

    }

    public void wypiszUzytkownika(Gra gra, Long idUser) {
        Query q = manager.createQuery("SELECT g FROM GraUser g  WHERE g.gra.id =:idGra AND g.uzytkownik.id =:idUser", GraUser.class);
        q.setParameter("idGra", gra.getId());
        q.setParameter("idUser", idUser);

        if (q.getResultList().size() > 0) {
            GraUser graUser = new GraUser();
            graUser = (GraUser) q.getResultList().get(0);
            usun(graUser.getId());
        }
    }

    public boolean sprawdzGracza(Long id, Long idGra) {
        Query q = manager.createQuery("SELECT g FROM GraUser g  WHERE g.gra.id =:idGra AND g.uzytkownik.id =:idUser", GraUser.class);
        q.setParameter("idGra", idGra);
        q.setParameter("idUser", id);
        List<Uzytkownik> lista = q.getResultList();
        return lista.size() > 0;
    }
}
