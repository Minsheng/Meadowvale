/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StudentInfoSheet.java
 *
 * Created on 26-Mar-2010, 9:06:08 AM
 */

package meadowvale;

import java.awt.Color;
import java.util.*;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import meadowvale.entities.MvClass;
import meadowvale.entities.MvEnrollment;
import meadowvale.entities.MvPerson;
import meadowvale.entities.MvUser;
import meadowvale.entities.MvWeeklyLog;

/**
 *
 * @author Team Sierra
 */
public class StudentInfoSheet extends javax.swing.JPanel {
    JDialog dialogWindow;
    public MvClass mvClass;
    public MvPerson mvPerson;
    public MvEnrollment mvEnrollment;
    public MvUser mvUser;
    public JLabel acctDisabledLabel;
    public Application mvApp;

    // Creates a new form StudentInfoSheet
    public StudentInfoSheet(Application mvApp, MvClass mvClass,
            MvPerson mvPerson, JLabel acctDisabledLabel) {
        this.mvApp = mvApp;
        this.mvClass = mvClass;
        this.mvPerson = mvPerson;
        this.acctDisabledLabel = acctDisabledLabel;

        initComponents();
        prepareSheet();
    }

    private void prepareSheet(){
        String fullName = this.mvPerson.getFirstName() + " " +
                this.mvPerson.getLastName();
        this.nameField.setText(fullName);

        String className = this.mvClass.getTerm() + " - No. " +
                this.mvClass.getClassNumber() + " - " +
                this.mvClass.getClassName();
        this.classField.setText(className);

        String findEnroll = "student_id = "
                + this.mvPerson.getId() + " AND class_id = "
                + this.mvClass.getId();
        List<MvEnrollment> enrollList =
                mvApp.selectEntitiesBy(MvEnrollment.class, findEnroll);
        this.mvEnrollment = enrollList.get(0);
        int coopReq = this.mvEnrollment.getUniqueCoopHour();
        this.coopReqField.setText(String.valueOf(coopReq));

        int totalApprovedMins = 0;
        int actualHrs = 0;
        int actualMins = 0;

        int totalApprovedICMins = 0;
        int actualICHrs = 0;
        int actualICMins = 0;

        String findUser = "mv_person_id = " + this.mvPerson.getId();
        List<MvUser> userList =
                this.mvApp.selectEntitiesBy(MvUser.class, findUser);
        this.mvUser = userList.get(0);

        String findLogs = "mv_student_id = " + this.mvUser.getId();
        List<MvWeeklyLog> weeklyLogList =
                mvApp.selectEntitiesBy(MvWeeklyLog.class, findLogs);

        for (MvWeeklyLog mvWeeklyLog : weeklyLogList){
            if (mvWeeklyLog.getWeekId().getClassId().getId()
                    == this.mvClass.getId()){
                if (mvWeeklyLog.getApprovedId() != null){
                    totalApprovedMins += mvWeeklyLog.getTotalMins();
                    totalApprovedICMins += mvWeeklyLog.getTotalIc();
                }
            }
        }

        actualHrs = totalApprovedMins / 60;
        actualMins = totalApprovedMins - actualHrs * 60;
        String totalApprovedHours = actualHrs + " Hours "
                + actualMins + " Minutes";

        actualICHrs = totalApprovedICMins / 60;
        actualICMins = totalApprovedICMins - actualICHrs * 60;
        String totalApprovedICHours = actualICHrs + " Hours "
                + actualICMins + " Minutes";

        this.coopAppField.setText(totalApprovedHours);
        this.icAppField.setText(totalApprovedICHours);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        modifyButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        classField = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        icAppField = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        coopAppField = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        coopReqField = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 232));

        jPanel1.setBackground(new java.awt.Color(255, 255, 232));

        modifyButton.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        modifyButton.setText("Modify Co-op Hours Required");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 232));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setText("Student Name:");

        nameField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        nameField.setText("Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(nameField)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 232));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setText("Class:");

        classField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        classField.setText("Class");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                .addComponent(classField, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(classField))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 232));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("In-class Hours Approved:");

        icAppField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        icAppField.setText("IC App");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(icAppField, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(icAppField))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 232));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setText("Co-op Hours Approved:");

        coopAppField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        coopAppField.setText("Coop App");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(coopAppField, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(coopAppField))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 232));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setText("Co-op Hours Required:");

        coopReqField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        coopReqField.setText("Coop Req");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(coopReqField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5)
            .addComponent(coopReqField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(modifyButton)
                        .addGap(18, 18, 18)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(modifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        this.dialogWindow.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        String newCoopReq = this.coopReqField.getText();
        if (newCoopReq.equals("")){
            JOptionPane.showMessageDialog(null, "Please enter the new " +
                "required Co-op hours.", "Sorry...",
                JOptionPane.WARNING_MESSAGE);
            this.coopReqField.requestFocus();
            return;
        }
        
        try {
            int intNewCoopReq = Integer.parseInt(newCoopReq);
            this.mvEnrollment.setUniqueCoopHour(intNewCoopReq);
            this.mvApp.MvEnrollmentController.edit(this.mvEnrollment);
            JOptionPane.showMessageDialog(null, "The required Co-op hours are" +
                    " successfully updated.\nPress 'OK' to exit.",
                    "Succeeded...", JOptionPane.INFORMATION_MESSAGE);
            this.acctDisabledLabel.setText("The required Co-op hours are " +
                    "successfully updated");
            this.acctDisabledLabel.setForeground(Color.green);
            this.dialogWindow.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "The new required" +
                " Co-op hours must be an integer.", "Sorry...",
                JOptionPane.WARNING_MESSAGE);
            this.coopReqField.setText("");
            this.coopReqField.requestFocus();
        }


    }//GEN-LAST:event_modifyButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel classField;
    private javax.swing.JLabel coopAppField;
    private javax.swing.JTextField coopReqField;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel icAppField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton modifyButton;
    private javax.swing.JLabel nameField;
    // End of variables declaration//GEN-END:variables

}