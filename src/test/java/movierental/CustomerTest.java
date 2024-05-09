package movierental;

import movierental.presenters.HtmlStatementPresenter;
import movierental.presenters.StringStatementPresenter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {


    @Test
    public void test_statement_calculation() {
        Customer customer = new Customer("Bob");
        customer.addRental(new Rental(new Movie("Jaws", Movie.PriceCode.REGULAR), 2));
        customer.addRental(new Rental(new Movie("Golden Eye", Movie.PriceCode.REGULAR), 3));
        customer.addRental(new Rental(new Movie("Short New", Movie.PriceCode.NEW_RELEASE), 1));

        Statement statement = customer.statement();
        assertEquals("Bob", statement.customerName());
        assertEquals(3, statement.rentalLines().size());
        {
            var rentalLine = statement.rentalLines().get(0);
            assertEquals("Jaws", rentalLine.movieTitle());
            assertEquals(2.0, rentalLine.amount(), 0.1);
        }
        {
            var rentalLine = statement.rentalLines().get(1);
            assertEquals("Golden Eye", rentalLine.movieTitle());
            assertEquals(3.5, rentalLine.amount(), 0.1);
        }
        {
            var rentalLine = statement.rentalLines().get(2);
            assertEquals("Short New", rentalLine.movieTitle());
            assertEquals(3.0, rentalLine.amount(), 0.1);
        }
        assertEquals(8.5, statement.totalAmount(), 0.1);
        assertEquals(3, statement.totalFrequentRenterPoints());
    }

    @Test
    public void test_string_presentation() {
        Customer customer = new Customer("Bob");
        customer.addRental(new Rental(new Movie("Jaws", Movie.PriceCode.REGULAR), 2));
        customer.addRental(new Rental(new Movie("Golden Eye", Movie.PriceCode.REGULAR), 3));
        customer.addRental(new Rental(new Movie("Short New", Movie.PriceCode.NEW_RELEASE), 1));
        customer.addRental(new Rental(new Movie("Long New", Movie.PriceCode.NEW_RELEASE), 2));
        customer.addRental(new Rental(new Movie("Bambi", Movie.PriceCode.CHILDRENS), 3));
        customer.addRental(new Rental(new Movie("Toy Story", Movie.PriceCode.CHILDRENS), 4));

        StringStatementPresenter stringStatementPresenter = new StringStatementPresenter();
        String expected = "Rental Record for Bob\n" +
                "\tJaws\t2.0\n" +
                "\tGolden Eye\t3.5\n" +
                "\tShort New\t3.0\n" +
                "\tLong New\t6.0\n" +
                "\tBambi\t1.5\n" +
                "\tToy Story\t3.0\n" +
                "Amount owed is 19.0\n" +
                "You earned 7 frequent renter points";

        assertEquals(expected, stringStatementPresenter.present(customer.statement()));
    }

    @Test
    public void test_html_presentation() {
        Customer customer = new Customer("Bob");
        customer.addRental(new Rental(new Movie("Jaws", Movie.PriceCode.REGULAR), 2));
        customer.addRental(new Rental(new Movie("Golden Eye", Movie.PriceCode.REGULAR), 3));
        customer.addRental(new Rental(new Movie("Short New", Movie.PriceCode.NEW_RELEASE), 1));
        customer.addRental(new Rental(new Movie("Long New", Movie.PriceCode.NEW_RELEASE), 2));
        customer.addRental(new Rental(new Movie("Bambi", Movie.PriceCode.CHILDRENS), 3));
        customer.addRental(new Rental(new Movie("Toy Story", Movie.PriceCode.CHILDRENS), 4));

        HtmlStatementPresenter htmlStatementPresenter = new HtmlStatementPresenter();
        String expected = "<h1>Rental Record for <em>Bob</em></h1>\n" +
                "<table>\n" +
                "  <tr><td>Jaws</td><td>2.0</td></tr>\n" +
                "  <tr><td>Golden Eye</td><td>3.5</td></tr>\n" +
                "  <tr><td>Short New</td><td>3.0</td></tr>\n" +
                "  <tr><td>Long New</td><td>6.0</td></tr>\n" +
                "  <tr><td>Bambi</td><td>1.5</td></tr>\n" +
                "  <tr><td>Toy Story</td><td>3.0</td></tr>\n" +
                "</table>\n" +
                "<p>Amount owed is <em>19.0</em></p>\n" +
                "<p>You earned <em>7</em> frequent renter points</p>\n";

        assertEquals(expected, htmlStatementPresenter.present(customer.statement()));
    }

    @Test
    public void test_html_presentation_no_rentals() {
        Customer customer = new Customer("Bob");

        HtmlStatementPresenter htmlStatementPresenter = new HtmlStatementPresenter();
        String expected = "<h1>Rental Record for <em>Bob</em></h1>\n" +
                "<table>\n" +
                "</table>\n" +
                "<p>Amount owed is <em>0.0</em></p>\n" +
                "<p>You earned <em>0</em> frequent renter points</p>\n";

        assertEquals(expected, htmlStatementPresenter.present(customer.statement()));
    }
}