package movierental;

public class Movie {
    public enum PriceCode {
        CHILDRENS, NEW_RELEASE, REGULAR
    }

    private final String title;
    private final PriceCode priceCode;

    public Movie(String title, PriceCode priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public PriceCode priceCode() {
        return priceCode;
    }

    public String title() {
        return title;
    }


}
