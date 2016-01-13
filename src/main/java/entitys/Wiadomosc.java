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
public class Wiadomosc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Uzytkownik nadawca;
    @ManyToOne(fetch = FetchType.EAGER)
    private Uzytkownik odbiorca;
    
    private String tresc;   
    @Temporal(TemporalType.TIMESTAMP)
    private Date czas;

    public Uzytkownik getNadawca() {
        return nadawca;
    }

    public void setNadawca(Uzytkownik nadawca) {
        this.nadawca = nadawca;
    }

    public Uzytkownik getOdbiorca() {
        return odbiorca;
    }

    public void setOdbiorca(Uzytkownik odbiorca) {
        this.odbiorca = odbiorca;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Date getCzas() {
        return czas;
    }

    public void setCzas(Date czas) {
        this.czas = czas;
    }

    

 
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Wiadomosc)) {
            return false;
        }
        Wiadomosc other = (Wiadomosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.Wiadomosc[ id=" + id + " ]";
    }
    
}
