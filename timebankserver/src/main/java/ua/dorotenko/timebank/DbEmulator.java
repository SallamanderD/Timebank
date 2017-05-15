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
    OrderCommentaryDao orderCommentaryDao;
    @Autowired
    RoleDao roleDao;

    public void emulate(){
        String text1 =
                "Задание - выгулять собаку в городе Харькове, Шевченковский район. Собачка добрая, ласковая, не агрессивная. Порода - лабрадор ретривер. Любит гоняться за палками и другими предметами, а также купаться. Собаку нужно выгуливать в течении двух часов. Отзывается на кличку Мартелл. Плачу 300 поинтов. Кто заинтересовался - просьба подписаться.";
        roleDao.save(new Role(1, "Администратор"));
        roleDao.save(new Role(2, "Пользователь"));
        User u = new User(1, "Sallamander", "Beeline1", "Dorotenko Aleksandr Vadimovich", "03.07.1997", 999999, roleDao.findById(2));
        userDao.save(u);
        u = new User(2, "UsagiBro", "1234321", "Zhazhky Ihor Ihorevich", "21.04.1997", 0, roleDao.findById(1));
        userDao.save(u);
        // ----------------------------------------------
        Tag t = new Tag(1, "Домашние животные");
        tagDao.save(t);
        t = new Tag(2, "Уборка");
        tagDao.save(t);
        t = new Tag(3, "Дети");
        tagDao.save(t);
        t = new Tag(4, "Бассейн");
        tagDao.save(t);
        t = new Tag(5, "Покупки");
        tagDao.save(t);
        Order o = new Order(
                1,
                "Выгул псины",
                text1,
                new ArrayList<Tag>(),
                userDao.findById(1),
                null,
                300,
                true
        );
        o.getTags().add(tagDao.findById(1));
        orderDao.save(o);
        // ----------------------------------------------
        OrderCommentary orderCommentary = new OrderCommentary(1, "Слишком занижена цена для такого задания", userDao.findById(2), orderDao.findById(1));
        u = userDao.findById(2);
        u.getOrderCommentaries().add(orderCommentary);
        userDao.update(u.getId(), u);
        o.getCommentaries().add(orderCommentary);
        orderDao.update(o.getId(), o);
        orderCommentaryDao.save(orderCommentary);
        // --------------------------------------------------------------
    }
}
