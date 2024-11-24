package com.example.application;

import com.example.domain.exception.UploadFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    private static final String IMAGE_PATH = "D:\\UDEMY\\tiendaonline\\public";

    public String UploadFile(MultipartFile multipartFile) {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString().substring(0, 6) + "-" + multipartFile.getOriginalFilename();
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = multipartFile.getBytes();
                Path filePath = Paths.get(IMAGE_PATH + "//" + fileName);
                Files.write(filePath, bytes);
                return fileName;
            } catch (Exception e) {
                throw new UploadFileException("Error al subir la imagen");
            }
        }
        throw new UploadFileException("La imagen esta vacía");
    }

    public void updateFile(String fileName, byte[] newFile) {
        File file = new File(IMAGE_PATH + "//" + fileName);
        if (file.exists()) {
            try {
                if (file.delete()) {
                    Files.write(file.toPath(), newFile);
                } else {
                    throw new UploadFileException("No se pudo eliminar el archivo");
                }
            } catch (IOException e) {
                throw new UploadFileException("Error al guardar el nuevo archivo");
            }
        } else {
            throw new UploadFileException("El archivo no existe");
        }
    }

    public boolean removeFile(String fileName) {
        File file = new File(IMAGE_PATH + "//" + fileName);
        if (file.exists()) {
            return file.delete();
        }
        throw new UploadFileException("El archivo esta vacío");
    }
}
