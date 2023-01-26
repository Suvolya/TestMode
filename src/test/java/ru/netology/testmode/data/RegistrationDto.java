package ru.netology.testmode.data;


public class RegistrationDto {

    String login;
    String password;
    String status;

    public RegistrationDto(String randomLogin, String randomPassword, String status) {
        login = randomLogin;
        password = randomPassword;
        status = status;

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}





