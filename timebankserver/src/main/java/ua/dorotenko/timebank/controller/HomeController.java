package ua.dorotenko.timebank.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.dorotenko.timebank.*;
import ua.dorotenko.timebank.DaoLayer.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class HomeController {
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    UserCommentaryDao userCommentaryDao;
    @Autowired
    OrderCommentaryDAO orderCommentaryDAO;
    @Autowired
    DbEmulator dbEmulator;
    @RequestMapping("/emul")
    public void emulate(){
        dbEmulator.emulate();
    }
    @RequestMapping("/getUsers")
    public List<User> getAllUsers(){
        return userDao.findAll();

    }

    @RequestMapping("getTags")
    public List<Tag> getTags(){
        return tagDao.findAll();
    }
    @RequestMapping("/getOrders")
    public List<Order> getAllOrders(){
        return orderDao.findAll();
    }
    @RequestMapping("/getUserById")
    public User getUserById(@RequestParam("id") int id){
        return userDao.findById(id);
    }
    @RequestMapping("/getOrderById")
    public Order getOrderById(@RequestParam("id") int id){
        return orderDao.findById(id);
    }

    @RequestMapping("/getOrdersByTag")
    public List<Order> getOrdersByTag(String tagname){
        List<Order> result = new ArrayList<>();
        for(Order order : orderDao.findAll()){
            for(Tag tag : order.getTags()){
                if(tag.getName().equals(tagname)){
                    result.add(order);
                    break;
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public Boolean saveUser(@RequestBody User user){
        userDao.save(user);
        return true;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Boolean updateUser(@RequestBody User user){
        userDao.update(user.getId(), user);
        return true;
    }

    @RequestMapping("/getUserCommentaries")
    public List<UserCommentary> getUserCommentaries(){
        return userCommentaryDao.findAll();
    }

    @RequestMapping("/getOrderCommentaries")
    public List<OrderCommentary> getOrderCommentaries(){
        return orderCommentaryDAO.findAll();
    }

    @RequestMapping("/getUserCommentaryByAuthor")
    public List<UserCommentary> getUserCommentaryByAuthor(@RequestParam("id") int id){
        return getUserById(id).getUserCommentaries();
    }

    @RequestMapping("/getUserCommentaryByTarget")
    public List<UserCommentary> getUserCommentaryByTarget(@RequestParam("id") int id){
        return getUserById(id).getCommentaries();
    }

    @RequestMapping("/getOrderCommentaryByAuthor")
    public List<OrderCommentary> getOrderCommentaryByAuthor(@RequestParam("id") int id){
        return getUserById(id).getOrderCommentaries();
    }

    @RequestMapping("/getOrderCommentaryByTarget")
    public List<OrderCommentary> getOrderCommentaryByTarget(@RequestParam("id") int id){
        return getOrderById(id).getCommentaries();
    }

}

