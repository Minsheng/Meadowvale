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
import meadowvale.entities.MvClass;
import meadowvale.entities.MvEnrollment;
import meadowvale.entities.MvPerson;

/**
 *
 * @author Eric
 */
public class MvEnrollmentJpaController extends AbstractController{

    public MvEnrollmentJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }

    public void create(MvEnrollment mvEnrollment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvClass classId = mvEnrollment.getClassId();
            if (classId != null) {
                classId = em.getReference(classId.getClass(), classId.getId());
                mvEnrollment.setClassId(classId);
            }
            MvPerson studentId = mvEnrollment.getStudentId();
            if (studentId != null) {
                studentId = em.getReference(studentId.getClass(), studentId.getId());
                mvEnrollment.setStudentId(studentId);
            }
            em.persist(mvEnrollment);
            if (classId != null) {
                classId.getMvEnrollmentCollection().add(mvEnrollment);
                classId = em.merge(classId);
            }
            if (studentId != null) {
                studentId.getMvEnrollmentCollection().add(mvEnrollment);
                studentId = em.merge(studentId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvEnrollment mvEnrollment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvEnrollment persistentMvEnrollment = em.find(MvEnrollment.class, mvEnrollment.getId());
            MvClass classIdOld = persistentMvEnrollment.getClassId();
            MvClass classIdNew = mvEnrollment.getClassId();
            MvPerson studentIdOld = persistentMvEnrollment.getStudentId();
            MvPerson studentIdNew = mvEnrollment.getStudentId();
            if (classIdNew != null) {
                classIdNew = em.getReference(classIdNew.getClass(), classIdNew.getId());
                mvEnrollment.setClassId(classIdNew);
            }
            if (studentIdNew != null) {
                studentIdNew = em.getReference(studentIdNew.getClass(), studentIdNew.getId());
                mvEnrollment.setStudentId(studentIdNew);
            }
            mvEnrollment = em.merge(mvEnrollment);
            if (classIdOld != null && !classIdOld.equals(classIdNew)) {
                classIdOld.getMvEnrollmentCollection().remove(mvEnrollment);
                classIdOld = em.merge(classIdOld);
            }
            if (classIdNew != null && !classIdNew.equals(classIdOld)) {
                classIdNew.getMvEnrollmentCollection().add(mvEnrollment);
                classIdNew = em.merge(classIdNew);
            }
            if (studentIdOld != null && !studentIdOld.equals(studentIdNew)) {
                studentIdOld.getMvEnrollmentCollection().remove(mvEnrollment);
                studentIdOld = em.merge(studentIdOld);
            }
            if (studentIdNew != null && !studentIdNew.equals(studentIdOld)) {
                studentIdNew.getMvEnrollmentCollection().add(mvEnrollment);
                studentIdNew = em.merge(studentIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvEnrollment.getId();
                if (findMvEnrollment(id) == null) {
                    throw new NonexistentEntityException("The mvEnrollment with id " + id + " no longer exists.");
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
            MvEnrollment mvEnrollment;
            try {
                mvEnrollment = em.getReference(MvEnrollment.class, id);
                mvEnrollment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvEnrollment with id " + id + " no longer exists.", enfe);
            }
            MvClass classId = mvEnrollment.getClassId();
            if (classId != null) {
                classId.getMvEnrollmentCollection().remove(mvEnrollment);
                classId = em.merge(classId);
            }
            MvPerson studentId = mvEnrollment.getStudentId();
            if (studentId != null) {
                studentId.getMvEnrollmentCollection().remove(mvEnrollment);
                studentId = em.merge(studentId);
            }
            em.remove(mvEnrollment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvEnrollment> findMvEnrollmentEntities() {
        return findMvEnrollmentEntities(true, -1, -1);
    }

    public List<MvEnrollment> findMvEnrollmentEntities(int maxResults, int firstResult) {
        return findMvEnrollmentEntities(false, maxResults, firstResult);
    }

    private List<MvEnrollment> findMvEnrollmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvEnrollment.class));
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

    public MvEnrollment findMvEnrollment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvEnrollment.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvEnrollmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvEnrollment> rt = cq.from(MvEnrollment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
