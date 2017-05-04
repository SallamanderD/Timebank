package ua.dorotenko.timebank.DaoLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ua.dorotenko.timebank.Tag;

import java.util.List;

@Component
public class TagDao {
    @Autowired
    private MongoOperations mongoOperations;

    public void save(Tag tag){
        mongoOperations.save(tag);
    }

    public List<Tag> findAll(){
        return mongoOperations.findAll(Tag.class);
    }

    public Tag findById(int id){
        return mongoOperations.findOne(new Query().addCriteria(Criteria.where("id").is(id)), Tag.class);
    }
}
