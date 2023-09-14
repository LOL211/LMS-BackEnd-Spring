package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.classes.ClassEntity;
import jakarta.persistence.*;
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
    private String test_name;

    @ManyToOne
    @JoinColumn(name="className")
    private ClassEntity className;
}

