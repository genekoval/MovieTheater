/**
 * The Auditorium class represents a room in a movie theater that plays movies.
 * 
 * An Auditorium keeps track of the movie that it will show as well as the 
 * number of tickets sold for its movie. 
 *
 * The Auditorium's seats are arranged in a grid pattern with a given number 
 * of rows and seats per row. The seating structure is implemented using a 
 * modified simply-linked list design. In order to conserve memory usage, only 
 * information about occupied seats is stored. Each occupied seat is linked to 
 * the next occupied seat in the Auditorium. 
 * 
 * Additionally, positional information about where each film viewer is in the 
 * room is stored with that seat. Positional information is stored in a 
 * one-dimensional fashion with a number from 1 to n, with n being the maximum 
 * capacity of the Auditorium. Adding and removing occupied seats does not affect 
 * any of the other seat's positions. 
 *
 * New film viewers are added in empty positions starting from 1 and going to n.
 * Viewers in a given party may not be seated next to each other, since empty 
 * seats are filled in order. Another important deviation that the Auditorium
 * makes from a traditional simply-linked strucuture is that members of one
 * viewing party are added one-by-one during one traversal through the seats.
 * In other words, as one party member is placed in an empty position, the 
 * Auditorium searches for another empty seat starting from where that last
 * party member was placed. This is done in an effort to cut down the number 
 * of link traversals when adding multiple copies of the same viewer name.
 *
 * A simply-linked structure approach was chosen over an array-based one 
 * since many additions and removals may take place in the center of the 
 * Auditorium's collection of occupied seats. An array-based approach would 
 * require frequent shifting to accomodate this behavior. Also, with a
 * simply-linked structure, there is no allocation of memory for empty
 * seats.
 *
 * @author Eugene Koval
 * @version 2017.04.16 
 */
public class Auditorium {

    /* The name of the movie being shown in this Auditorium. */
    private String currentMovie;
    /* The number of tickets sold for this Auditorium's movie. */
    private int ticketsSold = 0;
    /* The number of seats currently occupied in this Auditorium. */
    private int numViewers = 0;
    /* The number of rows of seats in this Auditorium. */
    private int rows;
    /* This auditorium's total number of seats. */
    private int capacity;
    /* The occupied seat with the lowest positional value. A null value here
       indicates an empty Auditorium. */
    private Seat head = null;

    /**
     * Constructor for an Auditorium.
     * Creates an Auditorium that plays the given movie with the given
     * number of rows of seats and number of seats per row.
     *
     * @param currentMovie the movie currently being played
     * @param rows the number of rows of seats
     * @param seatsPerRow the number of seats per row
     */
    public Auditorium(String currentMovie, int rows, int seatsPerRow) {
        this.currentMovie = currentMovie;
        this.rows = rows;
        capacity = rows * seatsPerRow;
    }

    /**
     * Returns the name of the movie currently being played.
     *
     * @return the movie currently being played.
     */
    public String getMovie() {
        return currentMovie;
    }

    /**
     * Sets this Auditorium's movie to the given movie.
     * Resets the number of tickets sold to zero.
     *
     * @param newMovie the name of the new movie that will be played.
     */
    public void setMovie(String newMovie) {
        currentMovie = newMovie;
        ticketsSold = 0;
    }

    /**
     * Returns the total number of tickets sold for this movie.
     *
     * @return the total number of tickets sold.
     */
    public int getTicketsSold() {
        return ticketsSold;
    }

    /** 
     * Returns true if all seats are empty.
     *
     * @return true if all seats are empty.
     */
    public boolean isEmpty() {
        return numViewers == 0;
    }

    /**
     * Returns the number of remaining empty seats.
     *
     * @return the number of empty seats.
     */
    public int remainingSeats() {
        return capacity - numViewers;
    }

    /**
     * Returns this Auditorium's total number of seats.
     *
     * @return the total number of seats.
     */
    public int getMaximumCapacity() {
        return capacity;
    }

    /**
     * Return the number of currently occupied seats.
     *
     * @return the number of occupied seats.
     */
    public int getNumberOfViewers() {
        return numViewers;
    }

    /**
     * Add a viewing party to this Auditorium.
     * The given party size must be less than or equal to the number of 
     * remaining seats. All party members will be listed under the given name.
     *
     * @param name the name of the viewing party.
     * @param partySize the number of seats to occupy with the party name.
     */
    public void addParty(String name, int partySize) {
        if (partySize <= (capacity - numViewers)) {
            ticketsSold += partySize;
            numViewers += partySize;
            Seat current = head;
            if (head == null || head.getPosition() > 1) {
                head = new Seat(1, name, current);
                partySize--;
            }
            while (current != null && partySize > 0) {
                int currPos = current.getPosition();
                Seat next = current.getNext();
                int nextPos = (next != null) ? next.getPosition() : (capacity + 1);
                if ((nextPos - currPos) > 1) {
                    current.setNext(new Seat(currPos + 1, name, next));
                    partySize--;
                }
                current = next;
            }
        }
        else
            throw new TheaterException(
                "Not enough room for " + partySize + " viewers in " + currentMovie);
    }

    /**
     * Removes a viewing party from this Auditorium.
     * The given party size should logically be less than or equal to the 
     * total number of viewers currently in this Auditorium. Care should be
     * given in providing an accurate party size. This method makes no attempt
     * to find more party members than is specified by the given party size. 
     * Once a number of viewers equal to the given party size has been removed,
     * searching stops. If the number of party members removed is less than the 
     * given party size, a TheaterException will be thrown. Likewise, too few 
     * party members removed will result in a memory leak.
     *
     * @param name the name of the viewers to remove.
     * @param partySize the number of viewers of the party to remove.
     */
    public void removeParty(String name, int partySize) {
        if (numViewers >= partySize) {
            if (head.getName().equals(name)) {
                head = head.getNext();
                numViewers--;
                partySize--;
            }
            Seat current = head;
            while (current != null && partySize > 0) {
                Seat next = current.getNext();
                if (next.getName().equals(name)) {
                    current.setNext(next.getNext());
                    numViewers--;
                    partySize--;
                }
            }
            if (partySize > 0)
                throw new TheaterException(
                    "Some party members may not have been removed.");
        }
        else
            throw new TheaterException(
                "Party size cannot be greater than number of viewers.");
    }

    /**
     * Removes all viewers from this Auditorium.
     * All seats will be empty after this call returns.
     */
    public void removeAllViewers() {
        head = null;
    }
    
    /**
     * Generates and returns a list of all seats in this Auditorium.
     * The individual seat information indicates its position in the 
     * Auditorium's theoretical grid layout. Each seat is indicated 
     * as being free or as being occupied. Occupied seats will display
     * the party member name that is using it. 
     *
     * @return the list of seats with information about each seat.
     */
    public String generateSeatingChart() {
        String chart = "";
        int seats = capacity / rows;
        int occupied = (head != null) ? head.getPosition() : (capacity + 1);
        Seat current = head;
        for (int r = 1, position = 1; r <= rows; r++) 
            for (int s = 1; s <= seats; s++, position++) 
                chart += "\nRow " + r + " seat " + s;
        		// TODO fix variable resolution error
                if (position < occupied) 
                    chart += " is free.";
                else {
                    chart += " used by " + current.getName() + "'s party.";
                    current = current.getNext();
                    occupied = (current != null) ? 
                        current.getPosition() : (capacity + 1);
                }
       return chart;
    }
}
