package com.kush.banbah.soloprojectbackend.database.studentTest;


import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_test")
@IdClass(UserTestId.class)
public class StudentTestEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestsEntity test;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @NotNull
    private int score;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserTestId implements Serializable {
    private UserEntity user;
    private TestsEntity test;
}