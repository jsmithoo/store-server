package com.example.adapters.inbound.controller;

import com.example.apis.UploadControllerApi;
import com.example.application.StorageService;
import com.example.domain.exception.UploadFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = "*")
@RequestMapping(value = "/api/v1.0/storage")
@RestController
public class UploadController implements UploadControllerApi {

    public final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam(value = "file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new UploadFileException("Archivo requerido");
        }
        return new ResponseEntity<>(storageService.UploadFile(multipartFile), HttpStatus.OK);
    }

    @PutMapping("/update/{file}")
    public ResponseEntity<String> updateFile(
            @PathVariable(name = "file") String fileName, @RequestBody byte[] newFile) {
        try {
            storageService.updateFile(fileName, newFile);
            return ResponseEntity.ok("Imagen actualizada con exito");
        } catch (UploadFileException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<Boolean> removeFile(@PathVariable(value = "filename") String fileName) {
        storageService.removeFile(fileName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
