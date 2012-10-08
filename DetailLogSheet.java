/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DetailLogSheet.java
 *
 * Created on 2010-2-22, 22:04:14
 */

package meadowvale;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import meadowvale.controllers.exceptions.NonexistentEntityException;
import meadowvale.entities.MvClass;
import meadowvale.entities.MvPerson;
import meadowvale.entities.MvUser;
import meadowvale.entities.MvWeek;
import meadowvale.entities.MvWeeklyLog;

/**
 *
 * @author Team Sierra
 */
public class DetailLogSheet extends javax.swing.JPanel {
    // Creates new form DetailLogSheet
    public Application mvApp;
    public JDialog dialogWindow;
    public MvWeeklyLog log;
    public MvClass mvClass;
    public MvPerson student;
    public MvWeek mvWeek;
    public int mvWeekNum;
    public int class_id;
    public boolean monC = false;
    public boolean tueC = false;
    public boolean wedC = false;
    public boolean thuC = false;
    public boolean friC = false;
    public boolean satC = false;
    public int totalMinsSoFar;
    public int totalMinsThisWeek;
    public int totalICMinsSoFar;
    public int totalICMinsThisWeek;
    public JList logList;
    
    public DetailLogSheet(Application mvApp, int class_id, MvWeeklyLog mwl,
            MvPerson student, JList logList) {
        this.mvApp = mvApp;
        this.logList = logList;
        this.log = mwl;
        this.student = student;
        this.class_id = class_id;
        this.mvWeek = mwl.getWeekId();
        this.mvWeekNum = this.mvWeek.getWeekNumber();

        String findClass = "id = " + class_id;
        List<MvClass> classesList =
                mvApp.selectEntitiesBy(MvClass.class, findClass);
        this.mvClass = classesList.get(0);

        initComponents();
        prepare_sheet();
    }

    private void prepare_sheet() {
        // Set week name
        MvWeek week = this.log.getWeekId();
        String weekName = String.format("%s", week.getWeekNumber());
        this.sheetNumLabel.setText(weekName);

        // Set person name
        String personName = String.format("%s %s",
                                      mvApp.user.getMvPersonId().getFirstName(),
                                      mvApp.user.getMvPersonId().getLastName());
        this.nameLabel.setText(personName);

        // Set class name
        String className = this.mvClass.getTerm() + " - Class No. " +
                this.mvClass.getClassNumber() + " - " + 
                this.mvClass.getClassName();
        this.classLabel.setText(className);

        Date dueDate = this.mvWeek.getDueDate();
        SimpleDateFormat dDate = new SimpleDateFormat("MMMM dd,yyyy");
        this.dueDateLabel.setText(dDate.format(dueDate));

        Date endDate = this.mvWeek.getLastDay();
        SimpleDateFormat eDate = new SimpleDateFormat("MMMM dd,yyyy");
        this.weekEndingLabel.setText(eDate.format(endDate));

        // Set date
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd,yyyy");
        this.date_label.setText(df.format(today));

        if (this.log.getMark() != null) {
            float floatMark = this.log.getMark();
            this.markField.setText(String.valueOf(floatMark));
        } else {
            this.markField.setText("Not Marked Yet.");
        }

        if (this.log.getAbsentDays() != null) {
            Integer daysOfAbsence = new Integer(this.log.getAbsentDays());
            this.absentDays.setValue(daysOfAbsence);
            this.absentReasons.setText(this.log.getAbsentReason());
            int lenOfReasons = this.absentReasons.getText().length();
            int leftOfReaons = 100 - lenOfReasons;
            this.numCharLeft.setText(String.valueOf(leftOfReaons));
        }

        if (this.mvWeek.getMon() != null) {
            if ((this.mvWeek.getMon().startsWith("IC")) ||
                    (this.mvWeek.getMon().equals("Chapel"))) {
                if (this.log.getDateSubmitted() == null) {
                    this.monIn.setText(this.mvWeek.getMon());
                    this.monOut.setText(this.mvWeek.getMon());
                } else {
                    if (this.log.getMonInHrs() != null) {
                        int monInHrs = this.log.getMonInHrs();
                        int monInMins = this.log.getMonInMins();
                        int monOutHrs = this.log.getMonOutHrs();
                        int monOutMins = this.log.getMonOutMins();
                        this.monIn.setText(setTimeStr(monInHrs, monInMins));
                        this.monOut.setText(setTimeStr(monOutHrs, monOutMins));
                    }
                }
            } else {
                this.monC = true;
                this.monIn.setEditable(false);
                this.monOut.setEditable(false);
                this.monIn.setText(this.mvWeek.getMon());
                this.monOut.setText(this.mvWeek.getMon());
            }
            
        } else if (this.mvWeek.getMon() == null) {
            if (this.log.getMonInHrs() != null) {
                int monInHrs = this.log.getMonInHrs();
                int monInMins = this.log.getMonInMins();
                int monOutHrs = this.log.getMonOutHrs();
                int monOutMins = this.log.getMonOutMins();
                this.monIn.setText(setTimeStr(monInHrs, monInMins));
                this.monOut.setText(setTimeStr(monOutHrs, monOutMins));
            }
        }

        if (this.mvWeek.getTue() != null) {
            if ((this.mvWeek.getTue().startsWith("IC")) ||
                    (this.mvWeek.getTue().equals("Chapel"))) {
                if (this.log.getDateSubmitted() == null) {
                    this.tueIn.setText(this.mvWeek.getTue());
                    this.tueOut.setText(this.mvWeek.getTue());
                } else {
                    if (this.log.getTueInHrs() != null) {
                        int tueInHrs = this.log.getTueInHrs();
                        int tueInMins = this.log.getTueInMins();
                        int tueOutHrs = this.log.getTueOutHrs();
                        int tueOutMins = this.log.getTueOutMins();
                        this.tueIn.setText(setTimeStr(tueInHrs, tueInMins));
                        this.tueOut.setText(setTimeStr(tueOutHrs, tueOutMins));
                    }
                }
            } else {
                this.tueC = true;
                this.tueIn.setEditable(false);
                this.tueOut.setEditable(false);
                this.tueIn.setText(this.mvWeek.getTue());
                this.tueOut.setText(this.mvWeek.getTue());
            }
            
        } else if (this.mvWeek.getTue() == null) {
            if (this.log.getTueInHrs() != null) {
                int tueInHrs = this.log.getTueInHrs();
                int tueInMins = this.log.getTueInMins();
                int tueOutHrs = this.log.getTueOutHrs();
                int tueOutMins = this.log.getTueOutMins();
                this.tueIn.setText(setTimeStr(tueInHrs, tueInMins));
                this.tueOut.setText(setTimeStr(tueOutHrs, tueOutMins));
            }
        }

        if (this.mvWeek.getWed() != null) {
            if ((this.mvWeek.getWed().startsWith("IC")) ||
                    (this.mvWeek.getWed().equals("Chapel"))) {
                if (this.log.getDateSubmitted() == null) {
                    this.wedIn.setText(this.mvWeek.getWed());
                    this.wedOut.setText(this.mvWeek.getWed());
                } else {
                    if (this.log.getWedInHrs() != null) {
                        int wedInHrs = this.log.getWedInHrs();
                        int wedInMins = this.log.getWedInMins();
                        int wedOutHrs = this.log.getWedOutHrs();
                        int wedOutMins = this.log.getWedOutMins();
                        this.wedIn.setText(setTimeStr(wedInHrs, wedInMins));
                        this.wedOut.setText(setTimeStr(wedOutHrs, wedOutMins));
                    }
                }
            } else {
                this.wedC = true;
                this.wedIn.setEditable(false);
                this.wedOut.setEditable(false);
                this.wedIn.setText(this.mvWeek.getWed());
                this.wedOut.setText(this.mvWeek.getWed());
            }
        } else if (this.mvWeek.getWed() == null) {
            if (this.log.getWedInHrs() != null) {
                int wedInHrs = this.log.getWedInHrs();
                int wedInMins = this.log.getWedInMins();
                int wedOutHrs = this.log.getWedOutHrs();
                int wedOutMins = this.log.getWedOutMins();
                this.wedIn.setText(setTimeStr(wedInHrs, wedInMins));
                this.wedOut.setText(setTimeStr(wedOutHrs, wedOutMins));
            }
        }

        if (this.mvWeek.getThu() != null) {
            if ((this.mvWeek.getThu().startsWith("IC")) ||
                    (this.mvWeek.getThu().equals("Chapel"))) {
                if (this.log.getDateSubmitted() == null) {
                    this.thuIn.setText(this.mvWeek.getThu());
                    this.thuOut.setText(this.mvWeek.getThu());
                } else {
                    if (this.log.getThuInHrs() != null) {
                        int thuInHrs = this.log.getThuInHrs();
                        int thuInMins = this.log.getThuInMins();
                        int thuOutHrs = this.log.getThuOutHrs();
                        int thuOutMins = this.log.getThuOutMins();
                        this.thuIn.setText(setTimeStr(thuInHrs, thuInMins));
                        this.thuOut.setText(setTimeStr(thuOutHrs, thuOutMins));
                    }
                }
            } else {
                this.thuC = true;
                this.thuIn.setEditable(false);
                this.thuOut.setEditable(false);
                this.thuIn.setText(this.mvWeek.getThu());
                this.thuOut.setText(this.mvWeek.getThu());
            }
            
        } else if (this.mvWeek.getThu() == null) {
            if (this.log.getThuInHrs() != null) {
                int thuInHrs = this.log.getThuInHrs();
                int thuInMins = this.log.getThuInMins();
                int thuOutHrs = this.log.getThuOutHrs();
                int thuOutMins = this.log.getThuOutMins();
                this.thuIn.setText(setTimeStr(thuInHrs, thuInMins));
                this.thuOut.setText(setTimeStr(thuOutHrs, thuOutMins));
            }
        }

        if (this.mvWeek.getFri() != null) {
            if ((this.mvWeek.getFri().startsWith("IC")) ||
                    (this.mvWeek.getFri().equals("Chapel"))) {
                if (this.log.getDateSubmitted() == null) {
                    this.friIn.setText(this.mvWeek.getFri());
                    this.friOut.setText(this.mvWeek.getFri());
                } else {
                    if (this.log.getFriInHrs() != null) {
                        int friInHrs = this.log.getFriInHrs();
                        int friInMins = this.log.getFriInMins();
                        int friOutHrs = this.log.getFriOutHrs();
                        int friOutMins = this.log.getFriOutMins();
                        this.friIn.setText(setTimeStr(friInHrs, friInMins));
                        this.friOut.setText(setTimeStr(friOutHrs, friOutMins));
                    }
                }
            } else {
                this.friC = true;
                this.friIn.setEditable(false);
                this.friOut.setEditable(false);
                this.friIn.setText(this.mvWeek.getFri());
                this.friOut.setText(this.mvWeek.getFri());
            }
        } else if (this.mvWeek.getFri() == null) {
            if (this.log.getFriInHrs() != null) {
                int friInHrs = this.log.getFriInHrs();
                int friInMins = this.log.getFriInMins();
                int friOutHrs = this.log.getFriOutHrs();
                int friOutMins = this.log.getFriOutMins();
                this.friIn.setText(setTimeStr(friInHrs, friInMins));
                this.friOut.setText(setTimeStr(friOutHrs, friOutMins));
            }
        }

        if (this.mvWeek.getSat() != null) {
            if ((this.mvWeek.getSat().startsWith("IC")) ||
                    (this.mvWeek.getSat().equals("Chapel"))) {
                if (this.log.getDateSubmitted() == null) {
                    this.satIn.setText(this.mvWeek.getSat());
                    this.satOut.setText(this.mvWeek.getSat());
                } else {
                    if (this.log.getSatInHrs() != null) {
                        int satInHrs = this.log.getSatInHrs();
                        int satInMins = this.log.getSatInMins();
                        int satOutHrs = this.log.getSatOutHrs();
                        int satOutMins = this.log.getSatOutMins();
                        this.satIn.setText(setTimeStr(satInHrs, satInMins));
                        this.satOut.setText(setTimeStr(satOutHrs, satOutMins));
                    }
                }
            } else {
                this.satC = true;
                this.satIn.setEditable(false);
                this.satOut.setEditable(false);
                this.satIn.setText(this.mvWeek.getSat());
                this.satOut.setText(this.mvWeek.getSat());
            }
        } else if (this.mvWeek.getSat() == null) {
            if (this.log.getSatInHrs() != null) {
                int satInHrs = this.log.getSatInHrs();
                int satInMins = this.log.getSatInMins();
                int satOutHrs = this.log.getSatOutHrs();
                int satOutMins = this.log.getSatOutMins();
                this.satIn.setText(setTimeStr(satInHrs, satInMins));
                this.satOut.setText(setTimeStr(satOutHrs, satOutMins));
            }
        }

        int totalMins = 0;
        int actualHrs = 0;
        int actualMins = 0;
        int totalICMins = 0;
        int actualICHrs = 0;
        int actualICMins = 0;

        String findUser = "mv_person_id = " + this.student.getId();
        List<MvUser> userList = mvApp.selectEntitiesBy(MvUser.class, findUser);
        MvUser mvUser = userList.get(0);

        String findLogs = "mv_student_id = " + mvUser.getId();
        List<MvWeeklyLog> logList = mvApp.selectEntitiesBy(MvWeeklyLog.class, findLogs);
        for (MvWeeklyLog mvLog : logList) {
            if (mvLog.getWeekId().getClassId().getId() == this.mvClass.getId()) {
                    if (mvLog.getWeekId().getWeekNumber() < this.mvWeekNum) {
                            if (mvLog.getApprovedId() != null) {
                                    totalMins += mvLog.getTotalMins();
                                    totalICMins += mvLog.getTotalIc();
                            }
                    }
            }
        }

        this.totalMinsSoFar = totalMins;
        actualHrs = totalMins / 60;
        actualMins = totalMins - actualHrs * 60;

        this.totalICMinsSoFar = totalICMins;
        actualICHrs = totalICMins / 60;
        actualICMins = totalICMins - actualICHrs * 60;

        this.hourSoFar.setText(createStr(actualHrs, actualMins, ""));
        this.hourTotal.setText(createStr(actualHrs, actualMins, ""));
        this.totalICHour.setText(createStr(actualICHrs, actualICMins, ""));

        this.updateTime();

        if ((this.log.getAct1() != null) && (this.log.getAct1t() != null)) {
            this.act1.setText(this.log.getAct1());
            this.act1t.setText(this.log.getAct1t());
        }

        if ((this.log.getAct2() != null) && (this.log.getAct2t() != null)) {
            this.act2.setText(this.log.getAct2());
            this.act2t.setText(this.log.getAct2t());
        }

        if ((this.log.getAct3() != null) && (this.log.getAct3t() != null)) {
            this.act3.setText(this.log.getAct3());
            this.act3t.setText(this.log.getAct3t());
        }

        if ((this.log.getAct4() != null) && (this.log.getAct4t() != null)) {
            this.act4.setText(this.log.getAct4());
            this.act4t.setText(this.log.getAct4t());
        }

        if ((this.log.getAct5() != null) && (this.log.getAct5t() != null)) {
            this.act5.setText(this.log.getAct5());
            this.act5t.setText(this.log.getAct5t());
        }

        if ((this.log.getAct6() != null) && (this.log.getAct6t() != null)) {
            this.act6.setText(this.log.getAct6());
            this.act6t.setText(this.log.getAct6t());
        }

        if ((this.log.getAct7() != null) && (this.log.getAct7t() != null)) {
            this.act7.setText(this.log.getAct7());
            this.act7t.setText(this.log.getAct7t());
        }

        if ((this.log.getAct8() != null) && (this.log.getAct8t() != null)) {
            this.act8.setText(this.log.getAct8());
            this.act8t.setText(this.log.getAct8t());
        }

        if ((this.log.getAct9() != null) && (this.log.getAct9t() != null)) {
            this.act9.setText(this.log.getAct9());
            this.act9t.setText(this.log.getAct9t());
        }

        if ((this.log.getAct10() != null) && (this.log.getAct10t() != null)) {
            this.act10.setText(this.log.getAct10());
            this.act10t.setText(this.log.getAct10t());
        }

        if ((this.log.getAct11() != null) && (this.log.getAct11t() != null)) {
            this.act11.setText(this.log.getAct11());
            this.act11t.setText(this.log.getAct11t());
        }

        if ((this.log.getAct12() != null) && (this.log.getAct12t() != null)) {
            this.act12.setText(this.log.getAct12());
            this.act12t.setText(this.log.getAct12t());
        }

        if ((this.log.getAct13() != null) && (this.log.getAct13t() != null)) {
            this.act13.setText(this.log.getAct13());
            this.act13t.setText(this.log.getAct13t());
        }

        if ((this.log.getAct14() != null) && (this.log.getAct14t() != null)) {
            this.act14.setText(this.log.getAct14());
            this.act14t.setText(this.log.getAct14t());
        }

        if ((this.log.getAct15() != null) && (this.log.getAct15t() != null)) {
            this.act15.setText(this.log.getAct15());
            this.act15t.setText(this.log.getAct15t());
        }

        if ((this.log.getAct16() != null) && (this.log.getAct16t() != null)) {
            this.act16.setText(this.log.getAct16());
            this.act16t.setText(this.log.getAct16t());
        }

        if ((this.log.getAct17() != null) && (this.log.getAct17t() != null)) {
            this.act17.setText(this.log.getAct17());
            this.act17t.setText(this.log.getAct17t());
        }

        if ((this.log.getAct18() != null) && (this.log.getAct18t() != null)) {
            this.act18.setText(this.log.getAct18());
            this.act18t.setText(this.log.getAct18t());
        }
    }

    private void updateTime() {
        int totalHrs = 0, totalMins = 0;
        int totalICHrs = 0, totalICMins = 0;

        boolean monF = false, tueF = false, wedF = false, thuF = false,
                friF = false, satF = false;

        if (this.mvWeek.getMon() != null) {
            if ((!this.mvWeek.getMon().startsWith("IC")) &&
                    (!this.mvWeek.getMon().equals("Chapel"))) {
                monF = true;
            }
        }
        if (this.mvWeek.getTue() != null) {
            if ((!this.mvWeek.getTue().startsWith("IC")) &&
                    (!this.mvWeek.getTue().equals("Chapel"))) {
                tueF = true;
            }
        }
        if (this.mvWeek.getWed() != null) {
            if ((!this.mvWeek.getWed().startsWith("IC")) &&
                    (!this.mvWeek.getWed().equals("Chapel"))) {
                wedF = true;
            }
        }
        if (this.mvWeek.getThu() != null) {
            if ((!this.mvWeek.getThu().startsWith("IC")) &&
                    (!this.mvWeek.getThu().equals("Chapel"))) {
                thuF = true;
            }
        }
        if (this.mvWeek.getFri() != null) {
            if ((!this.mvWeek.getFri().startsWith("IC")) &&
                    (!this.mvWeek.getFri().equals("Chapel"))) {
                friF = true;
            }
        }
        if (this.mvWeek.getSat() != null) {
            if ((!this.mvWeek.getSat().startsWith("IC")) &&
                    (!this.mvWeek.getSat().equals("Chapel"))) {
                satF = true;
            }
        }

        if (monF == false) {
            String mon1 = this.monIn.getText();
            String mon2 = this.monOut.getText();
            Date monD1 = this.parseTime(mon1);
            Date monD2 = this.parseTime(mon2);
            if ((monD1 != null) && (monD2 != null) && (!monD1.after(monD2))) {
                int[] monHrMin = this.subtractDate(monD1, monD2);
                if (this.mvWeek.getMon() != null) {
                    if (this.mvWeek.getMon().startsWith("IC")) {
                        totalICHrs = totalICHrs + monHrMin[0];
                        totalICMins = totalICMins + monHrMin[1];
                    } else if (this.mvWeek.getMon().equals("Chapel")) {
                        totalHrs = totalHrs + monHrMin[0];
                        totalMins = totalMins + monHrMin[1];
                    }
                } else {
                    totalHrs = totalHrs + monHrMin[0];
                    totalMins = totalMins + monHrMin[1];
                }
                this.monTime.setText(createStr(monHrMin[0], monHrMin[1], "         "));
                monF = true;
                this.monC = true;
            } else {
                this.monC = false;
            }
        }

        if (tueF == false) {
            String tue1 = this.tueIn.getText();
            String tue2 = this.tueOut.getText();
            Date tueD1 = this.parseTime(tue1);
            Date tueD2 = this.parseTime(tue2);
            if ((tueD1 != null) && (tueD2 != null) && (!tueD1.after(tueD2))) {
                int[] tueHrMin = this.subtractDate(tueD1, tueD2);
                if (this.mvWeek.getTue() != null) {
                    if (this.mvWeek.getTue().startsWith("IC")) {
                        totalICHrs = totalICHrs + tueHrMin[0];
                        totalICMins = totalICMins + tueHrMin[1];
                    } else if (this.mvWeek.getTue().equals("Chapel")) {
                        totalHrs = totalHrs + tueHrMin[0];
                        totalMins = totalMins + tueHrMin[1];
                    }
                } else {
                    totalHrs = totalHrs + tueHrMin[0];
                    totalMins = totalMins + tueHrMin[1];
                }
                this.tueTime.setText(createStr(tueHrMin[0], tueHrMin[1], "         "));
                tueF = true;
                this.tueC = true;
            } else {
                this.tueC = false;
            }
        }

        if (wedF == false) {
            String wed1 = this.wedIn.getText();
            String wed2 = this.wedOut.getText();
            Date wedD1 = this.parseTime(wed1);
            Date wedD2 = this.parseTime(wed2);
            if ((wedD1 != null) && (wedD2 != null) && (!wedD1.after(wedD2))) {
                int[] wedHrMin = this.subtractDate(wedD1, wedD2);
                if (this.mvWeek.getWed() != null) {
                    if (this.mvWeek.getWed().startsWith("IC")) {
                        totalICHrs = totalICHrs + wedHrMin[0];
                        totalICMins = totalICMins + wedHrMin[1];
                    } else if (this.mvWeek.getWed().equals("Chapel")) {
                        totalHrs = totalHrs + wedHrMin[0];
                        totalMins = totalMins + wedHrMin[1];
                    }
                } else {
                    totalHrs = totalHrs + wedHrMin[0];
                    totalMins = totalMins + wedHrMin[1];
                }
                this.wedTime.setText(createStr(wedHrMin[0], wedHrMin[1], "         "));
                wedF = true;
                this.wedC = true;
            } else {
                this.wedC = false;
            }
        }

        if (thuF == false) {
            String thu1 = this.thuIn.getText();
            String thu2 = this.thuOut.getText();
            Date thuD1 = this.parseTime(thu1);
            Date thuD2 = this.parseTime(thu2);
            if ((thuD1 != null) && (thuD2 != null) && (!thuD1.after(thuD2))) {
                int[] thuHrMin = this.subtractDate(thuD1, thuD2);
                if (this.mvWeek.getThu() != null) {
                    if (this.mvWeek.getThu().startsWith("IC")) {
                        totalICHrs = totalICHrs + thuHrMin[0];
                        totalICMins = totalICMins + thuHrMin[1];
                    } else if (this.mvWeek.getThu().equals("Chapel")) {
                        totalHrs = totalHrs + thuHrMin[0];
                        totalMins = totalMins + thuHrMin[1];
                    }
                } else {
                    totalHrs = totalHrs + thuHrMin[0];
                    totalMins = totalMins + thuHrMin[1];
                }
                this.thuTime.setText(createStr(thuHrMin[0], thuHrMin[1], "         "));
                thuF = true;
                this.thuC = true;
            } else {
                this.thuC = false;
            }
        }

        if (friF == false) {
            String fri1 = this.friIn.getText();
            String fri2 = this.friOut.getText();
            Date friD1 = this.parseTime(fri1);
            Date friD2 = this.parseTime(fri2);
            if ((friD1 != null) && (friD2 != null) && (!friD1.after(friD2))) {
                int[] friHrMin = this.subtractDate(friD1, friD2);
                if (this.mvWeek.getFri() != null) {
                    if (this.mvWeek.getFri().startsWith("IC")) {
                        totalICHrs = totalICHrs + friHrMin[0];
                        totalICMins = totalICMins + friHrMin[1];
                    } else if (this.mvWeek.getFri().equals("Chapel")) {
                        totalHrs = totalHrs + friHrMin[0];
                        totalMins = totalMins + friHrMin[1];
                    }
                } else {
                    totalHrs = totalHrs + friHrMin[0];
                    totalMins = totalMins + friHrMin[1];
                }
                this.friTime.setText(createStr(friHrMin[0], friHrMin[1], "         "));
                friF = true;
                this.friC = true;
            } else {
                this.friC = false;
            }
        }

        if (satF == false) {
            String sat1 = this.satIn.getText();
            String sat2 = this.satOut.getText();
            Date satD1 = this.parseTime(sat1);
            Date satD2 = this.parseTime(sat2);
            if ((satD1 != null) && (satD2 != null) && (!satD1.after(satD2))) {
                int[] satHrMin = this.subtractDate(satD1, satD2);
                if (this.mvWeek.getSat() != null) {
                    if (this.mvWeek.getSat().startsWith("IC")) {
                        totalICHrs = totalICHrs + satHrMin[0];
                        totalICMins = totalICMins + satHrMin[1];
                    } else if (this.mvWeek.getSat().equals("Chapel")) {
                        totalHrs = totalHrs + satHrMin[0];
                        totalMins = totalMins + satHrMin[1];
                    }
                } else {
                    totalHrs = totalHrs + satHrMin[0];
                    totalMins = totalMins + satHrMin[1];
                }
                this.satTime.setText(createStr(satHrMin[0], satHrMin[1], "         "));
                satF = true;
                this.satC = true;
            } else {
                this.satC = false;
            }
        }

        if (monF == true && tueF == true && wedF == true && thuF == true &&
                friF == true && satF == true) {
            int actualHrs = totalHrs + totalMins / 60;
            int actualMins = totalMins - (totalMins / 60) * 60;
            this.hourWeek.setText(createStr(actualHrs, actualMins, ""));
            this.totalMinsThisWeek = actualHrs * 60 + actualMins;

            int realTotalMins = this.totalMinsSoFar + this.totalMinsThisWeek;
            int actualTotalHrs = realTotalMins / 60;
            int actualTotalMIns = realTotalMins - actualTotalHrs * 60;
            this.hourTotal.setText(createStr(actualTotalHrs,
                    actualTotalMIns, ""));

            int actualTotalICMins = this.totalICMinsSoFar + totalICMins +
                    totalICHrs * 60;
            this.totalICMinsThisWeek = totalICMins + totalICHrs * 60;
            int actualTotalICHrsDisplayed = actualTotalICMins / 60;
            int actualTotalICMinsDispalyed = actualTotalICMins -
                    actualTotalICHrsDisplayed * 60;
            this.totalICHour.setText(createStr(actualTotalICHrsDisplayed,
                    actualTotalICMinsDispalyed, ""));
        }

    }

    private boolean typeCheck(String type) {
        if (type.equals("O")) {
            return true;
        } else if (type.equals("PA")) {
            return true;
        } else if (type.equals("PI")) {
            return true;
        } else if (type.endsWith("")) {
            return true;
        } else {
            return false;
        }
    }

    private Date parseTime(String time) {
        boolean isAM = false;

        String[] hrMin = time.split(":");
        if (hrMin.length == 1) {
            return null;
        }

        String hr = hrMin[0];
        String min = hrMin[1];

        if (min.endsWith("A")) {
            isAM = true;
            min = min.substring(0, min.length()-1);
        }

        if ((isInteger(hr) == false) || (isInteger(min) == false)) {
            return null;
        }

        int intHr = Integer.parseInt(hr);
        int intMin = Integer.parseInt(min);

        if (isAM == false) {
            if ((intHr) < 12 && (intHr != 0)) {
                intHr = intHr + 12;
            }
        }

        if ((intHr < 0) || (intHr > 24)) {
            return null;
        }

        if ((intMin < 0) || (intMin >= 60)) {
            return null;
        }

        GregorianCalendar gc = new GregorianCalendar(2010, 1, 1, intHr, intMin, 0);
        Date date = gc.getTime();

        return date;
    }

    private String setTimeStr(int hr, int min) {
        String hrStr, minStr, all;
        String AM = "";

        if ((hr <= 11) && (hr != 0)) {
            AM = "A";
        } else if (hr > 12) {
            hr = hr - 12;
        }

        if (hr <= 9) {
            hrStr = "0" + Integer.toString(hr);
        } else {
            hrStr = Integer.toString(hr);
        }

        if (min <= 9) {
            minStr = "0" + Integer.toString(min);
        } else {
            minStr = Integer.toString(min);
        }

        all = hrStr + ":" + minStr + AM;

        return all;
    }

    private int[] subtractDate(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        long diff = (l2 - l1) / 1000;
        int seconds = (int)diff;

        int hours, minutes;

        hours = seconds / 3600;
        seconds = seconds - (hours * 3600);
        minutes = seconds / 60;

        int[] hrMin = {hours, minutes};
        return hrMin;
    }

    private String createStr(int hr, int min, String space) {
        String hrStr, minStr, all;

        if (hr <= 9) {
            hrStr = "0" + Integer.toString(hr);
        } else {
            hrStr = Integer.toString(hr);
        }

        if (min <= 9) {
            minStr = "0" + Integer.toString(min);
        } else {
            minStr = Integer.toString(min);
        }

        all = space + hrStr + ":" + minStr;

        return all;
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int[] getHrMin(String time) {
        boolean isAM = false;

        String[] hrMin = time.split(":");
        if (hrMin.length == 1) {
            return null;
        }

        String hr = hrMin[0];
        String min = hrMin[1];

        if (min.endsWith("A")) {
            isAM = true;
            min = min.substring(0, min.length()-1);
        }

        if ((isInteger(hr) == false) || (isInteger(min) == false)) {
            return null;
        }

        int intHr = Integer.parseInt(hr);
        int intMin = Integer.parseInt(min);

        if (isAM == false) {
            if ((intHr < 12) && (intHr != 0)) {
                intHr = intHr + 12;
            }
        }

        if ((intHr < 0) || (intHr > 24)) {
            return null;
        }

        if ((intMin < 0) || (intMin >= 60)) {
            return null;
        }

        int[] intHrMin = {intHr, intMin};
        return intHrMin;
    }

    private void updateLogList() {
        String findUser = "mv_person_id = " + this.student.getId();
        List <MvUser> userList =
                mvApp.selectEntitiesBy(MvUser.class, findUser);
        MvUser mv_user = userList.get(0);

        String student_id = mv_user.getId().toString();
        String predicate = "mv_student_id = " + student_id;

        // Retrieve all the weekly logs of that student
        List<MvWeeklyLog> log_list = mvApp.selectEntitiesBy(MvWeeklyLog.class, predicate);

        String findWeeks = "class_id = " + this.class_id + " order by week_number";
        List<MvWeek> weekList = mvApp.selectEntitiesBy(MvWeek.class, findWeeks);

        String[] listItems = new String[weekList.size()];
        int i = 1;
        for (MvWeek oneMvWeek : weekList) {
            String status = "Not Yet Submitted";
            for (MvWeeklyLog oneLog : log_list) {
                if (oneLog.getWeekId().getId().equals(oneMvWeek.getId())) {
                    if (oneLog.getApprovedId() != null) {
                        status = "Approved";
                    } else if (oneLog.getDisapprovedId() != null) {
                        status = "Disapproved";
                    } else {
                        status = "Not Yet Approved";
                    }
                }
            }
            listItems[i-1] = "Week No. " + i + " - " + status;
            i++;
        }
        this.logList.setListData(listItems);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modify_button = new javax.swing.JButton();
        approve_button = new javax.swing.JButton();
        admin_log_exit_button = new javax.swing.JButton();
        mark_button = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel19 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        label2 = new java.awt.Label();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        hourWeek = new javax.swing.JLabel();
        hourSoFar = new javax.swing.JLabel();
        hourTotal = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        monOut = new javax.swing.JTextField();
        monIn = new javax.swing.JTextField();
        satIn = new javax.swing.JTextField();
        friOut = new javax.swing.JTextField();
        thuIn = new javax.swing.JTextField();
        wedOut = new javax.swing.JTextField();
        tueIn = new javax.swing.JTextField();
        tueOut = new javax.swing.JTextField();
        wedIn = new javax.swing.JTextField();
        thuOut = new javax.swing.JTextField();
        friIn = new javax.swing.JTextField();
        satOut = new javax.swing.JTextField();
        monTime = new javax.swing.JLabel();
        tueTime = new javax.swing.JLabel();
        wedTime = new javax.swing.JLabel();
        thuTime = new javax.swing.JLabel();
        friTime = new javax.swing.JLabel();
        satTime = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        totalICHour = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        absentDays = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        absentReasons = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        numCharLeft = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        class_name_label2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        week_numebr_head = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jRadioButton19 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        weekEndingLabel = new javax.swing.JLabel();
        dueDateLabel = new javax.swing.JLabel();
        sheetNumLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        classLabel = new javax.swing.JLabel();
        markField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        date_label = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        act1 = new javax.swing.JTextField();
        act2 = new javax.swing.JTextField();
        act3 = new javax.swing.JTextField();
        act4 = new javax.swing.JTextField();
        act5 = new javax.swing.JTextField();
        act6 = new javax.swing.JTextField();
        act7 = new javax.swing.JTextField();
        act8 = new javax.swing.JTextField();
        act9 = new javax.swing.JTextField();
        act1t = new javax.swing.JTextField();
        act2t = new javax.swing.JTextField();
        act3t = new javax.swing.JTextField();
        act4t = new javax.swing.JTextField();
        act5t = new javax.swing.JTextField();
        act6t = new javax.swing.JTextField();
        act7t = new javax.swing.JTextField();
        act8t = new javax.swing.JTextField();
        act9t = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        act10 = new javax.swing.JTextField();
        act11 = new javax.swing.JTextField();
        act12 = new javax.swing.JTextField();
        act13 = new javax.swing.JTextField();
        act14 = new javax.swing.JTextField();
        act15 = new javax.swing.JTextField();
        act16 = new javax.swing.JTextField();
        act17 = new javax.swing.JTextField();
        act18 = new javax.swing.JTextField();
        act10t = new javax.swing.JTextField();
        act11t = new javax.swing.JTextField();
        act12t = new javax.swing.JTextField();
        act13t = new javax.swing.JTextField();
        act14t = new javax.swing.JTextField();
        act15t = new javax.swing.JTextField();
        act16t = new javax.swing.JTextField();
        act17t = new javax.swing.JTextField();
        act18t = new javax.swing.JTextField();
        disapprove_button = new javax.swing.JButton();

        modify_button.setFont(new java.awt.Font("Verdana", 1, 14));
        modify_button.setText("Modify");
        modify_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modify_buttonActionPerformed(evt);
            }
        });

        approve_button.setFont(new java.awt.Font("Verdana", 1, 14));
        approve_button.setText("Approve");
        approve_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approve_buttonActionPerformed(evt);
            }
        });

        admin_log_exit_button.setFont(new java.awt.Font("Verdana", 1, 14));
        admin_log_exit_button.setText("Cancel / Exit");
        admin_log_exit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_log_exit_buttonActionPerformed(evt);
            }
        });

        mark_button.setFont(new java.awt.Font("Verdana", 1, 14));
        mark_button.setText("Mark");
        mark_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mark_buttonActionPerformed(evt);
            }
        });

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        jPanel19.setMaximumSize(new java.awt.Dimension(887, 895));
        jPanel19.setMinimumSize(new java.awt.Dimension(887, 895));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel18.setPreferredSize(new java.awt.Dimension(880, 500));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/meadowvale/resources/meadowvale_logo.jpg"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/meadowvale/resources/crest_70_70.gif"))); // NOI18N

        label2.setBackground(new java.awt.Color(255, 51, 51));
        label2.setFont(new java.awt.Font("Verdana", 0, 36));
        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("CO-OPERATIVE EDUCATION");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
        jPanel6.setPreferredSize(new java.awt.Dimension(300, 114));

        jLabel26.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("<html>\n<body>\n<p>Hours</p>\n<p>Carried</p> \n<p>Foward:</p>\n</body></html>");

        jLabel27.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("<html> <body> <p>Hours</p> <p>This</p>  <p>Week:</p> </body></html>");

        jLabel28.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("<html> <body> <p>Cumulatives</p> <p>Hours</p>  <p>To Date:</p> </body></html>");

        jLabel29.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("=");

        jLabel30.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("+");

        hourWeek.setFont(new java.awt.Font("Verdana", 1, 12));
        hourWeek.setText("00:00");

        hourSoFar.setFont(new java.awt.Font("Verdana", 1, 12));

        hourTotal.setFont(new java.awt.Font("Verdana", 1, 12));
        hourTotal.setText("00:00");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hourSoFar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addComponent(hourWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hourTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hourWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hourTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hourSoFar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));

        jLabel31.setBackground(new java.awt.Color(153, 153, 153));
        jLabel31.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("WEEKLY HOURS");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(714, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(153, 153, 153));

        jLabel32.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("DAY");
        jLabel32.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel33.setFont(new java.awt.Font("Verdana", 0, 12));
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("TIME");
        jLabel33.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel34.setFont(new java.awt.Font("Verdana", 0, 12));
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("IN");
        jLabel34.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel35.setFont(new java.awt.Font("Verdana", 0, 12));
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("OUT");
        jLabel35.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel36.setFont(new java.awt.Font("Verdana", 0, 12));
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("TOTAL");
        jLabel36.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel37.setFont(new java.awt.Font("Verdana", 0, 12));
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("HOURS:MINS");
        jLabel37.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(153, 153, 153));

        jPanel11.setBackground(new java.awt.Color(153, 153, 153));

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("THURSDAY");

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("WEDNESDAY");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("TUESDAY");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("MONDAY");

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("FRIDAY");

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("SATURDAY");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap(37, Short.MAX_VALUE)
                        .addComponent(jLabel15)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel20)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(153, 153, 153));

        monOut.setFont(new java.awt.Font("Tahoma", 1, 12));
        monOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                monOutKeyReleased(evt);
            }
        });

        monIn.setFont(new java.awt.Font("Tahoma", 1, 12));
        monIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                monInKeyReleased(evt);
            }
        });

        satIn.setFont(new java.awt.Font("Tahoma", 1, 12));
        satIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                satInKeyReleased(evt);
            }
        });

        friOut.setFont(new java.awt.Font("Tahoma", 1, 12));
        friOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                friOutKeyReleased(evt);
            }
        });

        thuIn.setFont(new java.awt.Font("Tahoma", 1, 12));
        thuIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                thuInKeyReleased(evt);
            }
        });

        wedOut.setFont(new java.awt.Font("Tahoma", 1, 12));
        wedOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                wedOutKeyReleased(evt);
            }
        });

        tueIn.setFont(new java.awt.Font("Tahoma", 1, 12));
        tueIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tueInKeyReleased(evt);
            }
        });

        tueOut.setFont(new java.awt.Font("Tahoma", 1, 12));
        tueOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tueOutKeyReleased(evt);
            }
        });

        wedIn.setFont(new java.awt.Font("Tahoma", 1, 12));
        wedIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                wedInKeyReleased(evt);
            }
        });

        thuOut.setFont(new java.awt.Font("Tahoma", 1, 12));
        thuOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                thuOutKeyReleased(evt);
            }
        });

        friIn.setFont(new java.awt.Font("Tahoma", 1, 12));
        friIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                friInKeyReleased(evt);
            }
        });

        satOut.setFont(new java.awt.Font("Tahoma", 1, 12));
        satOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                satOutKeyReleased(evt);
            }
        });

        monTime.setFont(new java.awt.Font("Tahoma", 1, 12));
        monTime.setText("         00:00");
        monTime.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tueTime.setFont(new java.awt.Font("Tahoma", 1, 12));
        tueTime.setText("         00:00");
        tueTime.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        wedTime.setFont(new java.awt.Font("Tahoma", 1, 12));
        wedTime.setText("         00:00");
        wedTime.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        thuTime.setFont(new java.awt.Font("Tahoma", 1, 12));
        thuTime.setText("         00:00");
        thuTime.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        friTime.setFont(new java.awt.Font("Tahoma", 1, 12));
        friTime.setText("         00:00");
        friTime.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        satTime.setFont(new java.awt.Font("Tahoma", 1, 12));
        satTime.setText("         00:00");
        satTime.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(satIn)
                        .addComponent(thuIn)
                        .addComponent(tueIn)
                        .addComponent(monIn, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                    .addComponent(wedIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(friIn, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tueOut)
                        .addComponent(monOut, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                    .addComponent(wedOut, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(thuOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(friOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(satOut, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(satTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(friTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(thuTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(wedTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tueTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(monTime))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(tueTime, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(wedTime, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(thuTime, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(friTime, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(satTime, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monTime, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tueIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tueOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wedIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wedOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(thuIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(thuOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(friOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(friIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(satIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(satOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))
        );

        jPanel20.setBackground(new java.awt.Color(153, 153, 153));

        jLabel54.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("<html> <body> <p>Total</p> <p>In-Class</p>  <p>Hours:</p> </body></html>");

        totalICHour.setFont(new java.awt.Font("Verdana", 1, 12));
        totalICHour.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(totalICHour, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalICHour, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(153, 153, 153));
        jPanel14.setPreferredSize(new java.awt.Dimension(300, 171));

        absentReasons.setColumns(20);
        absentReasons.setRows(5);
        absentReasons.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                absentReasonsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(absentReasons);

        jLabel55.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("<html> <body> <p>Reasons:</p> </html>");

        jLabel56.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("<html> <body> <p>Days Absent:</p> </html>");

        jLabel57.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Number of Characters Left:");

        numCharLeft.setFont(new java.awt.Font("Verdana", 1, 12));
        numCharLeft.setForeground(new java.awt.Color(255, 255, 255));
        numCharLeft.setText("100");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(absentDays, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(10, 10, 10)
                        .addComponent(numCharLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(absentDays, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(numCharLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(153, 153, 153));
        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        class_name_label2.setFont(new java.awt.Font("Verdana", 1, 12));
        class_name_label2.setForeground(new java.awt.Color(255, 255, 255));
        class_name_label2.setText("CLASS:");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("WEEK ENDING:");

        week_numebr_head.setFont(new java.awt.Font("Verdana", 1, 12));
        week_numebr_head.setForeground(new java.awt.Color(255, 255, 255));
        week_numebr_head.setText("WEEK LOG SHEET:");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("STUDENT NAME:");

        jRadioButton19.setBackground(new java.awt.Color(153, 153, 153));
        jRadioButton19.setText("0");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("FINAL MARK:");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Log/Journal Due:");

        weekEndingLabel.setFont(new java.awt.Font("Tahoma", 1, 12));

        dueDateLabel.setFont(new java.awt.Font("Tahoma", 1, 12));

        sheetNumLabel.setFont(new java.awt.Font("Tahoma", 1, 12));

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 12));

        classLabel.setFont(new java.awt.Font("Tahoma", 1, 12));

        markField.setFont(new java.awt.Font("Tahoma", 1, 12));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jRadioButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(week_numebr_head, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sheetNumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(dueDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(weekEndingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(class_name_label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(markField, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(classLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(week_numebr_head, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                        .addComponent(sheetNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(weekEndingLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(class_name_label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(classLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(396, 396, 396)
                        .addComponent(jRadioButton19))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dueDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(markField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 24));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Weekly Log / Journal Sheet");

        date_label.setFont(new java.awt.Font("Verdana", 1, 14));
        date_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date_label.setText("Feburary 22, 2010");

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));

        jLabel38.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("BRIEF SUMMARY OF THIS WEEK'S ACTIVITES:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addContainerGap(479, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(153, 153, 153));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel10.setText("TASKS");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel11.setText("TASKS");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(199, 199, 199))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
        );

        jLabel12.setText(" 1.");

        jLabel13.setText(" 2.");

        jLabel14.setText(" 3.");

        jLabel39.setText(" 4.");

        jLabel40.setText(" 5.");

        jLabel41.setText(" 6.");

        jLabel42.setText(" 7.");

        jLabel43.setText(" 8.");

        jLabel44.setText(" 9.");

        jLabel45.setText("10.");

        jLabel46.setText("11.");

        jLabel47.setText("12.");

        jLabel48.setText("13.");

        jLabel49.setText("14.");

        jLabel50.setText("15.");

        jLabel51.setText("16.");

        jLabel52.setText("17.");

        jLabel53.setText("18.");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act9))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act8))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act7))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act6))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act5))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act4))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(act1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(act8t)
                    .addComponent(act7t)
                    .addComponent(act6t)
                    .addComponent(act5t)
                    .addComponent(act4t)
                    .addComponent(act3t)
                    .addComponent(act2t)
                    .addComponent(act1t)
                    .addComponent(act9t, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel52)
                    .addComponent(jLabel51)
                    .addComponent(jLabel53)
                    .addComponent(jLabel50)
                    .addComponent(jLabel49)
                    .addComponent(jLabel48)
                    .addComponent(jLabel47)
                    .addComponent(jLabel46)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(act18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(act17)
                        .addComponent(act16)
                        .addComponent(act15)
                        .addComponent(act14)
                        .addComponent(act13)
                        .addComponent(act12)
                        .addComponent(act11)
                        .addComponent(act10, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(act11t)
                    .addComponent(act12t)
                    .addComponent(act10t, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(act13t)
                    .addComponent(act14t)
                    .addComponent(act15t)
                    .addComponent(act16t)
                    .addComponent(act17t)
                    .addComponent(act18t))
                .addContainerGap())
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act1t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act2t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act3t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act4t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(act5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(act5t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel40))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act6t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act7t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act8t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act9t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act10t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act11t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act12t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act13t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act14t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act15t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act16t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act17t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(act18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(act18t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                .addComponent(date_label, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jPanel4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(date_label)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 895, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jScrollPane4.setViewportView(jPanel19);

        disapprove_button.setFont(new java.awt.Font("Verdana", 1, 14));
        disapprove_button.setText("Disapprove");
        disapprove_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disapprove_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(approve_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(disapprove_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(mark_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(modify_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(admin_log_exit_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(approve_button, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(disapprove_button, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(mark_button, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(modify_button, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(admin_log_exit_button, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void modify_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_buttonActionPerformed
        String confirm = "Do you want to modify this weekly log sheet?";
        int selection = JOptionPane.showConfirmDialog(null, confirm, "Confirmation", 0);
        if (selection == 1) {
            return;
        }

        int absent_days = (Integer)this.absentDays.getValue();
        String absent_reasons = this.absentReasons.getText();
        
        if (absent_reasons.length() > 100) {
            JOptionPane.showMessageDialog(null, "Your reason for absence "
                    + "can be only 100 character long.", "Sorry...",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        this.log.setAbsentDays(absent_days);
        this.log.setAbsentReason(absent_reasons);
        
        if (this.monC == false) {
            this.monIn.setText("");
            this.monOut.setText("");
        }
        
        if (this.tueC == false) {
            this.tueIn.setText("");
            this.tueOut.setText("");
        }
        
        if (this.wedC == false) {
            this.wedIn.setText("");
            this.wedOut.setText("");
        }
        
        if (this.thuC == false) {
            this.thuIn.setText("");
            this.thuOut.setText("");
        }
        
        if (this.friC == false) {
            this.friIn.setText("");
            this.friOut.setText("");
        }
        
        if (this.satC == false) {
            this.satIn.setText("");
            this.satOut.setText("");
        }
        
        if (this.monC == false || this.tueC == false
                || this.wedC == false || this.thuC == false
                || this.friC == false || this.satC == false) {
                JOptionPane.showMessageDialog(null, "You typed some of the " +
                        "time " + "incorrectly.\nThe correct format is " +
                        "'number:number' (e.g. 10:10).\nPlease re-fill time " +
                        "entries that are left blank.", "Sorry...",
                        JOptionPane.WARNING_MESSAGE);
                return;
        }

        boolean typeCorrect = true;

        if (!(this.act1.getText().equals(""))) {
            if (typeCheck(this.act1t.getText())) {
                this.log.setAct1(this.act1.getText());
                this.log.setAct1t(this.act1t.getText());
            } else {
                this.act1t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act1t.setText("");
        }

        if (!(this.act2.getText().equals(""))) {
            if (typeCheck(this.act2t.getText())) {
                this.log.setAct2(this.act2.getText());
                this.log.setAct2t(this.act2t.getText());
            } else {
                this.act2t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act2t.setText("");
        }

        if (!(this.act3.getText().equals(""))) {
            if (typeCheck(this.act3t.getText())) {
                this.log.setAct3(this.act3.getText());
                this.log.setAct3t(this.act3t.getText());
            } else {
                this.act3t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act3t.setText("");
        }

        if (!(this.act4.getText().equals(""))) {
            if (typeCheck(this.act4t.getText())) {
                this.log.setAct4(this.act4.getText());
                this.log.setAct4t(this.act4t.getText());
            } else {
                this.act4t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act4t.setText("");
        }

        if (!(this.act5.getText().equals(""))) {
            if (typeCheck(this.act5t.getText())) {
                this.log.setAct5(this.act5.getText());
                this.log.setAct5t(this.act5t.getText());
            } else {
                this.act5t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act5t.setText("");
        }

        if (!(this.act6.getText().equals(""))) {
            if (typeCheck(this.act6t.getText())) {
                this.log.setAct6(this.act6.getText());
                this.log.setAct6t(this.act6t.getText());
            } else {
                this.act6t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act6t.setText("");
        }

        if (!(this.act7.getText().equals(""))) {
            if (typeCheck(this.act7t.getText())) {
                this.log.setAct7(this.act7.getText());
                this.log.setAct7t(this.act7t.getText());
            } else {
                this.act7t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act7t.setText("");
        }

        if (!(this.act8.getText().equals(""))) {
            if (typeCheck(this.act8t.getText())) {
                this.log.setAct8(this.act8.getText());
                this.log.setAct8t(this.act8t.getText());
            } else {
                this.act8t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act8t.setText("");
        }

        if (!(this.act9.getText().equals(""))) {
            if (typeCheck(this.act9t.getText())) {
                this.log.setAct9(this.act9.getText());
                this.log.setAct9t(this.act9t.getText());
            } else {
                this.act9t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act9t.setText("");
        }

        if (!(this.act10.getText().equals(""))) {
            if (typeCheck(this.act10t.getText())) {
                this.log.setAct10(this.act10.getText());
                this.log.setAct10t(this.act10t.getText());
            } else {
                this.act10t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act10t.setText("");
        }

        if (!(this.act11.getText().equals(""))) {
            if (typeCheck(this.act11t.getText())) {
                this.log.setAct11(this.act11.getText());
                this.log.setAct11t(this.act11t.getText());
            } else {
                this.act11t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act11t.setText("");
        }

        if (!(this.act12.getText().equals(""))) {
            if (typeCheck(this.act12t.getText())) {
                this.log.setAct12(this.act12.getText());
                this.log.setAct12t(this.act12t.getText());
            } else {
                this.act12t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act12t.setText("");
        }

        if (!(this.act13.getText().equals(""))) {
            if (typeCheck(this.act13t.getText())) {
                this.log.setAct13(this.act13.getText());
                this.log.setAct13t(this.act13t.getText());
            } else {
                this.act13t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act13t.setText("");
        }

        if (!(this.act14.getText().equals(""))) {
            if (typeCheck(this.act14t.getText())) {
                this.log.setAct14(this.act14.getText());
                this.log.setAct14t(this.act14t.getText());
            } else {
                this.act14t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act14t.setText("");
        }

        if (!(this.act15.getText().equals(""))) {
            if (typeCheck(this.act15t.getText())) {
                this.log.setAct15(this.act15.getText());
                this.log.setAct15t(this.act15t.getText());
            } else {
                this.act15t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act15t.setText("");
        }

        if (!(this.act16.getText().equals(""))) {
            if (typeCheck(this.act16t.getText())) {
                this.log.setAct16(this.act16.getText());
                this.log.setAct16t(this.act16t.getText());
            } else {
                this.act16t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act16t.setText("");
        }

        if (!(this.act17.getText().equals(""))) {
            if (typeCheck(this.act17t.getText())) {
                this.log.setAct17(this.act17.getText());
                this.log.setAct17t(this.act17t.getText());
            } else {
                this.act17t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act17t.setText("");
        }

        if (!(this.act18.getText().equals(""))) {
            if (typeCheck(this.act18t.getText())) {
                this.log.setAct18(this.act18.getText());
                this.log.setAct18t(this.act18t.getText());
            } else {
                this.act18t.setText("");
                typeCorrect = false;
            }
        } else {
            this.act18t.setText("");
        }

        if (typeCorrect == false) {
                JOptionPane.showMessageDialog(null, "You typed some of activity"
                        + " types incorrectly.\nThe correct type is one of the"
                        + " the following: 'O', 'PA', or 'PI'.\nPlease re-fill"
                        + " activity type entries that are left blank.",
                        "Sorry...", JOptionPane.WARNING_MESSAGE);
                return;
        }

        this.log.setDateSubmitted(new Date());

        this.log.setTotalMins(this.totalMinsThisWeek);
        this.log.setTotalIc(this.totalICMinsThisWeek);

        if ((this.mvWeek.getMon() == null) ||
                (this.mvWeek.getMon().startsWith("IC")) ||
                (this.mvWeek.getMon().equals("Chapel"))) {
            String mon1 = this.monIn.getText();
            String mon2 = this.monOut.getText();
            int[] monInHrMin = this.getHrMin(mon1);
            int[] monOutHrMin = this.getHrMin(mon2);
            this.log.setMonInHrs(monInHrMin[0]);
            this.log.setMonInMins(monInHrMin[1]);
            this.log.setMonOutHrs(monOutHrMin[0]);
            this.log.setMonOutMins(monOutHrMin[1]);
        }

        if ((this.mvWeek.getTue() == null) ||
                (this.mvWeek.getTue().startsWith("IC")) ||
                (this.mvWeek.getTue().equals("Chapel"))) {
            String tue1 = this.tueIn.getText();
            String tue2 = this.tueOut.getText();
            int[] tueInHrMin = this.getHrMin(tue1);
            int[] tueOutHrMin = this.getHrMin(tue2);
            this.log.setTueInHrs(tueInHrMin[0]);
            this.log.setTueInMins(tueInHrMin[1]);
            this.log.setTueOutHrs(tueOutHrMin[0]);
            this.log.setTueOutMins(tueOutHrMin[1]);
        }

        if ((this.mvWeek.getWed() == null) ||
                (this.mvWeek.getWed().startsWith("IC")) ||
                (this.mvWeek.getWed().equals("Chapel"))) {
            String wed1 = this.wedIn.getText();
            String wed2 = this.wedOut.getText();
            int[] wedInHrMin = this.getHrMin(wed1);
            int[] wedOutHrMin = this.getHrMin(wed2);
            this.log.setWedInHrs(wedInHrMin[0]);
            this.log.setWedInMins(wedInHrMin[1]);
            this.log.setWedOutHrs(wedOutHrMin[0]);
            this.log.setWedOutMins(wedOutHrMin[1]);
        }

        if ((this.mvWeek.getThu() == null) ||
                (this.mvWeek.getThu().startsWith("IC")) ||
                (this.mvWeek.getThu().equals("Chapel"))) {
            String thu1 = this.thuIn.getText();
            String thu2 = this.thuOut.getText();
            int[] thuInHrMin = this.getHrMin(thu1);
            int[] thuOutHrMin = this.getHrMin(thu2);
            this.log.setThuInHrs(thuInHrMin[0]);
            this.log.setThuInMins(thuInHrMin[1]);
            this.log.setThuOutHrs(thuOutHrMin[0]);
            this.log.setThuOutMins(thuOutHrMin[1]);
        }

        if ((this.mvWeek.getFri() == null) ||
                (this.mvWeek.getFri().startsWith("IC")) ||
                (this.mvWeek.getFri().equals("Chapel"))) {
            String fri1 = this.friIn.getText();
            String fri2 = this.friOut.getText();
            int[] friInHrMin = this.getHrMin(fri1);
            int[] friOutHrMin = this.getHrMin(fri2);
            this.log.setFriInHrs(friInHrMin[0]);
            this.log.setFriInMins(friInHrMin[1]);
            this.log.setFriOutHrs(friOutHrMin[0]);
            this.log.setFriOutMins(friOutHrMin[1]);
        }

        if ((this.mvWeek.getSat() == null) ||
                (this.mvWeek.getSat().startsWith("IC")) ||
                (this.mvWeek.getSat().equals("Chapel"))) {
            String sat1 = this.satIn.getText();
            String sat2 = this.satOut.getText();
            int[] satInHrMin = this.getHrMin(sat1);
            int[] satOutHrMin = this.getHrMin(sat2);
            this.log.setSatInHrs(satInHrMin[0]);
            this.log.setSatInMins(satInHrMin[1]);
            this.log.setSatOutHrs(satOutHrMin[0]);
            this.log.setSatOutMins(satOutHrMin[1]);
        }

        try {
            this.mvApp.MvWeeklyLogController.edit(this.log);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(null, "Weekly Log Sheet successfully " +
                "modified.", "Succeeded...", JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_modify_buttonActionPerformed

    private void approve_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approve_buttonActionPerformed
       if (this.log.getApprovedId() != null) {
           JOptionPane.showMessageDialog(null, "This Weekly Log Sheet has" +
                   " already been approved.", "Sorry...",
                   JOptionPane.WARNING_MESSAGE);
           this.dialogWindow.dispose();
           return;
       }
       
       this.log.setApprovedId(mvApp.user);
       this.log.setDisapprovedId(null);

       try {
           this.mvApp.MvWeeklyLogController.edit(this.log);
       } catch (NonexistentEntityException ex) {
            Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
       } catch (Exception ex) {
           Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
       }
       JOptionPane.showMessageDialog(null, "Weekly Log Sheet successfully " +
               "approved.\nPress 'OK' to exit.",
               "Succeeded...", JOptionPane.INFORMATION_MESSAGE);
       updateLogList();
       this.dialogWindow.dispose();
}//GEN-LAST:event_approve_buttonActionPerformed

    private void admin_log_exit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_log_exit_buttonActionPerformed
        this.dialogWindow.dispose();
}//GEN-LAST:event_admin_log_exit_buttonActionPerformed

    private void monInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monInKeyReleased
        updateTime();
    }//GEN-LAST:event_monInKeyReleased

    private void tueInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tueInKeyReleased
        updateTime();
    }//GEN-LAST:event_tueInKeyReleased

    private void tueOutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tueOutKeyReleased
        updateTime();
    }//GEN-LAST:event_tueOutKeyReleased

    private void wedInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_wedInKeyReleased
        updateTime();
    }//GEN-LAST:event_wedInKeyReleased

    private void wedOutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_wedOutKeyReleased
        updateTime();
    }//GEN-LAST:event_wedOutKeyReleased

    private void thuInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_thuInKeyReleased
        updateTime();
    }//GEN-LAST:event_thuInKeyReleased

    private void thuOutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_thuOutKeyReleased
        updateTime();
    }//GEN-LAST:event_thuOutKeyReleased

    private void friInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_friInKeyReleased
        updateTime();
    }//GEN-LAST:event_friInKeyReleased

    private void friOutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_friOutKeyReleased
        updateTime();
    }//GEN-LAST:event_friOutKeyReleased

    private void satInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_satInKeyReleased
        updateTime();
    }//GEN-LAST:event_satInKeyReleased

    private void satOutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_satOutKeyReleased
        updateTime();
    }//GEN-LAST:event_satOutKeyReleased

    private void mark_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mark_buttonActionPerformed
        if (this.markField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter the mark " +
               "first.", "Sorry...", JOptionPane.WARNING_MESSAGE);
            return;
        }

        float mark;

        try {
            mark = Float.parseFloat(this.markField.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Please enter a number for " +
               "the mark.", "Sorry...", JOptionPane.WARNING_MESSAGE);
            this.markField.setText("");
            this.markField.requestFocus();
            return;
        }

        this.log.setMark(mark);

        try {
            this.mvApp.MvWeeklyLogController.edit(this.log);
        } catch (NonexistentEntityException ex) {
             Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "The mark has been successfully " +
                "submitted.\nPress 'OK' to exit.",
                "Succeeded...", JOptionPane.INFORMATION_MESSAGE);
        updateLogList();
        this.dialogWindow.dispose();
    }//GEN-LAST:event_mark_buttonActionPerformed

    private void monOutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monOutKeyReleased
        updateTime();
    }//GEN-LAST:event_monOutKeyReleased

    private void disapprove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disapprove_buttonActionPerformed
        if (this.log.getDisapprovedId() != null) {
           JOptionPane.showMessageDialog(null, "This Weekly Log Sheet has" +
                   " already been disapproved.", "Sorry...",
                   JOptionPane.WARNING_MESSAGE);
           this.dialogWindow.dispose();
           return;
       }

       this.log.setDisapprovedId(mvApp.user);
       this.log.setApprovedId(null);

       try {
           this.mvApp.MvWeeklyLogController.edit(this.log);
       } catch (NonexistentEntityException ex) {
            Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
       } catch (Exception ex) {
           Logger.getLogger(WeeklyLogSheet.class.getName()).log(Level.SEVERE, null, ex);
       }
       JOptionPane.showMessageDialog(null, "Weekly Log Sheet successfully " +
               "disapproved.\nPress 'OK' to exit.",
               "Succeeded...", JOptionPane.INFORMATION_MESSAGE);
       updateLogList();
       this.dialogWindow.dispose();
    }//GEN-LAST:event_disapprove_buttonActionPerformed

    private void absentReasonsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_absentReasonsKeyReleased
        int len = this.absentReasons.getText().length();
        int left = 100 - len;
        this.numCharLeft.setText(String.valueOf(left));
    }//GEN-LAST:event_absentReasonsKeyReleased

    /**
     * change the approve_id of a weekly log to the current administrator's id
     * @param evt
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner absentDays;
    private javax.swing.JTextArea absentReasons;
    private javax.swing.JTextField act1;
    private javax.swing.JTextField act10;
    private javax.swing.JTextField act10t;
    private javax.swing.JTextField act11;
    private javax.swing.JTextField act11t;
    private javax.swing.JTextField act12;
    private javax.swing.JTextField act12t;
    private javax.swing.JTextField act13;
    private javax.swing.JTextField act13t;
    private javax.swing.JTextField act14;
    private javax.swing.JTextField act14t;
    private javax.swing.JTextField act15;
    private javax.swing.JTextField act15t;
    private javax.swing.JTextField act16;
    private javax.swing.JTextField act16t;
    private javax.swing.JTextField act17;
    private javax.swing.JTextField act17t;
    private javax.swing.JTextField act18;
    private javax.swing.JTextField act18t;
    private javax.swing.JTextField act1t;
    private javax.swing.JTextField act2;
    private javax.swing.JTextField act2t;
    private javax.swing.JTextField act3;
    private javax.swing.JTextField act3t;
    private javax.swing.JTextField act4;
    private javax.swing.JTextField act4t;
    private javax.swing.JTextField act5;
    private javax.swing.JTextField act5t;
    private javax.swing.JTextField act6;
    private javax.swing.JTextField act6t;
    private javax.swing.JTextField act7;
    private javax.swing.JTextField act7t;
    private javax.swing.JTextField act8;
    private javax.swing.JTextField act8t;
    private javax.swing.JTextField act9;
    private javax.swing.JTextField act9t;
    private javax.swing.JButton admin_log_exit_button;
    private javax.swing.JButton approve_button;
    private javax.swing.JLabel classLabel;
    private javax.swing.JLabel class_name_label2;
    private javax.swing.JLabel date_label;
    private javax.swing.JButton disapprove_button;
    private javax.swing.JLabel dueDateLabel;
    private javax.swing.JTextField friIn;
    private javax.swing.JTextField friOut;
    private javax.swing.JLabel friTime;
    private javax.swing.JLabel hourSoFar;
    private javax.swing.JLabel hourTotal;
    private javax.swing.JLabel hourWeek;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton19;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private java.awt.Label label2;
    private javax.swing.JTextField markField;
    private javax.swing.JButton mark_button;
    private javax.swing.JButton modify_button;
    private javax.swing.JTextField monIn;
    private javax.swing.JTextField monOut;
    private javax.swing.JLabel monTime;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel numCharLeft;
    private javax.swing.JTextField satIn;
    private javax.swing.JTextField satOut;
    private javax.swing.JLabel satTime;
    private javax.swing.JLabel sheetNumLabel;
    private javax.swing.JTextField thuIn;
    private javax.swing.JTextField thuOut;
    private javax.swing.JLabel thuTime;
    private javax.swing.JLabel totalICHour;
    private javax.swing.JTextField tueIn;
    private javax.swing.JTextField tueOut;
    private javax.swing.JLabel tueTime;
    private javax.swing.JTextField wedIn;
    private javax.swing.JTextField wedOut;
    private javax.swing.JLabel wedTime;
    private javax.swing.JLabel weekEndingLabel;
    private javax.swing.JLabel week_numebr_head;
    // End of variables declaration//GEN-END:variables

}
