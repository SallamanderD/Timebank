package ua.dorotenko.timebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dorotenko.timebank.DaoLayer.*;

import java.util.ArrayList;

@Component
public class DbEmulator {
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

    public void emulate(){
        User u = new User(1, "Sallamander", "Beeline1", "Dorotenko Aleksandr Vadimovich", "03.07.1997", 0);
        userDao.save(u);
        u = new User(2, "UsagiBro", "1234321", "Zhazhky Ihor Ihorevich", "21.04.1997", 0);
        userDao.save(u);
        // ----------------------------------------------
        Tag t = new Tag(1, "Домашние животные");
        tagDao.save(t);
        Order o = new Order(
                1,
                "Выгул псины",
                "Надо выгулять псину по кличке Кобра",
                new ArrayList<Tag>(),
                userDao.findById(1),
                false,
                null,
                300
        );
        o.getTags().add(tagDao.findById(1));
        orderDao.save(o);
        // ----------------------------------------------
        OrderCommentary orderCommentary = new OrderCommentary(1, "Слишком занижена цена для такого задания", userDao.findById(2), orderDao.findById(1));
        u = userDao.findById(2);
        u.getOrderCommentaries().add(orderCommentary);
        userDao.update(u.getId(), u);
        orderCommentaryDAO.save(orderCommentary);
        // --------------------------------------------------------------
    }
}
