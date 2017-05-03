/**
 * The Auditorium class represents a room in a movie theater that plays movies.
 * <p>
 * An Auditorium keeps track of the mvoies that it will show as well as the
 * number of tickets sold for its movie.
 * <p>
 * Since the Auditorium is identified by the name of the movie it is playing,
 * the movie title can be obtained by a call to the getKey() method.
 * <p>
 * The Auditorium's seats are arranged in a grid pattern with a given number 
 * of rows and seats per row. 
 * <p>
 * The structure of an Auditorium is immutable. Seats may not be added or removed 
 * after its initial construction. This is done so that the collection of 
 * occupied seats is not left in an inconsistentstate. Additionally, since each 
 * auditorium is identified by its movie title, the movie cannot be changed 
 * either. This keeps the movie from changing while there are seat occupants
 * watching the current movie. If an Auditorium with a different movie or 
 * different seating structure is desired, a new Auditorium will have to be 
 * constructed.
 *
 * @author Eugene Koval
 * @version 2017.04.23
 */
public class Auditorium extends KeyedItem<String> {
    
    /* The number of tickets sold for this Auditorium's movie. */
    private int ticketsSold = 0;
    /* The number of rows of seats in this Auditorium. */
    private int rows;
    /* This auditorium's total number of seats. */
    private int capacity;
    /* The list of all occupied seats. */
    private ListInterface<Seat> seats;

    /** 
     * Constructor for an Auditorium.
     * Creates an Auditorium that plays the given movie with the given number
     * of rows of seats and number of seats per row.
     *
     * @param movie the movie being played by this Auditorium.
     * @param rows the number of rows of seats.
     * @param seatsPerRow the number of seats per row.
     */
    public Auditorium(String movie, int rows, int seatsPerRow) {
        super(movie);
        this.rows = rows;
        capacity = rows * seatsPerRow;
        seats = new ArrayBasedList();
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
     * Resets the number of tickets sold to zero.
     */
    public void resetTicketCounter() {
        ticketsSold = 0;
    }

    /**
     * Returns true if all seats in this auditorium are empty.
     *
     * @return true if all seats are empty.
     */
    public boolean isEmpty() {
        return seats.size() == 0;
    }

    /**
     * Returns the number of currently occupied seats.
     *
     * @return the number of occupied seats.
     */
    public int getNumberOfViewers() {
        return seats.size();
    }

    /** 
     * Returns the number of remaining empty seats.
     *
     * @return the number of empty seats.
     */
    public int getRemainingSeats() {
        return capacity - seats.size();
    }

    /** 
     * Returns the total number of rows of seats.
     *
     * @return the numebr of rows.
     */
    public int getNumberOfRows() {
        return rows;
    }

    /**
     * Returns the total number of seats in this Auditorium, empty or otherwise.
     *
     * @return the total number of seats.
     */
    public int getSeatingCapacity() {
        return capacity;
    }

    /**
     * Adds a viewing party to this Auditorium.
     * The given party size must be less than or equal to the number of remaining
     * seats. All party members will be listed under the given name.
     *
     * @param partyName the name of the viewing party.
     * @param partySize the number of seats to occupy.
     */
    public void addParty(String partyName, int partySize) {
        if (partySize <= (capacity - seats.size())) {
            ticketsSold += partySize;
            // Make sure first seat is filled.
            if (seats.isEmpty() || seats.get(0).getPosition() > 1) {
                seats.add(0, new Seat(1, partyName));
                partySize--;
            }
            while (partySize > 0) {
                int index = 0;
                int position = 0;
                int numSeats = seats.size();
                int lastPosition = seats.get(numSeats - 1).getPosition();
                // Check if all occupied seats are clustered together.
                if (numSeats == lastPosition) {
                    index = numSeats;
                    position = numSeats + 1;
                }
                // Search for empty gap.
                else {
                    // Based on previous checks, we know there is a 
                    // guaranteed empty seat between first and last
                    // occupied seats. No need to check if index will
                    // go out of bounds.
                    boolean searching = true;
                    while (searching) {
                        int currentP = seats.get(index).getPosition();
                        int nextP = seats.get(++index).getPosition();
                        if (nextP - currentP > 1) {
                            position = currentP + 1;
                            searching = false;
                        }
                    } // end while
                } // end else
                seats.add(index, new Seat(position, partyName));
                partySize--;
            } // end while
        }
        else
            throw new TheaterException(
                "Not enough room for " + partySize + " viewers in "  +
                getKey());
    }

    /**
     * Removes a viewing party from this Auditorium.
     * All viewers matching the given name will be removed.
     * The given party size must be less than or equal to the total number of 
     * viewers currently in this Auditorium. Care should be given in providing
     * an accurate party size. This method makes no attempt to find more party
     * members than is specified in the argument. Once a number of viewers equal
     * to the given party size has been removed, removal stops. If the number of
     * party members removed is less than the given party size, a TheaterException
     * will be thrown. Likewise, too few party members removed will result in 
     * some party members being left in the theater. An additional call to this
     * method would fix such a problem.
     *
     * @param partyName the name of the viewers to remove.
     * @param partySize the numebr of viewers of the party to remove.
     */
    public void removeParty(String partyName, int partySize) {
        int numSeats = seats.size();
        if (numSeats >= partySize) {
            int index = 0;
            while (index < numSeats && partySize > 0) 
                if (seats.get(index).getOccupant().equals(partyName)) {
                    seats.remove(index);
                    partySize--;
                    numSeats--;
                }
                else
                    index++;
            if (partySize > 0)
                throw new TheaterException(
                    "Some party members may not have been removed.");
        }
        else
            throw new TheaterException(
                "Party size cannot be greater than number of viewers.");
    }

    /**
     * Removes all film viewers leaving all seats empty.
     */
    public void clearAllSeats() {
        seats.removeAll();
    }

    /**
     * Returns a formatted String representation of this Auditorium's seating.
     * For each seat in the Auditorium, information about its location in 
     * relation to the rows is provided. Also, information about whether the seat
     * is free or occupied is given. Occupied seats will have the occupant's name
     * provided.
     *
     * @return the String representation of the seating chart.
     */
    public String generateSeatingChart() {
        String chart = "";
        int numSeats = seats.size();
        int lastSeat = numSeats - 1;
        int seatsPerRow = capacity / rows;
        int end = capacity + 1;
        int occupied = 
            (seats.isEmpty()) ? end : seats.get(0).getPosition();
        for (int r = 1, index = 0, position = 1; r <= rows; r++)
            for (int s = 1; s <= seatsPerRow; s++, position++) {
                chart += "\nRow " + r + " seat " + s;
                if (position < occupied)
                    chart += " is free.";
                else {
                    Seat viewer = seats.get(index);
                    chart += " used by " + viewer.getOccupant() + "'s party.";
                    occupied = (index < lastSeat) ? 
                        seats.get(++index).getPosition() : end;
                }
            }
        return chart;
    } 
}
