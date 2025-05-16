/*package com.example.demo.Service;
import com.example.demo.Dto.Request.BiletAlDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BiletSilService {

    BiletRepository biletRepository;
    KullaniciRepository kullaniciRepository;
    KullaniciBiletRepository kullaniciBiletRepository;
    SeansRepository seansRepository;
    KoltukRepository koltukRepository;
    SeansKoltukBiletRepository seansKoltukBiletRepository;
    SatinAlService satinAlService;

    @Autowired
    public BiletSilService(SatinAlService satinAlService,KoltukRepository koltukRepository,SeansRepository seansRepository,BiletRepository biletRepository, KullaniciRepository kullaniciRepository, KullaniciBiletRepository kullaniciBiletRepository, SeansKoltukBiletRepository seansKoltukBiletRepository) {
        this.satinAlService=satinAlService;
        this.biletRepository = biletRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.kullaniciBiletRepository = kullaniciBiletRepository;
        this.seansKoltukBiletRepository = seansKoltukBiletRepository;
        this.seansRepository = seansRepository;
        this.koltukRepository = koltukRepository;
    }
    @Transactional
    public boolean biletSil(Long biletId, Long kullaniciId) {

        BiletEntity bilet = kullaniciBiletRepository.findBiletByKullaniciAndBilet(biletId, kullaniciId);
        KullaniciBiletEntity kb = kullaniciBiletRepository.findKullaniciBiletEntityByKullaniciAndBilet(kullaniciId,bilet);

        biletRepository.delete(bilet);
        kullaniciBiletRepository.delete(kb);
        return true;
    }

}*/
