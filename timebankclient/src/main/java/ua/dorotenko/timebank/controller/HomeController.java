package ua.dorotenko.timebank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ua.dorotenko.timebank.Order;
import ua.dorotenko.timebank.Tag;
import ua.dorotenko.timebank.User;

import javax.servlet.http.HttpSession;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private final String SERVER_URI = "http://localhost:6060/";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/feed")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("feed");
        return model;
    }

    @RequestMapping("/orders")
    public ModelAndView orders() {
        ModelAndView model = new ModelAndView("orders");
        List<Order> orders = restTemplate.exchange(SERVER_URI + "getOrders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {
                }).getBody();
        model.addObject("orders", orders);
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerGet() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        if (httpSession.getAttributeNames().hasMoreElements()) {
            httpSession.removeAttribute("userId");

        }
        return new ModelAndView("redirect:/feed");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("repassword") String repassword,
                                     @RequestParam("fio") String fio,
                                     @RequestParam("bornDate") String bornDate) {
        List<String> errors = new ArrayList<>();
        List<User> users = restTemplate.exchange(SERVER_URI + "getUsers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
        if (username.trim().equals(""))
            errors.add("Необходимо ввести имя пользователя :3");
        if (password.trim().equals(""))
            errors.add("Необходимо ввести пароль :3");
        if (repassword.trim().equals(""))
            errors.add("Необходимо подтвердить пароль :3");
        if (fio.trim().equals(""))
            errors.add("Необходимо ввести ФИО :3");
        if (bornDate.trim().equals(""))
            errors.add("Необходимо ввести дату рождения :3");

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                errors.add("Это имя пользователя уже занято кем-то другим, выберите другое :3");
                break;
            }
        }
        if (password.length() < 8)
            errors.add("Пароль должен быть больше 8 символов :3");
        if (!password.equals(repassword))
            errors.add("Заданные пароли не совпадают :3");
        User u = new User(users.size() + 1, username, password, fio, bornDate, 0);
        if (errors.size() != 0) {
            ModelAndView model = new ModelAndView("register");
            model.addObject("errors", errors);
            model.addObject("user", u);
            return model;
        }
        Boolean result = restTemplate.postForEntity(SERVER_URI + "saveUser", u, Boolean.class).getBody();
        ModelAndView model = new ModelAndView("redirect:/chooseTags");
        if (result) {
            httpSession.setAttribute("userId", u.getId());
        }
        return model;
    }

    @RequestMapping("/navbar")
    public ModelAndView navbar() {
        ModelAndView model = new ModelAndView("navbar");
        if (httpSession.getAttributeNames().hasMoreElements()) {
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            model.addObject("user", u);
        }
        return model;
    }

    @RequestMapping(value = "/chooseTags")
    public ModelAndView chooseTags() {
        if (httpSession.getAttributeNames().hasMoreElements()) {
            ModelAndView model = new ModelAndView("chooseTags");
            List<Tag> tags = restTemplate.exchange(SERVER_URI + "getTags",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Tag>>() {
                    }).getBody();
            model.addObject("tags", tags);
            return model;
        } else {
            return new ModelAndView("redirect:/feed");
        }
    }

    @RequestMapping(value = "/chooseTags", method = RequestMethod.POST)
    public ModelAndView chooseTagsPost(@RequestParam("tagString") String tagString) {
        if (httpSession.getAttributeNames().hasMoreElements()) {
            String[] tagNames = tagString.split(";");
            List<Tag> tags = restTemplate.exchange(SERVER_URI + "getTags",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Tag>>() {
                    }).getBody();
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            List<Tag> preferTags = new ArrayList<>();
            for(Tag t : tags){
                for(String s : tagNames){
                    if(t.getName().equals(s)){
                        preferTags.add(t);
                        continue;
                    }
                }
            }
            u.setPreferTags(preferTags);
            Boolean result = restTemplate.postForEntity(SERVER_URI + "updateUser", u, Boolean.class).getBody();
            if(result){
                ModelAndView model = new ModelAndView("redirect:/feed");
                return model;
            }
            return new ModelAndView("redirect:/chooseTags");
        }
        return new ModelAndView("redirect:/feed");
    }

    @RequestMapping(value = "/login")
    public ModelAndView loginGet() {
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("login");
        } else {
            return new ModelAndView("redirect:/feed");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        List<User> users = restTemplate.exchange(SERVER_URI + "getUsers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                httpSession.setAttribute("userId", u.getId());
                return new ModelAndView("redirect:/feed");
            }
        }
        String error = "Данная комбинация логина и пароля не найдена.";
        ModelAndView model = new ModelAndView("login");
        model.addObject("error", error);
        return model;
    }

}

