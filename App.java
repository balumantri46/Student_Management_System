import java.sql.*;
import java.util.*;

class initialize {
    Scanner sc = new Scanner(System.in);
    String url, user, pass;
    Connection con;
    Statement stmt;

    public initialize() throws Exception {
        this.url = "jdbc:mysql://localhost:3306/sms";
        this.user = "root";
        this.pass = "Balu@448";
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection(url, user, pass);
        this.stmt = con.createStatement();
    }

    public void reg() throws SQLException {
        System.out.print("email:");
        String email = sc.nextLine();
        System.out.print("full name:");
        String fname = sc.nextLine();
        System.out.print("Username:");
        String name = sc.nextLine();
        System.out.print("Password:");
        String pass = sc.nextLine();
        System.out.print("Roll-Number:");
        int uid = sc.nextInt();
        String q = "insert into logs(user_id,username,password,full_name,email) values (?,?,?,?,?);";
        PreparedStatement p_stmt = con.prepareStatement(q);
        p_stmt.setInt(1, uid);
        p_stmt.setString(2, name);
        p_stmt.setString(3, pass);
        p_stmt.setString(4, fname);
        p_stmt.setString(5, email);
        p_stmt.executeUpdate();
        stmt.executeUpdate("insert into stu_details(user_id,full_name,email) values("+uid+",'"+fname+"','"+email+"');");
        System.out.println("Account created!, login now.");


    }

    public boolean login() throws SQLException {
        System.out.println("enter login details.");
        System.out.print("username:");
        String u_name = sc.nextLine();
        System.out.print("password:");
        String pass = sc.nextLine();
        ResultSet rs = stmt.executeQuery("select username,password from logs where username='" + u_name + "'");
        if (rs.next()) {
            String name = rs.getString(1);
            String password = rs.getString(2);
            if (Objects.equals(name, u_name) && Objects.equals(password, pass)) {
                System.out.println("Welcome Mr/Ms. " + name);
                return true;
            } else {
                System.out.println("not matched.");
                return false;
            }
        } else {
            System.out.println("No user present please signup.");
            return false;
        }
    }

    public void view() throws Exception {
        System.out.println("enter Roll-Number:");
        int id = sc.nextInt();
        ResultSet rs = stmt.executeQuery("select * from stu_details where user_id='" + id + "';");
        if (rs.next()) {
            System.out.println("Roll number is " + rs.getInt(1));
            System.out.println("fullName is " + rs.getString(2));
            System.out.println("date of birth is " + rs.getDate(3));
            System.out.println("gender is " + rs.getString(5));
            System.out.println("is studying "+rs.getString(9));
            System.out.println("branch/standard studying is " + rs.getString(4));
            System.out.println("state is " + rs.getString(6));
            System.out.println("city is " + rs.getString(7));
            System.out.println("email is " + rs.getString(8));
        }
        else{
            System.out.println("Empty set. no data present");
        }
    }
    public void insert() throws SQLException {
        System.out.println("Enter details, leave blank if u don't want to fill.");
        System.out.print("School/college:");
        String s_c = sc.nextLine();
        System.out.print("course/standard you are studying:");
        String br = sc.nextLine();
        System.out.print("State:");
        String st = sc.nextLine();
        System.out.print("City:");
        String ct = sc.nextLine();
        System.out.print("Gender(M/F):");
        String g = sc.nextLine();
        System.out.print("date of birth(YYYY/MM/DD):");
        String dob = sc.nextLine();
        System.out.print("Roll-number:");
        int id = sc.nextInt();
        stmt.executeUpdate("update stu_details set dob='" + dob + "',branch='" + br + "',gender='" + g + "',state='" + st + "',city='" + ct + "',s_c='"+s_c+"' where user_id=" + id + ";");
        System.out.println("student data inserted.");
    }
    public void delete() throws SQLException{
        System.out.print("Roll-Number to delete:");
        int uid = sc.nextInt();
        stmt.executeUpdate("delete from stu_details where user_id="+uid+";");
        stmt.executeUpdate("delete from logs where user_id="+uid+";");
        System.out.println("Deleted.");
    }

    public void exit() throws Exception {
        System.out.println("exiting.....");
        sc.close();
        stmt.close();
        con.close();
    }
}

/**
 * <ul>
 *     <li> main class
 *     </li>
 *
 * </ul>
 */
public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        initialize in = new initialize();
        boolean isit = false;
        System.out.println("welcome to the Student Management System.");
        System.out.println("please signIn/signUp");
        System.out.println("Do you have an account(y/n)");
        char ch = sc.next().charAt(0);
        if (ch == 'n') {
            System.out.println("let's register you.");
            in.reg();
        } else if (ch == 'y') {
            isit = in.login();
        } else {
            System.out.println("wrong choice.");
        }
        if (isit) {
            System.out.println("actions are");
            System.out.println("1.add/update student data");
            System.out.println("2.view student data.");
            System.out.println("3.Delete from database.");
            System.out.println("4.Exit from the system.");
            System.out.println("enter your Choice");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    in.insert();
                    break;
                case 2:
                    in.view();
                    break;
                case 3:
                    in.delete();
                    break;
                case 4:
                    in.exit();
                    break;
                default:
                    System.out.println("wrong choice.");
                    break;
            }
        }
    }
}

