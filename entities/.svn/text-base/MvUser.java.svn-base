/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meadowvale.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Eric
 */
@Entity
@Table(name = "mv_user")
@NamedQueries({
    @NamedQuery(name = "MvUser.findAll", query = "SELECT m FROM MvUser m"),
    @NamedQuery(name = "MvUser.findById", query = "SELECT m FROM MvUser m WHERE m.id = :id"),
    @NamedQuery(name = "MvUser.findByUserName", query = "SELECT m FROM MvUser m WHERE m.userName = :userName"),
    @NamedQuery(name = "MvUser.findByUserPassword", query = "SELECT m FROM MvUser m WHERE m.userPassword = :userPassword"),
    @NamedQuery(name = "MvUser.findByActive", query = "SELECT m FROM MvUser m WHERE m.active = :active"),
    @NamedQuery(name = "MvUser.findByDateCreated", query = "SELECT m FROM MvUser m WHERE m.dateCreated = :dateCreated")})
public class MvUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @Column(name = "user_password")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mvStudentId")
    private Collection<MvWeeklyLog> mvWeeklyLogCollection;
    @OneToMany(mappedBy = "disapprovedId")
    private Collection<MvWeeklyLog> mvWeeklyLogCollection1;
    @OneToMany(mappedBy = "approvedId")
    private Collection<MvWeeklyLog> mvWeeklyLogCollection2;
    @JoinColumn(name = "mv_person_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvPerson mvPersonId;

    public MvUser() {
    }

    public MvUser(Integer id) {
        this.id = id;
    }

    public MvUser(Integer id, String userName, String userPassword, boolean active, Date dateCreated) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.active = active;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Collection<MvWeeklyLog> getMvWeeklyLogCollection() {
        return mvWeeklyLogCollection;
    }

    public void setMvWeeklyLogCollection(Collection<MvWeeklyLog> mvWeeklyLogCollection) {
        this.mvWeeklyLogCollection = mvWeeklyLogCollection;
    }

    public Collection<MvWeeklyLog> getMvWeeklyLogCollection1() {
        return mvWeeklyLogCollection1;
    }

    public void setMvWeeklyLogCollection1(Collection<MvWeeklyLog> mvWeeklyLogCollection1) {
        this.mvWeeklyLogCollection1 = mvWeeklyLogCollection1;
    }

    public Collection<MvWeeklyLog> getMvWeeklyLogCollection2() {
        return mvWeeklyLogCollection2;
    }

    public void setMvWeeklyLogCollection2(Collection<MvWeeklyLog> mvWeeklyLogCollection2) {
        this.mvWeeklyLogCollection2 = mvWeeklyLogCollection2;
    }

    public MvPerson getMvPersonId() {
        return mvPersonId;
    }

    public void setMvPersonId(MvPerson mvPersonId) {
        this.mvPersonId = mvPersonId;
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
        if (!(object instanceof MvUser)) {
            return false;
        }
        MvUser other = (MvUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvUser[id=" + id + "]";
    }

}
