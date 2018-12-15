package cz.fi.muni.pa165.secretagency.controllers;

import cz.fi.muni.pa165.secretagency.ApiUris;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoutingController {

    @RequestMapping(ApiUris.ROUTING_URL_BASE + "/**")
    public String index() {
        return "index.html";
    }
}
