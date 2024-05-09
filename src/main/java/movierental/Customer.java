package movierental;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final String name;
    private final List<Rental> rentals = new ArrayList<Rental>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    Statement statement() {
        Statement statement = new Statement(this.name);

        for (Rental rental : rentals) {
            int frequentRenterPoints = 0;
            double thisAmount = 0;

            //determine amounts for rental line
            switch (rental.getMovie().getPriceCode()) {
                case REGULAR:
                    thisAmount = evaluateAmountForRegularPrice(rental);
                    break;
                case NEW_RELEASE:
                    thisAmount += evaluateAmountForNewReleasePrice(rental);
                    break;
                case CHILDRENS:
                    thisAmount = evaluateAmountForChildrensPrice(rental);
                    break;
            }

            // add frequent renter points
            frequentRenterPoints++;
            // add bonus for a two days new release rental
            if ((rental.getMovie().getPriceCode() == Movie.PriceCode.NEW_RELEASE) && rental.getDaysRented() > 1)
                frequentRenterPoints++;

            // show figures for this rental
            statement.addRentalLine(new Statement.RentalLine(rental.getMovie().getTitle(), thisAmount), frequentRenterPoints);
        }//END FOR
        return statement;
    }

    private static double evaluateAmountForChildrensPrice(Rental rental) {
        double amount = 1.5;
        if (rental.getDaysRented() > 3)
            amount += (rental.getDaysRented() - 3) * 1.5;
        return amount;
    }

    private static int evaluateAmountForNewReleasePrice(Rental rental) {
        return rental.getDaysRented() * 3;
    }

    private static double evaluateAmountForRegularPrice(Rental rental) {
        double amount = 2;
        if (rental.getDaysRented() > 2)
            amount += (rental.getDaysRented() - 2) * 1.5;
        return amount;
    }

}
