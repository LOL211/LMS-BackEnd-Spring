package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.classes.ClassEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test")
public class TestsEntity {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String test_name;

    @ManyToOne
    @JoinColumn(name = "class_name")
    private ClassEntity className;
}

