/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meadowvale.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Eric
 */
@Entity
@Table(name = "mv_class")
@NamedQueries({
    @NamedQuery(name = "MvClass.findAll", query = "SELECT m FROM MvClass m"),
    @NamedQuery(name = "MvClass.findById", query = "SELECT m FROM MvClass m WHERE m.id = :id"),
    @NamedQuery(name = "MvClass.findByClassNumber", query = "SELECT m FROM MvClass m WHERE m.classNumber = :classNumber"),
    @NamedQuery(name = "MvClass.findByTerm", query = "SELECT m FROM MvClass m WHERE m.term = :term"),
    @NamedQuery(name = "MvClass.findByClassName", query = "SELECT m FROM MvClass m WHERE m.className = :className"),
    @NamedQuery(name = "MvClass.findByRequiredCoopHours", query = "SELECT m FROM MvClass m WHERE m.requiredCoopHours = :requiredCoopHours")})
public class MvClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "class_number")
    private int classNumber;
    @Basic(optional = false)
    @Column(name = "term")
    private String term;
    @Basic(optional = false)
    @Column(name = "class_name")
    private String className;
    @Basic(optional = false)
    @Column(name = "required_coop_hours")
    private int requiredCoopHours;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classId")
    private Collection<MvWeek> mvWeekCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classId")
    private Collection<MvEnrollment> mvEnrollmentCollection;

    public MvClass() {
    }

    public MvClass(Integer id) {
        this.id = id;
    }

    public MvClass(Integer id, int classNumber, String term, String className, int requiredCoopHours) {
        this.id = id;
        this.classNumber = classNumber;
        this.term = term;
        this.className = className;
        this.requiredCoopHours = requiredCoopHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getRequiredCoopHours() {
        return requiredCoopHours;
    }

    public void setRequiredCoopHours(int requiredCoopHours) {
        this.requiredCoopHours = requiredCoopHours;
    }

    public Collection<MvWeek> getMvWeekCollection() {
        return mvWeekCollection;
    }

    public void setMvWeekCollection(Collection<MvWeek> mvWeekCollection) {
        this.mvWeekCollection = mvWeekCollection;
    }

    public Collection<MvEnrollment> getMvEnrollmentCollection() {
        return mvEnrollmentCollection;
    }

    public void setMvEnrollmentCollection(Collection<MvEnrollment> mvEnrollmentCollection) {
        this.mvEnrollmentCollection = mvEnrollmentCollection;
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
        if (!(object instanceof MvClass)) {
            return false;
        }
        MvClass other = (MvClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvClass[id=" + id + "]";
    }

}
