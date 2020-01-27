package rc.legostoremaven.persistence;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;
import rc.legostoremaven.model.AvgRatingModel;
import rc.legostoremaven.model.LegoSet;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class ReportService {

    private MongoTemplate mongoTemplate;

    public ReportService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AvgRatingModel> getAvgRatingReports() {
        ProjectionOperation projectToMatchModel = project()
                .andExpression("name").as("productName")
                .andExpression("{$avg: '$reviews.rating'}").as("avgRating");
        Aggregation avgRatingAggregation = newAggregation(LegoSet.class, projectToMatchModel);
        return this.mongoTemplate.aggregate(avgRatingAggregation, LegoSet.class, AvgRatingModel.class).getMappedResults();
    }
}
