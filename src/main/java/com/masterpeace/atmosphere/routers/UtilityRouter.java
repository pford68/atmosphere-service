package com.masterpeace.atmosphere.routers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class UtilityRouter {


    @RequestMapping(value="/ping", method= RequestMethod.GET)
    public int ping() throws Exception {
        return 1;
    }
}
