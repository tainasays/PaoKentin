package com.ifpe.paokentin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class ClienteController {

	@GetMapping({ "paokentin/", "paokentin/cliente" })
	public String home() {
		return "cliente";
	}

}
