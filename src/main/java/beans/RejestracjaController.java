/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beansDB.UzytkownikBean;
import entitys.Uzytkownik;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Marcin
 */
@ManagedBean(name = "rejestracja")
@RequestScoped
public class RejestracjaController {

    
    private static MessageDigest md;

    
    private Uzytkownik uzytkownik;
    private String komunikat = "";

    @EJB
    private UzytkownikBean request;

    public static MessageDigest getMd() {
        return md;
    }

    public static void setMd(MessageDigest md) {
        RejestracjaController.md = md;
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

    public RejestracjaController() {
        uzytkownik = new Uzytkownik();
    }

    @PostConstruct
    public void init(){
        this.uzytkownik = new Uzytkownik();
    }


    public String dodaj() {
        if (request.getAll().isEmpty()) {
            uzytkownik.setAdministrator(true);
        } else {
            uzytkownik.setAdministrator(false);
        }

        uzytkownik.setDataZalozenia(new Date());
        uzytkownik.setZalogowany(false);
        String tempPass1 = haszujMD5(uzytkownik.getPassword());
        uzytkownik.setPassword(tempPass1);
        request.add(uzytkownik);

        return "index";
    }

    public static String haszujMD5(String password) {
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digestet = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digestet.length; i++) {
                sb.append(Integer.toHexString(0xff & digestet[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger("Blad ");
        }
        return null;
    }
    
    public static String dateToString(Date data){
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(data);
    }
    public static String dateToStringHm(Date data){
        SimpleDateFormat dateFormat = new  SimpleDateFormat("HH:mm");
        return dateFormat.format(data);
    }
}
