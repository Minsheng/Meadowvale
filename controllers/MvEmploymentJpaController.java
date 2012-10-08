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
import meadowvale.entities.MvCoopPlacement;
import meadowvale.entities.MvEmployment;
import meadowvale.entities.MvPerson;

/**
 *
 * @author Eric
 */
public class MvEmploymentJpaController extends AbstractController{

    public MvEmploymentJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }


    public void create(MvEmployment mvEmployment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvCoopPlacement placementId = mvEmployment.getPlacementId();
            if (placementId != null) {
                placementId = em.getReference(placementId.getClass(), placementId.getId());
                mvEmployment.setPlacementId(placementId);
            }
            MvPerson studentId = mvEmployment.getStudentId();
            if (studentId != null) {
                studentId = em.getReference(studentId.getClass(), studentId.getId());
                mvEmployment.setStudentId(studentId);
            }
            em.persist(mvEmployment);
            if (placementId != null) {
                placementId.getMvEmploymentCollection().add(mvEmployment);
                placementId = em.merge(placementId);
            }
            if (studentId != null) {
                studentId.getMvEmploymentCollection().add(mvEmployment);
                studentId = em.merge(studentId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvEmployment mvEmployment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvEmployment persistentMvEmployment = em.find(MvEmployment.class, mvEmployment.getId());
            MvCoopPlacement placementIdOld = persistentMvEmployment.getPlacementId();
            MvCoopPlacement placementIdNew = mvEmployment.getPlacementId();
            MvPerson studentIdOld = persistentMvEmployment.getStudentId();
            MvPerson studentIdNew = mvEmployment.getStudentId();
            if (placementIdNew != null) {
                placementIdNew = em.getReference(placementIdNew.getClass(), placementIdNew.getId());
                mvEmployment.setPlacementId(placementIdNew);
            }
            if (studentIdNew != null) {
                studentIdNew = em.getReference(studentIdNew.getClass(), studentIdNew.getId());
                mvEmployment.setStudentId(studentIdNew);
            }
            mvEmployment = em.merge(mvEmployment);
            if (placementIdOld != null && !placementIdOld.equals(placementIdNew)) {
                placementIdOld.getMvEmploymentCollection().remove(mvEmployment);
                placementIdOld = em.merge(placementIdOld);
            }
            if (placementIdNew != null && !placementIdNew.equals(placementIdOld)) {
                placementIdNew.getMvEmploymentCollection().add(mvEmployment);
                placementIdNew = em.merge(placementIdNew);
            }
            if (studentIdOld != null && !studentIdOld.equals(studentIdNew)) {
                studentIdOld.getMvEmploymentCollection().remove(mvEmployment);
                studentIdOld = em.merge(studentIdOld);
            }
            if (studentIdNew != null && !studentIdNew.equals(studentIdOld)) {
                studentIdNew.getMvEmploymentCollection().add(mvEmployment);
                studentIdNew = em.merge(studentIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvEmployment.getId();
                if (findMvEmployment(id) == null) {
                    throw new NonexistentEntityException("The mvEmployment with id " + id + " no longer exists.");
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
            MvEmployment mvEmployment;
            try {
                mvEmployment = em.getReference(MvEmployment.class, id);
                mvEmployment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvEmployment with id " + id + " no longer exists.", enfe);
            }
            MvCoopPlacement placementId = mvEmployment.getPlacementId();
            if (placementId != null) {
                placementId.getMvEmploymentCollection().remove(mvEmployment);
                placementId = em.merge(placementId);
            }
            MvPerson studentId = mvEmployment.getStudentId();
            if (studentId != null) {
                studentId.getMvEmploymentCollection().remove(mvEmployment);
                studentId = em.merge(studentId);
            }
            em.remove(mvEmployment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvEmployment> findMvEmploymentEntities() {
        return findMvEmploymentEntities(true, -1, -1);
    }

    public List<MvEmployment> findMvEmploymentEntities(int maxResults, int firstResult) {
        return findMvEmploymentEntities(false, maxResults, firstResult);
    }

    private List<MvEmployment> findMvEmploymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvEmployment.class));
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

    public MvEmployment findMvEmployment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvEmployment.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvEmploymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvEmployment> rt = cq.from(MvEmployment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
