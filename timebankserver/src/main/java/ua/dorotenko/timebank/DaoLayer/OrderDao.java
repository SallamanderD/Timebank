package ua.dorotenko.timebank.DaoLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ua.dorotenko.timebank.Order;

import java.util.List;

@Component
public class OrderDao {
    @Autowired
    private MongoOperations mongoOperations;

    public void save(Order order){
        mongoOperations.save(order);
    }

    public List<Order> findAll(){
        return  mongoOperations.findAll(Order.class);
    }

    public Order findById(int id){
        return mongoOperations.findOne(new Query().addCriteria(Criteria.where("id").is(id)), Order.class);
    }

    public void update(int id, Order order){
        Update update = new Update();
        update.set("name", order.getName());
        update.set("description", order.getDescription());
        update.set("tags", order.getTags());
        update.set("author", order.getAuthor());
        update.set("isCompleted", order.isCompleted());
        update.set("executer", order.getExecuter());
        update.set("commentaries", order.getCommentaries());
        mongoOperations.updateFirst(new Query().addCriteria(Criteria.where("id").is(id)), update, Order.class);
    }
}
