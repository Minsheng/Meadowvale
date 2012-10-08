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
@Table(name = "mv_week")
@NamedQueries({
    @NamedQuery(name = "MvWeek.findAll", query = "SELECT m FROM MvWeek m"),
    @NamedQuery(name = "MvWeek.findById", query = "SELECT m FROM MvWeek m WHERE m.id = :id"),
    @NamedQuery(name = "MvWeek.findByFirstDay", query = "SELECT m FROM MvWeek m WHERE m.firstDay = :firstDay"),
    @NamedQuery(name = "MvWeek.findByLastDay", query = "SELECT m FROM MvWeek m WHERE m.lastDay = :lastDay"),
    @NamedQuery(name = "MvWeek.findByWeekNumber", query = "SELECT m FROM MvWeek m WHERE m.weekNumber = :weekNumber"),
    @NamedQuery(name = "MvWeek.findByDueDate", query = "SELECT m FROM MvWeek m WHERE m.dueDate = :dueDate"),
    @NamedQuery(name = "MvWeek.findByMon", query = "SELECT m FROM MvWeek m WHERE m.mon = :mon"),
    @NamedQuery(name = "MvWeek.findByTue", query = "SELECT m FROM MvWeek m WHERE m.tue = :tue"),
    @NamedQuery(name = "MvWeek.findByWed", query = "SELECT m FROM MvWeek m WHERE m.wed = :wed"),
    @NamedQuery(name = "MvWeek.findByThu", query = "SELECT m FROM MvWeek m WHERE m.thu = :thu"),
    @NamedQuery(name = "MvWeek.findByFri", query = "SELECT m FROM MvWeek m WHERE m.fri = :fri"),
    @NamedQuery(name = "MvWeek.findBySat", query = "SELECT m FROM MvWeek m WHERE m.sat = :sat")})
public class MvWeek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "first_day")
    @Temporal(TemporalType.DATE)
    private Date firstDay;
    @Basic(optional = false)
    @Column(name = "last_day")
    @Temporal(TemporalType.DATE)
    private Date lastDay;
    @Basic(optional = false)
    @Column(name = "week_number")
    private int weekNumber;
    @Basic(optional = false)
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "mon")
    private String mon;
    @Column(name = "tue")
    private String tue;
    @Column(name = "wed")
    private String wed;
    @Column(name = "thu")
    private String thu;
    @Column(name = "fri")
    private String fri;
    @Column(name = "sat")
    private String sat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weekId")
    private Collection<MvWeeklyLog> mvWeeklyLogCollection;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvClass classId;

    public MvWeek() {
    }

    public MvWeek(Integer id) {
        this.id = id;
    }

    public MvWeek(Integer id, Date firstDay, Date lastDay, int weekNumber, Date dueDate) {
        this.id = id;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.weekNumber = weekNumber;
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Date firstDay) {
        this.firstDay = firstDay;
    }

    public Date getLastDay() {
        return lastDay;
    }

    public void setLastDay(Date lastDay) {
        this.lastDay = lastDay;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public Collection<MvWeeklyLog> getMvWeeklyLogCollection() {
        return mvWeeklyLogCollection;
    }

    public void setMvWeeklyLogCollection(Collection<MvWeeklyLog> mvWeeklyLogCollection) {
        this.mvWeeklyLogCollection = mvWeeklyLogCollection;
    }

    public MvClass getClassId() {
        return classId;
    }

    public void setClassId(MvClass classId) {
        this.classId = classId;
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
        if (!(object instanceof MvWeek)) {
            return false;
        }
        MvWeek other = (MvWeek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvWeek[id=" + id + "]";
    }

}
