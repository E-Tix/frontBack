package com.example.demo.Controller;

import com.example.demo.Dto.Request.EtkinlikEkleDto;
import com.example.demo.Dto.Request.EtkinlikGuncelleDto;
import com.example.demo.Dto.Request.SinemaEkleDto;
import com.example.demo.Dto.Request.SinemaGuncelleDto;
import com.example.demo.Dto.Response.EtkinlikForOrgDetayDto;
import com.example.demo.Dto.Response.EtkinlikForOrgDto;
import com.example.demo.Dto.Response.SalonDto;
import com.example.demo.Dto.Response.SehirDto;
import com.example.demo.Dto.Response.*;
import com.example.demo.Repository.OrganizatorRepository;
import com.example.demo.Service.OrganizatorLandingService;
import com.example.demo.Service.SinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizatorMainPage")
public class OrganizatorLandingController {

    private final OrganizatorLandingService organizatorLandingService;
    private final OrganizatorRepository organizatorRepository;
    private final SinemaService sinemaService;

    @Autowired
    public OrganizatorLandingController(OrganizatorLandingService organizatorLandingService, OrganizatorRepository organizatorRepository ,SinemaService sinemaService)
    {
        this.organizatorLandingService = organizatorLandingService;
        this.organizatorRepository = organizatorRepository;
        this.sinemaService = sinemaService;
    }

    @PostMapping("/addEvent/save")
    public boolean etkinlikEkle(@RequestBody EtkinlikEkleDto etkinlikEkleDto){
        //orgid changePassworddaki gibi alınacak
        Long id = getCurrentOrganizatorId();
        System.out.println("id" + id);
        organizatorLandingService.etkinlikEkle(etkinlikEkleDto,id);
        return true;
    }

    @PostMapping("/getSalonsForSehir")
    public List<SalonDto> getSalonsForSehir(@RequestBody SehirDto sehirDto)
    {
        return organizatorLandingService.getSalonsForSehir(sehirDto);
    }

    @GetMapping("/getSehirs")
    public List<SehirDto> getSehirs()
    {
        return organizatorLandingService.getSehirs();
    }

    /*@GetMapping("/addEvent")
    public List<EtkinlikTurEntity> getEtkinlikTursForAddEvent(){
        return organizatorLandingService.getEtkinlikTursAndSalonsForAddAndUpdateEvent();
    }*/

    @GetMapping("/updateEvent/{eventId}")
    public EtkinlikGuncelleDto getEtkinlikGuncelle(@PathVariable Long eventId){
        Long id = getCurrentOrganizatorId();
        return organizatorLandingService.getEtkinlikGuncelleDto(id,eventId);

    }

    @PutMapping("/updateEvent/save")
    public boolean etkinlikGuncelle(@RequestBody EtkinlikGuncelleDto etkinlikGuncelleDto){
        Long id = getCurrentOrganizatorId();
        organizatorLandingService.etkinlikGuncelle(id,etkinlikGuncelleDto);
        return true;

    }

    @DeleteMapping("/deleteEvent/{eventId}")
    public boolean etkinlikSil(@PathVariable Long eventId)//pathten id alma dtodan al
    {
        return organizatorLandingService.etkinlikSil(eventId);
    }

    @GetMapping("/getEtkinlik/{eventId}")
    public EtkinlikForOrgDetayDto getEtkinlik(@PathVariable Long eventId)
    {
        Long orgId = getCurrentOrganizatorId();
        return organizatorLandingService.getEtkinlik(eventId);
    }

    @GetMapping("/")
    public Page<EtkinlikForOrgDto> getEtkinlikler(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Long orgId = getCurrentOrganizatorId();
        return organizatorLandingService.getEtkinlikler(orgId, page, size);
    }

    @PostMapping("/addCinema/save")
    public boolean sinemaEkle(@RequestBody SinemaEkleDto sinemaEkleDto)
    {
        Long orgId = getCurrentOrganizatorId();
        return sinemaService.sinemaEkle(orgId,sinemaEkleDto);
    }

    @DeleteMapping("/deleteCinema")
    public boolean sinemaSil(@RequestParam Long sinemaId)
    {
        Long orgId = getCurrentOrganizatorId();
        return sinemaService.sinemaSil(orgId,sinemaId);
    }

    //sinema güncelleme formunun doldurulması için.
    @GetMapping("/updateCinema")
    public SinemaGuncelleDto getSinemaGuncelleDto(@RequestParam Long sinemaId)
    {
        Long orgId = getCurrentOrganizatorId();
        return sinemaService.getSinemaGuncelleDto(orgId,sinemaId);
    }

    @PutMapping("updateCinema/save")
    public boolean sinemaGuncelle(@RequestBody SinemaGuncelleDto sinemaGuncelleDto)
    {
        Long orgId = getCurrentOrganizatorId();
        return sinemaService.sinemaGuncelle(orgId,sinemaGuncelleDto);
    }

    @GetMapping("/getCinema")
    public SinemaForOrgDetayDto getSinema(@RequestParam Long sinemaId)
    {
        Long orgId = getCurrentOrganizatorId();
        return sinemaService.getSinema(orgId,sinemaId);
    }

    private Long getCurrentOrganizatorId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Kullanıcı doğrulanmamış");
        }

        String email = auth.getName(); // Eğer JWT'de "subject" olarak e-mail varsa

        System.out.println("Auth name: " + auth.getName()); //hata bulma amaçlı eklendiler
        System.out.println("Authorities: " + auth.getAuthorities());

        return organizatorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizatör bulunamadı"))
                .getOrganizatorID();
    }
}