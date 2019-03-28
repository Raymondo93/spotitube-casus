package com.spotitube.app.service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import org.json.JSONObject;
import java.util.Date;


@Path("/login")
public class LoginService {

    @Inject
    private IUserDAO userDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserLoginDTO dto) {
        System.out.println("komt die hier?");
        UserLoginResponseDTO loginResponseDTO;
        try {
            if (userDAO.loginUser(dto)) {
                loginResponseDTO = new UserLoginResponseDTO(generateToken(dto.getUsername()), dto.getUsername());
                userDAO.saveUserToken(loginResponseDTO);
                return Response.ok().entity(loginResponseDTO).build();
            }
        } catch (UserOrPasswordFailException e) {
            e.printStackTrace();
        }
        return getResponse(null, 401);
    }

    private String generateToken(String username) {
        Date date = new Date();
        long millis = date.getTime();
        return username + millis;
    }

    private Response getResponse(JSONObject responseData, int httpStatus) {
        return Response.status(httpStatus).entity(responseData).build();
    }

}

