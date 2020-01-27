package rc.legostoremaven.model;

import org.springframework.data.mongodb.core.index.TextIndexed;

public class ProductReview {

    @TextIndexed
    private String userName;
    private int rating;

    public ProductReview(final String userName, final int rating) {
        this.userName = userName;
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public int getRating() {
        return rating;
    }
}
