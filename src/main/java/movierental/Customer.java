package movierental;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Customer {

    private final String name;
    private final List<Rental> rentals = new ArrayList<>();

    private static final Map<Movie.PriceCode, Function<Rental, Double>> amountCalculationRules = new EnumMap<>(Movie.PriceCode.class);
    private static final Map<Movie.PriceCode, Function<Rental, Integer>> frequentRenterPointsCalculationRules = new EnumMap<>(Movie.PriceCode.class);

    static {
        amountCalculationRules.put(Movie.PriceCode.REGULAR, Customer::evaluateAmountForRegularPrice);
        amountCalculationRules.put(Movie.PriceCode.NEW_RELEASE, Customer::evaluateAmountForNewReleasePrice);
        amountCalculationRules.put(Movie.PriceCode.CHILDRENS, Customer::evaluateAmountForChildrensPrice);

        frequentRenterPointsCalculationRules.put(Movie.PriceCode.REGULAR, r -> 1);
        frequentRenterPointsCalculationRules.put(Movie.PriceCode.CHILDRENS, r -> 1);
        frequentRenterPointsCalculationRules.put(Movie.PriceCode.NEW_RELEASE, Customer::evaluateFrequentRenterPointsForNewRelease);
    }

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    Statement statement() {
        Statement statement = new Statement(this.name);
        rentals.forEach(rental -> {
            double thisAmount = evaluateAmount(rental);
            int frequentRenterPoints = evaluateFrequentRenterPoints(rental);
            statement.addRentalLine(new Statement.RentalLine(rental.movieTitle(), thisAmount), frequentRenterPoints);
        });
        return statement;
    }

    private int evaluateFrequentRenterPoints(Rental rental) {
        return frequentRenterPointsCalculationRules.getOrDefault(rental.moviePriceCode(), r -> 0).apply(rental);
    }

    private static Double evaluateAmount(Rental rental) {
        return amountCalculationRules.getOrDefault(rental.moviePriceCode(), r -> 0.0).apply(rental);
    }

    private static int evaluateFrequentRenterPointsForNewRelease(Rental rental) {
        // add frequent renter points
        int frequentRenterPoints = 1;
        // add bonus for a two days new release rental
        if ((rental.moviePriceCode() == Movie.PriceCode.NEW_RELEASE) && rental.daysRented() > 1)
            frequentRenterPoints++;
        return frequentRenterPoints;
    }

    private static double evaluateAmountForChildrensPrice(Rental rental) {
        double amount = 1.5;
        if (rental.daysRented() > 3)
            amount += (rental.daysRented() - 3) * 1.5;
        return amount;
    }

    private static double evaluateAmountForNewReleasePrice(Rental rental) {
        return rental.daysRented() * 3;
    }

    private static double evaluateAmountForRegularPrice(Rental rental) {
        double amount = 2;
        if (rental.daysRented() > 2)
            amount += (rental.daysRented() - 2) * 1.5;
        return amount;
    }

}
