package com.example.demo.Controller;

import com.example.demo.Service.SehirService;
import com.example.demo.Dto.Response.SehirDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sehir")
public class SehirController {

    //Hem headerda hem de etkinlikte kullanmak için burada endpoint ekledim
    @Autowired
    private SehirService sehirService;

    @GetMapping("/sehirler")
    public List<SehirDto> getAllSehirler() {
        return sehirService.getAllSehirler().stream()
                .map(sehir -> new SehirDto(sehir.getPlakaKodu(), sehir.getSehirAdi()))
                .collect(Collectors.toList());
    }
}
