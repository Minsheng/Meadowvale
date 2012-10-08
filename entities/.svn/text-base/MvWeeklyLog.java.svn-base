/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meadowvale.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Eric
 */
@Entity
@Table(name = "mv_weekly_log")
@NamedQueries({
    @NamedQuery(name = "MvWeeklyLog.findAll", query = "SELECT m FROM MvWeeklyLog m"),
    @NamedQuery(name = "MvWeeklyLog.findById", query = "SELECT m FROM MvWeeklyLog m WHERE m.id = :id"),
    @NamedQuery(name = "MvWeeklyLog.findByDateSubmitted", query = "SELECT m FROM MvWeeklyLog m WHERE m.dateSubmitted = :dateSubmitted"),
    @NamedQuery(name = "MvWeeklyLog.findByDateCreated", query = "SELECT m FROM MvWeeklyLog m WHERE m.dateCreated = :dateCreated"),
    @NamedQuery(name = "MvWeeklyLog.findByMark", query = "SELECT m FROM MvWeeklyLog m WHERE m.mark = :mark"),
    @NamedQuery(name = "MvWeeklyLog.findByTotalMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.totalMins = :totalMins"),
    @NamedQuery(name = "MvWeeklyLog.findByTotalIc", query = "SELECT m FROM MvWeeklyLog m WHERE m.totalIc = :totalIc"),
    @NamedQuery(name = "MvWeeklyLog.findByAbsentDays", query = "SELECT m FROM MvWeeklyLog m WHERE m.absentDays = :absentDays"),
    @NamedQuery(name = "MvWeeklyLog.findByAbsentReason", query = "SELECT m FROM MvWeeklyLog m WHERE m.absentReason = :absentReason"),
    @NamedQuery(name = "MvWeeklyLog.findByMonInHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.monInHrs = :monInHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByMonInMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.monInMins = :monInMins"),
    @NamedQuery(name = "MvWeeklyLog.findByMonOutHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.monOutHrs = :monOutHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByMonOutMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.monOutMins = :monOutMins"),
    @NamedQuery(name = "MvWeeklyLog.findByTueInHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.tueInHrs = :tueInHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByTueInMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.tueInMins = :tueInMins"),
    @NamedQuery(name = "MvWeeklyLog.findByTueOutHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.tueOutHrs = :tueOutHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByTueOutMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.tueOutMins = :tueOutMins"),
    @NamedQuery(name = "MvWeeklyLog.findByWedInHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.wedInHrs = :wedInHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByWedInMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.wedInMins = :wedInMins"),
    @NamedQuery(name = "MvWeeklyLog.findByWedOutHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.wedOutHrs = :wedOutHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByWedOutMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.wedOutMins = :wedOutMins"),
    @NamedQuery(name = "MvWeeklyLog.findByThuInHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.thuInHrs = :thuInHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByThuInMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.thuInMins = :thuInMins"),
    @NamedQuery(name = "MvWeeklyLog.findByThuOutHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.thuOutHrs = :thuOutHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByThuOutMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.thuOutMins = :thuOutMins"),
    @NamedQuery(name = "MvWeeklyLog.findByFriInHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.friInHrs = :friInHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByFriInMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.friInMins = :friInMins"),
    @NamedQuery(name = "MvWeeklyLog.findByFriOutHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.friOutHrs = :friOutHrs"),
    @NamedQuery(name = "MvWeeklyLog.findByFriOutMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.friOutMins = :friOutMins"),
    @NamedQuery(name = "MvWeeklyLog.findBySatInHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.satInHrs = :satInHrs"),
    @NamedQuery(name = "MvWeeklyLog.findBySatInMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.satInMins = :satInMins"),
    @NamedQuery(name = "MvWeeklyLog.findBySatOutHrs", query = "SELECT m FROM MvWeeklyLog m WHERE m.satOutHrs = :satOutHrs"),
    @NamedQuery(name = "MvWeeklyLog.findBySatOutMins", query = "SELECT m FROM MvWeeklyLog m WHERE m.satOutMins = :satOutMins"),
    @NamedQuery(name = "MvWeeklyLog.findByAct1", query = "SELECT m FROM MvWeeklyLog m WHERE m.act1 = :act1"),
    @NamedQuery(name = "MvWeeklyLog.findByAct2", query = "SELECT m FROM MvWeeklyLog m WHERE m.act2 = :act2"),
    @NamedQuery(name = "MvWeeklyLog.findByAct3", query = "SELECT m FROM MvWeeklyLog m WHERE m.act3 = :act3"),
    @NamedQuery(name = "MvWeeklyLog.findByAct4", query = "SELECT m FROM MvWeeklyLog m WHERE m.act4 = :act4"),
    @NamedQuery(name = "MvWeeklyLog.findByAct5", query = "SELECT m FROM MvWeeklyLog m WHERE m.act5 = :act5"),
    @NamedQuery(name = "MvWeeklyLog.findByAct6", query = "SELECT m FROM MvWeeklyLog m WHERE m.act6 = :act6"),
    @NamedQuery(name = "MvWeeklyLog.findByAct7", query = "SELECT m FROM MvWeeklyLog m WHERE m.act7 = :act7"),
    @NamedQuery(name = "MvWeeklyLog.findByAct8", query = "SELECT m FROM MvWeeklyLog m WHERE m.act8 = :act8"),
    @NamedQuery(name = "MvWeeklyLog.findByAct9", query = "SELECT m FROM MvWeeklyLog m WHERE m.act9 = :act9"),
    @NamedQuery(name = "MvWeeklyLog.findByAct10", query = "SELECT m FROM MvWeeklyLog m WHERE m.act10 = :act10"),
    @NamedQuery(name = "MvWeeklyLog.findByAct11", query = "SELECT m FROM MvWeeklyLog m WHERE m.act11 = :act11"),
    @NamedQuery(name = "MvWeeklyLog.findByAct12", query = "SELECT m FROM MvWeeklyLog m WHERE m.act12 = :act12"),
    @NamedQuery(name = "MvWeeklyLog.findByAct13", query = "SELECT m FROM MvWeeklyLog m WHERE m.act13 = :act13"),
    @NamedQuery(name = "MvWeeklyLog.findByAct14", query = "SELECT m FROM MvWeeklyLog m WHERE m.act14 = :act14"),
    @NamedQuery(name = "MvWeeklyLog.findByAct15", query = "SELECT m FROM MvWeeklyLog m WHERE m.act15 = :act15"),
    @NamedQuery(name = "MvWeeklyLog.findByAct16", query = "SELECT m FROM MvWeeklyLog m WHERE m.act16 = :act16"),
    @NamedQuery(name = "MvWeeklyLog.findByAct17", query = "SELECT m FROM MvWeeklyLog m WHERE m.act17 = :act17"),
    @NamedQuery(name = "MvWeeklyLog.findByAct18", query = "SELECT m FROM MvWeeklyLog m WHERE m.act18 = :act18"),
    @NamedQuery(name = "MvWeeklyLog.findByAct1t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act1t = :act1t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct2t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act2t = :act2t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct3t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act3t = :act3t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct4t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act4t = :act4t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct5t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act5t = :act5t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct6t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act6t = :act6t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct7t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act7t = :act7t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct8t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act8t = :act8t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct9t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act9t = :act9t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct10t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act10t = :act10t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct11t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act11t = :act11t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct12t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act12t = :act12t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct13t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act13t = :act13t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct14t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act14t = :act14t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct15t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act15t = :act15t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct16t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act16t = :act16t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct17t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act17t = :act17t"),
    @NamedQuery(name = "MvWeeklyLog.findByAct18t", query = "SELECT m FROM MvWeeklyLog m WHERE m.act18t = :act18t")})
public class MvWeeklyLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_submitted")
    @Temporal(TemporalType.DATE)
    private Date dateSubmitted;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @Column(name = "mark")
    private Float mark;
    @Basic(optional = false)
    @Column(name = "total_mins")
    private int totalMins;
    @Basic(optional = false)
    @Column(name = "total_ic")
    private int totalIc;
    @Column(name = "absent_days")
    private Integer absentDays;
    @Column(name = "absent_reason")
    private String absentReason;
    @Column(name = "monInHrs")
    private Integer monInHrs;
    @Column(name = "monInMins")
    private Integer monInMins;
    @Column(name = "monOutHrs")
    private Integer monOutHrs;
    @Column(name = "monOutMins")
    private Integer monOutMins;
    @Column(name = "tueInHrs")
    private Integer tueInHrs;
    @Column(name = "tueInMins")
    private Integer tueInMins;
    @Column(name = "tueOutHrs")
    private Integer tueOutHrs;
    @Column(name = "tueOutMins")
    private Integer tueOutMins;
    @Column(name = "wedInHrs")
    private Integer wedInHrs;
    @Column(name = "wedInMins")
    private Integer wedInMins;
    @Column(name = "wedOutHrs")
    private Integer wedOutHrs;
    @Column(name = "wedOutMins")
    private Integer wedOutMins;
    @Column(name = "thuInHrs")
    private Integer thuInHrs;
    @Column(name = "thuInMins")
    private Integer thuInMins;
    @Column(name = "thuOutHrs")
    private Integer thuOutHrs;
    @Column(name = "thuOutMins")
    private Integer thuOutMins;
    @Column(name = "friInHrs")
    private Integer friInHrs;
    @Column(name = "friInMins")
    private Integer friInMins;
    @Column(name = "friOutHrs")
    private Integer friOutHrs;
    @Column(name = "friOutMins")
    private Integer friOutMins;
    @Column(name = "satInHrs")
    private Integer satInHrs;
    @Column(name = "satInMins")
    private Integer satInMins;
    @Column(name = "satOutHrs")
    private Integer satOutHrs;
    @Column(name = "satOutMins")
    private Integer satOutMins;
    @Column(name = "act1")
    private String act1;
    @Column(name = "act2")
    private String act2;
    @Column(name = "act3")
    private String act3;
    @Column(name = "act4")
    private String act4;
    @Column(name = "act5")
    private String act5;
    @Column(name = "act6")
    private String act6;
    @Column(name = "act7")
    private String act7;
    @Column(name = "act8")
    private String act8;
    @Column(name = "act9")
    private String act9;
    @Column(name = "act10")
    private String act10;
    @Column(name = "act11")
    private String act11;
    @Column(name = "act12")
    private String act12;
    @Column(name = "act13")
    private String act13;
    @Column(name = "act14")
    private String act14;
    @Column(name = "act15")
    private String act15;
    @Column(name = "act16")
    private String act16;
    @Column(name = "act17")
    private String act17;
    @Column(name = "act18")
    private String act18;
    @Column(name = "act1t")
    private String act1t;
    @Column(name = "act2t")
    private String act2t;
    @Column(name = "act3t")
    private String act3t;
    @Column(name = "act4t")
    private String act4t;
    @Column(name = "act5t")
    private String act5t;
    @Column(name = "act6t")
    private String act6t;
    @Column(name = "act7t")
    private String act7t;
    @Column(name = "act8t")
    private String act8t;
    @Column(name = "act9t")
    private String act9t;
    @Column(name = "act10t")
    private String act10t;
    @Column(name = "act11t")
    private String act11t;
    @Column(name = "act12t")
    private String act12t;
    @Column(name = "act13t")
    private String act13t;
    @Column(name = "act14t")
    private String act14t;
    @Column(name = "act15t")
    private String act15t;
    @Column(name = "act16t")
    private String act16t;
    @Column(name = "act17t")
    private String act17t;
    @Column(name = "act18t")
    private String act18t;
    @JoinColumn(name = "mv_student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvUser mvStudentId;
    @JoinColumn(name = "week_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MvWeek weekId;
    @JoinColumn(name = "disapproved_id", referencedColumnName = "id")
    @ManyToOne
    private MvUser disapprovedId;
    @JoinColumn(name = "approved_id", referencedColumnName = "id")
    @ManyToOne
    private MvUser approvedId;

    public MvWeeklyLog() {
    }

    public MvWeeklyLog(Integer id) {
        this.id = id;
    }

    public MvWeeklyLog(Integer id, Date dateCreated, int totalMins, int totalIc) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.totalMins = totalMins;
        this.totalIc = totalIc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    public int getTotalMins() {
        return totalMins;
    }

    public void setTotalMins(int totalMins) {
        this.totalMins = totalMins;
    }

    public int getTotalIc() {
        return totalIc;
    }

    public void setTotalIc(int totalIc) {
        this.totalIc = totalIc;
    }

    public Integer getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(Integer absentDays) {
        this.absentDays = absentDays;
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason;
    }

    public Integer getMonInHrs() {
        return monInHrs;
    }

    public void setMonInHrs(Integer monInHrs) {
        this.monInHrs = monInHrs;
    }

    public Integer getMonInMins() {
        return monInMins;
    }

    public void setMonInMins(Integer monInMins) {
        this.monInMins = monInMins;
    }

    public Integer getMonOutHrs() {
        return monOutHrs;
    }

    public void setMonOutHrs(Integer monOutHrs) {
        this.monOutHrs = monOutHrs;
    }

    public Integer getMonOutMins() {
        return monOutMins;
    }

    public void setMonOutMins(Integer monOutMins) {
        this.monOutMins = monOutMins;
    }

    public Integer getTueInHrs() {
        return tueInHrs;
    }

    public void setTueInHrs(Integer tueInHrs) {
        this.tueInHrs = tueInHrs;
    }

    public Integer getTueInMins() {
        return tueInMins;
    }

    public void setTueInMins(Integer tueInMins) {
        this.tueInMins = tueInMins;
    }

    public Integer getTueOutHrs() {
        return tueOutHrs;
    }

    public void setTueOutHrs(Integer tueOutHrs) {
        this.tueOutHrs = tueOutHrs;
    }

    public Integer getTueOutMins() {
        return tueOutMins;
    }

    public void setTueOutMins(Integer tueOutMins) {
        this.tueOutMins = tueOutMins;
    }

    public Integer getWedInHrs() {
        return wedInHrs;
    }

    public void setWedInHrs(Integer wedInHrs) {
        this.wedInHrs = wedInHrs;
    }

    public Integer getWedInMins() {
        return wedInMins;
    }

    public void setWedInMins(Integer wedInMins) {
        this.wedInMins = wedInMins;
    }

    public Integer getWedOutHrs() {
        return wedOutHrs;
    }

    public void setWedOutHrs(Integer wedOutHrs) {
        this.wedOutHrs = wedOutHrs;
    }

    public Integer getWedOutMins() {
        return wedOutMins;
    }

    public void setWedOutMins(Integer wedOutMins) {
        this.wedOutMins = wedOutMins;
    }

    public Integer getThuInHrs() {
        return thuInHrs;
    }

    public void setThuInHrs(Integer thuInHrs) {
        this.thuInHrs = thuInHrs;
    }

    public Integer getThuInMins() {
        return thuInMins;
    }

    public void setThuInMins(Integer thuInMins) {
        this.thuInMins = thuInMins;
    }

    public Integer getThuOutHrs() {
        return thuOutHrs;
    }

    public void setThuOutHrs(Integer thuOutHrs) {
        this.thuOutHrs = thuOutHrs;
    }

    public Integer getThuOutMins() {
        return thuOutMins;
    }

    public void setThuOutMins(Integer thuOutMins) {
        this.thuOutMins = thuOutMins;
    }

    public Integer getFriInHrs() {
        return friInHrs;
    }

    public void setFriInHrs(Integer friInHrs) {
        this.friInHrs = friInHrs;
    }

    public Integer getFriInMins() {
        return friInMins;
    }

    public void setFriInMins(Integer friInMins) {
        this.friInMins = friInMins;
    }

    public Integer getFriOutHrs() {
        return friOutHrs;
    }

    public void setFriOutHrs(Integer friOutHrs) {
        this.friOutHrs = friOutHrs;
    }

    public Integer getFriOutMins() {
        return friOutMins;
    }

    public void setFriOutMins(Integer friOutMins) {
        this.friOutMins = friOutMins;
    }

    public Integer getSatInHrs() {
        return satInHrs;
    }

    public void setSatInHrs(Integer satInHrs) {
        this.satInHrs = satInHrs;
    }

    public Integer getSatInMins() {
        return satInMins;
    }

    public void setSatInMins(Integer satInMins) {
        this.satInMins = satInMins;
    }

    public Integer getSatOutHrs() {
        return satOutHrs;
    }

    public void setSatOutHrs(Integer satOutHrs) {
        this.satOutHrs = satOutHrs;
    }

    public Integer getSatOutMins() {
        return satOutMins;
    }

    public void setSatOutMins(Integer satOutMins) {
        this.satOutMins = satOutMins;
    }

    public String getAct1() {
        return act1;
    }

    public void setAct1(String act1) {
        this.act1 = act1;
    }

    public String getAct2() {
        return act2;
    }

    public void setAct2(String act2) {
        this.act2 = act2;
    }

    public String getAct3() {
        return act3;
    }

    public void setAct3(String act3) {
        this.act3 = act3;
    }

    public String getAct4() {
        return act4;
    }

    public void setAct4(String act4) {
        this.act4 = act4;
    }

    public String getAct5() {
        return act5;
    }

    public void setAct5(String act5) {
        this.act5 = act5;
    }

    public String getAct6() {
        return act6;
    }

    public void setAct6(String act6) {
        this.act6 = act6;
    }

    public String getAct7() {
        return act7;
    }

    public void setAct7(String act7) {
        this.act7 = act7;
    }

    public String getAct8() {
        return act8;
    }

    public void setAct8(String act8) {
        this.act8 = act8;
    }

    public String getAct9() {
        return act9;
    }

    public void setAct9(String act9) {
        this.act9 = act9;
    }

    public String getAct10() {
        return act10;
    }

    public void setAct10(String act10) {
        this.act10 = act10;
    }

    public String getAct11() {
        return act11;
    }

    public void setAct11(String act11) {
        this.act11 = act11;
    }

    public String getAct12() {
        return act12;
    }

    public void setAct12(String act12) {
        this.act12 = act12;
    }

    public String getAct13() {
        return act13;
    }

    public void setAct13(String act13) {
        this.act13 = act13;
    }

    public String getAct14() {
        return act14;
    }

    public void setAct14(String act14) {
        this.act14 = act14;
    }

    public String getAct15() {
        return act15;
    }

    public void setAct15(String act15) {
        this.act15 = act15;
    }

    public String getAct16() {
        return act16;
    }

    public void setAct16(String act16) {
        this.act16 = act16;
    }

    public String getAct17() {
        return act17;
    }

    public void setAct17(String act17) {
        this.act17 = act17;
    }

    public String getAct18() {
        return act18;
    }

    public void setAct18(String act18) {
        this.act18 = act18;
    }

    public String getAct1t() {
        return act1t;
    }

    public void setAct1t(String act1t) {
        this.act1t = act1t;
    }

    public String getAct2t() {
        return act2t;
    }

    public void setAct2t(String act2t) {
        this.act2t = act2t;
    }

    public String getAct3t() {
        return act3t;
    }

    public void setAct3t(String act3t) {
        this.act3t = act3t;
    }

    public String getAct4t() {
        return act4t;
    }

    public void setAct4t(String act4t) {
        this.act4t = act4t;
    }

    public String getAct5t() {
        return act5t;
    }

    public void setAct5t(String act5t) {
        this.act5t = act5t;
    }

    public String getAct6t() {
        return act6t;
    }

    public void setAct6t(String act6t) {
        this.act6t = act6t;
    }

    public String getAct7t() {
        return act7t;
    }

    public void setAct7t(String act7t) {
        this.act7t = act7t;
    }

    public String getAct8t() {
        return act8t;
    }

    public void setAct8t(String act8t) {
        this.act8t = act8t;
    }

    public String getAct9t() {
        return act9t;
    }

    public void setAct9t(String act9t) {
        this.act9t = act9t;
    }

    public String getAct10t() {
        return act10t;
    }

    public void setAct10t(String act10t) {
        this.act10t = act10t;
    }

    public String getAct11t() {
        return act11t;
    }

    public void setAct11t(String act11t) {
        this.act11t = act11t;
    }

    public String getAct12t() {
        return act12t;
    }

    public void setAct12t(String act12t) {
        this.act12t = act12t;
    }

    public String getAct13t() {
        return act13t;
    }

    public void setAct13t(String act13t) {
        this.act13t = act13t;
    }

    public String getAct14t() {
        return act14t;
    }

    public void setAct14t(String act14t) {
        this.act14t = act14t;
    }

    public String getAct15t() {
        return act15t;
    }

    public void setAct15t(String act15t) {
        this.act15t = act15t;
    }

    public String getAct16t() {
        return act16t;
    }

    public void setAct16t(String act16t) {
        this.act16t = act16t;
    }

    public String getAct17t() {
        return act17t;
    }

    public void setAct17t(String act17t) {
        this.act17t = act17t;
    }

    public String getAct18t() {
        return act18t;
    }

    public void setAct18t(String act18t) {
        this.act18t = act18t;
    }

    public MvUser getMvStudentId() {
        return mvStudentId;
    }

    public void setMvStudentId(MvUser mvStudentId) {
        this.mvStudentId = mvStudentId;
    }

    public MvWeek getWeekId() {
        return weekId;
    }

    public void setWeekId(MvWeek weekId) {
        this.weekId = weekId;
    }

    public MvUser getDisapprovedId() {
        return disapprovedId;
    }

    public void setDisapprovedId(MvUser disapprovedId) {
        this.disapprovedId = disapprovedId;
    }

    public MvUser getApprovedId() {
        return approvedId;
    }

    public void setApprovedId(MvUser approvedId) {
        this.approvedId = approvedId;
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
        if (!(object instanceof MvWeeklyLog)) {
            return false;
        }
        MvWeeklyLog other = (MvWeeklyLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meadowvale.entities.MvWeeklyLog[id=" + id + "]";
    }

}
