# Student Grade Management System (Task 1)

## Requirements
- Java JDK 17 or later
- Command Prompt / Terminal, IntelliJ IDEA, Eclipse, or VS Code

## Project files
- `Student.java` — student model, encapsulated fields, course marks, average and CGPA.
- `GradeManager.java` — menu-driven console application and class statistics.

## Run using Command Prompt
1. Extract this ZIP file.
2. Open Command Prompt in the extracted `StudentGradeManagementSystem` folder.
3. Compile:
   ```bash
   javac Student.java GradeManager.java
   ```
4. Run:
   ```bash
   java GradeManager
   ```

## Menu
1. Add Student
2. Record / Update Course Marks
3. View All Students
4. Generate Student Report Card
5. Class Average and Median
6. Update Student Name
7. View Grading Scale
0. Exit

## GPA calculation used
This project uses a simple 10-point scale:
- 90–100 = 10.0
- 80–89.99 = 9.0
- 70–79.99 = 8.0
- 60–69.99 = 7.0
- 50–59.99 = 6.0
- 40–49.99 = 5.0
- Below 40 = 0.0

CGPA is the unweighted average of grade points across the student's recorded courses.
The class average and median are calculated from all individual marks entered for all students.
If your college provides a different official GPA conversion table or course-credit weighting,
replace `Student.scoreToGradePoint()` and the CGPA calculation accordingly.

## OOP concepts demonstrated
- Encapsulation: private fields and public getters/setters.
- Abstraction: grade calculations are exposed through methods.
- Collections: `ArrayList<Student>` and `LinkedHashMap<String, Double>`.
- Validation: duplicate IDs, empty values, and marks outside 0–100 are rejected.
