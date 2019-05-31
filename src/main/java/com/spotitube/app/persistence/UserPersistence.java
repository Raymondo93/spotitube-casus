package com.spotitube.app.persistence;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.entity.User;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import com.spotitube.app.exceptions.UserTokenException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

public class UserPersistence {

    private static final String UNITNAME = "spotitube_research";

    public void loginUser(UserLoginDTO dto) throws UserOrPasswordFailException {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        EntityManager entityManager = emFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT 1 AS loggedIn FROM User u WHERE u.username = :username AND u.password = :password");
        query.setParameter("username", dto.getUser());
        query.setParameter("password", dto.getPassword());
        List<Long> user = query.getResultList();
        for(Long i: user) {
            if(i != 1) {
                throw new UserOrPasswordFailException("No user/password found on user " + dto.getUser());
            }
        }
        entityManager.close();
        emFactory.close();
    }

    public void createUser(UserLoginDTO userLoginDTO) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User();
        user.setUsername(userLoginDTO.getUser());
        user.setPassword(userLoginDTO.getPassword());
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        emFactory.close();
    }

    public void saveUserToken(UserLoginResponseDTO dto) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, dto.getUser());
        user.setToken(dto.getToken());
        entityManager.getTransaction().commit();
        entityManager.close();
        emFactory.close();
    }

    public boolean isAuthorized(String userToken) throws NotAuthorizedException {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        EntityManager entityManager = emFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT 1 from User WHERE token = :token");
        query.setParameter("token", userToken);
        List<Long> authorized = query.getResultList();
        for(Long i: authorized) {
            if(i != 1) {
                throw new NotAuthorizedException("User is not authorized. Please log in");
            }
        }
        entityManager.close();
        emFactory.close();
        return true;
    }

    public String getUsernameByToken(String usertoken) throws UserTokenException {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(UNITNAME);
        EntityManager entityManager = emFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT u.username FROM User u WHERE token = :token");
        List<String> userList = query.getResultList();
        String user = null;
        for(String u: userList) {
            user = u;
        }
        if(user == null) {
            throw new UserTokenException("No user found on token " + usertoken);
        }
        entityManager.close();
        emFactory.close();
        return user;
    }

}
