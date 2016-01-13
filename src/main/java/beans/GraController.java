/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import beansDB.GraBean;
import beansDB.KomenatrzBean;
import beansDB.UzytkownikBean;
import entitys.Dzial;
import entitys.Gra;
import entitys.Komentarz;
import entitys.Uzytkownik;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Marcin
 */
@ManagedBean(name = "graBean")
@ViewScoped
public class GraController {

    
    private Gra gra;
//    private Date data;

    private String dataString;
    private String liczbaChetnych = "";

    private String komentarz = "";

    private boolean chetny = false;
    private List<Komentarz> komentarze;
    @EJB
    private GraBean request;
    @EJB
    private UzytkownikBean uzytkownikRequest;
    @EJB
    private KomenatrzBean komentarzRequest;

    public List<Komentarz> getKomentarze() {
        if (gra != null) {
            komentarze = komentarzRequest.getAllByDzial(Dzial.GRA, gra.getId());
        }
        return komentarze;
    }

    public void setKomentarze(List<Komentarz> komentarze) {
        this.komentarze = komentarze;
    }

    public boolean isChetny() {
        Uzytkownik temp = new Uzytkownik();
        temp = uzytkownikRequest.getUserById((Long) Session.getSession().getAttribute("userID"));
        chetny = request.sprawdzGracza(gra.getId(),temp.getId());
        return chetny;
    }

    public void setChetny(boolean chetny) {
        this.chetny = chetny;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public String getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }

    public String getLiczbaChetnych() {
        return liczbaChetnych;
    }

    public void setLiczbaChetnych(String liczbaChetnych) {
        this.liczbaChetnych = liczbaChetnych;
    }

    public Gra getGra() {
        pokazInformacjeZDnia();
        return gra;
    }

    public void setGra(Gra gra) {
        this.gra = gra;
    }

//    public Date getData() {
//        return data;
//    }
//
//    public void setData(Date data) {
//        this.data = data;
//    }

    public GraController() {
        gra = new Gra();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataString = sdf.format(new Date());
    }

    public void pokazInformacjeZDnia() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            data = sdf.parse(dataString);
//        } catch (ParseException ex) {
//            Logger.getLogger(GraBean.class.getName()).log(Level.SEVERE, null, ex);
//        }

        Date data = new Date();
        if (request.sprawdzCzyGraja(data)) {
            gra = request.wezGraByData(data);
        } else {
            dodajGre();
            gra = request.wezGraByData(data);
        }
    }

    public void dodajGre() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date nowa = null;
//        try {
//            nowa = sdf.parse(dataString);
//        } catch (ParseException ex) {
//            Logger.getLogger(GraBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
        gra = new Gra();
        gra.setData(new Date());
        gra.setDodatkoweInformacje("Gra dnia");
        request.add(gra);
    }

    public void zapiszWypisz() {
        Uzytkownik temp = new Uzytkownik();
        temp = uzytkownikRequest.getUserById((Long) Session.getSession().getAttribute("userID"));
        boolean flag = false;
        for (Uzytkownik u : gra.getListaChetnych()) {
            if (u.getId() == temp.getId()) {
                flag = true;
            }
        }
        if (flag) {
            request.usunChętnego(temp, gra.getId());
            chetny = false;
        } else {
            request.dodajChetnego(temp, gra.getId());
            chetny = true;
        }
    }

    public String pokazDate() {
        Date data = new Date();
        int dzien;
        int miesiąc;
        int rok;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);

        dzien = calendar.get(Calendar.DAY_OF_MONTH);
        miesiąc = calendar.get(Calendar.MONTH);
        rok = calendar.get(Calendar.YEAR);
        String wynik;

        String miesiacPol = getPOLName(miesiąc);
        String dzienPol = new SimpleDateFormat("EEEE", Locale.ROOT).format(data);

        wynik = getPOLNameDay(dzienPol) + " " + dzien + " " + miesiacPol + " " + rok;
        return wynik;

    }

    public String getPOLNameDay(String dzien) {
        if (dzien.equals("Monday")) {
            return "Poniedziałek";
        } else if (dzien.equals("Tuesday")) {
            return "Wtorek";
        } else if (dzien.equals("Wednesday")) {
            return "Środa";
        } else if (dzien.equals("Thursday")) {
            return "Czwartek";
        } else if (dzien.equals("Friday")) {
            return "Piątek";
        } else if (dzien.equals("Saturday")) {
            return "Sobota";
        } else {
            return "Niedziela";
        }
    }

    public String getPOLName(int miesiac) {
        if (miesiac == 0) {
            return "Styczeń";
        } else if (miesiac == 1) {
            return "Luty";
        } else if (miesiac == 2) {
            return "Marzec";
        } else if (miesiac == 3) {
            return "Kwiecień";
        } else if (miesiac == 4) {
            return "Maj";
        } else if (miesiac == 5) {
            return "Czerwiec";
        } else if (miesiac == 6) {
            return "Lipiec";
        } else if (miesiac == 7) {
            return "Sierpień";
        } else if (miesiac == 8) {
            return "Wrzesień";
        } else if (miesiac == 9) {
            return "Październik";
        } else if (miesiac == 10) {
            return "Listopad";
        } else {
            return "Grudzień";
        }
    }

    public void dodajKomentarz() {
        Komentarz kom = new Komentarz();
        kom.setData(new Date());
        kom.setDzial(Dzial.GRA);
        kom.setKomentarz(komentarz);
        kom.setIdDzialu(gra.getId());
        Uzytkownik temp = new Uzytkownik();
        temp = uzytkownikRequest.getUserById((Long) Session.getSession().getAttribute("userID"));
        kom.setUzytkownik(temp);
        komentarzRequest.dodaj(kom);
        komentarz = "";
    }
    
}
