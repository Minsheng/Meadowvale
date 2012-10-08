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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Eric
 */
@Entity
@Table(name = "mv_coop_placement")
@NamedQueries({
    @NamedQuery(name = "MvCoopPlacement.findAll", query = "SELECT m FROM MvCoopPlacement m"),
    @NamedQuery(name = "MvCoopPlacement.findById", query = "SELECT m FROM MvCoopPlacement m WHERE m.id = :id"),
    @NamedQuery(name = "MvCoopPlacement.findByName", query = "SELECT m FROM MvCoopPlacement m WHERE m.name = :name"),
    @NamedQuery(name = "MvCoopPlacement.findByCategory", query = "SELECT m FROM MvCoopPlacement m WHERE m.category = :category"),
    @NamedQuery(name = "MvCoopPlacement.findByContactId", query = "SELECT m FROM MvCoopPlacement m WHERE m.contactId = :contactId")})
public class MvCoopPlacement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "category")
    private String category;
    @Basic(optional = false)
    @Column(name = "contact_id")
    private int contactId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placementId")
    private Collection<MvEmployment> mvEmploymentCollection;
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ManyToOne
    private MvAddress addressId;

    public MvCoopPlacement() {
    }

    public MvCoopPlacement(Integer id) {
        this.id = id;
    }

    public MvCoopPlacement(Integer id, String name, String category, int contactId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.contactId = contactId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public Collection<MvEmployment> getMvEmploymentCollection() {
        return mvEmploymentCollection;
    }

    public void setMvEmploymentCollection(Collection<MvEmployment> mvEmploymentCollection) {
        this.mvEmploymentCollection = mvEmploymentCollection;
    }

    public MvAddress getAddressId() {
        return addressId;
    }

    public void setAddressId(MvAddress addressId) {
        this.addressId = addressId;
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
        if (!(object instanceof MvCoopPlacement)) {
            return false;
        }
        MvCoopPlacement other = (MvCoopPlacement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvCoopPlacement[id=" + id + "]";
    }

}
