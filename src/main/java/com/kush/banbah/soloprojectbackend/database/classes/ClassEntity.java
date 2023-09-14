package com.kush.banbah.soloprojectbackend.database.classes;

import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class")
public class ClassEntity {

    @Id
    private String className;

    @OneToOne
    @JoinColumn(name="teacher_id")
    private UserEntity teacher;

    @ManyToMany(mappedBy = "classes")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<UserEntity> users;

}
