package Admin;

import FinanceMgr.Payment;
import FinanceMgr.PurchaseRequisition;
import PurchaseMgr.PurchaseOrder;
import SalesMgr.Sales;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.io.FileWriter;
import java.util.regex.Pattern;

public class User {
    public String UserID, Username, Password, FullName, Email, AccType, Phone;
    public int RememberMe;
    public LocalDate DateOfRegis;

    public User(String UserID, String Username, String Password, String FullName,
                String Email, String Phone, String AccType, LocalDate DateOfRegis,
                int RememberMe) {
        this.UserID = UserID;
        this.Username = Username;
        this.Password = Password;
        this.FullName = FullName;
        this.Email = Email;
        this.Phone = Phone;
        this.AccType = AccType;
        this.DateOfRegis = DateOfRegis;
        this.RememberMe = RememberMe;
    }

    public static List<User> ListAllUser(String filename) {
        List<User> allUser = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String UserID = "", Username = "", Password = "", FullName = "", Email = "", AccType = "", Phone = "";
            int RememberMe = 0;
            LocalDate DateOfRegis = null;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        UserID = line.substring(16);
                        break;
                    case 2:
                        Username = line.substring(16);
                        break;
                    case 3:
                        Password = line.substring(16);
                        break;
                    case 4:
                        FullName = line.substring(16);
                        break;
                    case 5:
                        Email = line.substring(16);
                        break;
                    case 6:
                        Phone = line.substring(16);
                        break;
                    case 7:
                        AccType = line.substring(16);
                        break;
                    case 8:
                        DateOfRegis = LocalDate.parse(line.substring(16), Main.df);
                        break;
                    case 9:
                        RememberMe = Integer.parseInt(line.substring(16));
                        break;
                    default:
                        counter = 0;
                        allUser.add(new User(UserID, Username, Password, FullName, Email,
                                Phone, AccType, DateOfRegis, RememberMe));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allUser;
    }

    public static List<User> ListAllUserFromFilter(String filename, String type, String filter, User current_user) {
        List<User> user_list = ListAllUser(filename);
        List<User> type_user_list = new ArrayList<>();
        List<User> filtered_user_list = new ArrayList<>();
        if (type.isEmpty() && filter.isEmpty()) {
            if (current_user != null) {
                user_list.removeIf(user -> Objects.equals(user.UserID, current_user.UserID));
            }
            user_list.removeIf(user -> Objects.equals(user.UserID.substring(2), "0000000000"));
            return user_list;
        }
        for (User user: user_list) {
            if (!Objects.equals(user.UserID, current_user.UserID) &&
                    !Objects.equals(user.UserID.substring(2), "0000000000")) {
                if (Objects.equals(user.AccType, type)) {
                    type_user_list.add(user);
                } else if (type.isEmpty()) {
                    type_user_list.add(user);
                }
            }
        }
        if (filter.isEmpty()) {
            return type_user_list;
        }
        for (User user: type_user_list) {
            if ((user.UserID.toLowerCase().contains(filter.toLowerCase().replace(" ", "")) ||
                    user.Email.toLowerCase().replace(" ", "").contains(filter.toLowerCase().replace(" ", "")) ||
                    user.Phone.contains(filter.toLowerCase().replace(" ", "")) ||
                    user.DateOfRegis.toString().contains(filter.toLowerCase().replace(" ", "")) ||
                    user.Username.toLowerCase().contains(filter.toLowerCase().replace(" ", "")) ||
                    user.FullName.toLowerCase().replace(" ", "").contains(filter.toLowerCase().replace(" ", "")) ||
                    user.AccType.toLowerCase().replace(" ", "").contains(filter.toLowerCase().replace(" ", "")))) {
                filtered_user_list.add(user);
            }
        }
        return filtered_user_list;
    }

    public static User GetUserById(String id, String filename) {
        List<User> user_list = ListAllUser(filename);
        User user_temp = null;
        for (User user: user_list) {
            if (Objects.equals(user.UserID, id)) {
                user_temp = user;
                break;
            }
        }
        return user_temp;
    }

    public static List<User> GetUsersByIds(List<String> ids, String filename) {
        List<User> user_list = new ArrayList<>();
        for (String id: ids) {
            user_list.add(GetUserById(id, filename));
        }
        return user_list;
    }

    public static User GetRememberedUser(String filename) {
        List<User> allUser = ListAllUser(filename);
        User r_user = null;
        for (User user : allUser) {
            if (Objects.equals(user.RememberMe, 1)) {
                r_user = user;
                break;
            }
        }
        return r_user;
    }

    public static void UnrememberAllUser(String filename) {
        List<User> allUser = ListAllUser(filename);
        for (User user : allUser) {
            if (Objects.equals(user.RememberMe, 1)) {
                user.RememberMe = 0;
                break;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : allUser) {
                writer.write("UserID:         " + user.UserID + "\n");
                writer.write("Username:       " + user.Username + "\n");
                writer.write("Password:       " + user.Password + "\n");
                writer.write("FullName:       " + user.FullName + "\n");
                writer.write("Email:          " + user.Email + "\n");
                writer.write("Phone:          " + user.Phone + "\n");
                writer.write("AccType:        " + user.AccType + "\n");
                writer.write("DateOfRegis:    " + user.DateOfRegis.format(Main.df) + "\n");
                writer.write("RememberMe:     " + user.RememberMe + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static String idMaker(String AccType, String filename) {
        List<User> allUser = ListAllUser(filename);
        boolean repeated = false;
        boolean success = false;
        String role = switch (AccType) {
            case "Administrator" -> "AD";
            case "Sales Manager" -> "SM";
            case "Purchase Manager" -> "PM";
            case "Inventory Manager" -> "IM";
            case "Finance Manager" -> "FM";
            default -> "";
        };
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E10);
            while (newNum == 0) {
                newNum = (long) (Math.random() * 1E10);
            }
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 10) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", role, number);
            for (User user : allUser) {
                if (Objects.equals(user.UserID, newId) && Objects.equals(user.AccType, AccType)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }

    public static boolean UsernameChecker(String Username, String filename) {
        List<User> allUser = ListAllUser(filename);
        boolean repeated = false;
        for (User user : allUser) {
            if (Objects.equals(user.Username.toLowerCase(), Username.toLowerCase())) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public static boolean EmailChecker(String Email, String filename) {
        List<User> allUser = ListAllUser(filename);
        boolean repeated = false;
        for (User user : allUser) {
            if (Objects.equals(user.Email, Email)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public static boolean PhoneChecker(String Phone, String filename) {
        List<User> allUser = ListAllUser(filename);
        boolean repeated = false;
        for (User user : allUser) {
            if (Objects.equals(user.Phone, Phone)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public static boolean PasswordChecker(String password) {
        return password.matches(Main.upper_regex) && password.matches(Main.lower_regex) &&
                password.matches(Main.digit_regex) && password.matches(Main.special_regex);
    }

    public static String ValidityChecker(String Username, String Password, String FullName,
                                         String Email, String Phone, String filename) {
//      Sample output: 0X0X00X0X
        String indicator = "";
        if (Username.length() >= 8 && Username.length() <= 36) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (UsernameChecker(Username, filename)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (Password.length() >= 8) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PasswordChecker(Password)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (FullName.length() >= 8 && FullName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (Pattern.compile(Main.email_regex).matcher(Email).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (EmailChecker(Email, filename)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (Pattern.compile(Main.phone_regex).matcher(Phone).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PhoneChecker(Phone, filename)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        return indicator;
    }

    public static String ValidityCheckerWithHistory(String Username, String Password, String FullName,
                                                    String Email, String Phone, String filename, User old) {
//      Sample output: 0X0X00X0X
        String indicator = "";
        if (Username.length() >= 8 && Username.length() <= 36) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (UsernameChecker(Username, filename)) {
            indicator += "O";
        } else {
            if (Username.equals(old.Username)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        if (Password.length() >= 8) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PasswordChecker(Password)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (FullName.length() >= 8 && FullName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (Pattern.compile(Main.email_regex).matcher(Email).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (EmailChecker(Email, filename)) {
            indicator += "O";
        } else {
            if (Email.equals(old.Email)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        if (Pattern.compile(Main.phone_regex).matcher(Phone).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PhoneChecker(Phone, filename)) {
            indicator += "O";
        } else {
            if (Phone.equals(old.Phone)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        return indicator;
    }

    public static void SaveNewUser(User user, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("UserID:         " + user.UserID + "\n");
            writer.write("Username:       " + user.Username + "\n");
            writer.write("Password:       " + user.Password + "\n");
            writer.write("FullName:       " + user.FullName + "\n");
            writer.write("Email:          " + user.Email + "\n");
            writer.write("Phone:          " + user.Phone + "\n");
            writer.write("AccType:        " + user.AccType + "\n");
            writer.write("DateOfRegis:    " + user.DateOfRegis.format(Main.df) + "\n");
            writer.write("RememberMe:     0\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void RemoveUser(String UserID, String filename) {
        List<User> allUser = ListAllUser(filename);
        allUser.removeIf(user -> Objects.equals(user.UserID, UserID));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : allUser) {
                writer.write("UserID:         " + user.UserID + "\n");
                writer.write("Username:       " + user.Username + "\n");
                writer.write("Password:       " + user.Password + "\n");
                writer.write("FullName:       " + user.FullName + "\n");
                writer.write("Email:          " + user.Email + "\n");
                writer.write("Phone:          " + user.Phone + "\n");
                writer.write("AccType:        " + user.AccType + "\n");
                writer.write("DateOfRegis:    " + user.DateOfRegis.format(Main.df) + "\n");
                writer.write("RememberMe:     " + user.RememberMe + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void ModifyUser(String UserID, User Incoming, String filename) {
        List<User> allUser = ListAllUser(filename);
        for (User user : allUser) {
            if (Objects.equals(user.UserID, UserID)) {
                user.UserID = Incoming.UserID;
                user.Username = Incoming.Username;
                user.FullName = Incoming.FullName;
                user.Password = Incoming.Password;
                user.Phone = Incoming.Phone;
                user.Email = Incoming.Email;
                user.AccType = Incoming.AccType;
                user.DateOfRegis = Incoming.DateOfRegis;
                user.RememberMe = Incoming.RememberMe;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : allUser) {
                writer.write("UserID:         " + user.UserID + "\n");
                writer.write("Username:       " + user.Username + "\n");
                writer.write("Password:       " + user.Password + "\n");
                writer.write("FullName:       " + user.FullName + "\n");
                writer.write("Email:          " + user.Email + "\n");
                writer.write("Phone:          " + user.Phone + "\n");
                writer.write("AccType:        " + user.AccType + "\n");
                writer.write("DateOfRegis:    " + user.DateOfRegis.format(Main.df) + "\n");
                writer.write("RememberMe:     " + user.RememberMe + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    
    public static List<Sales> checkSalesRecordByID(String UserID, String filename) {
        List<Sales> userRelatedRecords = new ArrayList<>();
        List<Sales> allSales = Sales.listAllSales(filename);
        for (Sales sales: allSales) {
            if (Objects.equals(sales.SalesMgrID, UserID)) {
                userRelatedRecords.add(sales);
            }
        }
        return userRelatedRecords;
    }

    public static List<PurchaseRequisition> checkPRRecordByID(String UserID, String filename) {
        List<PurchaseRequisition> userRelatedRecords = new ArrayList<>();
        List<PurchaseRequisition> allPR = PurchaseRequisition.listAllPurchaseRequisitions(filename);
        for (PurchaseRequisition pr: allPR) {
            if (Objects.equals(pr.SalesMgrID, UserID)) {
                userRelatedRecords.add(pr);
            }
        }
        return userRelatedRecords;
    }

    public static List<PurchaseOrder> checkPORecordByID(String UserID, String filename) {
        List<PurchaseOrder> userRelatedRecords = new ArrayList<>();
        List<PurchaseOrder> allPO = PurchaseOrder.listAllPurchaseOrders(filename);
        for (PurchaseOrder po: allPO) {
            if (Objects.equals(po.PurchaseMgrID, UserID)) {
                userRelatedRecords.add(po);
            }
        }
        return userRelatedRecords;
    }

    public static List<Payment> checkPYRecordByID(String UserID, String filename) {
        List<Payment> userRelatedRecords = new ArrayList<>();
        List<Payment> allPY = Payment.listAllPayment(filename);
        for (Payment py: allPY) {
            if (Objects.equals(py.FinanceMgrID, UserID)) {
                userRelatedRecords.add(py);
            }
        }
        return userRelatedRecords;
    }
}
