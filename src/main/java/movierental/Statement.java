package movierental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statement {
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

    public static class RentalLine {
        private final String movieTitle;
        private final double amount;

        RentalLine(String movieTitle, double amount) {
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
