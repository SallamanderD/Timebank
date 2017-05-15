package ua.dorotenko.timebank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ua.dorotenko.timebank.Order;
import ua.dorotenko.timebank.Role;
import ua.dorotenko.timebank.Tag;
import ua.dorotenko.timebank.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final String SERVER_URI = "http://localhost:6060/";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/feed")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("feed");
        List<Order> orders = restTemplate.exchange(SERVER_URI + "getOrders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {
                }).getBody();
        List<Order> availableOrders = orders.stream()
                .filter(o -> o.getExecuter() == null).collect(Collectors.toList());
        if (httpSession.getAttributeNames().hasMoreElements()) {
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            if (u.getPreferTags().size() != 0) {
                List<Order> result = new ArrayList<>();
                for (Order o : availableOrders) {
                    for (Tag t : u.getPreferTags()) {
                        if (o.getTags().contains(t)) {
                            result.add(o);
                            continue;
                        }
                    }
                }
                model.addObject("orders", result);
            }
        }
        model.addObject("orders", availableOrders);
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
        List<Order> availableOrders = new ArrayList<>();
        for(Order o : orders){
            if(o.getExecuter() == null)
                availableOrders.add(o);
        }
        model.addObject("orders", availableOrders);
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerGet() {
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("register");
        }
        return new ModelAndView("redirect:/feed");
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
        if (!httpSession.getAttributeNames().hasMoreElements()) {
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
            Role r = restTemplate.getForObject(SERVER_URI + "getRoleById?id={id}", Role.class, 2);
            User u = new User(users.size() + 1, username, password, fio, bornDate, 0, r);
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
        return new ModelAndView("redirect:/feed");
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
            for (Tag t : tags) {
                for (String s : tagNames) {
                    if (t.getName().equals(s)) {
                        preferTags.add(t);
                        continue;
                    }
                }
            }
            u.setPreferTags(preferTags);
            Boolean result = restTemplate.postForEntity(SERVER_URI + "updateUser", u, Boolean.class).getBody();
            if (result) {
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

    @RequestMapping("/order/{id}")
    public ModelAndView order(@PathVariable(value = "id") int id) {
        ModelAndView model = new ModelAndView("order");
        Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, id);
        model.addObject("order", o);
        if (httpSession.getAttributeNames().hasMoreElements()) {
            int userId = (int) httpSession.getAttribute("userId");
            boolean type = true;
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, userId);
            if (o.getAuthor().getId() == u.getId()) {
                type = false;
                model.addObject("type", type);
            }
            for (User user : o.getPossibleExecuters())
                if (user.getId() == u.getId()) {
                    type = true;
                    model.addObject("type", type);
                    break;
                }
            if (o.getExecuter() != null && u.getId() == o.getExecuter().getId()){
                type = true;
                model.addObject("type", type);
            }

        }
        return model;
    }

    @RequestMapping("/orderCommentaries/{id}")
    public ModelAndView orderCommentaries(@PathVariable(value = "id") int id) {
        Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, id);
        ModelAndView model = new ModelAndView("orderCommentaries");
        model.addObject("commentaries", o.getCommentaries());
        return model;
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public ModelAndView assign(@RequestParam("orderId") int orderId) {
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("redirect:/feed");
        } else {
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, orderId);
            for (User us : o.getPossibleExecuters()) {
                if (us.getId() == u.getId()) {
                    return new ModelAndView("redirect:/order/" + orderId);
                }
            }
            if (o.getExecuter()!= null && o.getExecuter().getId() == u.getId()) {
                return new ModelAndView("redirect:/order/" + orderId);
            }
            o.getPossibleExecuters().add(u);
            Boolean result = restTemplate.postForEntity(SERVER_URI + "updateOrder", o, Boolean.class).getBody();
            if (result)
                return new ModelAndView("redirect:/profile");
            else
                return new ModelAndView("redirect:/order/" + orderId);
        }
    }

    @RequestMapping(value = "unassign", method = RequestMethod.POST)
    public ModelAndView unassign(@RequestParam("orderId") int orderId) {
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("redirect:/feed");
        } else {
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, orderId);
            for (int i = 0; i < o.getPossibleExecuters().size(); i++) {
                if (o.getPossibleExecuters().get(i).getId() == u.getId()) {
                    o.getPossibleExecuters().remove(i);
                }
            }
            if (o.getExecuter() != null && o.getExecuter().getId() == u.getId()) {
                o.setExecuter(null);
            }
            restTemplate.postForEntity(SERVER_URI + "updateOrder", o, Boolean.class).getBody();
            return new ModelAndView("redirect:/order/" + orderId);
        }
    }

    @RequestMapping(value = "/markOrderCompletedByExecuter", method = RequestMethod.POST)
    public ModelAndView completeByExecuter(@RequestParam("orderId") int orderId){
        if(!httpSession.getAttributeNames().hasMoreElements())
            return new ModelAndView("redirect:/register");
        Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, orderId);
        o.setIsCompletedByExecuter(true);
        restTemplate.postForEntity(SERVER_URI + "updateOrder", o, Boolean.class).getBody();
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/markOrderCompletedByAuthor", method = RequestMethod.POST)
    public ModelAndView completeByAuthor(@RequestParam("orderId") int orderId){
        if(!httpSession.getAttributeNames().hasMoreElements()){
            return new ModelAndView("redirect:/register");
        }
        Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, orderId);
        o.setIsCompletedByAuthor(true);
        o.setIsCompletedByExecuter(true);
        o.setIsCompleted(true);
        User u = o.getExecuter();
        u.setBalance(u.getBalance() + o.getCount());
        restTemplate.postForEntity(SERVER_URI + "updateUser", u, Boolean.class).getBody();
        restTemplate.postForEntity(SERVER_URI + "updateOrder", o, Boolean.class).getBody();
        return new ModelAndView("redirect:/profile");
    }



    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("redirect:/feed");
        } else {
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            ModelAndView model = new ModelAndView("profile");
            model.addObject("user", u);
            return model;
        }
    }

    @RequestMapping("/placeorder")
    public ModelAndView createOrder() {
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("redirect:/login");
        } else {
            List<Tag> tags = restTemplate.exchange(SERVER_URI + "getTags",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Tag>>() {
                    }).getBody();
            ModelAndView model = new ModelAndView("placeorder");
            model.addObject("tags", tags);
            return model;
        }
    }

    @RequestMapping(value = "/placeorder", method = RequestMethod.POST)
    public ModelAndView createOrderPost(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("tagString") String tagString,
                                        @RequestParam("count") int count,
                                        @RequestParam(value = "iot", defaultValue = "false") String iot) {
        boolean forIot = Boolean.valueOf(iot);
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("redirect:/login");
        } else {
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            List<Tag> tags = restTemplate.exchange(SERVER_URI + "getTags",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Tag>>() {
                    }).getBody();
            List<String> errors = new ArrayList<>();
            if (count > u.getBalance()) {
                errors.add("Цена не может быть выше чем ваш баланс. Ваш баланс:" + u.getBalance());
            }
            if (name.trim().equals("")) {
                errors.add("Необходимо ввести название.");
            }
            if (description.trim().equals("")) {
                errors.add("Необходимо ввести описание.");
            }
            if (count == 0) {
                errors.add("Необходимо ввести цену.");
            }
            if (errors.size() != 0) {
                ModelAndView model = new ModelAndView("placeorder");
                model.addObject("order", new Order(1, name, description, new ArrayList<>(), null, null, 0, false));
                model.addObject("tags", tags);
                return model;
            }
            List<Order> orders = restTemplate.exchange(SERVER_URI + "getOrders",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Order>>() {
                    }).getBody();
            String[] tagNames = tagString.split(";");

            List<Tag> orderTags = new ArrayList<>();
            for (Tag t : tags) {
                for (String s : tagNames) {
                    if (t.getName().equals(s)) {
                        orderTags.add(t);
                        continue;
                    }
                }
            }
            Order o = new Order(orders.get(orders.size() - 1).getId() + 1, name, description, orderTags, u, null, count, forIot);
            Boolean result = restTemplate.postForEntity(SERVER_URI + "saveOrder", o, Boolean.class).getBody();
            if (result) {
                u.setBalance(u.getBalance() - count);
                restTemplate.postForEntity(SERVER_URI + "updateUser", u, Boolean.class).getBody();
                return new ModelAndView("redirect:/order/" + o.getId());
            }
            return new ModelAndView("redirect:/feed");

        }
    }

    @RequestMapping("/profileorder")
    public ModelAndView profileOrder() {
        if (httpSession.getAttributeNames().hasMoreElements()) {
            int id = (int) httpSession.getAttribute("userId");
            User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, id);
            List<Order> orders = restTemplate.exchange(SERVER_URI + "getOrders",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Order>>() {
                    }).getBody();
            List<Order> authorOrders = new ArrayList<>();
            List<Order> assignOrders = new ArrayList<>();
            List<Order> executingOrders = new ArrayList<>();
            for (Order o : orders) {
                if (o.getAuthor().getId() == u.getId()) {
                    authorOrders.add(o);
                    continue;
                } else if (o.getExecuter() != null) {
                    if (o.getExecuter().getId() == u.getId()) {
                        executingOrders.add(o);
                        continue;
                    }
                }
                for (User user : o.getPossibleExecuters()) {
                    if (user.getId() == u.getId()) {
                        assignOrders.add(o);
                    }
                }
            }
            ModelAndView model = new ModelAndView("profileOrder");
            model.addObject("authorOrders", authorOrders);
            model.addObject("assignOrders", assignOrders);
            model.addObject("executingOrders", executingOrders);
            return model;
        }
        return new ModelAndView("redirect:/feed");
    }

    @RequestMapping(value = "/appoint", method = RequestMethod.POST)
    public ModelAndView appoint(@RequestParam("orderId") int orderId, @RequestParam("userId") int userId) {
        if (!httpSession.getAttributeNames().hasMoreElements()) {
            return new ModelAndView("redirect:/register");
        }
        User u = restTemplate.getForObject(SERVER_URI + "getUserById?id={id}", User.class, userId);
        Order o = restTemplate.getForObject(SERVER_URI + "getOrderById?id={id}", Order.class, orderId);
        o.setPossibleExecuters(new ArrayList<>());
        o.setExecuter(u);
        Boolean result = restTemplate.postForEntity(SERVER_URI + "updateOrder", o, Boolean.class).getBody();
        if (result) {
            return new ModelAndView("redirect:/order/" + orderId);
        } else {
            return new ModelAndView("redirect:/feed");
        }

    }
}

