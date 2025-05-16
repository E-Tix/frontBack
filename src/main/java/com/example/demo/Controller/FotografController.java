package com.example.demo.Controller;

import com.example.demo.Service.FotografService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping("/etkinlikler")
@CrossOrigin(origins = "http://localhost:3000")
public class FotografController {

    @Autowired
    private FotografService fotografService;

    // Kapak fotoğrafı yükleme endpointi
    @PostMapping("/kapak-foto/{id}")
    public ResponseEntity<?> uploadKapakFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            fotografService.kapakFotografYukle(id, file);
            return ResponseEntity.ok("Kapak fotoğrafı başarıyla yüklendi.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yükleme hatası.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Kapak fotoğrafı getirme endpointi
    @GetMapping("/kapak-foto/{dosyaAdi}")
    public ResponseEntity<Resource> getKapakFoto(@PathVariable String dosyaAdi) {
        try {
            Path filePath = fotografService.getKapakFotografPath(dosyaAdi);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + dosyaAdi + "\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.IMAGE_JPEG) // veya dosya türüne göre dinamik ayarlanabilir
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
