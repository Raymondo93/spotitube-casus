package com.spotitube.app.service;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import com.spotitube.app.exceptions.UserTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

public class LoginServiceTest {

    static final String USERNAME = "admin";
    static final String PASSWORD = "welkom01";
    static final String TOKEN = "admin23423422";
    static UserLoginResponseDTO responseDTO;
    static UserLoginDTO dto;

    private static LoginService service;
    private IUserDAO userDAO;

    @BeforeAll
    public static void setupAll() {
        responseDTO = new UserLoginResponseDTO();
        dto = new UserLoginDTO(USERNAME, PASSWORD);
        responseDTO.setUser(USERNAME);
        responseDTO.setToken(TOKEN);
        service = new LoginService();
    }

    @BeforeEach
    void setup() {
        userDAO = Mockito.mock(UserDAO.class);
        service.setLoginDAO(userDAO);
    }

    @Test
    public void loginUserTest() throws UserOrPasswordFailException, UserTokenException {
        Mockito.doNothing().when(userDAO).loginUser(dto);

        Mockito.doNothing().when(userDAO).saveUserToken(responseDTO);

        Response loginResponse = service.loginUser(dto);

        Assertions.assertEquals(200, loginResponse.getStatus());
    }

    @Test
    public void loginUserPasswordExceptionTest() throws UserOrPasswordFailException {
        Mockito.doThrow(new UserOrPasswordFailException("Wrong password mate")).when(userDAO).loginUser(dto);
        Response loginResponse = service.loginUser(dto);
        Assertions.assertEquals(401, loginResponse.getStatus());
    }

}
