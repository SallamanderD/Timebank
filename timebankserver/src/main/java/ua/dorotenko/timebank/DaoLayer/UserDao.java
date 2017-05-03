package ua.dorotenko.timebank.DaoLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ua.dorotenko.timebank.User;

import java.util.List;

@Component
public class UserDao {
    @Autowired
    private MongoOperations mongoOperations;

    public void save(User u){
        mongoOperations.save(u);
    }

    public List<User> findAll(){
        return mongoOperations.findAll(User.class);
    }

    public User findById(int id){
        return mongoOperations.findOne(new Query().addCriteria(Criteria.where("id").is(id)), User.class);
    }

    public void update(int id, User user){
        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("password", user.getPassword());
        update.set("fio", user.getFio());
        update.set("borndate", user.getBornDate());
        update.set("rating", user.getRating());
        update.set("commentaries", user.getCommentaries());
        update.set("userCommentaries", user.getUserCommentaries());
        update.set("orderCommentaries", user.getOrderCommentaries());
        mongoOperations.updateFirst(new Query()
                .addCriteria(Criteria.where("id").is(id)), update, User.class);
    }

}
