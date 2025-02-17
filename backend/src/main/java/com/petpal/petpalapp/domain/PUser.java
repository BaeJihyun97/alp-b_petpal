package com.petpal.petpalapp.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petpal.petpalapp.common.enums.PState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.JoinColumns;
// import com.petpal.petpalapp.domain.code.UserGroupCode;
@Entity
@Table(name = "puser")
@Getter
@Setter
public class PUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String phoneNumber;

    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private LocalDateTime registerDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PState state;

    @Column(name = "user_group_code_id", length = 50)
    private String userGroupCodeId;

    @Column(name = "user_group_code_group_id", length = 50)
    private String userGroupCodeGroupId;

    // @ManyToOne
    // @JoinColumns({
    //     @JoinColumn(name = "user_group_code", referencedColumnName = "user_group_code"),
    //     @JoinColumn(name = "code", referencedColumnName = "code")
    // })
    // private UserGroupCode userGroupCode;

}
