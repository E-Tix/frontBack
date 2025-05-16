package com.example.demo.Controller;

import com.example.demo.Dto.KullaniciProfiliDto;
import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Service.LandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mainPage")
public class LandingController {
    private final LandingService landingService;

    @Autowired
    public LandingController(LandingService landingService) {
        this.landingService = landingService;
    }

    @GetMapping("/getSehir")//bir alttakine entegre edilebilir
    public List<SehirDto> getSehirler(){
        return landingService.getSehirler();
    }

    @GetMapping("/")
    public Page<EtkinlikEntity> getEtkinlikler(
            @RequestParam(required = false) String etkinlikTurAdi,
            @RequestParam(required = false) String sehirAdi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return landingService.getEtkinlikler(etkinlikTurAdi, sehirAdi, page, size);
    }

    @GetMapping("/{eventId}")
    public EtkinlikDetayDto getEtkinlik(@PathVariable Long eventId) {
        return landingService.getEtkinlik(eventId); // sadece seansList ve bu seansların salonu dönsün
    }

    @GetMapping("/sinema/{eventId}")
    public SinemaDetayDto getSinema(@PathVariable Long eventId)
    {
        return landingService.getSinema(eventId);
    }

    @GetMapping("/search")
    public List<AramaDto> etkinlikAra(@RequestParam String arananEtkinlikAdi)
    {
        return landingService.etkinlikAra(arananEtkinlikAdi);
    }

    @GetMapping("/koltukDurumu/{seansId}")
    public List<KoltukDurumDto> getKoltukDurumu(@PathVariable Long seansId) {
        return landingService.getKoltukDurumu(seansId);
    }
}
