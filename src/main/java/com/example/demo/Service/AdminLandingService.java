package com.example.demo.Service;

import com.example.demo.Dto.Response.BiletDto;
import com.example.demo.Dto.Response.SalonDto;
import com.example.demo.Dto.Response.SehirDto;
import com.example.demo.Dto.Response.SilinecekBiletDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.BiletRepository;
import com.example.demo.Repository.EtkinlikSalonSeansRepository;
import com.example.demo.Repository.KullaniciBiletRepository;
import com.example.demo.Repository.SeansKoltukBiletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminLandingService {
    private final KullaniciBiletRepository kullaniciBiletRepository;
    private final SeansKoltukBiletRepository seansKoltukBiletRepository;
    private final BiletRepository biletRepository;
    private final EtkinlikSalonSeansRepository etkinlikSalonSeansRepository;

    public AdminLandingService(EtkinlikSalonSeansRepository etkinlikSalonSeansRepository, KullaniciBiletRepository kullaniciBiletRepository, SeansKoltukBiletRepository seansKoltukBiletRepository, BiletRepository biletRepository)
    {
        this.etkinlikSalonSeansRepository=etkinlikSalonSeansRepository;
        this.kullaniciBiletRepository=kullaniciBiletRepository;
        this.biletRepository=biletRepository;
        this.seansKoltukBiletRepository=seansKoltukBiletRepository;
    }


    public List<SilinecekBiletDto> getSilinecekBiletler()
    {
        List<SilinecekBiletDto> silinecekBiletDtoList = new ArrayList<>();
        List<KullaniciBiletEntity> kullaniciBiletEntityList = kullaniciBiletRepository.findByIptalIstendiMiTrue();

        SeansKoltukBiletEntity seansKoltukBilet;
        EtkinlikSalonSeansEntity etkinlikSalonSeans;

        for(KullaniciBiletEntity kb:kullaniciBiletEntityList)
        {
            seansKoltukBilet=seansKoltukBiletRepository.findSeansKoltukBiletEntityByBilet(kb.getBilet());
            etkinlikSalonSeans=etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntityBySeans(seansKoltukBilet.getSeans());

            silinecekBiletDtoList.add(new SilinecekBiletDto(
                    kb.getKullanici(),
                    new BiletDto(
                            kb.getBilet().getBiletID(),
                            kb.getBilet().getOdenenMiktar(),
                            seansKoltukBilet.getKoltuk().getKoltukNumarasi(),
                            etkinlikSalonSeans.getEtkinlik().getEtkinlikAdi(),
                            new SehirDto(etkinlikSalonSeans.getEtkinlik().getSehir().getPlakaKodu(),etkinlikSalonSeans.getEtkinlik().getSehir().getSehirAdi()),
                            new SalonDto(etkinlikSalonSeans.getSalon().getSalonID(),etkinlikSalonSeans.getSalon().getSalonAdi(),etkinlikSalonSeans.getSalon().getAdres()),
                            etkinlikSalonSeans.getSeans()
                    )
            ));
        }

        return silinecekBiletDtoList;

    }

    public boolean biletSil(Long biletId)
    {
        Optional<BiletEntity> optionalBilet = biletRepository.findByBiletID(biletId);

        if (optionalBilet.isPresent())
        {
            BiletEntity bilet = optionalBilet.get();

            KullaniciBiletEntity kullaniciBiletEntity = kullaniciBiletRepository.findByBilet(bilet);
            kullaniciBiletRepository.delete(kullaniciBiletEntity);

            SeansKoltukBiletEntity seansKoltukBiletEntity = seansKoltukBiletRepository.findSeansKoltukBiletEntityByBilet(bilet);

            seansKoltukBiletEntity.setKoltukdurumu(false);
            seansKoltukBiletEntity.setBilet(null);
            seansKoltukBiletRepository.save(seansKoltukBiletEntity);

            biletRepository.delete(bilet);

            return true;
        }
        return false;
    }
}
