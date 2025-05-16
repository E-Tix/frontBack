package com.example.demo.Controller;

import com.example.demo.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Dto.Response.SilinecekBiletDto;
import com.example.demo.Service.AdminLandingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/adminMainPage")
public class AdminLandingController {

    private final AdminLandingService adminLandingService;

    @Autowired
    public AdminLandingController(AdminLandingService adminLandingService, AdminRepository adminRepository)
    {
        this.adminLandingService=adminLandingService;
    }


    public AdminLandingController(AdminLandingService adminLandingService)
    {
        this.adminLandingService = adminLandingService;
    }

    @GetMapping("/")
    public List<SilinecekBiletDto> getSilinecekBiletler()
    {
        return adminLandingService.getSilinecekBiletler();
    }

    @DeleteMapping("/biletSil")
    public boolean biletSil(@RequestParam Long biletId)
    {
        return adminLandingService.biletSil(biletId);
    }
}
