package movierental;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import movierental.presenters.StringStatementPresenter;
import org.junit.Test;

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
            assertEquals(2.0, rentalLine.amount());
        }
        {
            var rentalLine = statement.rentalLines().get(1);
            assertEquals("Golden Eye", rentalLine.movieTitle());
            assertEquals(3.5, rentalLine.amount());
        }
        {
            var rentalLine = statement.rentalLines().get(2);
            assertEquals("Short New", rentalLine.movieTitle());
            assertEquals(3.0, rentalLine.amount());
        }
        assertEquals(8.5, statement.totalAmount());
        assertEquals(3, statement.totalFrequentRenterPoints());
    }

    @Test
    public void test() {
        Customer customer = new Customer("Bob");
        customer.addRental(new Rental(new Movie("Jaws", Movie.PriceCode.REGULAR), 2));
        customer.addRental(new Rental(new Movie("Golden Eye", Movie.PriceCode.REGULAR), 3));
        customer.addRental(new Rental(new Movie("Short New", Movie.PriceCode.NEW_RELEASE), 1));
        customer.addRental(new Rental(new Movie("Long New", Movie.PriceCode.NEW_RELEASE), 2));
        customer.addRental(new Rental(new Movie("Bambi", Movie.PriceCode.CHILDRENS), 3));
        customer.addRental(new Rental(new Movie("Toy Story", Movie.PriceCode.CHILDRENS), 4));

        StringStatementPresenter stringStatementPresenter = new StringStatementPresenter();
        String expected = "" +
                "Rental Record for Bob\n" +
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

}