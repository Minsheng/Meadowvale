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
@Table(name = "mv_contact")
@NamedQueries({
    @NamedQuery(name = "MvContact.findAll", query = "SELECT m FROM MvContact m"),
    @NamedQuery(name = "MvContact.findById", query = "SELECT m FROM MvContact m WHERE m.id = :id"),
    @NamedQuery(name = "MvContact.findByYearNumber", query = "SELECT m FROM MvContact m WHERE m.yearNumber = :yearNumber"),
    @NamedQuery(name = "MvContact.findByContact", query = "SELECT m FROM MvContact m WHERE m.contact = :contact"),
    @NamedQuery(name = "MvContact.findByPhone", query = "SELECT m FROM MvContact m WHERE m.phone = :phone"),
    @NamedQuery(name = "MvContact.findByAlterContact", query = "SELECT m FROM MvContact m WHERE m.alterContact = :alterContact"),
    @NamedQuery(name = "MvContact.findByCompany", query = "SELECT m FROM MvContact m WHERE m.company = :company"),
    @NamedQuery(name = "MvContact.findByTopic", query = "SELECT m FROM MvContact m WHERE m.topic = :topic"),
    @NamedQuery(name = "MvContact.findByComments", query = "SELECT m FROM MvContact m WHERE m.comments = :comments")})
public class MvContact implements Serializable {
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
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "alter_contact")
    private String alterContact;
    @Basic(optional = false)
    @Column(name = "company")
    private String company;
    @Basic(optional = false)
    @Column(name = "topic")
    private String topic;
    @Basic(optional = false)
    @Column(name = "comments")
    private String comments;

    public MvContact() {
    }

    public MvContact(Integer id) {
        this.id = id;
    }

    public MvContact(Integer id, String yearNumber, String contact, String phone, String alterContact, String company, String topic, String comments) {
        this.id = id;
        this.yearNumber = yearNumber;
        this.contact = contact;
        this.phone = phone;
        this.alterContact = alterContact;
        this.company = company;
        this.topic = topic;
        this.comments = comments;
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

    public String getAlterContact() {
        return alterContact;
    }

    public void setAlterContact(String alterContact) {
        this.alterContact = alterContact;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        if (!(object instanceof MvContact)) {
            return false;
        }
        MvContact other = (MvContact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvContact[id=" + id + "]";
    }

}
