package com.kush.banbah.soloprojectbackend.controller.file.ResponseAndRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class FileListResponse {
    private String fileName;
    private long fileSize;
    private String fileType;
    private String fileUploadDate;
}
