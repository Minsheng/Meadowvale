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
import meadowvale.controllers.exceptions.NonexistentEntityException;
import meadowvale.entities.MvUser;
import meadowvale.entities.MvWeek;
import meadowvale.entities.MvWeeklyLog;

/**
 *
 * @author Eric
 */
public class MvWeeklyLogJpaController extends AbstractController{

    public MvWeeklyLogJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }

    public void create(MvWeeklyLog mvWeeklyLog) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvUser mvStudentId = mvWeeklyLog.getMvStudentId();
            if (mvStudentId != null) {
                mvStudentId = em.getReference(mvStudentId.getClass(), mvStudentId.getId());
                mvWeeklyLog.setMvStudentId(mvStudentId);
            }
            MvWeek weekId = mvWeeklyLog.getWeekId();
            if (weekId != null) {
                weekId = em.getReference(weekId.getClass(), weekId.getId());
                mvWeeklyLog.setWeekId(weekId);
            }
            MvUser disapprovedId = mvWeeklyLog.getDisapprovedId();
            if (disapprovedId != null) {
                disapprovedId = em.getReference(disapprovedId.getClass(), disapprovedId.getId());
                mvWeeklyLog.setDisapprovedId(disapprovedId);
            }
            MvUser approvedId = mvWeeklyLog.getApprovedId();
            if (approvedId != null) {
                approvedId = em.getReference(approvedId.getClass(), approvedId.getId());
                mvWeeklyLog.setApprovedId(approvedId);
            }
            em.persist(mvWeeklyLog);
            if (mvStudentId != null) {
                mvStudentId.getMvWeeklyLogCollection().add(mvWeeklyLog);
                mvStudentId = em.merge(mvStudentId);
            }
            if (weekId != null) {
                weekId.getMvWeeklyLogCollection().add(mvWeeklyLog);
                weekId = em.merge(weekId);
            }
            if (disapprovedId != null) {
                disapprovedId.getMvWeeklyLogCollection().add(mvWeeklyLog);
                disapprovedId = em.merge(disapprovedId);
            }
            if (approvedId != null) {
                approvedId.getMvWeeklyLogCollection().add(mvWeeklyLog);
                approvedId = em.merge(approvedId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvWeeklyLog mvWeeklyLog) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvWeeklyLog persistentMvWeeklyLog = em.find(MvWeeklyLog.class, mvWeeklyLog.getId());
            MvUser mvStudentIdOld = persistentMvWeeklyLog.getMvStudentId();
            MvUser mvStudentIdNew = mvWeeklyLog.getMvStudentId();
            MvWeek weekIdOld = persistentMvWeeklyLog.getWeekId();
            MvWeek weekIdNew = mvWeeklyLog.getWeekId();
            MvUser disapprovedIdOld = persistentMvWeeklyLog.getDisapprovedId();
            MvUser disapprovedIdNew = mvWeeklyLog.getDisapprovedId();
            MvUser approvedIdOld = persistentMvWeeklyLog.getApprovedId();
            MvUser approvedIdNew = mvWeeklyLog.getApprovedId();
            if (mvStudentIdNew != null) {
                mvStudentIdNew = em.getReference(mvStudentIdNew.getClass(), mvStudentIdNew.getId());
                mvWeeklyLog.setMvStudentId(mvStudentIdNew);
            }
            if (weekIdNew != null) {
                weekIdNew = em.getReference(weekIdNew.getClass(), weekIdNew.getId());
                mvWeeklyLog.setWeekId(weekIdNew);
            }
            if (disapprovedIdNew != null) {
                disapprovedIdNew = em.getReference(disapprovedIdNew.getClass(), disapprovedIdNew.getId());
                mvWeeklyLog.setDisapprovedId(disapprovedIdNew);
            }
            if (approvedIdNew != null) {
                approvedIdNew = em.getReference(approvedIdNew.getClass(), approvedIdNew.getId());
                mvWeeklyLog.setApprovedId(approvedIdNew);
            }
            mvWeeklyLog = em.merge(mvWeeklyLog);
            if (mvStudentIdOld != null && !mvStudentIdOld.equals(mvStudentIdNew)) {
                mvStudentIdOld.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                mvStudentIdOld = em.merge(mvStudentIdOld);
            }
            if (mvStudentIdNew != null && !mvStudentIdNew.equals(mvStudentIdOld)) {
                mvStudentIdNew.getMvWeeklyLogCollection().add(mvWeeklyLog);
                mvStudentIdNew = em.merge(mvStudentIdNew);
            }
            if (weekIdOld != null && !weekIdOld.equals(weekIdNew)) {
                weekIdOld.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                weekIdOld = em.merge(weekIdOld);
            }
            if (weekIdNew != null && !weekIdNew.equals(weekIdOld)) {
                weekIdNew.getMvWeeklyLogCollection().add(mvWeeklyLog);
                weekIdNew = em.merge(weekIdNew);
            }
            if (disapprovedIdOld != null && !disapprovedIdOld.equals(disapprovedIdNew)) {
                disapprovedIdOld.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                disapprovedIdOld = em.merge(disapprovedIdOld);
            }
            if (disapprovedIdNew != null && !disapprovedIdNew.equals(disapprovedIdOld)) {
                disapprovedIdNew.getMvWeeklyLogCollection().add(mvWeeklyLog);
                disapprovedIdNew = em.merge(disapprovedIdNew);
            }
            if (approvedIdOld != null && !approvedIdOld.equals(approvedIdNew)) {
                approvedIdOld.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                approvedIdOld = em.merge(approvedIdOld);
            }
            if (approvedIdNew != null && !approvedIdNew.equals(approvedIdOld)) {
                approvedIdNew.getMvWeeklyLogCollection().add(mvWeeklyLog);
                approvedIdNew = em.merge(approvedIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvWeeklyLog.getId();
                if (findMvWeeklyLog(id) == null) {
                    throw new NonexistentEntityException("The mvWeeklyLog with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvWeeklyLog mvWeeklyLog;
            try {
                mvWeeklyLog = em.getReference(MvWeeklyLog.class, id);
                mvWeeklyLog.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvWeeklyLog with id " + id + " no longer exists.", enfe);
            }
            MvUser mvStudentId = mvWeeklyLog.getMvStudentId();
            if (mvStudentId != null) {
                mvStudentId.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                mvStudentId = em.merge(mvStudentId);
            }
            MvWeek weekId = mvWeeklyLog.getWeekId();
            if (weekId != null) {
                weekId.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                weekId = em.merge(weekId);
            }
            MvUser disapprovedId = mvWeeklyLog.getDisapprovedId();
            if (disapprovedId != null) {
                disapprovedId.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                disapprovedId = em.merge(disapprovedId);
            }
            MvUser approvedId = mvWeeklyLog.getApprovedId();
            if (approvedId != null) {
                approvedId.getMvWeeklyLogCollection().remove(mvWeeklyLog);
                approvedId = em.merge(approvedId);
            }
            em.remove(mvWeeklyLog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvWeeklyLog> findMvWeeklyLogEntities() {
        return findMvWeeklyLogEntities(true, -1, -1);
    }

    public List<MvWeeklyLog> findMvWeeklyLogEntities(int maxResults, int firstResult) {
        return findMvWeeklyLogEntities(false, maxResults, firstResult);
    }

    private List<MvWeeklyLog> findMvWeeklyLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvWeeklyLog.class));
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

    public MvWeeklyLog findMvWeeklyLog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvWeeklyLog.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvWeeklyLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvWeeklyLog> rt = cq.from(MvWeeklyLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
