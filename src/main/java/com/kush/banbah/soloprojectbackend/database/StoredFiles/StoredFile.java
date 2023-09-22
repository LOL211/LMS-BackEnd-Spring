package com.kush.banbah.soloprojectbackend.database.StoredFiles;

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
@Table(name = "files")
public class StoredFile {

    @Id
    @GeneratedValue
    private int file_id;

    private String name;
    private String type;
//    @Lob
    private byte[] data;
}
