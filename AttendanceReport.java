import java.util.*;

public class AttendanceReport {
    public void generateReport(List<Teacher> teachers) {
        teachers.sort((t1, t2) -> Integer.compare(t2.periodsHandled, t1.periodsHandled));
        System.out.println("Attendance Report:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.name + ": " + teacher.periodsHandled + " periods");
        }
    }
}
