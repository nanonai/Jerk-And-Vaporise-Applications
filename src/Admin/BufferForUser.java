package Admin;

import java.time.LocalDate;

public class BufferForUser {
    public String UserID, Username, Password, FullName, Email, AccType, Phone;
    public int RememberMe;
    public LocalDate DateOfRegis;

    public BufferForUser(String UserID, String Username, String Password, String FullName,
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
}