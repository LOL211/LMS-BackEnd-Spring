package com.kush.banbah.soloprojectbackend.database.studentTest;


import com.kush.banbah.soloprojectbackend.database.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class StudentTest {

    @Id
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Tests test;
    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;
    @NotNull
    @Max(100)
    @Min(0)
    private int score;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserTestId implements Serializable {
    private User student;
    private Tests test;
}