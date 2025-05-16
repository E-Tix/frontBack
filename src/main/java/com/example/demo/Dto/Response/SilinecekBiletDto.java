package com.example.demo.Dto.Response;

import com.example.demo.Entity.KullaniciEntity;
import com.example.demo.Entity.SeansEntity;

public class SilinecekBiletDto {
    private KullaniciEntity kullanici;
    private BiletDto biletDto;

    public SilinecekBiletDto(KullaniciEntity kullanici, BiletDto biletDto) {
        this.kullanici = kullanici;
        this.biletDto = biletDto;
    }

    public SilinecekBiletDto() {
    }

    public KullaniciEntity getKullanici() {
        return kullanici;
    }

    public void setKullanici(KullaniciEntity kullanici) {
        this.kullanici = kullanici;
    }

    public BiletDto getBiletDto() {
        return biletDto;
    }

    public void setBiletDto(BiletDto biletDto) {
        this.biletDto = biletDto;
    }
}
