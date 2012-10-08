/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meadowvale;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import meadowvale.controllers.*;
import meadowvale.entities.*;


/**
 *
 * @author Team Sierra
 */
public class Application {
    public final String TITLE = "Meadowvale";
    private EntityManagerFactory emf = null;

    public MvAddressJpaController MvAddressController;
    public MvClassJpaController MvClassController;
    public MvCoopPlacementJpaController MvCoopPlacementController;
    public MvEmploymentJpaController MvEmploymentController;
    public MvEnrollmentJpaController MvEnrollmentController;
    public MvPersonJpaController MvPersonController;
    public MvPersonGroupJpaController MvPersonGroupController;
    public MvUserJpaController MvUserController;
    public MvWeekJpaController MvWeekController;
    public MvWeeklyLogJpaController MvWeeklyLogController;
    public MvCoopJpaController MvCoopController;
    public MvContactJpaController MvContactController;

    private Logger log;
    public String loginType;
    public MvUser user;
    public boolean loggedin = false;

    public Application() {
        try {
            System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.dat"))));
            System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.dat"))));
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
        log = Logger.getLogger(Application.class.getName());
        ConsoleHandler ch = new ConsoleHandler();
        log.addHandler(ch);
        
        boolean append = true;
        try {
            FileHandler fh = new FileHandler("meadowvale.log", append);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
        log.setLevel(Level.INFO);
        log.info("logger initialized!");
        log.info("current directory is: "+System.getProperty("user.dir"));
        initControllers();
        
        Properties databaseConfig = getDatabaseConfig();
        // FOR DEBUG USE:
        // System.out.println(databaseConfig);
        emf = Persistence.createEntityManagerFactory("MeadowvalePU");
        // FOR DEBUG USE:
        // System.out.println(emf.getProperties());
        EntityManager em = emf.createEntityManager();
        em.close();   // Temp em used to initiate connection with the databse.
    }

    private Properties getDatabaseConfig() {
        Properties databaseConfig = new Properties();
        try {
            InputStream input = Application.class.getClassLoader().getResourceAsStream("meadowvale/config/database.properties");
           
            if (input == null) {
                File f = new File(System.getProperty("user.dir")+"/config/database.properties");
                input = new FileInputStream(f);
            }
            databaseConfig.load(input);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return databaseConfig;
    }

    public void initControllers() {
        MvAddressController = new MvAddressJpaController(emf);
        MvClassController = new MvClassJpaController(emf);
        MvCoopPlacementController = new MvCoopPlacementJpaController(emf);
        MvEmploymentController = new MvEmploymentJpaController(emf);
        MvEnrollmentController = new MvEnrollmentJpaController(emf);
        MvPersonController = new MvPersonJpaController(emf);
        MvPersonGroupController = new MvPersonGroupJpaController(emf);
        MvUserController = new MvUserJpaController(emf);
        MvWeekController = new MvWeekJpaController(emf);
        MvWeeklyLogController = new MvWeeklyLogJpaController(emf);
        MvCoopController = new MvCoopJpaController(emf);
        MvContactController = new MvContactJpaController(emf);
    }
    
    /**
     *
     * @param entityClass e.g. MvUser.class
     * @param field the field name should be in camelCase with first letter
     *        lower cased
     * @return list of results returned by executing a query
     */
    public List selectEntitiesBy(Class entityClass, String predicate) {
        EntityManager em = emf.createEntityManager();

        // Create the basic part of a query
        String selectQuery = "select * from " +
                             this.camelToLower(entityClass.getSimpleName());

        // Append the WHERE clause
        if (predicate != null) {
            selectQuery += " where " + predicate + ";";
        }

        // Create the query for the specified entity class
        Query q = em.createNativeQuery(selectQuery, entityClass);

        // Retrieve the results as a list
        List resultList = q.getResultList();

        em.close();

        return resultList;
        
    }
    public int authentication(String username, char[] password) {

        // Get MvUser object that corresponds to rows in mv_user table
        String predicate = "user_name ='" + username + "'";
        List<MvUser> rl = this.selectEntitiesBy(MvUser.class, predicate);

        String pass = new String(password);
        
        // Invalid Username or Password
        if(rl.isEmpty()) {   
            return 0;
        } else if (rl.size() > 1) { // Should never occur due to the UNIQUE
            return 0;               // Key on the username field
        }else{  // Correct rows returned
            MvUser mv_user = rl.get(0);
            if (mv_user.getActive() == false) {
                return -1;
            } else {
                if (mv_user.getUserPassword().equals(pass)) {
                    this.user = mv_user;
                    MvPerson person = mv_user.getMvPersonId();
                    MvPersonGroup pg = person.getPersonGroupId();
                    this.loginType = pg.getTypeName();
                    return pg.getId();
                }
                return 0;
            }
        }
    }

    public String camelToLower(String camel) {
        Pattern p = Pattern.compile("([a-z]*+)([A-Z][a-z]*+)");
        Matcher m = p.matcher(camel);
        String result = "";
        
        while (m.find()) {
            result += m.group(1);
            result += "_"+m.group(2).toLowerCase();
        }
        
        if (result.startsWith("_")) {
            result = result.substring(1);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
    }
}