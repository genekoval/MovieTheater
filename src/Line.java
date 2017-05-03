/**
 * The Line class represents a line of customers.
 * A Line is a Queue of Customer objects that also has a name that can be used
 * to identify it.
 *
 * @author Eugene Koval
 * @version 2017.04.22
 */
public class Line extends Queue<Customer> {

    /* The name of this Line. */
    private String name;

    /**
     * Constructs a Line with the given name.
     *
     * @param name the name of this Line.
     */
    public Line(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this Line.
     *
     * @return the name of this line.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this line's name to a new one.
     *
     * @param newName the new name of this line.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Returns the String representation of this Line.
     * If this Line is empty, a string indicating as such will be returned.
     * If there are Customers in this Line, the Customer's String 
     * representation will show up in the returned String.
     * This Line's name will be used to identify it.
     *
     * @return this Line's String representation.
     */
    @Override 
    public String toString() {
        String lineString = "";
        if (isEmpty())
            lineString = "No customers in the " + name + " line!";
        else {
            lineString = "The following " 
                + ((numItems == 1) ? "customer" : "customer")
                + " is in the " + name + " line:";
            lineString += super.toString();
        }
        return lineString;
    }
}
