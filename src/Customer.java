/**
 * The Customer class represents a theater viewing party.
 * The party is identified by the name of a member of the party. The Customer
 * also keeps track of the movie the party is seeing. Finally, the number of
 * members in the viewing party is kept track of. Since Customer is a subclass
 * of KeyedItem, the party name is acts as the identifying key. A call to
 * getKey() will return the name of this Customer.
 *
 * @author Brandon Campbell
 * @version 04.09.2017
 */
public class Customer extends KeyedItem<String>
{
    /* The name of the movie this Customer is seeing. */
    private String movie;
    /* The number of members in this Customer's viewing party. */
    private int partySize;

    /**
     * Constructor for customer
     * @param name Name of customer.
     * @param movie Name of movie the customer is here to see.
     * @param partySize Number of people in the customer's party.
     */
    public Customer(String name, String movie, int partySize)
    {
        super(name);
        this.movie = movie;
        this.partySize = partySize;
    }

    /**
     * Returns the name of the movie this Customer is seeing.
     *
     * @return the name of the movie.
     */
    public String getMovie()
    {
        return movie;
    }

    /**
     * Sets this Customer's movie to the given new movie.
     *
     * @param newMovie the title of the new movie.
     */
    public void setMovie(String newMovie)
    {
        movie = newMovie;
    }

    /**
     * Returns the number of members in this Customer's viewing party.
     *
     * @return the size of the viewing party.
     */
    public int getPartySize()
    {
        return partySize;
    }

    /**
     * Returns the String representation of this Customer.
     * The String includes this Customer's name, movie, and party size.
     *
     * @return this Customer's String representation.
     */
    @Override
    public String toString()
    {
        String customer = "\n\tCustomer ";
        customer += getKey() + " party of ";
        customer += partySize + " for ";
        customer += movie + " movie.";
        return customer;
    }
}
