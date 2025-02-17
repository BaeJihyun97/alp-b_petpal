package com.petpal.petpalapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.dto.PUserDTO;
import com.petpal.petpalapp.dto.request.PUserRegistDTO;
import com.petpal.petpalapp.dto.request.PUserRequestDTO;
import com.petpal.petpalapp.dto.response.LoginResponseDTO;
import com.petpal.petpalapp.dto.response.PUserResponseDTO;

@Mapper(componentModel = "spring")
public interface PUserMapper {

    // PUser -> ?
    LoginResponseDTO PUser2LoginResponseDTO(PUser user);

    PUserDTO PUser2PUserDTO(PUser user);

    PUserRequestDTO PUser2PUserRequestDTO(PUser user);

    // ? -> PUser
    PUser PUserRegistDTO2PUser(PUserRegistDTO user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "state", constant = "ENABLED")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "registerDate", expression = "java(java.time.LocalDateTime.now())")
    PUser toEntity(PUserRequestDTO requestDTO);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "userGroupCodeId", source = "userGroupCodeId")
    @Mapping(target = "userGroupCodeGroupId", source = "userGroupCodeGroupId")
    @Mapping(target = "lastLogin", source = "lastLogin")
    @Mapping(target = "registerDate", source = "registerDate")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PUserResponseDTO toResponseDTO(PUser user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "registerDate", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateFromDTO(PUserRequestDTO requestDTO, @MappingTarget PUser user);

}