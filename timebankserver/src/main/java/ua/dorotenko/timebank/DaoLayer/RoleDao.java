package ua.dorotenko.timebank.DaoLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ua.dorotenko.timebank.Role;

import java.util.List;

@Component
public class RoleDao {
    @Autowired
    private MongoOperations mongoOperations;

    public void save(Role r){
        mongoOperations.save(r);
    }

    public List<Role> findAllRoles(){
        return mongoOperations.findAll(Role.class);
    }

    public Role findById(int id){
        return mongoOperations.findOne(new Query().addCriteria(Criteria.where("id").is(id)), Role.class);
    }
}
