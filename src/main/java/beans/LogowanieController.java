/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beansDB.UzytkownikBean;
import entitys.Uzytkownik;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */
@ManagedBean(name = "logowanie")
@SessionScoped
public class LogowanieController implements Serializable {

   
    private Uzytkownik uzytkownik;

    private boolean zalogowany = false;
    private String komunikat = "";


    private String stareHaslo;
    private String haslo;
    private String powtorzHaslo;
    private String nowyEmail;
    private String staryEmail;
    
    @EJB
    private UzytkownikBean request;


    public String getStaryEmail() {
        return staryEmail;
    }

    public void setStaryEmail(String staryEmail) {
        this.staryEmail = staryEmail;
    }

    public String getNowyEmail() {
        return nowyEmail;
    }

    public void setNowyEmail(String nowyEmail) {
        this.nowyEmail = nowyEmail;
    }

    public String getStareHaslo() {
        return stareHaslo;
    }

    public void setStareHaslo(String stareHaslo) {
        this.stareHaslo = stareHaslo;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getPowtorzHaslo() {
        return powtorzHaslo;
    }

    public void setPowtorzHaslo(String powtorzHaslo) {
        this.powtorzHaslo = powtorzHaslo;
    }

    public boolean isZalogowany() {
        return zalogowany;
    }

    public void setZalogowany(boolean zalogowany) {
        this.zalogowany = zalogowany;
    }

    public String getKomunikat() {
        return komunikat;
    }

    public void setKomunikat(String komunikat) {
        this.komunikat = komunikat;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public String zaloguj() {
        if (request.checkSingleUser(uzytkownik.getLogin())) {
            if (request.sign(uzytkownik)) {
                uzytkownik = request.getUser(uzytkownik.getLogin());

                komunikat = "Jesteś zalogowany jako " + uzytkownik.getLogin();

                //request.updateLastLoginDate(uzytkownik.getLogin());
                zalogowany = true;
                HttpSession session = Session.getSession();
                session.setAttribute("userID", uzytkownik.getId());
                return "index";
            } else {
                komunikat = "Błędny dane";
            }
        } else {
            komunikat = "Błędne dane";
        }

        return "logowanie";
    }

    public String wyloguj() {
        uzytkownik = request.getUserById((Long) Session.getSession().getAttribute("userID"));
        uzytkownik.setZalogowany(false);
        request.zamien(uzytkownik);
        Session.getSession().invalidate();
        return "index";
    }

    public String wylogujPoUsunieciu() {
        uzytkownik = request.getUserById((Long) Session.getSession().getAttribute("userID"));
        uzytkownik.setZalogowany(false);
        request.usun(uzytkownik.getId());
        Session.getSession().invalidate();
        return "index";
    }

    public LogowanieController() {
        uzytkownik = new Uzytkownik();
        zalogowany = false;
    }

    public String zmienHaslo() {
        if (RejestracjaController.haszujMD5(stareHaslo).equals(uzytkownik.getPassword())) {
            if (RejestracjaController.haszujMD5(haslo).equals(RejestracjaController.haszujMD5(powtorzHaslo))) {
                Uzytkownik temp = new Uzytkownik();
                temp = request.getUserById((Long) Session.getSession().getAttribute("userID"));
                temp.setPassword(RejestracjaController.haszujMD5(haslo));
                stareHaslo = "";
                haslo = "";
                powtorzHaslo = "";
                return wyloguj();

            } else {
                komunikat = "Hasła się różnią!!";
                return "zmianaHasla";
            }
        } else {
            komunikat = "Aktualne hasło jest inne!!";
            return "zmianaHasla";
        }

    }

    public String zmienEmail() {

        if (RejestracjaController.haszujMD5(haslo).equals(uzytkownik.getPassword())) {
            if (uzytkownik.getEmail().equals(staryEmail)) {
                Uzytkownik temp = new Uzytkownik();
                temp = request.getUserById((Long) Session.getSession().getAttribute("userID"));
                temp.setEmail(nowyEmail);
                return wyloguj();

            } else {
                komunikat = "Podany adres email różni się od obecnego!!";
                return "zmianaHasla";
            }
        } else {
            komunikat = "Aktualne hasło jest inne!!";
            return "zmianaHasla";
        }

    }

    
}
