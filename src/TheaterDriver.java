import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.NumberFormatException;

/**
 * The Main class for the Wonderful Movie Theater Program.
 * This class handles all user input/output and coordinates all actions 
 * requested by the user.
 *
 * @author Eugene Koval
 * @version 2017.04.22
 */
public class TheaterDriver {
    
    /** Buffered Reader for handling of all user input/output. */
    public static BufferedReader stdin = new BufferedReader(
                                         new InputStreamReader(
                                         System.in));
    
    /**
     * The program's main method.
     * Prints a greeting message, creates a theater with auditoriums for 
     * the "Life" and "Logan" movies, and adds three customer lines to the 
     * theater. Then, runs the program loop.
     *
     * @param args program commandline arguments (not used)
     */
    public static void main(String[] args) {
    
        // Prints Theater greeting.
        System.out.println("\nWelcome to the Wonderful Movie Theater program!" +
            "\n\tTonight's feature are:\n\t\t\"Life\" and \"Logan\"");

        // Assembles a theater with users's input.
        System.out.println("\nPlease specifiy the size of the Movie Theaters:");
        Auditorium logan = createAuditorium("Logan");
        Auditorium life = createAuditorium("Life");
        double price = getDoubleInput("\t>> Enter the price of a ticket");
        Theater theater = new Theater(price);
        theater.addAuditorium(logan);
        theater.addAuditorium(life);
        theater.addLine("express");
        theater.addLine("first");
        theater.addLine("second");

        // The program's main loop.
        for (String option; ! (option = getMenuInput()).equals("8");)
            dispatchAction(theater, option);

        // Closes the program.
        System.out.println("The Wonderful Movie Theater, who earned $" + 
            theater.getTicketSales() + ", kicks out remaining customers and " + 
            "closes...");
        close();
    }
    
    /**
     * Constructs and returns an auditorium.
     * Prompts the user for specifications for the auditorium that will play
     * the given movie. The returned auditorium will match these specifications.
     *
     * @param movie the movie for which to construct the auditorium.
     * @return the constructed auditorium.
     */
    public static Auditorium createAuditorium(String movie) {
        System.out.println("Enter information about the " +
            movie + " Movie Theater:");
        int rows = getIntegerInput("\t\t>> Enter number of rows");
        int seats = getIntegerInput(
                    "\t\t>> Enter number of seats in a row");
        return new Auditorium(movie, rows, seats);
    }

    /**
     * Closes the Buffered Reader.
     * Prints a goodbye message if the Buffered Reader closed successfully.
     */
    public static void close() {
        try {
            stdin.close();
            System.out.println("Good Bye!");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * ************************************************************************
     * User Input Methods
     * ************************************************************************
     */
    
    /**
     * Gets user input given a prompt message.
     * Returns the user's response to the prompt. Note that a colon and space 
     * are appended to the given prompt. The prompt will show as follows:
     *
     * "prompt: "
     *
     * @param prompt the message to show to user to prompt for input.
     * @return the user's response to the prompt.
     */
    public static String getInput(String prompt) {
        System.out.print(prompt + ": ");
        String input = "";
        try {
            input = stdin.readLine().trim();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(input);
        return input;
    }

    /**
     * Gets a user integer input given a prompt message.
     * Returns the user's integer if it is strictly greater than zero. If a
     * integer that is zero or less, or not an integer at all, is entered,
     * the prompt will repeat.
     *
     * @param prompt the message to show to user to prompt for input.
     * @return the user's integer response to the prompt.
     */
    public static int getIntegerInput(String prompt) {
        int response = 0;
        boolean valid = false;
        do {
            try {
                response = Integer.parseInt(getInput(prompt));
                if (response > 0)
                    valid = true;
                else
                    System.out.println("\n\tNumber must be greater than zero!\n");
            }
            catch (NumberFormatException ex) {
                System.out.println("\n\tInput must be a number!\n");
            }
        } while (! valid);
        return response;
    }

    /**
     * Gets a user Double input given a prompt message.
     * Returns the user's Double if it is strictly greater than zero. If a
     * Double that is zero or less, or not an Double at all, is entered,
     * the prompt will repeat.
     *
     * @param prompt the message to show to user to prompt for input.
     * @return the user's Double response to the prompt.
     */
    public static double getDoubleInput(String prompt) {
        double response = 0;
        boolean valid = false;
        do {
            try {
                response = Double.parseDouble(getInput(prompt));
                if (response > 0)
                    valid = true;
                else
                    System.out.println("\n\tNumber must be greater than zero!\n");
            }
            catch (NumberFormatException ex) {
                System.out.println("\n\tInput must be a number!\n");
            }
        } while (! valid);
        return response;
    }

    /**
     * Asks the user a question.
     * Prompts the user with the given prompt message and the given array
     * of possible answers. If <i>showPossible</i> is true, prompt shows up 
     * as follows:
     * <p>
     * <b>prompt (answer1/answer2/.../answerN):</b>
     * <p>
     * Returns the index of the answer that was chosen.
     * User answers that do not match exactly with one of the possible answers
     * will be rejected. The prompt will keep running until a valid answer is 
     * given.
     *
     * @param prompt the question to show to the user to prompt for input.
     * @param answers the array of valid answers.
     * @param showPossible if true, will show all possible answers to user.
     * @return the index of the user's answer.
     */
    public static int askQuestion(String prompt, String[] answers, 
    boolean showPossible) {
        if (showPossible) { 
            String ansString = "(";
            for (int i = 0, last = answers.length - 1; i < answers.length; i++) {
                ansString += answers[i];
                if (i != last)
                    ansString += '/';
            }
            ansString += ")";
            prompt = prompt + ' ' + ansString; 
        }
        int choice;
        boolean validAnswer = false;
        do {
            choice = 0; 
            String response = getInput(prompt);
            // Check against each possible answer.
            while (choice < answers.length && ! validAnswer)
                if (response.equals(answers[choice]))
                    validAnswer = true;
                else
                    choice++;
            if (! validAnswer)
                System.out.println("\n\tThat is not a valid answer!\n");
        } while (! validAnswer);
        return choice;
    }

    /**
     * Asks the user a yes-no question.
     * Prompts the user with the given prompt message.
     * The prompt shows up as follows:
     * <p>
     * <b>prompt (Y/N):</b>
     * <p>
     * The prompt will keep running until a case-sensitive 'Y' or 'N' are 
     * given. Returns true if the user input a 'Y'. Returns false if the 
     * use input a 'N'.
     *
     * @param prompt the yes-no question to show to the user.
     * @return true if user answers with a 'Y'; false if user enters a 'N'.
     */
    public static boolean askPolarQuestion(String prompt) {
        int response = askQuestion(prompt, new String[]{"Y", "N"}, true);
        return response == 0;
    }

    /**
     * Prints the program menu and gets user's menu option.
     * The program menu contains the following options:
       <p>  1. Customer(s) enter(s) Movie Theater.
       <p>  2. Customer buys ticket(s).
       <p>  3. Customer(s) leave(s) the theater.
       <p>  4. Display info about customers waiting for tickets.
       <p>  5. Display seating chart for Life Movie Theater.
       <p>  6. Display seating chart for Logan Movie Theater.
       <p>  7. Display number of tickets sold and total earnings.
       <p>  8. End the program.
       <p> 
     * Returns the user's menu choice.
     *
     * @return the user's menu choice.
     */
    public static String getMenuInput() {
        System.out.println(
            "\nSelect an operation from the following menu:"
            + "\n\t1. Customer(s) enter(s) Movie Theater."
            + "\n\t2. Customer buys ticket(s)."
            + "\n\t3. Customer(s) leave(s) the theater."
            + "\n\t4. Display info about customers waiting for tickets."
            + "\n\t5. Display seating chart for Life Movie Theater."
            + "\n\t6. Display seating chart for Logan Movie Theater."
            + "\n\t7. Display number of tickets sold and total earnings."
            + "\n\t8. End the program."
        );
        return getInput(">> Make your selection now");
    }

    /*
     * ************************************************************************
     * Program Actions
     * ************************************************************************
     */

    /**
     * Triggers the appropriate action based on the given option.
     * An action is performed on the given theater based on the given option.
     *
     * @param theater the theater on which to perform the action.
     * @param option this option dictates which action will be taken.
     */
    public static void dispatchAction(Theater theater, String option) {
        System.out.println();
        switch(option) {
            case "1":
                enterCustomer(theater);
                break;
            case "2":
                buyTickets(theater);
                break;
            case "3":
                removeCustomer(theater);
                break;
            case "4":
                displayCustomerLine(theater);
                break;
            case "5":
                displaySeating(theater, "Life");
                break;
            case "6":
                displaySeating(theater, "Logan");
                break;
            case "7":
                displayTicketInfo(theater);
                break;
            default:
                System.out.println(
                    "I'm sorry. I don't understand " + option);
                break;
        }
    }
    
    /**
     * Enter a customer into the movie theater line.
     * The user is prompted for the customer's name, the size of their party,
     * and the movie they would like to see. The name must not match with any 
     * customer that is currently in line or watching a movie in the theater. 
     * If there is a name conflict, the user will be prompted for a new name 
     * until no conflict arises.The user is also asked if the customer has a 
     * child 11 or younger with them. The response to this question will dictate 
     * whether the customer may enter the express line or not. Once a customer 
     * is constructed based on the input data, they are entered into the given 
     * theater.
     *
     * @param theater the theater into which to place the new customer.
     */
    public static void enterCustomer(Theater theater) {
        String name = "";
        do {
            String input = getInput("\t>> Enter customer name");
            if (! theater.containsCustomer(input))
                name = input;
            else
                System.out.println(
                    "Customer " + input + " is already in the theater!");
        } while (name.isEmpty());
        int partySize = getIntegerInput("\t>> Enter party size");
        String[] movies = {"Life", "Logan"};
        int movie = askQuestion("\t>> Enter movie name", movies, false);
        String movieString = movies[movie];
        Customer customer = new Customer(name, movieString, partySize);
        boolean hasChild = askPolarQuestion(
            "\t>> Is a child 11 or younger in this party?");
        Line line = theater.enqueueCustomer(customer, hasChild);
        System.out.println(
            "\nCustomer " + name + " is in " + line.getName() + " ticket line.");
    }

    /**
     * Serve a customer waiting in the theater line.
     * If the given theater has not designated a line to serve next, the user
     * will be prompted to select which line they would like to serve first.
     * The customer in that line will be served by the given theater.
     *
     * @param theater the theater that will serve its customer.
     */
    public static void buyTickets(Theater theater) {
        if (theater.getNextLine() == -1) 
            determineLineOrder(theater);
        if (! theater.allLinesEmpty())
            serveCustomer(theater);
        else 
            System.out.println("There are no customers waiting in any line.");
    }   

    /**
     * Remove a customer from the theater.
     * The user is prompted for the customer's name. All party members under
     * the entered name will be removed from the theater. All records of the 
     * customer will be removed from the theater.
     *
     * @param theater the theater from which to remove a customer.
     */
    public static void removeCustomer(Theater theater) {
        if (theater.allMoviesEmpty())
            System.out.println(
                "No customers are in the movie theater at this time.");
        else {
            String name = getInput(
                "\t>> Enter customer name to leave Movie Theater");
            try {
                theater.removeFromTheater(name);
                System.out.println(
                    "\nCustomer " + name + " has left the Movie Theater.");
            }
            catch (TheaterException ex) {
                System.out.println("\n" + ex.getMessage());
            }
        }
    }

    /**
     * Display information about the customers waiting in line.
     * Displays the customers in each of the given theater's lines.
     *
     * @param theater the theater for which to display customer lines.
     */
    public static void displayCustomerLine(Theater theater) {
        int numLines = theater.getNumberOfLines();
        for (int i = 1; i <= numLines; i++)
        {
            // The (i % numLines) parameter is used to list lines starting from 
            // line 1 and end with the express line.
            System.out.println(theater.getLineInfo(i % numLines));
        }
    }

    /**
     * Displays an auditorium's seating chart.
     * Displays the given theater's auditorium seating chart for the given 
     * movie.
     *
     * @param theater the theater for which to display the seating chart.
     * @param movie the movie auditorium for which to display the seating chart.
     */
    public static void displaySeating(Theater theater, String movie) {
        System.out.println(
            "Here's the seating chart for the " + movie + " Movie Theater:");
        System.out.println(theater.getSeatingChart(movie)); 
    }

    /**
     * Displays information about the tickets sold.
     * Displays the number of tickets sold for each movie as well as 
     * the total earnings for the given theater.
     *
     * @param theater the theater for which to display ticket information.
     */
    public static void displayTicketInfo(Theater theater) {
        System.out.println(theater.getNumberOfTicketsSold("Life") 
            + " tickets have been sold for the Life Movie.");
        System.out.println(theater.getNumberOfTicketsSold("Logan") 
            + " ticekts have been sold for the Logan Movie.");
        System.out.println("Total earnings: $" + theater.getTicketSales());
    }

    /*
     * ************************************************************************
     * Helper Methods for Actions
     * ************************************************************************
     */

    /**
     * Asks the user which customer line they would like to serve first.
     * The user may choose the express line or one of the two regular lines.
     *
     * @param theater the theater for which to determine line order.
     */
    public static void determineLineOrder(Theater theater) {
        int nextLine = askQuestion(
            "Which line would you like to serve customers first?", 
            new String[]{"Express", "Reg1", "Reg2"},
            true
        );
        theater.setNextLine(nextLine);
        System.out.println();
    }

    /**
     * Serve the next customer in line.
     * Gets the customer to be served next in the given theater.
     * Tries to seat the customer in their desired movie.
     * If that movie is sold out, tries to find an alternate movie.
     *
     * @param theater the theater from which to serve the customer.
     */
    public static void serveCustomer(Theater theater) {
        Customer customer = theater.getNextCustomer();
        String customerName = customer.getKey();
        String movie = customer.getMovie();
        int partySize = customer.getPartySize();
        System.out.println("Serving customer " + customerName + "...");
        if (theater.hasEnoughSeats(movie, partySize))
            seatCustomer(theater);
        else 
            findAlternateMovie(theater, customer);
    }

    /**
     * Tries to assign a movie to a customer that is not sold out.
     * If a movie with enough seats to accomodate the customer is found, the 
     * user will be asked whether they want to see the alternate movie.
     * If they deny or if all movies are sold out, the customer will be ejected
     * from the theater.
     *
     * @param theater the theater in which to find an alternate movie.
     * @param customer the customer for which to find the movie.
     */
    public static void findAlternateMovie(Theater theater, Customer customer) {
        String movie = theater.findFirstAvailableMovie(customer.getPartySize());
        if (! movie.isEmpty()) {
            System.out.println("Sorry. This movie is sold out.");
            boolean seeOther = askPolarQuestion(
                "Would you like to see the other movie?");
            if (seeOther) {
                customer.setMovie(movie);
                seatCustomer(theater);
            }
            else {
                System.out.println("Good bye!");
                ejectCustomer(theater, customer.getKey());
            }
        }
        else {
            System.out.println("Sorry. Both movies are sold out. Good bye!");
            ejectCustomer(theater, customer.getKey());
        }
    }

    /**
     * Seat the customer into the auditorium playing their desired movie.
     * Seats the next customer in line into their desired movie.
     * Assumes the customer's movie will not be sold out.
     *
     * @param theater the theater in which to seat the customer.
     */ 
    public static void seatCustomer(Theater theater) {
        Customer customer = theater.seatNextCustomer();
        System.out.println("\n"
            + customer.getKey() + ", party of " + customer.getPartySize()
            + ", has been seated in the " + customer.getMovie()
            + " Movie Theater.");
    }

    /**
     * Removes the next customer in line from the theater.
     * The customer to be served next will not view any movie, or purchase 
     * any tickets. All record of them will be removed from the given theater.
     *
     * @param theater the theater from which to remove the customer.
     * @param name the customer's name.
     */
    public static void ejectCustomer(Theater theater, String name) {
        System.out.println("\nCustomer " + name + " has left the Movie Theater.");
        theater.removeFromLine();
    }
}
