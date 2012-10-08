/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meadowvale.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Eric
 */
@Entity
@Table(name = "mv_coop")
@NamedQueries({
    @NamedQuery(name = "MvCoop.findAll", query = "SELECT m FROM MvCoop m"),
    @NamedQuery(name = "MvCoop.findById", query = "SELECT m FROM MvCoop m WHERE m.id = :id"),
    @NamedQuery(name = "MvCoop.findByYearNumber", query = "SELECT m FROM MvCoop m WHERE m.yearNumber = :yearNumber"),
    @NamedQuery(name = "MvCoop.findByBusiness", query = "SELECT m FROM MvCoop m WHERE m.business = :business"),
    @NamedQuery(name = "MvCoop.findByLocation", query = "SELECT m FROM MvCoop m WHERE m.location = :location"),
    @NamedQuery(name = "MvCoop.findByActivity", query = "SELECT m FROM MvCoop m WHERE m.activity = :activity"),
    @NamedQuery(name = "MvCoop.findByContact", query = "SELECT m FROM MvCoop m WHERE m.contact = :contact"),
    @NamedQuery(name = "MvCoop.findByPhone", query = "SELECT m FROM MvCoop m WHERE m.phone = :phone")})
public class MvCoop implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "year_number")
    private String yearNumber;
    @Basic(optional = false)
    @Column(name = "business")
    private String business;
    @Basic(optional = false)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Column(name = "activity")
    private String activity;
    @Basic(optional = false)
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;

    public MvCoop() {
    }

    public MvCoop(Integer id) {
        this.id = id;
    }

    public MvCoop(Integer id, String yearNumber, String business, String location, String activity, String contact, String phone) {
        this.id = id;
        this.yearNumber = yearNumber;
        this.business = business;
        this.location = location;
        this.activity = activity;
        this.contact = contact;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(String yearNumber) {
        this.yearNumber = yearNumber;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (!(object instanceof MvCoop)) {
            return false;
        }
        MvCoop other = (MvCoop) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvCoop[id=" + id + "]";
    }

}
