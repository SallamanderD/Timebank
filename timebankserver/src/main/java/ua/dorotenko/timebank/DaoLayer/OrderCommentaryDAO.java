package ua.dorotenko.timebank.DaoLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ua.dorotenko.timebank.Order;
import ua.dorotenko.timebank.OrderCommentary;
import ua.dorotenko.timebank.User;
import ua.dorotenko.timebank.UserCommentary;

import java.util.List;

@Component
public class OrderCommentaryDAO {
    @Autowired
    private MongoOperations mongoOperations;

    public void save(OrderCommentary orderCommentary){
        mongoOperations.save(orderCommentary);
    }

    public List<OrderCommentary> findAll(){
        return mongoOperations.findAll(OrderCommentary.class);
    }

    public OrderCommentary findById(int id){
        return mongoOperations.findOne(new Query().addCriteria(Criteria.where("id").is(id)), OrderCommentary.class);
    }

    public List<OrderCommentary> findByAuthor(User author){
        return mongoOperations.find(new Query().addCriteria(Criteria.where("author").is(author)), OrderCommentary.class);
    }

    public List<OrderCommentary> findByTarget(Order target){
        return mongoOperations.find(new Query().addCriteria(Criteria.where("order").is(target)), OrderCommentary.class);
    }

    public void update(int id, OrderCommentary orderCommentary){
        Update update = new Update();
        update.set("text", orderCommentary.getText());
        update.set("author", orderCommentary.getAuthor());
        update.set("rating", orderCommentary.getRating());
        update.set("order", orderCommentary.getOrder());
        mongoOperations.updateFirst(new Query().addCriteria(Criteria.where("id").is(id)), update, OrderCommentary.class);
    }
}
