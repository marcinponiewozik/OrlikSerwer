/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beansDB.KomenatrzBean;
import beansDB.NewsBean;
import beansDB.UzytkownikBean;
import entitys.Dzial;
import entitys.Komentarz;
import entitys.News;
import entitys.Uzytkownik;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marcin
 */
@ManagedBean(name = "newsEdit")
@ViewScoped
public class NewsZarzadzajBean {

    
    private Long idNewsa;
    private News newsDetails;

    private TimeZone timeZone;

    private List<Komentarz> komentarze;
    
    private String trescKomentarza;

    private Komentarz komentarz;
    
    private String nowaTresc;
    private String nowyTytul;
    private boolean edytowany=false;
    
    @EJB
    private KomenatrzBean komentarzRequest;
    @EJB
    private NewsBean request;
    @EJB
    private UzytkownikBean uzytkownikRequest;
    @PostConstruct
    public void initNewsID(){
        idNewsa= Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("newsID"));
        komentarz=new Komentarz();
        timeZone = TimeZone.getDefault();
    }

    public String getNowaTresc() {
        return nowaTresc;
    }

    public void setNowaTresc(String nowaTresc) {
        this.nowaTresc = nowaTresc;
    }

    public String getNowyTytul() {
        return nowyTytul;
    }

    public void setNowyTytul(String nowyTytul) {
        this.nowyTytul = nowyTytul;
    }

    
    public boolean isEdytowany() {
        return edytowany;
    }

    public void setEdytowany(boolean edytowany) {
        this.edytowany = edytowany;
    }

    
    public Komentarz getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(Komentarz komentarz) {
        this.komentarz = komentarz;
    }
    
    
    public List<Komentarz> getKomentarze() {
        komentarze = komentarzRequest.getAllByDzial(Dzial.NEWS, idNewsa);
        return Collections.unmodifiableList(komentarze);
    }

    public String getTrescKomentarza() {
        return trescKomentarza;
    }

    public void setTrescKomentarza(String trescKomentarza) {
        this.trescKomentarza = trescKomentarza;
    }

    public void setKomentarze(List<Komentarz> komentarze) {
        this.komentarze = komentarze;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public NewsZarzadzajBean() {
        newsDetails = new News();
    }

    public Long getIdNewsa() {
        return idNewsa;
    }

    public void setIdNewsa(Long idNewsa) {
        this.idNewsa = idNewsa;
    }

    public News getNewsDetails() {
        newsDetails = request.getById(idNewsa);
        nowaTresc = newsDetails.getTresc();
        nowyTytul = newsDetails.getTytul();
        return newsDetails;
    }

    public void setNewsDetails(News newsDetails) {
        this.newsDetails = newsDetails;
    }

    public String usunNews() {
        request.removeNews(idNewsa);
        komentarzRequest.removeAllByNews(Dzial.NEWS,idNewsa);
        return "aktualnosci";
    }

    public String usunKomentarz(Long id) {
        komentarzRequest.usun(id);
        return null;
    }
    public String dodajKomentarz() {
        Komentarz temp = new Komentarz();
        temp.setData(new Date());
        temp.setDzial(Dzial.NEWS);
        temp.setIdDzialu(idNewsa);
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik = uzytkownikRequest.getUserById((Long) Session.getSession().getAttribute("userID"));
        temp.setUzytkownik(uzytkownik);
        temp.setKomentarz(trescKomentarza);
        komentarzRequest.dodaj(temp);
        trescKomentarza="";
        return null;
    }
    
    public String edytujKomentarz(Long id){
        komentarz=komentarzRequest.get(id);
        trescKomentarza = komentarz.getKomentarz();
        edytowany=true;
        return null;   
    }
    public String zatwierdzKomentarz(){
        komentarz.setKomentarz(trescKomentarza);
        komentarzRequest.zamien(komentarz);
        komentarz=new Komentarz();
        trescKomentarza ="";
        edytowany=false;
        return null;   
    }
    public String anulujZmiany(){
        komentarz=new Komentarz();
        trescKomentarza ="";
        edytowany=false;
        return null;   
    }
    
}
