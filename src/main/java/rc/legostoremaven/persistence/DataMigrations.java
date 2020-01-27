package rc.legostoremaven.persistence;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import rc.legostoremaven.model.LegoSet;

@ChangeLog(order = "001") //order of the classes to be executed
public class DataMigrations {

    @ChangeSet(order = "001", author = "Stef", id="update nb sets")
    public void updateNbParts(MongoTemplate mongoTemplate) {
        Criteria priceZeroCriteria = new Criteria().orOperator(
                Criteria.where("nbParts").is(0),
                Criteria.where("nbParts").is(null));

        mongoTemplate.updateMulti(new Query(priceZeroCriteria), Update.update("nbParts", 122), LegoSet.class);

        System.out.println("Applied changeset 001");
    }
}
