import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeAnalysis {

    // ----------------------------------------------------------------
    // Data holder for a single student's result
    // ----------------------------------------------------------------
    public static class StudentResult {
        public final String name;
        public final int score;
        public final String grade;

        public StudentResult(String name, int score, String grade) {
            this.name = name;
            this.score = score;
            this.grade = grade;
        }
    }

    // ----------------------------------------------------------------
    // Data holder for the overall class analysis
    // ----------------------------------------------------------------
    public static class AnalysisResult {
        public final List<StudentResult> students;
        public final List<StudentResult> topStudents;
        public final double classAverage;
        public final int passedCount;
        public final int failedCount;
        public final boolean belowSeventy;

        public AnalysisResult(List<StudentResult> students,
                               List<StudentResult> topStudents,
                               double classAverage,
                               int passedCount,
                               int failedCount,
                               boolean belowSeventy) {
            this.students = students;
            this.topStudents = topStudents;
            this.classAverage = classAverage;
            this.passedCount = passedCount;
            this.failedCount = failedCount;
            this.belowSeventy = belowSeventy;
        }
    }

    // ----------------------------------------------------------------
    // Grading scale lookup
    // ----------------------------------------------------------------
    public static String getLetterGrade(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(
                "Score must be between 0 and 100, got " + score);
        }
        if (score >= 98) return "A+";
        if (score >= 92) return "A";
        if (score >= 87) return "B+";
        if (score >= 81) return "B";
        if (score >= 77) return "C+";
        if (score >= 71) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    // ----------------------------------------------------------------
    // Core analysis
    // ----------------------------------------------------------------
    public static AnalysisResult analyzeGrades(String[] names, int[] scores) {
        if (names.length != scores.length) {
            throw new IllegalArgumentException(
                "names and scores must be the same length (got "
                + names.length + " names and " + scores.length + " scores)");
        }
        if (names.length == 0) {
            throw new IllegalArgumentException(
                "Cannot analyze an empty class (no students provided)");
        }

        // Per-student grades
        List<StudentResult> students = new ArrayList<>();
        int highestScore = Integer.MIN_VALUE;
        long sum = 0;
        int passedCount = 0;
        int failedCount = 0;

        for (int i = 0; i < names.length; i++) {
            int score = scores[i];
            String grade = getLetterGrade(score);
            students.add(new StudentResult(names[i], score, grade));

            if (score > highestScore) {
                highestScore = score;
            }
            sum += score;
            if (score >= 60) {
                passedCount++;
            } else {
                failedCount++;
            }
        }

        // Top performer(s) - handle ties
        List<StudentResult> topStudents = new ArrayList<>();
        for (StudentResult s : students) {
            if (s.score == highestScore) {
                topStudents.add(s);
            }
        }

        double classAverage = (double) sum / scores.length;
        boolean belowSeventy = classAverage < 70;

        return new AnalysisResult(
            students, topStudents, classAverage, passedCount, failedCount, belowSeventy
        );
    }

    // ----------------------------------------------------------------
    // Pretty-print the report
    // ----------------------------------------------------------------
    public static void printReport(AnalysisResult results) {
        System.out.println("==================================================");
        System.out.printf("%35s%n","GRADE ANALYSIS REPORT");
        System.out.println("==================================================");

        System.out.println("\nIndividual Grades:");
        System.out.printf("  %-15s | %-6s | %-5s%n", "Name", "Score", "Grade");
        System.out.println("  " + "-".repeat(32));
        for (StudentResult s : results.students) {
            System.out.printf("  %-15s | %3d    |  %-2s  %n", s.name, s.score, s.grade);
        }

        System.out.println("\nTop Performer(s):");
        System.out.println("  ---------------------------");
        System.out.printf("  %-15s | %-5s%n", "Name", "Score");
        System.out.println("  ---------------------------");

        for (StudentResult s : results.topStudents) {
            System.out.printf("  %-15s | %3d  %n", s.name, s.score);
        }

        System.out.println("  ---------------------------");

       System.out.println("\nClass Summary:");
       System.out.println("  -----------------------------------");
       System.out.printf("  %-25s | %5.2f%n", "Class Average", results.classAverage);
       System.out.printf("  %-25s | %5d%n", "Passed", results.passedCount);
       System.out.printf("  %-25s | %5d%n", "Did Not Pass", results.failedCount);
       System.out.printf("  %-25s | %5s%n", "Average Below 70?", results.belowSeventy ? "Yes" : "No");
       System.out.println("  -----------------------------------");

       System.out.println("==================================================");
    }

    // ----------------------------------------------------------------
    // Interactive input via Scanner
    // ----------------------------------------------------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==================================================");
        System.out.println("        WELCOME TO THE GRADE ANALYSIS SYSTEM!");
        System.out.println("==================================================");
        System.out.println("This program analyzes student grades and");
        System.out.println("generates a class performance report.");
        System.out.println();

        // Ask user if they want to start
        String choice;
        while (true) {
            System.out.print("Do you want to start? (Y/N): ");
            choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("Y")) {
                break; // continue program
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println("Thank you for using the Grade Analysis System.");
                scanner.close();
                return; // end program
            } else {
                System.out.println("Please enter Y or N.");
        }
    }

    System.out.print("How many students? ");
    int count = readIntInRange(scanner, 1, Integer.MAX_VALUE);

        String[] names = new String[count];
        int[] scores = new int[count];

        for (int i = 0; i < count; i++) {
            System.out.println("\nStudent " + (i + 1) + ":");

            System.out.print("  Name: ");
            String name = scanner.nextLine().trim();
            while (name.isEmpty() || containsDigit(name)) {
                if (name.isEmpty()) {
                    System.out.print("  Name cannot be empty. Try again. ");
                } else {
                    System.out.print("  Name cannot contain numbers. Try again. ");
                }
                
                System.out.print("\n  Name: ");
                name = scanner.nextLine().trim();
            }
            names[i] = name;

            System.out.print("  Score (0-100): ");
            scores[i] = readIntInRange(scanner, 0, 100);
        }

        AnalysisResult results = analyzeGrades(names, scores);
        System.out.println();
        printReport(results);

        scanner.close();
    }

    // ----------------------------------------------------------------
    // Helper: checks whether a string contains any digit characters
    // ----------------------------------------------------------------
    private static boolean containsDigit(String text) {
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    // ----------------------------------------------------------------
    // Helper: reads an integer within [min, max], re-prompting on
    // invalid or out-of-range input
    // ----------------------------------------------------------------
    private static int readIntInRange(Scanner scanner, int min, int max) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.print("  Please enter a number between "
                        + min + " and " + max + ": ");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.print("  Please enter a valid whole number: ");
            }
        }
    }
}