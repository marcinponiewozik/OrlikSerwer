/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
public class Komentarz implements Serializable {
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String komentarz;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Uzytkownik uzytkownik;
   
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Enumerated(EnumType.STRING)
    private Dzial dzial;
    
    private Long idDzialu;

    public Long getIdDzialu() {
        return idDzialu;
    }

    public void setIdDzialu(Long idDzialu) {
        this.idDzialu = idDzialu;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    

    

    public Dzial getDzial() {
        return dzial;
    }

    public void setDzial(Dzial dzial) {
        this.dzial = dzial;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Komentarz)) {
            return false;
        }
        Komentarz other = (Komentarz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marcin.poniewozik.komentarz.Komentarz[ id=" + id + " ]";
    }
    
    
}
