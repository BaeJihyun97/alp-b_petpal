package com.petpal.petpalapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_group_code")
@Getter
@Setter
public class UserGroupCode {
    @Id
    @Column(name = "user_group_code", nullable = false, length = 20)
    private String userGroupCode;

    @Column(name = "user_group_name", nullable = false, length = 20)
    private String userGroupName;
}
