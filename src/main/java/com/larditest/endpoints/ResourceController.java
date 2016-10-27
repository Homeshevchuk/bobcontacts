package com.larditest.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by PC on 25.10.2016.
 */
@Controller
public class ResourceController {
    @RequestMapping("/")
        public String main(Principal principal){
            if(principal==null){
                return "redirect:login.html";
            }else {
                return "redirect:index.html";
            }

        }
}
