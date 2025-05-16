/*package com.example.demo.Controller;

import com.example.demo.Dto.Request.BiletSilDto;
import com.example.demo.Entity.KullaniciEntity;
import com.example.demo.Repository.KullaniciRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/biletsil")
public class BiletSilController {
    private final BiletSilService biletSilService;
    private final KullaniciRepository kullaniciRepository;

    @Autowired
    public BiletSilController(BiletSilService biletSilService, KullaniciRepository kullaniciRepository) {
        this.biletSilService = biletSilService;
        this.kullaniciRepository = kullaniciRepository;
    }

    @PostMapping("/")
    public boolean biletSil(@RequestBody BiletSilDto biletSilDto) {
        Long userId = getCurrentUserId();
        return biletSilService.biletSil(biletSilDto.getBiletId(), userId);
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciAdi(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return kullanici.getKullaniciID();
    }
}*/
