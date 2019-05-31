package com.spotitube.app.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.dao.src.DatabaseConnection;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.entity.User;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import com.spotitube.app.exceptions.UserTokenException;
import com.spotitube.app.persistence.UserPersistence;

import java.util.Date;


@Path("/login")
@RequestScoped
public class LoginService {


    private UserPersistence userPersistence;

    @Inject
    public void setLoginPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    /**
     * Login user
     * @param dto -> Data transfer object
     * @return -> DTO LoginResponse for frontend
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response loginUser(UserLoginDTO dto) {
        UserLoginResponseDTO loginResponseDTO = new UserLoginResponseDTO();
        try {
            userPersistence = new UserPersistence();
            userPersistence.loginUser(dto);
            loginResponseDTO.setUser(dto.getUser());
            loginResponseDTO.setToken(generateToken(dto.getUser()));
            userPersistence.saveUserToken(loginResponseDTO);
            return Response.ok().entity(loginResponseDTO).build();
        } catch (UserOrPasswordFailException e) {
            e.printStackTrace();
            return Response.status(401).build();
        }
    }


    /**
     * Generate token by current date and username
     * @param username -> current user who logged in
     * @return -> a token
     */
    private String generateToken(String username) {
        Date date = new Date();
        long millis = date.getTime();
        return username + millis;
    }

}

