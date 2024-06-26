package movierental.presenters;

import movierental.Statement;

public class HtmlStatementPresenter implements StatementPresenter {
    @Override
    public String present(Statement statement) {
        StringBuilder result = new StringBuilder();
        result.append(makeStatementHeader(statement.customerName()));

        for (Statement.RentalLine rental : statement.rentalLines()) {
            result.append(makeStatementRentalLine(rental.movieTitle(), rental.amount()));
        }

        // add footer lines
        result.append(makeStatementFooter(statement.totalAmount(), statement.totalFrequentRenterPoints()));

        return result.toString();
    }

    private static String makeStatementFooter(double totalAmount, int frequentRenterPoints) {
        return "</table>\n" +
                "<p>Amount owed is <em>" + totalAmount + "</em></p>\n" +
                "<p>You earned <em>" + frequentRenterPoints + "</em> frequent renter points</p>\n";
    }

    private static String makeStatementRentalLine(String rentalMovieTitle, double amount) {
        return "  <tr><td>" + rentalMovieTitle + "</td><td>" + amount + "</td></tr>\n";
    }

    private static String makeStatementHeader(String customerName) {
        return "<h1>Rental Record for <em>" + customerName + "</em></h1>\n" +
                "<table>\n";
    }
}
