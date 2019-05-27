package com.spotitube.app.dao;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.exceptions.NotAuthorizedException;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import com.spotitube.app.exceptions.UserTokenException;


public interface IUserDAO {

    /**
     * Login user
     * @param dto => credits of user
     * @throws UserOrPasswordFailException => when login fails.
     */
    void loginUser(UserLoginDTO dto) throws UserOrPasswordFailException;

    /**
     * Save user token while logging in
     * @param dto => credits of user
     * @throws UserTokenException => when saving token fails.
     */
    void saveUserToken(UserLoginResponseDTO dto) throws UserTokenException;

    /**
     * Check if user is authorized
     * @param userToken => user token
     * @throws NotAuthorizedException => When user is not authorized
     */
    boolean isAuthorized(String userToken) throws NotAuthorizedException;

}
