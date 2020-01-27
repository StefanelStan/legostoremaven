package rc.legostoremaven.persistence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import rc.legostoremaven.model.DeliveryInfo;
import rc.legostoremaven.model.LegoSet;
import rc.legostoremaven.model.LegoSetDifficulty;
import rc.legostoremaven.model.PaymentOptions;
import rc.legostoremaven.model.PaymentType;
import rc.legostoremaven.model.ProductReview;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@Service
public class DbSeeder implements CommandLineRunner {
    private MongoTemplate mongoTemplate;
    private LegoSetRepository legoSetRepository;

    PaymentOptions creditCardPayment = new PaymentOptions(PaymentType.CreditCard, 15);
    PaymentOptions payPalPayment = new PaymentOptions(PaymentType.PayPal, 6);
    PaymentOptions cashPayment = new PaymentOptions(PaymentType.Cash, 25);

    public DbSeeder(MongoTemplate mongoTemplate, LegoSetRepository legoSetRepository) {
        this.mongoTemplate = mongoTemplate;
        this.legoSetRepository = legoSetRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /* Add Lego Sets */
        //this.mongoTemplate.dropCollection(LegoSet.class);
        //this.mongoTemplate.insertAll(getInitialLegosets());

        //this.mongoTemplate.dropCollection(LegoSet.class);
        this.mongoTemplate.dropCollection(PaymentOptions.class);
        this.mongoTemplate.insert(creditCardPayment);
        this.mongoTemplate.insert(payPalPayment);
        this.mongoTemplate.insert(cashPayment);
        this.legoSetRepository.insert(getInitialLegosets());
    }

    private Collection<LegoSet> getInitialLegosets() {


        LegoSet milleniumFalcon = new LegoSet( "Millenium Falcon", "Star Wars", LegoSetDifficulty.HARD,
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),
                Arrays.asList(new ProductReview("Dan", 7), new ProductReview("Tom", 5), new ProductReview("John", 10)), creditCardPayment);

        LegoSet skyPolice = new LegoSet( "Sky Police Air Base", "City", LegoSetDifficulty.EASY,
                new DeliveryInfo(LocalDate.now().plusDays(3), 80, true),
                Arrays.asList(new ProductReview("Victor", 10), new ProductReview("Stefan", 10), new ProductReview("Dan", 8)),cashPayment);

        LegoSet mcLarenSenna = new LegoSet( "McLarenSenna", "SpeedChampions", LegoSetDifficulty.MEDIUM,
                new DeliveryInfo(LocalDate.now().plusDays(2), 40, false),
                Arrays.asList(new ProductReview("Mihai", 7), new ProductReview("George", 3), new ProductReview("Dumitru", 3)), payPalPayment);

        LegoSet mindstormEye = new LegoSet( "Mindstorms EV3", "Mindstorms", LegoSetDifficulty.HARD,
                new DeliveryInfo(LocalDate.now().plusDays(5), 120, false),
                Arrays.asList(new ProductReview("Cosmin", 9), new ProductReview("Jane", 8), new ProductReview("James", 6)), payPalPayment);

        return Arrays.asList(milleniumFalcon, skyPolice, mcLarenSenna, mindstormEye);
    }
}
