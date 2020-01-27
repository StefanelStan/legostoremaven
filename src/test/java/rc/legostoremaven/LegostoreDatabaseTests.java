package rc.legostoremaven;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import rc.legostoremaven.model.DeliveryInfo;
import rc.legostoremaven.model.LegoSet;
import rc.legostoremaven.model.LegoSetDifficulty;
import rc.legostoremaven.model.PaymentOptions;
import rc.legostoremaven.model.PaymentType;
import rc.legostoremaven.model.ProductReview;
import rc.legostoremaven.persistence.LegoSetRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class LegostoreDatabaseTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LegoSetRepository legoSetRepository;

    @Before
    public void before(){
        this.legoSetRepository.deleteAll();
        this.legoSetRepository.insert(getInitialLegosets());

    }

    @Test
    public void findAllByGreatReviews_should_return_products_with_correct_rating() {
        List<LegoSet> results = (List<LegoSet>) this.legoSetRepository.findAllByReviewsRatingEqualTo(5);
        assertEquals(1, results.size());
        assertEquals("Millenium Falcon", results.get(0).getName());
    }

    @Test
    public void getAll_should_return_products_with_correct_rating() {
        List<LegoSet> results = this.legoSetRepository.findAll();
        assertEquals(2, results.size());
    }

    @Test
    public void findAllInStock_should_return_correct_items_in_stock() {
        List<LegoSet> result = (List<LegoSet>) this.legoSetRepository.findAllByDeliveryInfoInStock(true);
        assertEquals(1, result.size());
        assertEquals("Sky Police Air Base", result.get(0).getName());
    }

    private Collection<LegoSet> getInitialLegosets() {
        LegoSet milleniumFalcon = new LegoSet("Millenium Falcon", "Star Wars", LegoSetDifficulty.HARD,
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, false),
                Arrays.asList(new ProductReview("Dan", 7), new ProductReview("Tom", 5), new ProductReview("John", 10)),
                new PaymentOptions(PaymentType.PayPal, 6));

        LegoSet skyPolice = new LegoSet("Sky Police Air Base", "City", LegoSetDifficulty.EASY,
                new DeliveryInfo(LocalDate.now().plusDays(3), 80, true),
                Arrays.asList(new ProductReview("Victor", 10), new ProductReview("Stefan", 10), new ProductReview("Dan", 8)),
                new PaymentOptions(PaymentType.CreditCard, 10));
        return Arrays.asList(milleniumFalcon, skyPolice);
    }
}
