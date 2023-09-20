package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.classes.Class;
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
public class Tests {

    @Id
    @GeneratedValue
    private int test_id;
    @NotNull
    @Column(name="test_name")
    private String testName;

    @ManyToOne
    @JoinColumn(name = "class_name")
    private Class belongsToClass;
}

