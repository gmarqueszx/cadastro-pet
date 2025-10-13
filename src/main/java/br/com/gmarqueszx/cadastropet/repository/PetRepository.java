package br.com.gmarqueszx.cadastropet.repository;

import br.com.gmarqueszx.cadastropet.model.Pet;
import br.com.gmarqueszx.cadastropet.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class PetRepository {

    public void save(Pet pet) {
        EntityManager em = JpaUtil.entityManager();
        try {
            em.getTransaction().begin();
            if (pet.getId() == null) {
                em.persist(pet);
            } else {
                em.merge(pet);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Pet> findAll() {
        EntityManager em = JpaUtil.entityManager();
        try {
            String jpql = "SELECT p FROM Pet p";
            return em.createQuery(jpql, Pet.class).getResultList();
        } finally {
            em.close();
        }
    }


    public void delete(int id) {
        EntityManager em = JpaUtil.entityManager();
        Pet pet = em.find(Pet.class, id);

        try {
            em.getTransaction().begin();
            if (pet != null) {
                em.remove(pet);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    public Pet findById(int id) {
        EntityManager em = JpaUtil.entityManager();

        try {
            return em.find(Pet.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pet> findByPetName(String name) {
        EntityManager em = JpaUtil.entityManager();

        try {
            String jpql = "SELECT p FROM Pet p WHERE p.name LIKE :name";
            TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
            query.setParameter("name", "%" + name  + "%");
            return query.getResultList();
        } finally {
            em.close();
        }

    }

    public List<Pet> findByPetGender (String gender) {
        EntityManager em = JpaUtil.entityManager();

        try {
            String jpql = "SELECT p FROM Pet p WHERE lower(p.gender) LIKE lower(:gender)";
            TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
            query.setParameter("gender", "%" + gender  + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pet> findByPetAge (double age) {
        EntityManager em = JpaUtil.entityManager();

        try {
            String jpql = "SELECT p FROM Pet p WHERE p.age = :age";
            TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
            query.setParameter("age", age);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pet> findByPetWeight (double weight) {
        EntityManager em = JpaUtil.entityManager();

        try {
            String jpql = "SELECT p FROM Pet p WHERE p.weight = :weight";
            TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
            query.setParameter("weight", weight);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pet> findByPetBreed (String breed) {
        EntityManager em = JpaUtil.entityManager();

        try {
            String jpql = "SELECT p FROM Pet p WHERE p.breed LIKE (:breed)";
            TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
            query.setParameter("breed", "%" + breed  + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pet> findByPetCity (String city) {
        EntityManager em = JpaUtil.entityManager();

        try {
            String jpql = "SELECT p FROM Pet p WHERE lower(p.city) LIKE lower(:city)";
            TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
            query.setParameter("city", "%" + city  + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }




}
