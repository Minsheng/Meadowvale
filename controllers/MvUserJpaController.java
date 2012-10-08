/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meadowvale.controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import meadowvale.controllers.exceptions.IllegalOrphanException;
import meadowvale.controllers.exceptions.NonexistentEntityException;
import meadowvale.entities.MvPerson;
import meadowvale.entities.MvUser;
import meadowvale.entities.MvWeeklyLog;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Eric
 */
public class MvUserJpaController extends AbstractController{

    public MvUserJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }

    public void create(MvUser mvUser) {
        if (mvUser.getMvWeeklyLogCollection() == null) {
            mvUser.setMvWeeklyLogCollection(new ArrayList<MvWeeklyLog>());
        }
        if (mvUser.getMvWeeklyLogCollection1() == null) {
            mvUser.setMvWeeklyLogCollection1(new ArrayList<MvWeeklyLog>());
        }
        if (mvUser.getMvWeeklyLogCollection2() == null) {
            mvUser.setMvWeeklyLogCollection2(new ArrayList<MvWeeklyLog>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvPerson mvPersonId = mvUser.getMvPersonId();
            if (mvPersonId != null) {
                mvPersonId = em.getReference(mvPersonId.getClass(), mvPersonId.getId());
                mvUser.setMvPersonId(mvPersonId);
            }
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollection = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollectionMvWeeklyLogToAttach : mvUser.getMvWeeklyLogCollection()) {
                mvWeeklyLogCollectionMvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollectionMvWeeklyLogToAttach.getClass(), mvWeeklyLogCollectionMvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollection.add(mvWeeklyLogCollectionMvWeeklyLogToAttach);
            }
            mvUser.setMvWeeklyLogCollection(attachedMvWeeklyLogCollection);
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollection1 = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollection1MvWeeklyLogToAttach : mvUser.getMvWeeklyLogCollection1()) {
                mvWeeklyLogCollection1MvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollection1MvWeeklyLogToAttach.getClass(), mvWeeklyLogCollection1MvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollection1.add(mvWeeklyLogCollection1MvWeeklyLogToAttach);
            }
            mvUser.setMvWeeklyLogCollection1(attachedMvWeeklyLogCollection1);
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollection2 = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollection2MvWeeklyLogToAttach : mvUser.getMvWeeklyLogCollection2()) {
                mvWeeklyLogCollection2MvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollection2MvWeeklyLogToAttach.getClass(), mvWeeklyLogCollection2MvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollection2.add(mvWeeklyLogCollection2MvWeeklyLogToAttach);
            }
            mvUser.setMvWeeklyLogCollection2(attachedMvWeeklyLogCollection2);
            em.persist(mvUser);
            if (mvPersonId != null) {
                mvPersonId.getMvUserCollection().add(mvUser);
                mvPersonId = em.merge(mvPersonId);
            }
            for (MvWeeklyLog mvWeeklyLogCollectionMvWeeklyLog : mvUser.getMvWeeklyLogCollection()) {
                MvUser oldMvStudentIdOfMvWeeklyLogCollectionMvWeeklyLog = mvWeeklyLogCollectionMvWeeklyLog.getMvStudentId();
                mvWeeklyLogCollectionMvWeeklyLog.setMvStudentId(mvUser);
                mvWeeklyLogCollectionMvWeeklyLog = em.merge(mvWeeklyLogCollectionMvWeeklyLog);
                if (oldMvStudentIdOfMvWeeklyLogCollectionMvWeeklyLog != null) {
                    oldMvStudentIdOfMvWeeklyLogCollectionMvWeeklyLog.getMvWeeklyLogCollection().remove(mvWeeklyLogCollectionMvWeeklyLog);
                    oldMvStudentIdOfMvWeeklyLogCollectionMvWeeklyLog = em.merge(oldMvStudentIdOfMvWeeklyLogCollectionMvWeeklyLog);
                }
            }
            for (MvWeeklyLog mvWeeklyLogCollection1MvWeeklyLog : mvUser.getMvWeeklyLogCollection1()) {
                MvUser oldDisapprovedIdOfMvWeeklyLogCollection1MvWeeklyLog = mvWeeklyLogCollection1MvWeeklyLog.getDisapprovedId();
                mvWeeklyLogCollection1MvWeeklyLog.setDisapprovedId(mvUser);
                mvWeeklyLogCollection1MvWeeklyLog = em.merge(mvWeeklyLogCollection1MvWeeklyLog);
                if (oldDisapprovedIdOfMvWeeklyLogCollection1MvWeeklyLog != null) {
                    oldDisapprovedIdOfMvWeeklyLogCollection1MvWeeklyLog.getMvWeeklyLogCollection1().remove(mvWeeklyLogCollection1MvWeeklyLog);
                    oldDisapprovedIdOfMvWeeklyLogCollection1MvWeeklyLog = em.merge(oldDisapprovedIdOfMvWeeklyLogCollection1MvWeeklyLog);
                }
            }
            for (MvWeeklyLog mvWeeklyLogCollection2MvWeeklyLog : mvUser.getMvWeeklyLogCollection2()) {
                MvUser oldApprovedIdOfMvWeeklyLogCollection2MvWeeklyLog = mvWeeklyLogCollection2MvWeeklyLog.getApprovedId();
                mvWeeklyLogCollection2MvWeeklyLog.setApprovedId(mvUser);
                mvWeeklyLogCollection2MvWeeklyLog = em.merge(mvWeeklyLogCollection2MvWeeklyLog);
                if (oldApprovedIdOfMvWeeklyLogCollection2MvWeeklyLog != null) {
                    oldApprovedIdOfMvWeeklyLogCollection2MvWeeklyLog.getMvWeeklyLogCollection2().remove(mvWeeklyLogCollection2MvWeeklyLog);
                    oldApprovedIdOfMvWeeklyLogCollection2MvWeeklyLog = em.merge(oldApprovedIdOfMvWeeklyLogCollection2MvWeeklyLog);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvUser mvUser) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvUser persistentMvUser = em.find(MvUser.class, mvUser.getId());
            MvPerson mvPersonIdOld = persistentMvUser.getMvPersonId();
            MvPerson mvPersonIdNew = mvUser.getMvPersonId();
            Collection<MvWeeklyLog> mvWeeklyLogCollectionOld = persistentMvUser.getMvWeeklyLogCollection();
            Collection<MvWeeklyLog> mvWeeklyLogCollectionNew = mvUser.getMvWeeklyLogCollection();
            Collection<MvWeeklyLog> mvWeeklyLogCollection1Old = persistentMvUser.getMvWeeklyLogCollection1();
            Collection<MvWeeklyLog> mvWeeklyLogCollection1New = mvUser.getMvWeeklyLogCollection1();
            Collection<MvWeeklyLog> mvWeeklyLogCollection2Old = persistentMvUser.getMvWeeklyLogCollection2();
            Collection<MvWeeklyLog> mvWeeklyLogCollection2New = mvUser.getMvWeeklyLogCollection2();
            List<String> illegalOrphanMessages = null;
            for (MvWeeklyLog mvWeeklyLogCollectionOldMvWeeklyLog : mvWeeklyLogCollectionOld) {
                if (!mvWeeklyLogCollectionNew.contains(mvWeeklyLogCollectionOldMvWeeklyLog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MvWeeklyLog " + mvWeeklyLogCollectionOldMvWeeklyLog + " since its mvStudentId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (mvPersonIdNew != null) {
                mvPersonIdNew = em.getReference(mvPersonIdNew.getClass(), mvPersonIdNew.getId());
                mvUser.setMvPersonId(mvPersonIdNew);
            }
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollectionNew = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollectionNewMvWeeklyLogToAttach : mvWeeklyLogCollectionNew) {
                mvWeeklyLogCollectionNewMvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollectionNewMvWeeklyLogToAttach.getClass(), mvWeeklyLogCollectionNewMvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollectionNew.add(mvWeeklyLogCollectionNewMvWeeklyLogToAttach);
            }
            mvWeeklyLogCollectionNew = attachedMvWeeklyLogCollectionNew;
            mvUser.setMvWeeklyLogCollection(mvWeeklyLogCollectionNew);
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollection1New = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollection1NewMvWeeklyLogToAttach : mvWeeklyLogCollection1New) {
                mvWeeklyLogCollection1NewMvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollection1NewMvWeeklyLogToAttach.getClass(), mvWeeklyLogCollection1NewMvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollection1New.add(mvWeeklyLogCollection1NewMvWeeklyLogToAttach);
            }
            mvWeeklyLogCollection1New = attachedMvWeeklyLogCollection1New;
            mvUser.setMvWeeklyLogCollection1(mvWeeklyLogCollection1New);
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollection2New = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollection2NewMvWeeklyLogToAttach : mvWeeklyLogCollection2New) {
                mvWeeklyLogCollection2NewMvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollection2NewMvWeeklyLogToAttach.getClass(), mvWeeklyLogCollection2NewMvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollection2New.add(mvWeeklyLogCollection2NewMvWeeklyLogToAttach);
            }
            mvWeeklyLogCollection2New = attachedMvWeeklyLogCollection2New;
            mvUser.setMvWeeklyLogCollection2(mvWeeklyLogCollection2New);
            mvUser = em.merge(mvUser);
            if (mvPersonIdOld != null && !mvPersonIdOld.equals(mvPersonIdNew)) {
                mvPersonIdOld.getMvUserCollection().remove(mvUser);
                mvPersonIdOld = em.merge(mvPersonIdOld);
            }
            if (mvPersonIdNew != null && !mvPersonIdNew.equals(mvPersonIdOld)) {
                mvPersonIdNew.getMvUserCollection().add(mvUser);
                mvPersonIdNew = em.merge(mvPersonIdNew);
            }
            for (MvWeeklyLog mvWeeklyLogCollectionNewMvWeeklyLog : mvWeeklyLogCollectionNew) {
                if (!mvWeeklyLogCollectionOld.contains(mvWeeklyLogCollectionNewMvWeeklyLog)) {
                    MvUser oldMvStudentIdOfMvWeeklyLogCollectionNewMvWeeklyLog = mvWeeklyLogCollectionNewMvWeeklyLog.getMvStudentId();
                    mvWeeklyLogCollectionNewMvWeeklyLog.setMvStudentId(mvUser);
                    mvWeeklyLogCollectionNewMvWeeklyLog = em.merge(mvWeeklyLogCollectionNewMvWeeklyLog);
                    if (oldMvStudentIdOfMvWeeklyLogCollectionNewMvWeeklyLog != null && !oldMvStudentIdOfMvWeeklyLogCollectionNewMvWeeklyLog.equals(mvUser)) {
                        oldMvStudentIdOfMvWeeklyLogCollectionNewMvWeeklyLog.getMvWeeklyLogCollection().remove(mvWeeklyLogCollectionNewMvWeeklyLog);
                        oldMvStudentIdOfMvWeeklyLogCollectionNewMvWeeklyLog = em.merge(oldMvStudentIdOfMvWeeklyLogCollectionNewMvWeeklyLog);
                    }
                }
            }
            for (MvWeeklyLog mvWeeklyLogCollection1OldMvWeeklyLog : mvWeeklyLogCollection1Old) {
                if (!mvWeeklyLogCollection1New.contains(mvWeeklyLogCollection1OldMvWeeklyLog)) {
                    mvWeeklyLogCollection1OldMvWeeklyLog.setDisapprovedId(null);
                    mvWeeklyLogCollection1OldMvWeeklyLog = em.merge(mvWeeklyLogCollection1OldMvWeeklyLog);
                }
            }
            for (MvWeeklyLog mvWeeklyLogCollection1NewMvWeeklyLog : mvWeeklyLogCollection1New) {
                if (!mvWeeklyLogCollection1Old.contains(mvWeeklyLogCollection1NewMvWeeklyLog)) {
                    MvUser oldDisapprovedIdOfMvWeeklyLogCollection1NewMvWeeklyLog = mvWeeklyLogCollection1NewMvWeeklyLog.getDisapprovedId();
                    mvWeeklyLogCollection1NewMvWeeklyLog.setDisapprovedId(mvUser);
                    mvWeeklyLogCollection1NewMvWeeklyLog = em.merge(mvWeeklyLogCollection1NewMvWeeklyLog);
                    if (oldDisapprovedIdOfMvWeeklyLogCollection1NewMvWeeklyLog != null && !oldDisapprovedIdOfMvWeeklyLogCollection1NewMvWeeklyLog.equals(mvUser)) {
                        oldDisapprovedIdOfMvWeeklyLogCollection1NewMvWeeklyLog.getMvWeeklyLogCollection1().remove(mvWeeklyLogCollection1NewMvWeeklyLog);
                        oldDisapprovedIdOfMvWeeklyLogCollection1NewMvWeeklyLog = em.merge(oldDisapprovedIdOfMvWeeklyLogCollection1NewMvWeeklyLog);
                    }
                }
            }
            for (MvWeeklyLog mvWeeklyLogCollection2OldMvWeeklyLog : mvWeeklyLogCollection2Old) {
                if (!mvWeeklyLogCollection2New.contains(mvWeeklyLogCollection2OldMvWeeklyLog)) {
                    mvWeeklyLogCollection2OldMvWeeklyLog.setApprovedId(null);
                    mvWeeklyLogCollection2OldMvWeeklyLog = em.merge(mvWeeklyLogCollection2OldMvWeeklyLog);
                }
            }
            for (MvWeeklyLog mvWeeklyLogCollection2NewMvWeeklyLog : mvWeeklyLogCollection2New) {
                if (!mvWeeklyLogCollection2Old.contains(mvWeeklyLogCollection2NewMvWeeklyLog)) {
                    MvUser oldApprovedIdOfMvWeeklyLogCollection2NewMvWeeklyLog = mvWeeklyLogCollection2NewMvWeeklyLog.getApprovedId();
                    mvWeeklyLogCollection2NewMvWeeklyLog.setApprovedId(mvUser);
                    mvWeeklyLogCollection2NewMvWeeklyLog = em.merge(mvWeeklyLogCollection2NewMvWeeklyLog);
                    if (oldApprovedIdOfMvWeeklyLogCollection2NewMvWeeklyLog != null && !oldApprovedIdOfMvWeeklyLogCollection2NewMvWeeklyLog.equals(mvUser)) {
                        oldApprovedIdOfMvWeeklyLogCollection2NewMvWeeklyLog.getMvWeeklyLogCollection2().remove(mvWeeklyLogCollection2NewMvWeeklyLog);
                        oldApprovedIdOfMvWeeklyLogCollection2NewMvWeeklyLog = em.merge(oldApprovedIdOfMvWeeklyLogCollection2NewMvWeeklyLog);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvUser.getId();
                if (findMvUser(id) == null) {
                    throw new NonexistentEntityException("The mvUser with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvUser mvUser;
            try {
                mvUser = em.getReference(MvUser.class, id);
                mvUser.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvUser with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MvWeeklyLog> mvWeeklyLogCollectionOrphanCheck = mvUser.getMvWeeklyLogCollection();
            for (MvWeeklyLog mvWeeklyLogCollectionOrphanCheckMvWeeklyLog : mvWeeklyLogCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MvUser (" + mvUser + ") cannot be destroyed since the MvWeeklyLog " + mvWeeklyLogCollectionOrphanCheckMvWeeklyLog + " in its mvWeeklyLogCollection field has a non-nullable mvStudentId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            MvPerson mvPersonId = mvUser.getMvPersonId();
            if (mvPersonId != null) {
                mvPersonId.getMvUserCollection().remove(mvUser);
                mvPersonId = em.merge(mvPersonId);
            }
            Collection<MvWeeklyLog> mvWeeklyLogCollection1 = mvUser.getMvWeeklyLogCollection1();
            for (MvWeeklyLog mvWeeklyLogCollection1MvWeeklyLog : mvWeeklyLogCollection1) {
                mvWeeklyLogCollection1MvWeeklyLog.setDisapprovedId(null);
                mvWeeklyLogCollection1MvWeeklyLog = em.merge(mvWeeklyLogCollection1MvWeeklyLog);
            }
            Collection<MvWeeklyLog> mvWeeklyLogCollection2 = mvUser.getMvWeeklyLogCollection2();
            for (MvWeeklyLog mvWeeklyLogCollection2MvWeeklyLog : mvWeeklyLogCollection2) {
                mvWeeklyLogCollection2MvWeeklyLog.setApprovedId(null);
                mvWeeklyLogCollection2MvWeeklyLog = em.merge(mvWeeklyLogCollection2MvWeeklyLog);
            }
            em.remove(mvUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvUser> findMvUserEntities() {
        return findMvUserEntities(true, -1, -1);
    }

    public List<MvUser> findMvUserEntities(int maxResults, int firstResult) {
        return findMvUserEntities(false, maxResults, firstResult);
    }

    private List<MvUser> findMvUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvUser.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MvUser findMvUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvUser> rt = cq.from(MvUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
