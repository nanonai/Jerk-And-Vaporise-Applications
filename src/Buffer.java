import java.time.LocalDate;

public class Buffer {
    String UserID, Username, Password, FullName, Email, AccType;
    int Phone, Validated, RememberMe;
    LocalDate DateOfRegis;

    public Buffer(String UserID, String Username, String Password, String FullName,
                  String Email, int Phone, String AccType, LocalDate DateOfRegis,
                  int Validated, int RememberMe) {
        this.UserID = UserID;
        this.Username = Username;
        this.Password = Password;
        this.FullName = FullName;
        this.Email = Email;
        this.Phone = Phone;
        this.AccType = AccType;
        this.DateOfRegis = DateOfRegis;
        this.Validated = Validated;
        this.RememberMe = RememberMe;
    }
}