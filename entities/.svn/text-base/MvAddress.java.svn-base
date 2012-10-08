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
@Table(name = "mv_address")
@NamedQueries({
    @NamedQuery(name = "MvAddress.findAll", query = "SELECT m FROM MvAddress m"),
    @NamedQuery(name = "MvAddress.findById", query = "SELECT m FROM MvAddress m WHERE m.id = :id"),
    @NamedQuery(name = "MvAddress.findByAddressLine1", query = "SELECT m FROM MvAddress m WHERE m.addressLine1 = :addressLine1"),
    @NamedQuery(name = "MvAddress.findByAddressLine2", query = "SELECT m FROM MvAddress m WHERE m.addressLine2 = :addressLine2"),
    @NamedQuery(name = "MvAddress.findByCity", query = "SELECT m FROM MvAddress m WHERE m.city = :city"),
    @NamedQuery(name = "MvAddress.findByProvince", query = "SELECT m FROM MvAddress m WHERE m.province = :province"),
    @NamedQuery(name = "MvAddress.findByCountry", query = "SELECT m FROM MvAddress m WHERE m.country = :country"),
    @NamedQuery(name = "MvAddress.findByPostalCode", query = "SELECT m FROM MvAddress m WHERE m.postalCode = :postalCode")})
public class MvAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "address_line_1")
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "province")
    private String province;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @Column(name = "postal_code")
    private String postalCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressId")
    private Collection<MvPerson> mvPersonCollection;
    @OneToMany(mappedBy = "addressId")
    private Collection<MvCoopPlacement> mvCoopPlacementCollection;

    public MvAddress() {
    }

    public MvAddress(Integer id) {
        this.id = id;
    }

    public MvAddress(Integer id, String addressLine1, String city, String province, String country, String postalCode) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Collection<MvPerson> getMvPersonCollection() {
        return mvPersonCollection;
    }

    public void setMvPersonCollection(Collection<MvPerson> mvPersonCollection) {
        this.mvPersonCollection = mvPersonCollection;
    }

    public Collection<MvCoopPlacement> getMvCoopPlacementCollection() {
        return mvCoopPlacementCollection;
    }

    public void setMvCoopPlacementCollection(Collection<MvCoopPlacement> mvCoopPlacementCollection) {
        this.mvCoopPlacementCollection = mvCoopPlacementCollection;
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
        if (!(object instanceof MvAddress)) {
            return false;
        }
        MvAddress other = (MvAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvAddress[id=" + id + "]";
    }

}
