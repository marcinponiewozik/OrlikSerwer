/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDB;

import beans.RejestracjaController;
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
public class UzytkownikBean {

    @PersistenceContext
    private EntityManager manager;

    public void add(Uzytkownik uzytkownik) {
        manager.persist(uzytkownik);
    }

    /**
     *
     * @param login
     * @return true if user exist false if user does not exist
     */
    public boolean checkSingleUser(String login) {
        Query query = manager.createQuery("SELECT u From Uzytkownik u WHERE u.login =:login ", Uzytkownik.class);
        query.setParameter("login", login);
        int rozmiar = query.getResultList().size();
        return rozmiar != 0;
    }

    /**
     *
     * @param uzytkownik
     * @return login
     */
    public boolean sign(Uzytkownik uzytkownik) {
        String login = uzytkownik.getLogin();
        String haslo = uzytkownik.getPassword();

        Uzytkownik temp = new Uzytkownik();
        Query query = manager.createQuery("SELECT u From Uzytkownik u WHERE u.login =:login ", Uzytkownik.class);
        query.setParameter("login", login);
        temp = (Uzytkownik) query.getResultList().get(0);

        return temp.getPassword().equals(RejestracjaController.haszujMD5(haslo));

    }

    public boolean signWS(Uzytkownik uzytkownik) {
        String login = uzytkownik.getLogin();
        String haslo = uzytkownik.getPassword();

        Uzytkownik temp = new Uzytkownik();
        Query query = manager.createQuery("SELECT u From Uzytkownik u WHERE u.login =:login ", Uzytkownik.class);
        query.setParameter("login", login);
        temp = (Uzytkownik) query.getResultList().get(0);

        return temp.getPassword().equals(haslo);

    }

    /**
     * @param login
     * @return user where user.login.equal(login)
     *
     */
    public Uzytkownik getUser(String login) {
        Uzytkownik temp = new Uzytkownik();
        Query query = manager.createQuery("SELECT u From Uzytkownik u WHERE u.login =:login ", Uzytkownik.class);
        query.setParameter("login", login);
        temp = (Uzytkownik) query.getResultList().get(0);
        temp.setZalogowany(true);
        manager.merge(temp);
        return temp;
    }

    public Uzytkownik getUserById(Long id) {
        Uzytkownik temp = new Uzytkownik();

        temp = manager.find(Uzytkownik.class, id);
        manager.merge(temp);
        return temp;
    }

    public void updateLastLoginDate(String login) {
        Query query = manager.createQuery("UPDATE Uzytkownik u SET u.ostatnieLogowanie =:date WHERE u.login =:login ", Uzytkownik.class);
        query.setParameter("date", new Date());
        query.setParameter("login", login);
        query.executeUpdate();
    }

    public void updatePassword(String pass, String login) {
        Query query = manager.createQuery("UPDATE Uzytkownik u SET u.password =:nowe WHERE u.login =:login ", Uzytkownik.class);
        query.setParameter("nowe", pass);
        query.setParameter("login", login);
        query.executeUpdate();
    }

    public void updateEmail(String email, String login) {
        Query query = manager.createQuery("UPDATE Uzytkownik u SET u.email =:email WHERE u.login =:login ", Uzytkownik.class);
        query.setParameter("email", email);
        query.setParameter("login", login);
        query.executeUpdate();
    }

    public void zamien(Uzytkownik u) {
        manager.merge(u);
    }

    public List<Uzytkownik> getAllByTeam(Long id) {
        Query q = manager.createQuery("SELECT u FROM Uzytkownik u WHERE u.team.id =:id", Uzytkownik.class);
        q.setParameter("id", id);
        List<Uzytkownik> lista = new ArrayList<Uzytkownik>();
        lista = q.getResultList();
        return lista;
    }

    public List<Uzytkownik> getAll() {
        Query q = manager.createQuery("SELECT u FROM Uzytkownik u", Uzytkownik.class);
        List<Uzytkownik> lista = new ArrayList<Uzytkownik>();
        lista = q.getResultList();
        return lista;
    }

    public List<Uzytkownik> wyszukaj(String fragment) {
        Query q = manager.createQuery("SELECT u FROM Uzytkownik u WHERE u.login LIKE :fragment", Uzytkownik.class);
        q.setParameter("fragment", fragment+"%");
        List<Uzytkownik> lista = new ArrayList<Uzytkownik>();
        lista = q.getResultList();
        return lista;
    }

    public void usun(Long id) {
        Uzytkownik temp = new Uzytkownik();
        temp = manager.find(Uzytkownik.class, id);
        manager.remove(temp);
    }
}
