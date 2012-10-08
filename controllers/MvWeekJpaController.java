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
import meadowvale.entities.MvClass;
import meadowvale.entities.MvWeek;
import meadowvale.entities.MvWeeklyLog;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Eric
 */
public class MvWeekJpaController extends AbstractController{

    public MvWeekJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }

    public void create(MvWeek mvWeek) {
        if (mvWeek.getMvWeeklyLogCollection() == null) {
            mvWeek.setMvWeeklyLogCollection(new ArrayList<MvWeeklyLog>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvClass classId = mvWeek.getClassId();
            if (classId != null) {
                classId = em.getReference(classId.getClass(), classId.getId());
                mvWeek.setClassId(classId);
            }
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollection = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollectionMvWeeklyLogToAttach : mvWeek.getMvWeeklyLogCollection()) {
                mvWeeklyLogCollectionMvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollectionMvWeeklyLogToAttach.getClass(), mvWeeklyLogCollectionMvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollection.add(mvWeeklyLogCollectionMvWeeklyLogToAttach);
            }
            mvWeek.setMvWeeklyLogCollection(attachedMvWeeklyLogCollection);
            em.persist(mvWeek);
            if (classId != null) {
                classId.getMvWeekCollection().add(mvWeek);
                classId = em.merge(classId);
            }
            for (MvWeeklyLog mvWeeklyLogCollectionMvWeeklyLog : mvWeek.getMvWeeklyLogCollection()) {
                MvWeek oldWeekIdOfMvWeeklyLogCollectionMvWeeklyLog = mvWeeklyLogCollectionMvWeeklyLog.getWeekId();
                mvWeeklyLogCollectionMvWeeklyLog.setWeekId(mvWeek);
                mvWeeklyLogCollectionMvWeeklyLog = em.merge(mvWeeklyLogCollectionMvWeeklyLog);
                if (oldWeekIdOfMvWeeklyLogCollectionMvWeeklyLog != null) {
                    oldWeekIdOfMvWeeklyLogCollectionMvWeeklyLog.getMvWeeklyLogCollection().remove(mvWeeklyLogCollectionMvWeeklyLog);
                    oldWeekIdOfMvWeeklyLogCollectionMvWeeklyLog = em.merge(oldWeekIdOfMvWeeklyLogCollectionMvWeeklyLog);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvWeek mvWeek) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvWeek persistentMvWeek = em.find(MvWeek.class, mvWeek.getId());
            MvClass classIdOld = persistentMvWeek.getClassId();
            MvClass classIdNew = mvWeek.getClassId();
            Collection<MvWeeklyLog> mvWeeklyLogCollectionOld = persistentMvWeek.getMvWeeklyLogCollection();
            Collection<MvWeeklyLog> mvWeeklyLogCollectionNew = mvWeek.getMvWeeklyLogCollection();
            List<String> illegalOrphanMessages = null;
            for (MvWeeklyLog mvWeeklyLogCollectionOldMvWeeklyLog : mvWeeklyLogCollectionOld) {
                if (!mvWeeklyLogCollectionNew.contains(mvWeeklyLogCollectionOldMvWeeklyLog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MvWeeklyLog " + mvWeeklyLogCollectionOldMvWeeklyLog + " since its weekId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (classIdNew != null) {
                classIdNew = em.getReference(classIdNew.getClass(), classIdNew.getId());
                mvWeek.setClassId(classIdNew);
            }
            Collection<MvWeeklyLog> attachedMvWeeklyLogCollectionNew = new ArrayList<MvWeeklyLog>();
            for (MvWeeklyLog mvWeeklyLogCollectionNewMvWeeklyLogToAttach : mvWeeklyLogCollectionNew) {
                mvWeeklyLogCollectionNewMvWeeklyLogToAttach = em.getReference(mvWeeklyLogCollectionNewMvWeeklyLogToAttach.getClass(), mvWeeklyLogCollectionNewMvWeeklyLogToAttach.getId());
                attachedMvWeeklyLogCollectionNew.add(mvWeeklyLogCollectionNewMvWeeklyLogToAttach);
            }
            mvWeeklyLogCollectionNew = attachedMvWeeklyLogCollectionNew;
            mvWeek.setMvWeeklyLogCollection(mvWeeklyLogCollectionNew);
            mvWeek = em.merge(mvWeek);
            if (classIdOld != null && !classIdOld.equals(classIdNew)) {
                classIdOld.getMvWeekCollection().remove(mvWeek);
                classIdOld = em.merge(classIdOld);
            }
            if (classIdNew != null && !classIdNew.equals(classIdOld)) {
                classIdNew.getMvWeekCollection().add(mvWeek);
                classIdNew = em.merge(classIdNew);
            }
            for (MvWeeklyLog mvWeeklyLogCollectionNewMvWeeklyLog : mvWeeklyLogCollectionNew) {
                if (!mvWeeklyLogCollectionOld.contains(mvWeeklyLogCollectionNewMvWeeklyLog)) {
                    MvWeek oldWeekIdOfMvWeeklyLogCollectionNewMvWeeklyLog = mvWeeklyLogCollectionNewMvWeeklyLog.getWeekId();
                    mvWeeklyLogCollectionNewMvWeeklyLog.setWeekId(mvWeek);
                    mvWeeklyLogCollectionNewMvWeeklyLog = em.merge(mvWeeklyLogCollectionNewMvWeeklyLog);
                    if (oldWeekIdOfMvWeeklyLogCollectionNewMvWeeklyLog != null && !oldWeekIdOfMvWeeklyLogCollectionNewMvWeeklyLog.equals(mvWeek)) {
                        oldWeekIdOfMvWeeklyLogCollectionNewMvWeeklyLog.getMvWeeklyLogCollection().remove(mvWeeklyLogCollectionNewMvWeeklyLog);
                        oldWeekIdOfMvWeeklyLogCollectionNewMvWeeklyLog = em.merge(oldWeekIdOfMvWeeklyLogCollectionNewMvWeeklyLog);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvWeek.getId();
                if (findMvWeek(id) == null) {
                    throw new NonexistentEntityException("The mvWeek with id " + id + " no longer exists.");
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
            MvWeek mvWeek;
            try {
                mvWeek = em.getReference(MvWeek.class, id);
                mvWeek.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvWeek with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MvWeeklyLog> mvWeeklyLogCollectionOrphanCheck = mvWeek.getMvWeeklyLogCollection();
            for (MvWeeklyLog mvWeeklyLogCollectionOrphanCheckMvWeeklyLog : mvWeeklyLogCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MvWeek (" + mvWeek + ") cannot be destroyed since the MvWeeklyLog " + mvWeeklyLogCollectionOrphanCheckMvWeeklyLog + " in its mvWeeklyLogCollection field has a non-nullable weekId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            MvClass classId = mvWeek.getClassId();
            if (classId != null) {
                classId.getMvWeekCollection().remove(mvWeek);
                classId = em.merge(classId);
            }
            em.remove(mvWeek);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvWeek> findMvWeekEntities() {
        return findMvWeekEntities(true, -1, -1);
    }

    public List<MvWeek> findMvWeekEntities(int maxResults, int firstResult) {
        return findMvWeekEntities(false, maxResults, firstResult);
    }

    private List<MvWeek> findMvWeekEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvWeek.class));
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

    public MvWeek findMvWeek(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvWeek.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvWeekCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvWeek> rt = cq.from(MvWeek.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
