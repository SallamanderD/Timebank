package ua.dorotenko.timebank.DaoLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ua.dorotenko.timebank.User;
import ua.dorotenko.timebank.UserCommentary;

import java.util.List;

@Component
public class UserCommentaryDao {
    @Autowired
    private MongoOperations mongoOperations;

    public void save(UserCommentary userCommentary){
        mongoOperations.save(userCommentary);
    }

    public List<UserCommentary> findAll(){
        return mongoOperations.findAll(UserCommentary.class);
    }

    public UserCommentary findById(int id){
        return mongoOperations.findOne(new Query().addCriteria(Criteria.where("id").is(id)), UserCommentary.class);
    }

    public List<UserCommentary> findByAuthor(User author){
        return mongoOperations.find(new Query().addCriteria(Criteria.where("author").is(author)), UserCommentary.class);
    }

    public List<UserCommentary> findByTarget(User target){
        return mongoOperations.find(new Query().addCriteria(Criteria.where("user").is(target)), UserCommentary.class);
    }

    public void update(int id, UserCommentary userCommentary){
        Update update = new Update();
        update.set("text", userCommentary.getText());
        update.set("author", userCommentary.getAuthor());
        update.set("rating", userCommentary.getRating());
        update.set("user", userCommentary.getUser());
        mongoOperations.updateFirst(new Query().addCriteria(Criteria.where("id").is(id)), update, UserCommentary.class);
    }
}
