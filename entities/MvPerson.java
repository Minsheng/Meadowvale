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
@Table(name = "mv_person")
@NamedQueries({
    @NamedQuery(name = "MvPerson.findAll", query = "SELECT m FROM MvPerson m"),
    @NamedQuery(name = "MvPerson.findById", query = "SELECT m FROM MvPerson m WHERE m.id = :id"),
    @NamedQuery(name = "MvPerson.findByFirstName", query = "SELECT m FROM MvPerson m WHERE m.firstName = :firstName"),
    @NamedQuery(name = "MvPerson.findByLastName", query = "SELECT m FROM MvPerson m WHERE m.lastName = :lastName"),
    @NamedQuery(name = "MvPerson.findByDateBirth", query = "SELECT m FROM MvPerson m WHERE m.dateBirth = :dateBirth"),
    @NamedQuery(name = "MvPerson.findByEmail", query = "SELECT m FROM MvPerson m WHERE m.email = :email"),
    @NamedQuery(name = "MvPerson.findByHomePhone", query = "SELECT m FROM MvPerson m WHERE m.homePhone = :homePhone"),
    @NamedQuery(name = "MvPerson.findByMobilePhone", query = "SELECT m FROM MvPerson m WHERE m.mobilePhone = :mobilePhone")})
public class MvPerson implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "date_birth")
    @Temporal(TemporalType.DATE)
    private Date dateBirth;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "home_phone")
    private Integer homePhone;
    @Column(name = "mobile_phone")
    private Integer mobilePhone;
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvAddress addressId;
    @JoinColumn(name = "person_group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvPersonGroup personGroupId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<MvEmployment> mvEmploymentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<MvEnrollment> mvEnrollmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mvPersonId")
    private Collection<MvUser> mvUserCollection;

    public MvPerson() {
    }

    public MvPerson(Integer id) {
        this.id = id;
    }

    public MvPerson(Integer id, String firstName, String lastName, Date dateBirth, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(Integer homePhone) {
        this.homePhone = homePhone;
    }

    public Integer getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Integer mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public MvAddress getAddressId() {
        return addressId;
    }

    public void setAddressId(MvAddress addressId) {
        this.addressId = addressId;
    }

    public MvPersonGroup getPersonGroupId() {
        return personGroupId;
    }

    public void setPersonGroupId(MvPersonGroup personGroupId) {
        this.personGroupId = personGroupId;
    }

    public Collection<MvEmployment> getMvEmploymentCollection() {
        return mvEmploymentCollection;
    }

    public void setMvEmploymentCollection(Collection<MvEmployment> mvEmploymentCollection) {
        this.mvEmploymentCollection = mvEmploymentCollection;
    }

    public Collection<MvEnrollment> getMvEnrollmentCollection() {
        return mvEnrollmentCollection;
    }

    public void setMvEnrollmentCollection(Collection<MvEnrollment> mvEnrollmentCollection) {
        this.mvEnrollmentCollection = mvEnrollmentCollection;
    }

    public Collection<MvUser> getMvUserCollection() {
        return mvUserCollection;
    }

    public void setMvUserCollection(Collection<MvUser> mvUserCollection) {
        this.mvUserCollection = mvUserCollection;
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
        if (!(object instanceof MvPerson)) {
            return false;
        }
        MvPerson other = (MvPerson) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvPerson[id=" + id + "]";
    }

}
