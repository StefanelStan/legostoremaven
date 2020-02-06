package rc.legostoremaven.api;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rc.legostoremaven.model.LegoSet;
import rc.legostoremaven.model.LegoSetDifficulty;
import rc.legostoremaven.model.QLegoSet;
import rc.legostoremaven.persistence.LegoSetRepository;

import java.util.Collection;

@RestController
@RequestMapping("legostore/api")
public class LegoStoreController {
    // private MongoTemplate mongoTemplate;
    private LegoSetRepository legoSetRepository;
    public LegoStoreController(LegoSetRepository legoSetRepository) {
        // this.mongoTemplate = mongoTemplate;
        this.legoSetRepository =  legoSetRepository;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.insert(legoSet);
    }

    @GetMapping("/all")
    public Collection<LegoSet> getAll(){
        final Sort sortByTheme = Sort.by("theme").ascending();
        return this.legoSetRepository.findAll(sortByTheme);
    }

    @PutMapping
    public void update(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.legoSetRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public LegoSet findById(@PathVariable String id){
        return this.legoSetRepository.findById(id).orElse(null);
    }

    @GetMapping("/byTheme/{theme}")
    public Collection<LegoSet> byTheme(@PathVariable String theme) {
        final Sort sortByTheme = Sort.by("theme").ascending();
        return this.legoSetRepository.findAllByThemeContains(theme, sortByTheme);
    }

    @GetMapping("/byNameAndDifficulty/{name}/{difficulty}")
    public Collection<LegoSet> getByNameAndDifficulty(@PathVariable String name, @PathVariable String difficulty) {
        return this.legoSetRepository.findAllByNameStartingWithAndDifficultyEquals(name, LegoSetDifficulty.valueOf(difficulty));
    }

    @GetMapping("/byDelivertFeeLessThan/{price}")
    public Collection<LegoSet> getByDelivertFeeLessThan(@PathVariable int price){
        final Sort sortByTheme = Sort.by("theme").ascending();
        return this.legoSetRepository.findAllByDeliveryFeeLessThan(price, sortByTheme);
    }

    @GetMapping("/byReviewRatingEqualTo/{rating}")
    public Collection<LegoSet> findAllByReviewsRatingEqualTo(@PathVariable int rating) {
        return this.legoSetRepository.findAllByReviewsRatingEqualTo(rating);
    }

    @GetMapping("/byThemeNotContaining/{theme}")
    public Collection<LegoSet> findAllByThemeNotContaining(@PathVariable String theme) {
        return this.legoSetRepository.findAllByThemeIsNotContaining(theme);
    }

    @GetMapping("/byDeliveryInStock/{inStock}")
    public Collection<LegoSet> findAllByDeliveryInfoInStock(@PathVariable String inStock) {
        return this.legoSetRepository.findAllByDeliveryInfoInStock(Boolean.valueOf(inStock));
    }

    @GetMapping("/bestBuys") // all sets in stock, fee < 50 and review of 10
    public Collection<LegoSet> bestBuys() {
        QLegoSet bestBuysQuery = new QLegoSet("bestBuysQuery");
        BooleanExpression inStockFilter = bestBuysQuery.deliveryInfo.inStock.isTrue();
        Predicate smalDeliveryFeeFilter = bestBuysQuery.deliveryInfo.deliveryFee.lt(50);
        Predicate greatReviewFilter = bestBuysQuery.reviews.any().rating.eq(10);
        Predicate bestBuysFilter = inStockFilter.and(smalDeliveryFeeFilter).and(greatReviewFilter);
        return (Collection<LegoSet>) legoSetRepository.findAll(bestBuysFilter, Sort.by("theme").ascending());
    }

    @GetMapping("/fullTextSearch/{text}")
    public Collection<LegoSet> fullTextSearch(@PathVariable String text){
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
        return this.legoSetRepository.findAllBy(textCriteria);
    }

    @GetMapping("/getNotinteresting")
    public Collection<LegoSet> getNotinteresting() {
        QLegoSet query = new QLegoSet("notInterestingQuery");
        BooleanExpression noReviewsFilter = query.reviews.isEmpty();
        Predicate notInStockFilter = query.deliveryInfo.inStock.isFalse();
        Predicate notInterestingFilter = noReviewsFilter.or(notInStockFilter);
        return (Collection<LegoSet>) this.legoSetRepository.findAll(notInterestingFilter, Sort.by("theme").ascending());
    }

    @GetMapping("/byPaymentOption/{id}")
    public Collection<LegoSet> getByPaymentOption(@PathVariable String id){
        return this.legoSetRepository.findByPaymentOptionId(id);
    }
}
