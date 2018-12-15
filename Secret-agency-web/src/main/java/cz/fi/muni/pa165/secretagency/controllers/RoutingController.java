package cz.fi.muni.pa165.secretagency.controllers;

import cz.fi.muni.pa165.secretagency.ApiUris;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller which supports React routing. To make React routing work when front-end is deployed to the same server
 *   as back-end, it's necessary to provide endpoints which always return index.html.<br>
 * For this app, every endpoint which path is starting with <b></>pa165/secretAgency/</b> returns index.html.
 *
 * @author Jan Pavlu
 */
@Controller
public class RoutingController {

    @RequestMapping(ApiUris.ROUTING_URL_BASE + "/**")
    public String index() {
        return "index.html";
    }
}
