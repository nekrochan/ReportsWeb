package org.example.auth.dto;

import jakarta.validation.constraints.*;

public class UserRegistrationDto {

    private String username;
    private String fullname;
    private String email;
    private int age;
    private String password;
    private String confirmPassword;

    public UserRegistrationDto() {}

    @NotEmpty(message = "User name cannot be null or empty!")
    @NotBlank(message = "Username field must be filled!")
    @Size(min = 5, max = 20)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Full name cannot be null or empty!")
    @NotBlank(message = "Full name field must be filled!")
    @Size(min = 5, max = 20)
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @NotEmpty(message = "Email cannot be null or empty!")
    @NotBlank(message = "Email field must be filled!")
    @Email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Min(0)
    @Max(90)
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @NotEmpty(message = "Password cannot be null or empty!")
    @NotBlank(message = "Password field must be filled!")
    @Size(min = 5, max = 20)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Confirm Password cannot be null or empty!")
    @NotBlank(message = "Confirm password field must be filled!")
    @Size(min = 5, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "username='" + username + '\'' +
                ", fullName='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
