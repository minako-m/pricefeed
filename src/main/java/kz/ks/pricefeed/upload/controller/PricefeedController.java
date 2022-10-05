package kz.ks.pricefeed.upload.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricefeedController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}