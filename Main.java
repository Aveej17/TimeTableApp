import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Management management = new Management();
        management.setup();
        LeaveManager leaveManager = new LeaveManager();
        AttendanceReport attendanceReport = new AttendanceReport();



        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Timetable");
            System.out.println("2. View Teacher Timetable");
            System.out.println("3. Manage Leave");
            System.out.println("4. Generate Attendance Report");
            System.out.println("5. View Timetable");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    management.createTimeTable();
                    break;
                case 2:
                    System.out.print("Enter teacher name: ");
                    String teacherName = scanner.nextLine();
                    management.displayTeacherTimeTable(teacherName);
                    break;
                case 3:
                    System.out.print("Enter teacher name: ");
                    String leaveTeacher = scanner.nextLine();
                    System.out.println("Enter the day of week Monday means 1 ... Friday means 5");
                    int dayOfWeek = scanner.nextInt();
                    leaveManager.handleLeave(leaveTeacher,dayOfWeek, management.getTimeTable(), management.getTeachers());
                    break;
                case 4:
                    attendanceReport.generateReport(management.getTeachers());
                    break;
                case 6:
                    System.exit(0);
                    break;
                case 5:
                    management.displayTimeTable();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
