package movierental;

/**
 * The rental class represents a customer renting a movie.
 */
public class Rental {

    private final Movie movie;
    private final int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public int daysRented() {
        return daysRented;
    }

    public Movie.PriceCode moviePriceCode(){
        return movie.priceCode();
    }

    public String movieTitle(){
        return movie.title();
    }
}
