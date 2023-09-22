package com.kush.banbah.soloprojectbackend.database.StoredFiles;

import com.kush.banbah.soloprojectbackend.database.classes.Class;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoredFile {

    private String name;
    private String type;
    private Class owningClass;



}
