package ua.dorotenko.timebank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ua.dorotenko.timebank.User;

import javax.servlet.http.HttpSession;
import java.sql.Wrapper;
import java.util.List;

@Controller
public class HomeController {
    private final String SERVER_URI = "http://localhost:6060/";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("index");
        List<User> users = restTemplate.exchange(SERVER_URI + "getUsers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}).getBody();
        model.addObject("users", users);
        return model;
    }
}
