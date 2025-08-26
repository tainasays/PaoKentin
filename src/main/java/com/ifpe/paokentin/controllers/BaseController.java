package com.ifpe.paokentin.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.ifpe.paokentin.domain.services.FornadaService;
import com.ifpe.paokentin.domain.services.PaoService;

public abstract class BaseController {

	@Autowired
	protected FornadaService fornadaService;

	@Autowired
	protected PaoService paoService;
}
