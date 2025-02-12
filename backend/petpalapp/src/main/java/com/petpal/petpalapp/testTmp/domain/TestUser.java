package com.petpal.petpalapp.testTmp.domain;

import java.time.LocalDateTime;

// import org.hibernate.type.internal.UserTypeJavaTypeWrapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TestUser")
public class TestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    protected TestUser() {
    }

    public TestUser(String email, String name) {
        this.email = email;
        this.name = name;
        this.createDate = LocalDateTime.now();
    }

    public TestUser(String email, String name, LocalDateTime createDate) {
        this.email = email;
        this.name = name;
        this.createDate = createDate;
    }

    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
        return;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

}
