package com.kush.banbah.soloprojectbackend.database.StoredFiles;

import com.kush.banbah.soloprojectbackend.database.classes.Class;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoredFile {

    private String fileName;
    private String type;
    private Class owningClass;
    private Date uploadDate;



}
