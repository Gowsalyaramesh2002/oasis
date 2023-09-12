import java.util.*;

class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Reservation {
    String trainNumber;
    String trainName;
    String classType;
    String dateOfJourney;
    String from;
    String to;
    String pnrNumber;

    public Reservation(String trainNumber, String trainName, String classType, String dateOfJourney, String from, String to) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    public String getPNRNumber() {
        return pnrNumber;
    }

    // Additional methods for reservation management can be added here
}

public class OnlineReservationSystem {
    private static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Simulate user authentication
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);

        if (authenticateUser(user)) {
            System.out.println("Login successful!");

            // Main menu
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Make a reservation");
                System.out.println("2. Cancel a reservation");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        cancelReservation();
                        break;
                    case 3:
                        System.out.println("Exiting the Online Reservation System.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Please check your username and password.");
        }
    }

    private static boolean authenticateUser(User user) {
        // Implement your authentication logic here (e.g., check against a database)
        // For simplicity, we are using hardcoded values
        return user.getUsername().equals("admin") && user.getPassword().equals("password");
    }

    private static void makeReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reservation form - Enter details:");

        System.out.print("Enter train number: ");
        String trainNumber = scanner.nextLine();

        System.out.print("Enter class type: ");
        String classType = scanner.nextLine();

        System.out.print("Enter date of journey: ");
        String dateOfJourney = scanner.nextLine();

        System.out.print("Enter departure from: ");
        String from = scanner.nextLine();

        System.out.print("Enter destination to: ");
        String to = scanner.nextLine();

        // For simplicity, we assume that the train name is automatically fetched based on the train number
        // In a real application, you might fetch this information from a database or API
        String trainName = getTrainName(trainNumber);

        // Create a Reservation object
        Reservation reservation = new Reservation(trainNumber, trainName, classType, dateOfJourney, from, to);

        // Save the reservation
        reservation.pnrNumber = generatePNR(trainNumber, dateOfJourney); // Generate and assign PNR number
        reservations.add(reservation);

        System.out.println("Reservation successfully made!");
        System.out.println("Your PNR number is: " + reservation.getPNRNumber());
    }

    private static String getTrainName(String trainNumber) {
        // Simulate fetching train name from a database or API
        // In a real application, you would implement logic to retrieve this information
        // For simplicity, we return a hardcoded value here
        if (trainNumber.equals("12345")) {
            return "Sample Train";
        } else {
            return "Unknown Train";
        }
    }

    private static void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cancellation form - Enter PNR number:");

        // Prompt the user for the PNR number
        System.out.print("Enter PNR number: ");
        String pnrNumber = scanner.nextLine();

        // Retrieve the reservation associated with the PNR number
        Reservation reservationToCancel = getReservationByPnr(pnrNumber);

        if (reservationToCancel != null) {
            // Display reservation details
            System.out.println("\nReservation details:");
            System.out.println("Train Number: " + reservationToCancel.trainNumber);
            System.out.println("Train Name: " + reservationToCancel.trainName);
            System.out.println("Class Type: " + reservationToCancel.classType);
            System.out.println("Date of Journey: " + reservationToCancel.dateOfJourney);
            System.out.println("From: " + reservationToCancel.from);
            System.out.println("To: " + reservationToCancel.to);

            // Ask for confirmation
            System.out.print("\nDo you want to cancel this reservation? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                // Cancel the reservation
                cancelReservationByPnr(pnrNumber);
                System.out.println("Reservation canceled successfully.");
            } else {
                System.out.println("Reservation not canceled.");
            }
        } else {
            System.out.println("Reservation with PNR number " + pnrNumber + " not found.");
        }
    }

    private static Reservation getReservationByPnr(String pnrNumber) {
        // Search for the reservation with the given PNR number
        for (Reservation reservation : reservations) {
            if (pnrNumber.equals(reservation.getPNRNumber())) {
                return reservation;
            }
        }
        return null; // Reservation not found
    }

    private static void cancelReservationByPnr(String pnrNumber) {
        // Remove the reservation with the given PNR number
        Reservation reservationToRemove = getReservationByPnr(pnrNumber);
        if (reservationToRemove != null) {
            reservations.remove(reservationToRemove);
        }
    }

    // Generate a PNR number based on reservation details
    private static String generatePNR(String trainNumber, String dateOfJourney) {
        // In a real application, you would generate a unique PNR based on reservation details
        // For simplicity, we use a random number here
        Random rand = new Random();
        int randomNumber = rand.nextInt(90000) + 10000; // Generate a 5-digit random number
        return trainNumber + "-" + dateOfJourney + "-" + randomNumber;
    }
}
