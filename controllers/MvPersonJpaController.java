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
import meadowvale.entities.MvAddress;
import meadowvale.entities.MvPerson;
import meadowvale.entities.MvPersonGroup;
import meadowvale.entities.MvEmployment;
import java.util.ArrayList;
import java.util.Collection;
import meadowvale.entities.MvEnrollment;
import meadowvale.entities.MvUser;

/**
 *
 * @author Eric
 */
public class MvPersonJpaController extends AbstractController{

    public MvPersonJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }


    public void create(MvPerson mvPerson) {
        if (mvPerson.getMvEmploymentCollection() == null) {
            mvPerson.setMvEmploymentCollection(new ArrayList<MvEmployment>());
        }
        if (mvPerson.getMvEnrollmentCollection() == null) {
            mvPerson.setMvEnrollmentCollection(new ArrayList<MvEnrollment>());
        }
        if (mvPerson.getMvUserCollection() == null) {
            mvPerson.setMvUserCollection(new ArrayList<MvUser>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvAddress addressId = mvPerson.getAddressId();
            if (addressId != null) {
                addressId = em.getReference(addressId.getClass(), addressId.getId());
                mvPerson.setAddressId(addressId);
            }
            MvPersonGroup personGroupId = mvPerson.getPersonGroupId();
            if (personGroupId != null) {
                personGroupId = em.getReference(personGroupId.getClass(), personGroupId.getId());
                mvPerson.setPersonGroupId(personGroupId);
            }
            Collection<MvEmployment> attachedMvEmploymentCollection = new ArrayList<MvEmployment>();
            for (MvEmployment mvEmploymentCollectionMvEmploymentToAttach : mvPerson.getMvEmploymentCollection()) {
                mvEmploymentCollectionMvEmploymentToAttach = em.getReference(mvEmploymentCollectionMvEmploymentToAttach.getClass(), mvEmploymentCollectionMvEmploymentToAttach.getId());
                attachedMvEmploymentCollection.add(mvEmploymentCollectionMvEmploymentToAttach);
            }
            mvPerson.setMvEmploymentCollection(attachedMvEmploymentCollection);
            Collection<MvEnrollment> attachedMvEnrollmentCollection = new ArrayList<MvEnrollment>();
            for (MvEnrollment mvEnrollmentCollectionMvEnrollmentToAttach : mvPerson.getMvEnrollmentCollection()) {
                mvEnrollmentCollectionMvEnrollmentToAttach = em.getReference(mvEnrollmentCollectionMvEnrollmentToAttach.getClass(), mvEnrollmentCollectionMvEnrollmentToAttach.getId());
                attachedMvEnrollmentCollection.add(mvEnrollmentCollectionMvEnrollmentToAttach);
            }
            mvPerson.setMvEnrollmentCollection(attachedMvEnrollmentCollection);
            Collection<MvUser> attachedMvUserCollection = new ArrayList<MvUser>();
            for (MvUser mvUserCollectionMvUserToAttach : mvPerson.getMvUserCollection()) {
                mvUserCollectionMvUserToAttach = em.getReference(mvUserCollectionMvUserToAttach.getClass(), mvUserCollectionMvUserToAttach.getId());
                attachedMvUserCollection.add(mvUserCollectionMvUserToAttach);
            }
            mvPerson.setMvUserCollection(attachedMvUserCollection);
            em.persist(mvPerson);
            if (addressId != null) {
                addressId.getMvPersonCollection().add(mvPerson);
                addressId = em.merge(addressId);
            }
            if (personGroupId != null) {
                personGroupId.getMvPersonCollection().add(mvPerson);
                personGroupId = em.merge(personGroupId);
            }
            for (MvEmployment mvEmploymentCollectionMvEmployment : mvPerson.getMvEmploymentCollection()) {
                MvPerson oldStudentIdOfMvEmploymentCollectionMvEmployment = mvEmploymentCollectionMvEmployment.getStudentId();
                mvEmploymentCollectionMvEmployment.setStudentId(mvPerson);
                mvEmploymentCollectionMvEmployment = em.merge(mvEmploymentCollectionMvEmployment);
                if (oldStudentIdOfMvEmploymentCollectionMvEmployment != null) {
                    oldStudentIdOfMvEmploymentCollectionMvEmployment.getMvEmploymentCollection().remove(mvEmploymentCollectionMvEmployment);
                    oldStudentIdOfMvEmploymentCollectionMvEmployment = em.merge(oldStudentIdOfMvEmploymentCollectionMvEmployment);
                }
            }
            for (MvEnrollment mvEnrollmentCollectionMvEnrollment : mvPerson.getMvEnrollmentCollection()) {
                MvPerson oldStudentIdOfMvEnrollmentCollectionMvEnrollment = mvEnrollmentCollectionMvEnrollment.getStudentId();
                mvEnrollmentCollectionMvEnrollment.setStudentId(mvPerson);
                mvEnrollmentCollectionMvEnrollment = em.merge(mvEnrollmentCollectionMvEnrollment);
                if (oldStudentIdOfMvEnrollmentCollectionMvEnrollment != null) {
                    oldStudentIdOfMvEnrollmentCollectionMvEnrollment.getMvEnrollmentCollection().remove(mvEnrollmentCollectionMvEnrollment);
                    oldStudentIdOfMvEnrollmentCollectionMvEnrollment = em.merge(oldStudentIdOfMvEnrollmentCollectionMvEnrollment);
                }
            }
            for (MvUser mvUserCollectionMvUser : mvPerson.getMvUserCollection()) {
                MvPerson oldMvPersonIdOfMvUserCollectionMvUser = mvUserCollectionMvUser.getMvPersonId();
                mvUserCollectionMvUser.setMvPersonId(mvPerson);
                mvUserCollectionMvUser = em.merge(mvUserCollectionMvUser);
                if (oldMvPersonIdOfMvUserCollectionMvUser != null) {
                    oldMvPersonIdOfMvUserCollectionMvUser.getMvUserCollection().remove(mvUserCollectionMvUser);
                    oldMvPersonIdOfMvUserCollectionMvUser = em.merge(oldMvPersonIdOfMvUserCollectionMvUser);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvPerson mvPerson) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvPerson persistentMvPerson = em.find(MvPerson.class, mvPerson.getId());
            MvAddress addressIdOld = persistentMvPerson.getAddressId();
            MvAddress addressIdNew = mvPerson.getAddressId();
            MvPersonGroup personGroupIdOld = persistentMvPerson.getPersonGroupId();
            MvPersonGroup personGroupIdNew = mvPerson.getPersonGroupId();
            Collection<MvEmployment> mvEmploymentCollectionOld = persistentMvPerson.getMvEmploymentCollection();
            Collection<MvEmployment> mvEmploymentCollectionNew = mvPerson.getMvEmploymentCollection();
            Collection<MvEnrollment> mvEnrollmentCollectionOld = persistentMvPerson.getMvEnrollmentCollection();
            Collection<MvEnrollment> mvEnrollmentCollectionNew = mvPerson.getMvEnrollmentCollection();
            Collection<MvUser> mvUserCollectionOld = persistentMvPerson.getMvUserCollection();
            Collection<MvUser> mvUserCollectionNew = mvPerson.getMvUserCollection();
            List<String> illegalOrphanMessages = null;
            for (MvEmployment mvEmploymentCollectionOldMvEmployment : mvEmploymentCollectionOld) {
                if (!mvEmploymentCollectionNew.contains(mvEmploymentCollectionOldMvEmployment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MvEmployment " + mvEmploymentCollectionOldMvEmployment + " since its studentId field is not nullable.");
                }
            }
            for (MvEnrollment mvEnrollmentCollectionOldMvEnrollment : mvEnrollmentCollectionOld) {
                if (!mvEnrollmentCollectionNew.contains(mvEnrollmentCollectionOldMvEnrollment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MvEnrollment " + mvEnrollmentCollectionOldMvEnrollment + " since its studentId field is not nullable.");
                }
            }
            for (MvUser mvUserCollectionOldMvUser : mvUserCollectionOld) {
                if (!mvUserCollectionNew.contains(mvUserCollectionOldMvUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MvUser " + mvUserCollectionOldMvUser + " since its mvPersonId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (addressIdNew != null) {
                addressIdNew = em.getReference(addressIdNew.getClass(), addressIdNew.getId());
                mvPerson.setAddressId(addressIdNew);
            }
            if (personGroupIdNew != null) {
                personGroupIdNew = em.getReference(personGroupIdNew.getClass(), personGroupIdNew.getId());
                mvPerson.setPersonGroupId(personGroupIdNew);
            }
            Collection<MvEmployment> attachedMvEmploymentCollectionNew = new ArrayList<MvEmployment>();
            for (MvEmployment mvEmploymentCollectionNewMvEmploymentToAttach : mvEmploymentCollectionNew) {
                mvEmploymentCollectionNewMvEmploymentToAttach = em.getReference(mvEmploymentCollectionNewMvEmploymentToAttach.getClass(), mvEmploymentCollectionNewMvEmploymentToAttach.getId());
                attachedMvEmploymentCollectionNew.add(mvEmploymentCollectionNewMvEmploymentToAttach);
            }
            mvEmploymentCollectionNew = attachedMvEmploymentCollectionNew;
            mvPerson.setMvEmploymentCollection(mvEmploymentCollectionNew);
            Collection<MvEnrollment> attachedMvEnrollmentCollectionNew = new ArrayList<MvEnrollment>();
            for (MvEnrollment mvEnrollmentCollectionNewMvEnrollmentToAttach : mvEnrollmentCollectionNew) {
                mvEnrollmentCollectionNewMvEnrollmentToAttach = em.getReference(mvEnrollmentCollectionNewMvEnrollmentToAttach.getClass(), mvEnrollmentCollectionNewMvEnrollmentToAttach.getId());
                attachedMvEnrollmentCollectionNew.add(mvEnrollmentCollectionNewMvEnrollmentToAttach);
            }
            mvEnrollmentCollectionNew = attachedMvEnrollmentCollectionNew;
            mvPerson.setMvEnrollmentCollection(mvEnrollmentCollectionNew);
            Collection<MvUser> attachedMvUserCollectionNew = new ArrayList<MvUser>();
            for (MvUser mvUserCollectionNewMvUserToAttach : mvUserCollectionNew) {
                mvUserCollectionNewMvUserToAttach = em.getReference(mvUserCollectionNewMvUserToAttach.getClass(), mvUserCollectionNewMvUserToAttach.getId());
                attachedMvUserCollectionNew.add(mvUserCollectionNewMvUserToAttach);
            }
            mvUserCollectionNew = attachedMvUserCollectionNew;
            mvPerson.setMvUserCollection(mvUserCollectionNew);
            mvPerson = em.merge(mvPerson);
            if (addressIdOld != null && !addressIdOld.equals(addressIdNew)) {
                addressIdOld.getMvPersonCollection().remove(mvPerson);
                addressIdOld = em.merge(addressIdOld);
            }
            if (addressIdNew != null && !addressIdNew.equals(addressIdOld)) {
                addressIdNew.getMvPersonCollection().add(mvPerson);
                addressIdNew = em.merge(addressIdNew);
            }
            if (personGroupIdOld != null && !personGroupIdOld.equals(personGroupIdNew)) {
                personGroupIdOld.getMvPersonCollection().remove(mvPerson);
                personGroupIdOld = em.merge(personGroupIdOld);
            }
            if (personGroupIdNew != null && !personGroupIdNew.equals(personGroupIdOld)) {
                personGroupIdNew.getMvPersonCollection().add(mvPerson);
                personGroupIdNew = em.merge(personGroupIdNew);
            }
            for (MvEmployment mvEmploymentCollectionNewMvEmployment : mvEmploymentCollectionNew) {
                if (!mvEmploymentCollectionOld.contains(mvEmploymentCollectionNewMvEmployment)) {
                    MvPerson oldStudentIdOfMvEmploymentCollectionNewMvEmployment = mvEmploymentCollectionNewMvEmployment.getStudentId();
                    mvEmploymentCollectionNewMvEmployment.setStudentId(mvPerson);
                    mvEmploymentCollectionNewMvEmployment = em.merge(mvEmploymentCollectionNewMvEmployment);
                    if (oldStudentIdOfMvEmploymentCollectionNewMvEmployment != null && !oldStudentIdOfMvEmploymentCollectionNewMvEmployment.equals(mvPerson)) {
                        oldStudentIdOfMvEmploymentCollectionNewMvEmployment.getMvEmploymentCollection().remove(mvEmploymentCollectionNewMvEmployment);
                        oldStudentIdOfMvEmploymentCollectionNewMvEmployment = em.merge(oldStudentIdOfMvEmploymentCollectionNewMvEmployment);
                    }
                }
            }
            for (MvEnrollment mvEnrollmentCollectionNewMvEnrollment : mvEnrollmentCollectionNew) {
                if (!mvEnrollmentCollectionOld.contains(mvEnrollmentCollectionNewMvEnrollment)) {
                    MvPerson oldStudentIdOfMvEnrollmentCollectionNewMvEnrollment = mvEnrollmentCollectionNewMvEnrollment.getStudentId();
                    mvEnrollmentCollectionNewMvEnrollment.setStudentId(mvPerson);
                    mvEnrollmentCollectionNewMvEnrollment = em.merge(mvEnrollmentCollectionNewMvEnrollment);
                    if (oldStudentIdOfMvEnrollmentCollectionNewMvEnrollment != null && !oldStudentIdOfMvEnrollmentCollectionNewMvEnrollment.equals(mvPerson)) {
                        oldStudentIdOfMvEnrollmentCollectionNewMvEnrollment.getMvEnrollmentCollection().remove(mvEnrollmentCollectionNewMvEnrollment);
                        oldStudentIdOfMvEnrollmentCollectionNewMvEnrollment = em.merge(oldStudentIdOfMvEnrollmentCollectionNewMvEnrollment);
                    }
                }
            }
            for (MvUser mvUserCollectionNewMvUser : mvUserCollectionNew) {
                if (!mvUserCollectionOld.contains(mvUserCollectionNewMvUser)) {
                    MvPerson oldMvPersonIdOfMvUserCollectionNewMvUser = mvUserCollectionNewMvUser.getMvPersonId();
                    mvUserCollectionNewMvUser.setMvPersonId(mvPerson);
                    mvUserCollectionNewMvUser = em.merge(mvUserCollectionNewMvUser);
                    if (oldMvPersonIdOfMvUserCollectionNewMvUser != null && !oldMvPersonIdOfMvUserCollectionNewMvUser.equals(mvPerson)) {
                        oldMvPersonIdOfMvUserCollectionNewMvUser.getMvUserCollection().remove(mvUserCollectionNewMvUser);
                        oldMvPersonIdOfMvUserCollectionNewMvUser = em.merge(oldMvPersonIdOfMvUserCollectionNewMvUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvPerson.getId();
                if (findMvPerson(id) == null) {
                    throw new NonexistentEntityException("The mvPerson with id " + id + " no longer exists.");
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
            MvPerson mvPerson;
            try {
                mvPerson = em.getReference(MvPerson.class, id);
                mvPerson.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvPerson with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MvEmployment> mvEmploymentCollectionOrphanCheck = mvPerson.getMvEmploymentCollection();
            for (MvEmployment mvEmploymentCollectionOrphanCheckMvEmployment : mvEmploymentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MvPerson (" + mvPerson + ") cannot be destroyed since the MvEmployment " + mvEmploymentCollectionOrphanCheckMvEmployment + " in its mvEmploymentCollection field has a non-nullable studentId field.");
            }
            Collection<MvEnrollment> mvEnrollmentCollectionOrphanCheck = mvPerson.getMvEnrollmentCollection();
            for (MvEnrollment mvEnrollmentCollectionOrphanCheckMvEnrollment : mvEnrollmentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MvPerson (" + mvPerson + ") cannot be destroyed since the MvEnrollment " + mvEnrollmentCollectionOrphanCheckMvEnrollment + " in its mvEnrollmentCollection field has a non-nullable studentId field.");
            }
            Collection<MvUser> mvUserCollectionOrphanCheck = mvPerson.getMvUserCollection();
            for (MvUser mvUserCollectionOrphanCheckMvUser : mvUserCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MvPerson (" + mvPerson + ") cannot be destroyed since the MvUser " + mvUserCollectionOrphanCheckMvUser + " in its mvUserCollection field has a non-nullable mvPersonId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            MvAddress addressId = mvPerson.getAddressId();
            if (addressId != null) {
                addressId.getMvPersonCollection().remove(mvPerson);
                addressId = em.merge(addressId);
            }
            MvPersonGroup personGroupId = mvPerson.getPersonGroupId();
            if (personGroupId != null) {
                personGroupId.getMvPersonCollection().remove(mvPerson);
                personGroupId = em.merge(personGroupId);
            }
            em.remove(mvPerson);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvPerson> findMvPersonEntities() {
        return findMvPersonEntities(true, -1, -1);
    }

    public List<MvPerson> findMvPersonEntities(int maxResults, int firstResult) {
        return findMvPersonEntities(false, maxResults, firstResult);
    }

    private List<MvPerson> findMvPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvPerson.class));
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

    public MvPerson findMvPerson(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvPerson.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvPersonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvPerson> rt = cq.from(MvPerson.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
