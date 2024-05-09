package movierental;

import java.util.ArrayList;
import java.util.Collections;
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

    public String getName() {
        return name;
    }

    static class Statement {
        private final String customerName;
        private double totalAmount=0;
        private int totalFrequentRenterPoints;
        private final List<RentalLine> rentalLines=new ArrayList<>();

        Statement(String customerName) {
            this.customerName = customerName;
        }

        void addRentalLine(RentalLine rentalLine, int frequentRenterPoints){
            this.rentalLines.add(rentalLine);
            this.totalAmount+=rentalLine.amount;
            this.totalFrequentRenterPoints += frequentRenterPoints;
        }

        public String customerName() {
            return customerName;
        }

        public double totalAmount() {
            return totalAmount;
        }

        public List<RentalLine> rentalLines() {
            return Collections.unmodifiableList(rentalLines);
        }

        public int totalFrequentRenterPoints() {
            return totalFrequentRenterPoints;
        }

        static class RentalLine {
            private final String movieTitle;
            private final double amount;

            private RentalLine(String movieTitle, double amount) {
                this.movieTitle = movieTitle;
                this.amount = amount;
            }

            public double amount() {
                return amount;
            }

            public String movieTitle() {
                return movieTitle;
            }
        }
    }
    Statement calculateStatement() {
        Statement statement = new Statement(this.name);

        for (Rental rental : rentals) {
            int frequentRenterPoints = 0;
            double thisAmount = 0;

            //determine amounts for rental line
            switch (rental.getMovie().getPriceCode()) {
                case REGULAR:
                    thisAmount += 2;
                    if (rental.getDaysRented() > 2)
                        thisAmount += (rental.getDaysRented() - 2) * 1.5;
                    break;
                case NEW_RELEASE:
                    thisAmount += rental.getDaysRented() * 3;
                    break;
                case CHILDRENS:
                    thisAmount += 1.5;
                    if (rental.getDaysRented() > 3)
                        thisAmount += (rental.getDaysRented() - 3) * 1.5;
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

    public String statement() {
        Statement statement = calculateStatement();
        String result = makeStatementHeader(statement.customerName());

        for (Statement.RentalLine rental : statement.rentalLines()) {
            result += makeStatementRentalLine(rental.movieTitle(), rental.amount());
        }

        // add footer lines
        result += makeStatementFooter(statement.totalAmount(), statement.totalFrequentRenterPoints());

        return result;
    }

    private static String makeStatementFooter(double totalAmount, int frequentRenterPoints) {
        return "Amount owed is " + totalAmount + "\n" +
                "You earned " + frequentRenterPoints + " frequent renter points";
    }

    private static String makeStatementRentalLine(String rentalMovieTitle, double amount) {
        return "\t" + rentalMovieTitle+ "\t" + amount + "\n";
    }

    private static String makeStatementHeader(String customerName) {
        return "Rental Record for " + customerName + "\n";
    }
}
