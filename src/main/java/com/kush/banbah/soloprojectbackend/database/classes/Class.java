package com.kush.banbah.soloprojectbackend.database.classes;

import com.kush.banbah.soloprojectbackend.database.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class")
public class Class {


    @ManyToMany(mappedBy = "student_classes")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<User> students;

    @Id
    @Column(name="class_name")
    private String className;

    @NotNull
    @ManyToOne()
    @JoinColumn(name="teacher_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User teacher;

}
