package rc.legostoremaven.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import rc.legostoremaven.model.LegoSet;
import rc.legostoremaven.model.LegoSetDifficulty;

import java.util.Collection;

@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String>, QuerydslPredicateExecutor<LegoSet> {

    Collection<LegoSet> findAllByThemeContains(String theme, Sort sortByTheme);

    Collection<LegoSet> findAllByNameStartingWithAndDifficultyEquals(String name, LegoSetDifficulty difficulty);

    Collection<LegoSet> findAllBy(TextCriteria textCriteria);

    @Query("{'delivery.deliveryFee' : {$lt : ?0}}")
    Collection<LegoSet> findAllByDeliveryFeeLessThan(int price, Sort sortByTheme);

    @Query("{'reviews.rating' : {$eq : ?0}}")
    Collection<LegoSet> findAllByReviewsRatingEqualTo(int rating);

    Collection<LegoSet> findAllByThemeIsNotContaining(String theme);

    @Query("{'deliveryInfo.inStock' : {$eq : ?0}}")
    Collection<LegoSet> findAllByDeliveryInfoInStock(boolean inStock);

    @Query("{'paymentOptions.id' : ?0}")
    Collection<LegoSet> findByPaymentOptionId(String id);
}
