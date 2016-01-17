/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Marcin
 */
@Entity
public class GraUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Uzytkownik.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID",referencedColumnName = "id")
    private Uzytkownik uzytkownik;
    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Gra.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "GRA_ID",referencedColumnName = "id")
    private Gra gra;
    private int decyzja;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Gra getGra() {
        return gra;
    }

    public void setGra(Gra gra) {
        this.gra = gra;
    }

    public int getDecyzja() {
        return decyzja;
    }

    public void setDecyzja(int decyzja) {
        this.decyzja = decyzja;
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
        if (!(object instanceof GraUser)) {
            return false;
        }
        GraUser other = (GraUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.GraUser[ id=" + id + " ]";
    }

}
