/**
 * The Seat class represents an occupied seat in a movie theater auditorium.
 * Seats keep track of the name of their occupant as well as a positional 
 * number. This number indicates where the seat is in relation to the other
 * seats in the auditorium.
 *
 * @author Eugene Koval
 * @version 2017.04.15
 */
public class Seat {

    /* The position of this Seat in the Auditorium. */
    private int position;
    /* The name of this Seat's occupant. */
    private String occupant;

    /**
     * Constructor for a Seat.
     * Constructs a Seat with positional information about where it is in the
     * Auditorium and the name of its occupant.
     *
     * @param position this Seat's position in the Auditorium.
     * @param occupant the name of this Seat's occupant.
     */
    public Seat(int position, String occupant) {
        this.position = position;
        this.occupant = occupant;
    }

    /**
     * Returns this Seat's position in the Auditorium.
     *
     * @return this Seat's position in the Auditorium.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Returns the name of this Seat's occupant.
     *
     * @return the name of this Seat's occupant.
     */
    public String getOccupant() {
        return occupant;
    }
}
