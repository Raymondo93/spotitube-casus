package com.spotitube.app.dao;

import com.spotitube.app.DTO.UserLoginDTO;
import com.spotitube.app.DTO.UserLoginResponseDTO;
import com.spotitube.app.exceptions.UserOrPasswordFailException;
import com.spotitube.app.model.IUserModel;

public interface IUserDAO {

    /**
     *  {@inheritDoc}
     */
    boolean loginUser(UserLoginDTO dto) throws UserOrPasswordFailException;

    /**
        {@inheritDoc}
     */
    void saveUserToken(UserLoginResponseDTO dto);

}
