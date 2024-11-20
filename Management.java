import java.util.*;

public class Management {
    private final List<Subject> subjects;
    private final List<Teacher> teachers;
    private final List<List<Period>> timeTable;
    private boolean isTimetableCreated;

    public Management() {
        subjects = new ArrayList<>();
        teachers = new ArrayList<>();
        timeTable = new ArrayList<>();
        isTimetableCreated = false; // Initializing flag as false
    }

    public void setup(){
        // Add subjects
        addSubject("Math", true);
        addSubject("Physics", true);
        addSubject("Chemistry", true);
        addSubject("Biology", true);
        addSubject("History", false);
        addSubject("Geography", false);
        addSubject("Civics", false);

        // Add teachers
        addTeacher("T1", true, "Math");
        addTeacher("T2", true, "Physics");
        addTeacher("T3", true, "Chemistry");
        addTeacher("T4", true, "Biology");
        addTeacher("T5", true, "History");
        addTeacher("T6", true, "Geography");
        addTeacher("T7", true, "Civics");
        addTeacher("T8", false, "Math");
        addTeacher("T9", false, "Physics");
        addTeacher("T10", false, "Chemistry");
        addTeacher("T11", false, "Biology");
    }
    public void addSubject(String name, boolean isCore) {
        subjects.add(new Subject(name, isCore));
    }

    public void addTeacher(String name, boolean isPrimary, String subject) {
        teachers.add(new Teacher(name, isPrimary, subject));
    }
    public void createTimeTable() {
        if (isTimetableCreated) {
            System.out.println("Timetable already created. Cannot create again.");
            return; // Skip the creation process if the timetable is already created
        }

        int totalDays = 5, totalPeriodsPerDay = 5;
        if(subjects.size()<totalPeriodsPerDay){
            System.out.println("Not enough periods to generate time table based on given constraints");
            return;
        }
        // Initialize subject frequencies (core: 4, non-core: 3)
        Map<String, Integer> subjectFrequency = new HashMap<>();
        for (Subject subject : subjects) {
            subjectFrequency.put(subject.name, subject.isCore ? 4 : 3);
        }

        // Prepare a queue of subjects for round-robin allocation
        Queue<Subject> subjectQueue = new LinkedList<>();
        for (Subject subject : subjects) {
            subjectQueue.add(subject);
        }

        for (int day = 0; day < totalDays; day++) {
            List<Period> dailyTimeTable = new ArrayList<>();
            Set<String> usedSubjects = new HashSet<>();

            while (dailyTimeTable.size() < totalPeriodsPerDay) {
                Subject currentSubject = subjectQueue.poll(); // Get the next subject in the queue
                if(currentSubject==null){
                    return;
                }
                // Check if the subject can be added (not used today and has remaining frequency)
//                assert currentSubject != null;
                if (subjectFrequency.get(currentSubject.name) > 0 && !usedSubjects.contains(currentSubject.name)) {
                    Teacher teacher = findAvailableTeacher(currentSubject.name);
                    if (teacher != null) {
                        dailyTimeTable.add(new Period(currentSubject.name, teacher.name));
                        teacher.periodsHandled++;
                        subjectFrequency.put(currentSubject.name, subjectFrequency.get(currentSubject.name) - 1);
                        usedSubjects.add(currentSubject.name);
                    }
                }

                // Re-add the subject to the queue if it still has remaining frequency
                if (subjectFrequency.get(currentSubject.name) > 0) {
                    subjectQueue.add(currentSubject);
                }

                // Break the loop if the queue is empty (to avoid infinite loops in edge cases)
                if (subjectQueue.isEmpty()) {
                    break;
                }
            }

            // Add the completed day's timetable
            timeTable.add(dailyTimeTable);
        }

        // Verify all periods are filled; throw an error if not
        for (List<Period> dailySchedule : timeTable) {
            if (dailySchedule.size() < totalPeriodsPerDay) {
                throw new RuntimeException("Failed to fill all periods. Check subject frequencies and constraints.");
            }
        }

        isTimetableCreated = true; // Set the flag to true after the timetable is created
        System.out.println("Timetable created successfully.");
    }





    private Teacher findAvailableTeacher(String subjectName) {
        for (Teacher teacher : teachers) {
            if (teacher.subject.equals(subjectName) && teacher.isPrimary) {
                return teacher;
            }
        }
        return null;
    }

    public void displayTimeTable() {
        if(!isTimetableCreated){
            System.out.println("Time Table is not yet Created");
            return;
        }
        for (int day = 0; day < timeTable.size(); day++) {
            System.out.println("Day " + (day + 1) + ": " + timeTable.get(day));
        }
    }

    public List<List<Period>> getTimeTable() {
        return timeTable;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void displayTeacherTimeTable(String teacherName) {
        if(!isTimetableCreated){
            System.out.println("Time Table is not yet Created");
            return;
        }
        int totalDays = 5;
        int totalPeriodsPerDay = 5;

        System.out.println("Timetable for Teacher: " + teacherName);

        for (int day = 0; day < totalDays; day++) {
            System.out.print("Day " + (day + 1) + ": ");
            List<Period> dailyTimeTable = timeTable.get(day);

            for (int period = 0; period < totalPeriodsPerDay; period++) {
                Period periodInfo = dailyTimeTable.get(period);

                // Check if the teacher is assigned to this period
                if (periodInfo.teacher.equals(teacherName)) {
                    System.out.print("[" + periodInfo.subject + " - " + teacherName + "] ");
                } else {
                    // Display 'Free' for unassigned periods
                    System.out.print("[Free - None] ");
                }
            }
            System.out.println();
        }
    }

}
