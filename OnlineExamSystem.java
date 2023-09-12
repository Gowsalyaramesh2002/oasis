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

class Question {
    String question;
    String[] options;
    int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}

class Exam {
    List<Question> questions;
    int currentQuestionIndex;
    int score;
    int timeRemaining;

    public Exam(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timeRemaining = 60;
    }

    public void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        System.out.println(currentQuestion.question);

        for (int i = 0; i < currentQuestion.options.length; i++) {
            System.out.println((i + 1) + ". " + currentQuestion.options[i]);
        }
    }

    public void submitAnswer(int selectedOption) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (selectedOption == currentQuestion.correctOption) {
            score++;
        }
        currentQuestionIndex++;
        timeRemaining = 60;
    }
}

class UserDatabase {
    private static Map<String, User> users = new HashMap<>();

    static {
        // Adding sample users to the database (replace with your actual user data)
        users.put("admin", new User("admin", "password123"));
        users.put("user1", new User("user1", "pass456"));
        // Add more users as needed
    }

    public static User getUserByUsername(String username) {
        return users.get(username);
    }
}


public class OnlineExamSystem {
    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 2));
        questions.add(new Question("Which planet is known as the Red Planet?", new String[]{"Mars", "Earth", "Jupiter", "Venus"}, 0));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);

        // Simulate user authentication (replace with your actual authentication logic)
        boolean isAuthenticated = authenticateUser(user);

        if (isAuthenticated) {
            Exam exam = new Exam(questions);

            while (exam.currentQuestionIndex < questions.size()) {
                exam.displayQuestion();
                System.out.print("Enter your answer (1-" + questions.get(0).options.length + "): ");
                int selectedOption = scanner.nextInt();

                // Validate the selected option
                if (selectedOption >= 1 && selectedOption <= questions.get(0).options.length) {
                    exam.submitAnswer(selectedOption);
                } else {
                    System.out.println("Invalid option. Please select a valid option.");
                }
            }

            System.out.println("Exam finished! Your score: " + exam.score);
        } else {
            System.out.println("Authentication failed. Please check your username and password.");
        }
    }

    private static boolean authenticateUser(User user) {
        User storedUser = UserDatabase.getUserByUsername(user.getUsername());

        if (storedUser != null) {
            // Compare the provided password with the stored password (you may want to hash and salt the passwords in practice)
            return storedUser.getPassword().equals(user.getPassword());
        }

        return false;
    }
}
