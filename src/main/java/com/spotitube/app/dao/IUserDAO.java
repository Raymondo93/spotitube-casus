package com.spotitube.app.dao;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.exceptions.UserOrPasswordFailException;

public interface IUserDAO {

    /**
     *  {@inheritDoc}
     */
    boolean loginUser(UserLoginDTO dto) throws UserOrPasswordFailException;

    /**
        {@inheritDoc}
     */
    boolean saveUserToken(UserLoginResponseDTO dto);

}
