package com.example.demo.Controller;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Request.OrganizatorProfiliDto;
import com.example.demo.Dto.Request.OrganizatorProfiliDuzenleDto;
import com.example.demo.Entity.OrganizatorEntity;
import com.example.demo.Repository.OrganizatorRepository;
import com.example.demo.Service.OrganizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orgProfile")
public class OrganizatorController {

    private final OrganizatorService organizatorService;
    private final OrganizatorRepository organizatorRepository;

    @Autowired
    public OrganizatorController(OrganizatorService organizatorService, OrganizatorRepository organizatorRepository) {
        this.organizatorService = organizatorService;
        this.organizatorRepository = organizatorRepository;
    }

    @PutMapping("/ChangePassword")
    public boolean changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        Long id = getCurrentOrganizatorId();
        return organizatorService.changePassword(changePasswordDto, id);
    }

    @GetMapping("/getOrganizatorProfile")
    public OrganizatorProfiliDto getOrganizatorProfili() {
        Long id = getCurrentOrganizatorId();
        return organizatorService.getOrganizatorProfili(id);
    }

    @GetMapping("/updateOrganizatorInfo")
    public OrganizatorProfiliDuzenleDto getOrganizatorProfiliDuzenleDto(){
        Long id= getCurrentOrganizatorId();
        return organizatorService.getOrganizatorProfiliDto(id);
    }

    @PutMapping("/updateOrganizatorInfo/save")
    public boolean organizatorProfiliDuzenle(@RequestBody OrganizatorProfiliDuzenleDto organizatorProfiliDuzenleDto) {
        Long id = getCurrentOrganizatorId();
        return organizatorService.organizatorProfiliDuzenle(organizatorProfiliDuzenleDto, id);
    }

    private Long getCurrentOrganizatorId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Kullanıcı doğrulanmamış");
        }

        String email = auth.getName(); // Eğer JWT'de "subject" olarak e-mail varsa
        return organizatorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizatör bulunamadı"))
                .getOrganizatorID();
    }

    //organizatörün kendisine ait etkinlikleri id ile çekmesi için
    //Bu kısmı henüz kullanmadım. Bugün bakacağım gerekirse değiştiririm
    @GetMapping("/get-id-by-email/{email}")
    public ResponseEntity<Long> getIdByEmail(@PathVariable String email) {
        Optional<OrganizatorEntity> organizator = organizatorRepository.findByEmail(email);
        return organizator.map(o -> ResponseEntity.ok(o.getOrganizatorID()))
                .orElse(ResponseEntity.notFound().build());
    }
}
