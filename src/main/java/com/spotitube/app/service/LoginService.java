package com.spotitube.app.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import java.util.Date;


@Path("/login")
@RequestScoped
public class LoginService {


    private IUserDAO userDAO;

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
            if (userDAO.loginUser(dto)) {
                loginResponseDTO.setUser(dto.getUser());
                loginResponseDTO.setToken(generateToken(dto.getUser()));
                userDAO.saveUserToken(loginResponseDTO);
                return Response.ok().entity(loginResponseDTO).build();
            }
        } catch (UserOrPasswordFailException e) {
            e.printStackTrace();
        }
        return Response.status(401).entity(loginResponseDTO).build();
    }

    @Inject
    public void setLoginDAO(IUserDAO userDAO) {this.userDAO = userDAO;}

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

