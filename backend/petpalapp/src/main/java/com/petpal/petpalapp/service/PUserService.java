package com.petpal.petpalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.StringUtils;

import com.petpal.petpalapp.common.enums.PState;
import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.domain.UserGroupCode;
import com.petpal.petpalapp.dto.ResponseDTO;
import com.petpal.petpalapp.dto.request.PUserRegistDTO;
import com.petpal.petpalapp.dto.request.PUserRequestDTO;
import com.petpal.petpalapp.dto.request.PUserUpdateDTO;
import com.petpal.petpalapp.dto.response.LoginResponseDTO;
import com.petpal.petpalapp.mapper.PUserMapper;
import com.petpal.petpalapp.repository.PUserRepository;
import com.petpal.petpalapp.repository.UserGroupCodeRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// TODO: service에서 response 객체 만드는 거 control로 빼기.

@Slf4j
@Service
@Transactional(readOnly = true)
public class PUserService {
    private final PUserRepository pUserRepository;
    private PUserMapper pUserMapper;
    private PasswordEncoder passwordEncoder;
    private final UserGroupCodeRepository userGroupCodeRepository;

    @Autowired
    public PUserService(PUserRepository pUserRepository, PUserMapper pUserMapper, PasswordEncoder passwordEncoder,
            UserGroupCodeRepository userGroupCodeRepository) {
        this.pUserRepository = pUserRepository;
        this.pUserMapper = pUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userGroupCodeRepository = userGroupCodeRepository;
    }

    @Transactional
    public ResponseDTO<PUser> createPUser(PUserRegistDTO userDto) {
        if (!StringUtils.hasText(userDto.getEmail()) || pUserRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Exist Email Address");
        }

        if (!StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getName()) ||
                !StringUtils.hasText(userDto.getPhoneNumber())) {
            throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
        }

        PUser user = this.pUserMapper.PUserRegistDTO2PUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 기본 그룹 설정
        UserGroupCode defaultGroup = userGroupCodeRepository.findById("100")
                .orElseThrow(() -> new RuntimeException("Default group not found"));
        user.setUserGroupCode(defaultGroup);

        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setState(PState.ENABLED);
        user.setRegisterDate(LocalDateTime.now());

        PUser savedUser = Optional.ofNullable(pUserRepository.save(user))
                .orElseThrow(() -> new RuntimeException("내부 오류. 관리자에게 연략하세요."));

        return new ResponseDTO<>(200, "회원가입 성공", savedUser);
    }

    public ResponseDTO<Boolean> isEmailAvailable(String email) {
        System.out.println(email);
        return new ResponseDTO<>(200, "이메일 조회 성공", !pUserRepository.existsByEmail(email));
    }

    @Transactional
    public ResponseDTO<PUser> updatePUser(Long id, PUserUpdateDTO user) {

        PUser existingUser = pUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "계정이 없습니다."));

        if (StringUtils.hasText(user.getName())) {
            existingUser.setName(user.getName());
        }

        if (StringUtils.hasText(user.getPhoneNumber())) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }

        existingUser.setUpdatedAt(LocalDateTime.now());

        PUser savedUser = Optional.ofNullable(pUserRepository.save(existingUser))
                .orElseThrow(() -> new RuntimeException("내부 오류. 관리자에게 연략하세요."));

        return new ResponseDTO<>(200, "정보 수정 성공", savedUser);

    }

    public ResponseDTO<List<PUser>> getAllPUsers() {
        return new ResponseDTO<>(200, "회원 목록 조회 성공", pUserRepository.findAll());
    }

    public ResponseDTO<PUser> getPUserById(Long id) {
        PUser foundUser = pUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "계정이 없습니다."));

        return new ResponseDTO<>(200, "회원 조회 성공", foundUser);
    }

    @Transactional
    public ResponseDTO<Void> deletePUser(Long id) {
        pUserRepository.deleteById(id);

        return new ResponseDTO<>(200, "회원 삭제 성공", null);
    }

    // Auth
    @Transactional
    public ResponseDTO<Void> updatePassword(PUserRequestDTO user) {

        PUser existingUser = pUserRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "계정이 없습니다."));

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setUpdatedAt(LocalDateTime.now());

            Optional.ofNullable(pUserRepository.save(existingUser))
                    .orElseThrow(() -> new RuntimeException("내부 오류. 관리자에게 연략하세요."));

            return new ResponseDTO<>(200, "비밀번호 변경 성공", null);
        }

        return new ResponseDTO<>(401, "비밀번호 형식이 옳바르지 않습니다.", null);
    }

    @Transactional
    public LoginResponseDTO login(PUserRequestDTO loginDto, HttpSession session) {
        PUser user = pUserRepository.findByEmail(loginDto.getEmail()).orElse(null);

        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            user.setLastLogin(LocalDateTime.now());
            pUserRepository.save(user);

            session.setAttribute("user", user);
            return this.pUserMapper.PUser2LoginResponseDTO(user);
            // throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 이메일/비밀번호");
        }

        return null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public PUser checkSession(HttpSession session) {
        return (PUser) session.getAttribute("user");
    }

}
