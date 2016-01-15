/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
public class Gra implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date data;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Chetni",
            joinColumns = @JoinColumn(name = "Gra_Id"),
            inverseJoinColumns = @JoinColumn(name = "Uzytkownik_Id"))
    private List<Uzytkownik> listaChetnych;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Niezdecydowani",
            joinColumns = @JoinColumn(name = "Gra_Id"),
            inverseJoinColumns = @JoinColumn(name = "Uzytkownik_Id"))
    private List<Uzytkownik> listaMoze;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Brak",
            joinColumns = @JoinColumn(name = "Gra_Id"),
            inverseJoinColumns = @JoinColumn(name = "Uzytkownik_Id"))
    private List<Uzytkownik> listaNie;

    private String dodatkoweInformacje;

    public String getDodatkoweInformacje() {
        return dodatkoweInformacje;
    }

    public void setDodatkoweInformacje(String dodatkoweInformacje) {
        this.dodatkoweInformacje = dodatkoweInformacje;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    

    
    @XmlTransient
    public List<Uzytkownik> getListaChetnych() {
        return this.listaChetnych;
    }

    public void setListaChetnych(List<Uzytkownik> listaChetnych) {
        this.listaChetnych = listaChetnych;
    }
    @XmlTransient
    public List<Uzytkownik> getListaMoze() {
        return listaMoze;
    }

    public void setListaMoze(List<Uzytkownik> listaMoze) {
        this.listaMoze = listaMoze;
    }
    @XmlTransient
    public List<Uzytkownik> getListaNie() {
        return listaNie;
    }

    public void setListaNie(List<Uzytkownik> listaNie) {
        this.listaNie = listaNie;
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
        if (!(object instanceof Gra)) {
            return false;
        }
        Gra other = (Gra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marcin.poniewozik.gra.Gra[ id=" + id + " ]";
    }
    
}
