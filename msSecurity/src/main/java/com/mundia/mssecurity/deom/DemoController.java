package com.mundia.mssecurity.deom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping("/")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Bonjour tout le monde");
    }
}
