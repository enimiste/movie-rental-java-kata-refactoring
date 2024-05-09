package movierental.presenters;

import movierental.Statement;

public class StringStatementPresenter implements StatementPresenter{
    @Override
    public String present(Statement statement) {
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
