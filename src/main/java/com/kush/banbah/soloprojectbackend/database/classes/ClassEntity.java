package com.kush.banbah.soloprojectbackend.database.classes;

import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
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
public class ClassEntity {

    @ManyToMany(mappedBy = "classes")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<UserEntity> users;
    @Id
    private String className;
    @NotNull
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

}
