package com.petpal.petpalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.petpal.petpalapp.common.enums.PState;
import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.dto.PUserDTO;
import com.petpal.petpalapp.dto.ResponseDTO;
import com.petpal.petpalapp.dto.request.PUserRequestDTO;
import com.petpal.petpalapp.dto.response.LoginResponseDTO;
import com.petpal.petpalapp.mapper.PUserMapper;
import com.petpal.petpalapp.repository.PUserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PUserService {
    private final PUserRepository pUserRepository;
    private PUserMapper pUserMapper;

    @Autowired
    public PUserService(PUserRepository pUserRepository, PUserMapper pUserMapper) {
        this.pUserRepository = pUserRepository;
        this.pUserMapper = pUserMapper;
    }

    @Transactional
    public PUser createPUser(PUser user) {
        if (pUserRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Exist Email Address");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Empty Password");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Empty Name");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Empty Phone Number");
        }

        // if null value set current time
        user.setUpdatedAt(LocalDateTime.now());
        if (user.getCreatedAt() == null)
            user.setCreatedAt(LocalDateTime.now());
        if (user.getState() == null)
            user.setState(PState.ENABLED);
        if (user.getRegisterDate() == null)
            user.setRegisterDate(LocalDateTime.now());

        return pUserRepository.save(user);
    }

    @Transactional
    public Optional<PUser> updatePUser(Long id, PUser user) {

        if (!pUserRepository.existsById(id)) {
            throw new IllegalArgumentException("User with id " + id + "not found");
        }

        PUser existingUser = pUserRepository.findById(id).get();

        if (user.getName() != null || !user.getName().isEmpty()) {
            existingUser.setName(user.getName());
        }

        if (user.getPhoneNumber() != null || !user.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }

        // if null value set current time
        existingUser.setUpdatedAt(LocalDateTime.now());

        return Optional.ofNullable(pUserRepository.save(existingUser));

    }

    public List<PUser> getAllPUsers() {
        return pUserRepository.findAll();
    }

    public Optional<PUser> getPUserById(Long id) {
        return pUserRepository.findById(id);
    }

    @Transactional
    public void deletePUser(Long id) {
        pUserRepository.deleteById(id);
    }

    @Transactional
    public ResponseDTO<Void> updatePassword(PUserRequestDTO user) {

        PUser existingUser = pUserRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "계정이 없습니다."));

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
            existingUser.setUpdatedAt(LocalDateTime.now());

            Optional.ofNullable(pUserRepository.save(existingUser))
                    .orElseThrow(() -> new RuntimeException("내부 오류. 관리자에게 연략하세요."));

            return new ResponseDTO<>(200, "비밀번호 변경 성공", null);
        }

        return new ResponseDTO<>(401, "비밀번호 형식이 옳바르지 않습니다.", null);
    }

    public ResponseDTO<LoginResponseDTO> login(PUserRequestDTO loginDto, HttpSession session) {
        PUser user = pUserRepository.findByEmail(loginDto.getEmail()).get();

        if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
            return new ResponseDTO<>(401, "잘못된 이메일/비밀번호", this.pUserMapper.PUser2LoginResponseDTO(user));
        }

        session.setAttribute("user", user);
        return new ResponseDTO<>(200, "로그인 성공", this.pUserMapper.PUser2LoginResponseDTO(user));
    }

    public ResponseDTO<Void> logout(HttpSession session) {
        session.invalidate();
        return new ResponseDTO<>(200, "로그아웃 성공", null);
    }

    public ResponseDTO<Void> checkSession(HttpSession session) {
        PUser user = (PUser) session.getAttribute("user");
        if (user == null) {
            return new ResponseDTO<>(401, "로그인 정보 없음", null);
        }
        return new ResponseDTO<>(200, "로그인됨", null);
    }

}
