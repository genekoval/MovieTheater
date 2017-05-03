/**
 * Theater where all customers enter in order to purchase tickets.
 * The theater contains a list of all customers present, auditoriums, and three
 * queues. Customers enter the shortest queue available to them and enter the
 * theater once they purchase their tickets.
 *
 * @author Brandon Campbell
 * @author Eugene Koval
 * @version 2017.04.23
 */
public class Theater
{
    /* A list of auditoriums in the theater. */
    private AscendinglyOrderedList<Auditorium, String> auditoriums;
    /* A list of customers that are present in the entire movie theater. */
    private AscendinglyOrderedList<Customer, String> customers;
    /* A list of each available line in the movie theater. */
    private ListInterface<Line> lines;
    /* The price for a single ticket into a movie. */
    private double ticketPrice;
    /* The line that is next in round robin order. */
    private int nextLine;

    /**
     * Constructor for a Theater.
     * Creates a theater with an empty collection of auditoriums, an empty
     * collection of customers, and an empty collection of customer lines.
     * <p>
     * By default, the customer line to be served first is set to -1. This must
     * be set to a value greater or equal to 0 and less than the total number
     * of lines before the theater will function properly.
     * <p>
     * The price for one theater ticket, regardless of movie being seen, is the
     * value passed as an argument.
     *
     * @param ticketPrice the price of one ticket.
     */
    public Theater(double ticketPrice) {
        this.ticketPrice = ticketPrice;
        auditoriums = new AscendinglyOrderedList<Auditorium, String>();
        customers = new AscendinglyOrderedList<Customer, String>();
        lines = new ArrayBasedList<Line>();
        nextLine = -1;
    }

    /**
     * Returns the price of one movie ticket.
     *
     * @return the price of one ticket.
     */
    public double getTicketPrice()
    {
        return ticketPrice;
    }

    /**
     * Sets the price of a single ticket for a movie.
     *
     * @param price the price of a movie ticket.
     */
    public void setTicketPrice(double price)
    {
        this.ticketPrice = price;
    }

    /**
     * Returns the index of the next customer line to be served.
     *
     * @return the index of the next line.
     */
    public int getNextLine()
    {
        return nextLine;
    }

    /**
     * Sets the index of the next customer line to be served.
     *
     * @param nextLine the index of the next line.
     */
    public void setNextLine(int nextLine)
    {
        if (nextLine >= 0 && nextLine < lines.size())
            this.nextLine = nextLine;
        else
            throw new TheaterException("Invalid index for next line.");
    }

    /**
     * Returns the total number of customer lines.
     *
     * @return the total number of customer lines.
     */
    public int getNumberOfLines()
    {
        return lines.size();
    }

    /**
     * Returns the total number of auditoriums in the theater.
     * This number is equal to the total number of movies currently playing
     * in this theater.
     *
     * @return the total number of auditoriums.
     */
    public int getNumberOfMovies()
    {
        return auditoriums.size();
    }

    /**
     * Enqueues a customer in the shortest line available to them.
     * This method is the only way to add customers to this theater. From the
     * time they enter a line to the time they leave, a customer with a name
     * matching the name of the given customer may not enter the theater.
     * <p>
     * The given customer may enter the express line only if hasChild is true.
     * A customer bound for the express line will be put in a regular line
     * instead if a regular line is atleast twice as short as the express line.
     * Out of the regular lines, the customer will be put in the shortest one.
     * <p>
     * Returns the line into which the customer was placed.
     *
     * @param customer the customer to be enqueued.
     * @param hasChild if true, the customer may be put in the express line.
     * @return the line into which the customer was placed.
     */
    public Line enqueueCustomer(Customer customer, boolean hasChild)
    {
        Line shortest = getShortestLine(hasChild);
        shortest.enqueue(customer);
        customers.add(customer);
        return shortest;
    }

    /**
     * Returns true if a customer with a name matching the given name is
     * currently in the theater's lines or auditoriums.
     *
     * @param name the name of the customer who is in the theater or not.
     * @return true if a customer with the given name is in the theater.
     */
    public boolean containsCustomer(String name) {
        return customers.contains(name);
    }

    /**
     * Returns the shortest line that a customer is allowed to be in.
     * If checkExpress is true, this method will check if there are any regular
     * lines that are twice as short as the express line. If checkExpress is
     * false, only the lengths of the regular lines will be compared.
     *
     * @param checkExpress whether to compare the express line length or not.
     * @return the shortest line that a customer is allowed to be in.
     */
    private Line getShortestLine(boolean checkExpress)
    {
        int shortIndex;
        double shortSize;
        if (checkExpress) {
            shortIndex = 0;
            shortSize = lines.get(0).size() / 2.0;
        }
        else {
            shortIndex = 1;
            shortSize = lines.get(1).size();
        }
        int numLines = lines.size();
        for (int i = shortIndex; i < numLines; i++) {
            int currSize = lines.get(i).size();
            if (currSize < shortSize) {
                shortIndex = i;
                shortSize = currSize;
            }
        }
        return lines.get(shortIndex);
    }

    /**
     * Returns true if the auditorium playing the given movie has a number of
     * empty seats that is greater or equal to the given party size.
     *
     * @param movie the movie for which to check seat count.
     * @param partySize the size of the customer party to find seating for.
     * @return true if there are enough seats.
     */
    public boolean hasEnoughSeats(String movie, int partySize)
    {
        int remaining = auditoriums.get(movie).getRemainingSeats();
        return remaining >= partySize;
    }

    /**
     * Advances the line index of the next customer line to serve to the next
     * one in the list.
     * Lines are served in a round-robin manner. After the last line in the list
     * is served, the line index will return to the first. Before this method
     * can execute, the next-line index must have been manually set to a valid
     * one.
     */
    private void advanceLine()
    {
        if (nextLine >= 0)
            nextLine = (nextLine + 1) % lines.size();
        else
            throw new TheaterException(
                "Customer line order has not been established.");
    }

    /**
     * Finds the first auditorium that has enough empty seats to accommodate
     * the given party size.
     * The auditoriums are searched by the title of the movie they are playing.
     * The movies are in ascending alphabetical order.
     * Returns the name of the movie that can be seen by the entire viewing
     * party. If all movies are sold out, an empty String is returned.
     *
     * @param partySize the party size to find seating for.
     * @return the name of the movie, or an empty String if all movies sold out.
     */
    public String findFirstAvailableMovie(int partySize)
    {
        String availableMovie = "";
        int numMovies = auditoriums.size();
        for (int i = 0; i < numMovies && availableMovie.isEmpty(); i++) {
            Auditorium current = auditoriums.get(i);
            if (current.getRemainingSeats() >= partySize)
                availableMovie = current.getKey();
        }
        return availableMovie;
    }

    /**
     * Returns the customer that is next up to be served.
     * If the line to served is empty, the next-line index is advanced until
     * it comes back to the current line or it finds a customer. Note that this
     * method only returns the customer reference. It does not remove the
     * customer from the theater line.
     *
     * @return the next customer to be served.
     */
    public Customer getNextCustomer()
    {
        Customer customer = null;
        int numLines = lines.size();
        for (int i = 0; i < numLines && customer == null; i++) {
            Queue<Customer> current = lines.get(nextLine);
            if (! current.isEmpty())
                customer = current.peek();
            else
                advanceLine();
        }
        return customer;
    }

    /**
     * Dequeues the next customer in the current line and seats them in their
     * desired auditorium.
     * Assumes this auditorium will have enough empty seats to accommodate the
     * entire customer party.
     *
     * @return the customer that has been served.
     */
    public Customer seatNextCustomer()
    {
        Customer next = dequeueNextCustomer();
        auditoriums.get(next.getMovie())
        .addParty(next.getKey(), next.getPartySize());
        return next;
    }

    /**
     * Dequeues and returns the next customer to be served.
     * The index of the next line to be served is advanced.
     *
     * @return the next customer to be served.
     */
    private Customer dequeueNextCustomer()
    {
        Line current = lines.get(nextLine);
        if (! current.isEmpty()) {
            advanceLine();
            return current.dequeue();
        }
        else
            throw new TheaterException(
                "The current customer line is empty!");
    }

    /**
     * Returns the string representation of the line at the given index.
     *
     * @param lineIndex the index of line to get the String representation of.
     * @return String representation of the line at the given index.
     */
    public String getLineInfo(int lineIndex)
    {
        return lines.get(lineIndex).toString();
    }

    /**
     * Removes the next customer to be served from the theater.
     * All records of this customer are removed from the theater.
     * They leave without purchasing any tickets.
     * The next-line index is advanced following this call.
     */
    public void removeFromLine()
    {
        customers.remove(dequeueNextCustomer().getKey());
    }

    /**
     * Removes a customer and all members of their party from the theater.
     * The customer party matching the given name is removed from whichever
     * auditorium they were watching their movie in.
     *
     * @param name the name of the customer party to remove.
     */
    public void removeFromTheater(String name)
    {
        try {
            Customer customer = customers.remove(name);
            Auditorium aud = auditoriums.get(customer.getMovie());
            aud.removeParty(name, customer.getPartySize());
        }
        catch (ListException ex) {
            throw new TheaterException(
                "This customer not in Movie Theater!");
        }
    }

    /**
     * Returns the total number of tickets sold for the given movie.
     *
     * @param movie the movie for which to get the number of tickets sold.
     * @return the number of tickets sold.
     */
    public int getNumberOfTicketsSold(String movie)
    {
        return auditoriums.get(movie).getTicketsSold();
    }

    /**
     * Each auditorium keeps count of its individual ticket sales.
     * Each auditorium's sales are printed out along with a grand total.
     * @return total Total sales made so far.
     */
    public double getTicketSales()
    {
        int totalTickets = 0;
        for(int i = 0, numAuds = auditoriums.size(); i < numAuds; i++)
            totalTickets += auditoriums.get(i).getTicketsSold();
        return totalTickets * ticketPrice;
    }

    /**
     * Adds a new auditorium for a movie to be played in.
     * @param auditorium the auditorium to be added to the theater list.
     */
    public void addAuditorium(Auditorium auditorium)
    {
        auditoriums.add(auditorium);
    }

    /**
     * Removes an existing auditorium from this theater.
     * The auditorium removed will be the one that is playing the given movie.
     *
     * @param movie the auditorium playing this movie will be removed.
     */
    public void removeAuditorium(String movie)
    {
        auditoriums.remove(movie);
    }

    /**
     * Add a customer line to the theater.
     * The line's name will be the given name.
     *
     * @param lineName the name of the new line to add to the theater.
     */
    public void addLine(String lineName) {
        lines.add(lines.size(), new Line(lineName));
    }

    /**
     * Checks each line to see if they are all empty.
     *
     * @return true if all customer lines are empty.
     */
    public boolean allLinesEmpty()
    {
        boolean allEmpty = true;
        for (int i = 0, size = lines.size(); i < size && allEmpty; i++)
            allEmpty = lines.get(i).isEmpty();
        return allEmpty;
    }

    /**
     * Checks each auditorium to see if they are all empty.
     *
     * @return true if all auditoriums are empty.
     */
    public boolean allMoviesEmpty()
    {
        boolean allEmpty = true;
        for (int i = 0, size = auditoriums.size(); i < size && allEmpty; i++)
            allEmpty = auditoriums.get(i).isEmpty();
        return allEmpty;
    }

    /**
     * Returns the seating chart of the auditorium playing the given movie.
     * The seating chart is returned as a formatted String displaying
     * information about each seat in the auditorium. The seats are numbered by
     * their position in the rows of seats.
     *
     * @param movie the movie indicating which Auditorium to get seating chart for
     * @return the given movie's auditorium's seating chart.
     */
    public String getSeatingChart(String movie)
    {
        return auditoriums.get(movie).generateSeatingChart();
    }
}
