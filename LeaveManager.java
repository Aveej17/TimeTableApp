import java.util.*;

public class LeaveManager {
    public void handleLeave(String teacherName, int dayIndex, List<List<Period>> timeTable, List<Teacher> teachers) {
        dayIndex = dayIndex-1;
        if (dayIndex < 0 || dayIndex >= timeTable.size()) {
            System.out.println("Invalid day selected!");
            return;
        }

        List<Period> dailySchedule = timeTable.get(dayIndex);

        System.out.println("Handling leave for teacher: " + teacherName + " on Day " + (dayIndex + 1));

        for (int i = 0; i < dailySchedule.size(); i++) {
            Period period = dailySchedule.get(i);

            // If the teacher is assigned to this period
            if (period.teacher.equals(teacherName)) {
                // Try to find a backup teacher
                Teacher backup = findBackupTeacher(period.subject, teachers);
                if (backup != null && !Objects.equals(backup.getName(), teacherName)) {
                    System.out.println("Assigning backup teacher: " + backup.name + " for subject: " + period.subject);
                    // Decrease periodsHandled for the teacher who is absent and increase for backup
                    updateTeacherPeriods(teacherName, -1, teachers);
                    updateTeacherPeriods(backup.name, 1, teachers);
                    period.teacher = backup.name;
                } else {
                    // No backup available; extend previous or next period
                    if (i > 0) {
                        // If a previous period exists, extend the previous teacher's period
                        String previousTeacherName = dailySchedule.get(i - 1).teacher;
                        String previousSubject = dailySchedule.get(i - 1).subject;
                        System.out.println("Extending previous period's teacher: " + previousTeacherName + " with subject: " + previousSubject);

                        // Update periodsHandled for the previous teacher (decrease it)
                        updateTeacherPeriods(period.teacher, -1, teachers);

                        // Assign the previous teacher to the current period
                        period.teacher = previousTeacherName;
                        period.subject = previousSubject; // Update the subject as well



                        // Update periodsHandled for the new teacher (increase it)
                        updateTeacherPeriods(period.teacher, 1, teachers);
                    } else if (i < dailySchedule.size() - 1) {
                        // If a next period exists, extend the next teacher's period
                        String nextTeacherName = dailySchedule.get(i + 1).teacher;
                        String nextSubject = dailySchedule.get(i + 1).subject;
                        System.out.println("Extending next period's teacher: " + nextTeacherName + " with subject: " + nextSubject);

                        // Update periodsHandled for the next teacher (decrease it)
                        updateTeacherPeriods(period.teacher, -1, teachers);

                        // Assign the next teacher to the current period
                        period.teacher = nextTeacherName;
                        period.subject = nextSubject; // Update the subject as well



                        // Update periodsHandled for the new teacher (increase it)
                        updateTeacherPeriods(period.teacher, 1, teachers);
                    }
                    else {
                        // No periods to extend. Marking period as Free.
                        System.out.println("No periods to extend. Marking period as Free.");
                        period.teacher = "Free - None";
                    }
                }
            }
        }
    }

    // Helper method to update teacher's periodsHandled count
    public static void updateTeacherPeriods(String teacherName, int increment, List<Teacher> teachers) {
        // Loop through the list to find the teacher by name
        for (Teacher teacher : teachers) {
            if (teacher.getName().equals(teacherName)) {
                // Update the periodsHandled
                teacher.periodsHandled += increment;
                System.out.println("Updated " + teacherName + " periodsHandled to: " + teacher.periodsHandled);
                return;  // Exit once the teacher is found and updated
            }
        }

        // If no teacher is found with the given name
        System.out.println("Teacher " + teacherName + " not found.");
    }





    private Teacher findBackupTeacher(String subjectName, List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            if (teacher.subject.equals(subjectName) && !teacher.isPrimary) {
                return teacher;
            }
        }
        return null;
    }
}

