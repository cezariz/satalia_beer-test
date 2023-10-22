package com.juliusramonas.beertest.api.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class FaviconController {

    @GetMapping("/favicon.ico")
    @ResponseBody
    public byte[] returnFavicon() throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource("static/images/favicon.ico");
        return classPathResource.getContentAsByteArray();
    }

}
