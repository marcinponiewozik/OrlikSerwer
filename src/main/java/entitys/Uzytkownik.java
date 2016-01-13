/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
public class Uzytkownik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    private boolean administrator;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataZalozenia;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ostatnieLogowanie;

    private boolean zalogowany;

    public boolean isZalogowany() {
        return zalogowany;
    }

    public void setZalogowany(boolean zalogowany) {
        this.zalogowany = zalogowany;
    }

    public Date getDataZalozenia() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String sDtate = sdf.format(dataZalozenia);

        try {
            dataZalozenia = sdf.parse(sDtate);
            return dataZalozenia;
        } catch (ParseException ex) {
            Logger.getLogger(Uzytkownik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataZalozenia;
    }

    public void setDataZalozenia(Date dataZalozenia) {
        this.dataZalozenia = dataZalozenia;
    }

    public Date getOstatnieLogowanie() {
        ostatnieLogowanie=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sDtate = sdf.format(ostatnieLogowanie);

        try {
            ostatnieLogowanie = sdf.parse(sDtate);
            return ostatnieLogowanie;
        } catch (ParseException ex) {
            Logger.getLogger(Uzytkownik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ostatnieLogowanie;
    }

    public void setOstatnieLogowanie(Date ostatnieLogowanie) {
        this.ostatnieLogowanie = ostatnieLogowanie;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
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
        if (!(object instanceof Uzytkownik)) {
            return false;
        }
        Uzytkownik other = (Uzytkownik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marcin.poniewozik.uzytkownik.Uzytkownik[ id=" + id + " ]";
    }
}
