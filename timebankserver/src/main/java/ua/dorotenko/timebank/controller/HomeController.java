package ua.dorotenko.timebank.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dorotenko.timebank.DaoLayer.UserDao;
import ua.dorotenko.timebank.User;

import java.util.List;


@RestController
public class HomeController {
    @Autowired
    UserDao userDao;
    @RequestMapping("/getUsers")
    public List<User> index(){
        return userDao.findAll();
    }

}

