import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents one student and the marks recorded for each course.
 * Encapsulation: fields are private and accessed through methods.
 */
public class Student {
    private final String id;
    private String name;
    private final Map<String, Double> grades;

    public Student(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.grades = new LinkedHashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        this.name = name.trim();
    }

    /** Returns a defensive copy so callers cannot modify the internal map. */
    public Map<String, Double> getGrades() {
        return new LinkedHashMap<>(grades);
    }

    public void recordGrade(String course, double score) {
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty.");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        grades.put(course.trim(), score);
    }

    public boolean hasGrades() {
        return !grades.isEmpty();
    }

    public double getAverageScore() {
        if (grades.isEmpty()) return 0.0;
        double total = 0.0;
        for (double score : grades.values()) total += score;
        return total / grades.size();
    }

    /**
     * Converts a percentage score to a 10-point grade point.
     * 90-100 = 10, 80-89.99 = 9, ... 40-49.99 = 5, below 40 = 0.
     */
    public static double scoreToGradePoint(double score) {
        if (score >= 90) return 10.0;
        if (score >= 80) return 9.0;
        if (score >= 70) return 8.0;
        if (score >= 60) return 7.0;
        if (score >= 50) return 6.0;
        if (score >= 40) return 5.0;
        return 0.0;
    }

    /** CGPA is the unweighted average of the grade points for recorded courses. */
    public double getCGPA() {
        if (grades.isEmpty()) return 0.0;
        double totalPoints = 0.0;
        for (double score : grades.values()) {
            totalPoints += scoreToGradePoint(score);
        }
        return totalPoints / grades.size();
    }

    public String getOverallResult() {
        if (grades.isEmpty()) return "NO MARKS";
        for (double score : grades.values()) {
            if (score < 40) return "FAIL";
        }
        return "PASS";
    }
}
