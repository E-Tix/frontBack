package com.example.demo.Controller;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Response.AdminProfiliDto;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminService adminService, AdminRepository adminRepository)
    {
        this.adminService=adminService;
        this.adminRepository = adminRepository;
    }

    @PutMapping("/changePassword")
    public boolean sifreDegistir(@RequestBody ChangePasswordDto changePasswordDto)
    {
        Long adminId = getCurrentAdminId();
        return adminService.sifreDegistir(adminId, changePasswordDto);
    }

    @GetMapping("/getAdminProfile")
    public AdminProfiliDto getAdminProfili()
    {
        Long adminId = getCurrentAdminId();
        return adminService.getAdminProfili(adminId);
    }

    private Long getCurrentAdminId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Kullanıcı doğrulanmamış");
        }

        String email = auth.getName(); // Eğer JWT'de "subject" olarak e-mail varsa
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin bulunamadı"))
                .getAdminID();
    }

}
