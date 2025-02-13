package com.petpal.petpalapp.mapper;

import org.mapstruct.Mapper;

import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.dto.PUserDTO;
import com.petpal.petpalapp.dto.request.PUserRegistDTO;
import com.petpal.petpalapp.dto.request.PUserRequestDTO;
import com.petpal.petpalapp.dto.response.LoginResponseDTO;

@Mapper(componentModel = "spring")
public interface PUserMapper {

    // PUser -> ?
    LoginResponseDTO PUser2LoginResponseDTO(PUser user);

    PUserDTO PUser2PUserDTO(PUser user);

    PUserRequestDTO PUser2PUserRequestDTO(PUser user);

    // ? -> PUser
    PUser PUserRegistDTO2PUser(PUserRegistDTO user);

}
