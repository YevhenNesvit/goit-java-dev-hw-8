package spring.boot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
public class IndexController {

    @GetMapping("")
    public ModelAndView getIndex() {

        return new ModelAndView("index");
    }

}
