package tn.waycon.alquasar.cs.red5.controller;

import java.util.List;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.util.ScopeUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.waycon.alquasar.cs.red5.api.Red5RestService;

@RestController
public class Red5ResController {

	private static Logger log = Red5LoggerFactory.getLogger(Red5ResController.class, "oflaDemo");

	private static final String VIEW_INDEX = "index";

	private static int counter = 0;
	@Autowired
	private Red5RestService red5RestService;

	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	public List<String> getApplications() {

		return red5RestService.getApplications();

	}

}
