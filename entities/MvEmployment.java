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
@Table(name = "mv_employment")
@NamedQueries({
    @NamedQuery(name = "MvEmployment.findAll", query = "SELECT m FROM MvEmployment m"),
    @NamedQuery(name = "MvEmployment.findById", query = "SELECT m FROM MvEmployment m WHERE m.id = :id")})
public class MvEmployment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "placement_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvCoopPlacement placementId;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvPerson studentId;

    public MvEmployment() {
    }

    public MvEmployment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MvCoopPlacement getPlacementId() {
        return placementId;
    }

    public void setPlacementId(MvCoopPlacement placementId) {
        this.placementId = placementId;
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
        if (!(object instanceof MvEmployment)) {
            return false;
        }
        MvEmployment other = (MvEmployment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvEmployment[id=" + id + "]";
    }

}
