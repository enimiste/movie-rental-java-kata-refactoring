package movierental;

public class Movie {
    public enum PriceCode {
        CHILDRENS, NEW_RELEASE, REGULAR
    }

    private String title;
    private PriceCode priceCode;

    public Movie(String title, PriceCode priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public PriceCode getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(PriceCode arg) {
        priceCode = arg;
    }
    public String getTitle() {
        return title;
    }


}
