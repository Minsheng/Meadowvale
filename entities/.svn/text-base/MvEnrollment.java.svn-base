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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Eric
 */
@Entity
@Table(name = "mv_enrollment")
@NamedQueries({
    @NamedQuery(name = "MvEnrollment.findAll", query = "SELECT m FROM MvEnrollment m"),
    @NamedQuery(name = "MvEnrollment.findById", query = "SELECT m FROM MvEnrollment m WHERE m.id = :id"),
    @NamedQuery(name = "MvEnrollment.findByUniqueCoopHour", query = "SELECT m FROM MvEnrollment m WHERE m.uniqueCoopHour = :uniqueCoopHour")})
public class MvEnrollment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "unique_coop_hour")
    private int uniqueCoopHour;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvClass classId;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvPerson studentId;

    public MvEnrollment() {
    }

    public MvEnrollment(Integer id) {
        this.id = id;
    }

    public MvEnrollment(Integer id, int uniqueCoopHour) {
        this.id = id;
        this.uniqueCoopHour = uniqueCoopHour;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUniqueCoopHour() {
        return uniqueCoopHour;
    }

    public void setUniqueCoopHour(int uniqueCoopHour) {
        this.uniqueCoopHour = uniqueCoopHour;
    }

    public MvClass getClassId() {
        return classId;
    }

    public void setClassId(MvClass classId) {
        this.classId = classId;
    }

    public MvPerson getStudentId() {
        return studentId;
    }

    public void setStudentId(MvPerson studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof MvEnrollment)) {
            return false;
        }
        MvEnrollment other = (MvEnrollment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvEnrollment[id=" + id + "]";
    }

}
