package com.example.heartdiseaseprediction;
import java.util.Map;

public class User {
    private String email;
    private String password; // Lưu ý: Không nên lưu trữ mật khẩu trong database
    private String username;
    private Map<String, String> cardiacInfo;

    // Constructor rỗng được yêu cầu bởi Firebase
    public User() {
    }

    // Constructor với các tham số
    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Getter và Setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter và Setter cho password
    // Lưu ý: Cân nhắc việc lưu trữ và truyền mật khẩu
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter và Setter cho username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getCardiacInfo() {
        return cardiacInfo;
    }
}

