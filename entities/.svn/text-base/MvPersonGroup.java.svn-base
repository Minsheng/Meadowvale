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
@Table(name = "mv_person_group")
@NamedQueries({
    @NamedQuery(name = "MvPersonGroup.findAll", query = "SELECT m FROM MvPersonGroup m"),
    @NamedQuery(name = "MvPersonGroup.findById", query = "SELECT m FROM MvPersonGroup m WHERE m.id = :id"),
    @NamedQuery(name = "MvPersonGroup.findByTypeName", query = "SELECT m FROM MvPersonGroup m WHERE m.typeName = :typeName")})
public class MvPersonGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type_name")
    private String typeName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personGroupId")
    private Collection<MvPerson> mvPersonCollection;

    public MvPersonGroup() {
    }

    public MvPersonGroup(Integer id) {
        this.id = id;
    }

    public MvPersonGroup(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Collection<MvPerson> getMvPersonCollection() {
        return mvPersonCollection;
    }

    public void setMvPersonCollection(Collection<MvPerson> mvPersonCollection) {
        this.mvPersonCollection = mvPersonCollection;
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
        if (!(object instanceof MvPersonGroup)) {
            return false;
        }
        MvPersonGroup other = (MvPersonGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvPersonGroup[id=" + id + "]";
    }

}
