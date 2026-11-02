import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Console application for managing student grades.
 * Compile: javac Student.java GradeManager.java
 * Run:     java GradeManager
 */
public class GradeManager {
    private final ArrayList<Student> students = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new GradeManager().start();
    }

    private void start() {
        System.out.println("==============================================");
        System.out.println("       STUDENT GRADE MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> recordMarks();
                case 3 -> viewAllStudents();
                case 4 -> generateReportCard();
                case 5 -> calculateClassStatistics();
                case 6 -> updateStudentName();
                case 7 -> showGradingScale();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using Student Grade Management System.");
                }
                default -> System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\\n--------------- MAIN MENU ----------------");
        System.out.println("1. Add Student");
        System.out.println("2. Record / Update Course Marks");
        System.out.println("3. View All Students");
        System.out.println("4. Generate Student Report Card");
        System.out.println("5. Class Average and Median");
        System.out.println("6. Update Student Name");
        System.out.println("7. View Grading Scale");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------------");
    }

    private void addStudent() {
        System.out.println("\\n--- Add Student ---");
        String id = readNonEmpty("Enter student ID: ");

        if (findStudent(id) != null) {
            System.out.println("A student with this ID already exists.");
            return;
        }

        String name = readNonEmpty("Enter student name: ");
        students.add(new Student(id, name));
        System.out.println("Student registered successfully.");
    }

    private void recordMarks() {
        System.out.println("\\n--- Record / Update Marks ---");
        String id = readNonEmpty("Enter student ID: ");
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found. Register the student first.");
            return;
        }

        String course = readNonEmpty("Enter course / subject name: ");
        double score = readDouble("Enter marks (0-100): ", 0, 100);
        boolean updating = student.getGrades().containsKey(course);

        student.recordGrade(course, score);
        System.out.println(updating ? "Marks updated successfully." : "Marks recorded successfully.");
        System.out.printf("Current CGPA: %.2f / 10.00%n", student.getCGPA());
    }

    private void viewAllStudents() {
        System.out.println("\\n--- Student List ---");
        if (students.isEmpty()) {
            System.out.println("No students registered yet.");
            return;
        }

        System.out.printf("%-12s %-24s %-12s %-10s %-12s%n",
                "STUDENT ID", "NAME", "COURSES", "CGPA", "RESULT");
        System.out.println("-----------------------------------------------------------------------");

        for (Student student : students) {
            System.out.printf("%-12s %-24s %-12d %-10.2f %-12s%n",
                    student.getId(), shorten(student.getName(), 23),
                    student.getGrades().size(), student.getCGPA(),
                    student.getOverallResult());
        }
    }

    private void generateReportCard() {
        System.out.println("\\n--- Student Report Card ---");
        String id = readNonEmpty("Enter student ID: ");
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\\n==================================================");
        System.out.println("                 ACADEMIC REPORT CARD");
        System.out.println("==================================================");
        System.out.println("Student ID   : " + student.getId());
        System.out.println("Student Name : " + student.getName());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-25s %-12s %-12s%n", "COURSE", "MARKS", "GRADE POINT");
        System.out.println("--------------------------------------------------");

        Map<String, Double> grades = student.getGrades();
        if (grades.isEmpty()) {
            System.out.println("No course marks recorded.");
        } else {
            for (Map.Entry<String, Double> entry : grades.entrySet()) {
                System.out.printf("%-25s %-12.2f %-12.2f%n",
                        shorten(entry.getKey(), 24), entry.getValue(),
                        Student.scoreToGradePoint(entry.getValue()));
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Average Marks : %.2f%n", student.getAverageScore());
        System.out.printf("CGPA          : %.2f / 10.00%n", student.getCGPA());
        System.out.println("Result        : " + student.getOverallResult());
        System.out.println("==================================================");
    }

    /**
     * Class statistics are calculated over every individual course mark
     * entered for every student, not over student averages.
     */
    private void calculateClassStatistics() {
        System.out.println("\\n--- Class Statistics ---");
        List<Double> allMarks = new ArrayList<>();

        for (Student student : students) {
            allMarks.addAll(student.getGrades().values());
        }

        if (allMarks.isEmpty()) {
            System.out.println("No marks available to calculate statistics.");
            return;
        }

        double total = 0.0;
        for (double mark : allMarks) total += mark;
        double average = total / allMarks.size();

        allMarks.sort(Comparator.naturalOrder());
        int middle = allMarks.size() / 2;
        double median;

        if (allMarks.size() % 2 == 0) {
            median = (allMarks.get(middle - 1) + allMarks.get(middle)) / 2.0;
        } else {
            median = allMarks.get(middle);
        }

        System.out.println("Number of recorded marks : " + allMarks.size());
        System.out.printf("Class Average            : %.2f%n", average);
        System.out.printf("Class Median             : %.2f%n", median);
    }

    private void updateStudentName() {
        System.out.println("\\n--- Update Student Name ---");
        String id = readNonEmpty("Enter student ID: ");
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String newName = readNonEmpty("Enter new name: ");
        student.setName(newName);
        System.out.println("Student name updated successfully.");
    }

    private void showGradingScale() {
        System.out.println("\\n--- Grading Scale (10-point) ---");
        System.out.println("90 - 100  : 10.0");
        System.out.println("80 - 89.99:  9.0");
        System.out.println("70 - 79.99:  8.0");
        System.out.println("60 - 69.99:  7.0");
        System.out.println("50 - 59.99:  6.0");
        System.out.println("40 - 49.99:  5.0");
        System.out.println("Below 40  :  0.0 (Fail)");
        System.out.println("\\nCGPA = average of grade points across recorded courses.");
    }

    private Student findStudent(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id.trim())) return student;
        }
        return null;
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty. Try again.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) {
                // Show the validation message below.
            }
            System.out.printf("Please enter a whole number from %d to %d.%n", min, max);
        }
    }

    private double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (Double.isFinite(value) && value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) {
                // Show the validation message below.
            }
            System.out.printf("Please enter a valid number from %.0f to %.0f.%n", min, max);
        }
    }

    private String shorten(String value, int maxLength) {
        if (value.length() <= maxLength) return value;
        return value.substring(0, maxLength - 3) + "...";
    }
}
