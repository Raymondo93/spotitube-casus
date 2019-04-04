package com.spotitube.app.service;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.dao.IUserDAO;
import com.spotitube.app.dao.src.UserDAO;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

public class LoginServiceTest {

    static final String USERNAME = "admin";
    static final String PASSWORD = "welkom01";
    static final String TOKEN = "admin23423422";

    private LoginService service;
    private IUserDAO userDAO;

    @BeforeEach
    void setup() {
        service = new LoginService();
        userDAO = Mockito.mock(UserDAO.class);
        service.setLoginDAO(userDAO);
    }

    @Test
    public void loginUserTest() throws UserOrPasswordFailException {
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        responseDTO.setUser(USERNAME);
        responseDTO.setToken(TOKEN);
        UserLoginDTO dto = new UserLoginDTO(USERNAME, PASSWORD);
        Mockito.when(userDAO.loginUser(dto)).thenReturn(true);

        Response loginResponse = service.loginUser(dto);

        Assertions.assertEquals(200, loginResponse.getStatus());
    }

}
