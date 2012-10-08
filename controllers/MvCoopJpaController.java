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
import meadowvale.entities.MvCoop;

/**
 *
 * @author Eric
 */
public class MvCoopJpaController extends AbstractController{

    public MvCoopJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }

    public void create(MvCoop mvCoop) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mvCoop);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvCoop mvCoop) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mvCoop = em.merge(mvCoop);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvCoop.getId();
                if (findMvCoop(id) == null) {
                    throw new NonexistentEntityException("The mvCoop with id " + id + " no longer exists.");
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
            MvCoop mvCoop;
            try {
                mvCoop = em.getReference(MvCoop.class, id);
                mvCoop.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvCoop with id " + id + " no longer exists.", enfe);
            }
            em.remove(mvCoop);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvCoop> findMvCoopEntities() {
        return findMvCoopEntities(true, -1, -1);
    }

    public List<MvCoop> findMvCoopEntities(int maxResults, int firstResult) {
        return findMvCoopEntities(false, maxResults, firstResult);
    }

    private List<MvCoop> findMvCoopEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvCoop.class));
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

    public MvCoop findMvCoop(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvCoop.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvCoopCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvCoop> rt = cq.from(MvCoop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
