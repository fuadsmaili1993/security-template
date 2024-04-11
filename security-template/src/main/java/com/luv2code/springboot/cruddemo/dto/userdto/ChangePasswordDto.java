package com.luv2code.springboot.cruddemo.dto.userdto;

public class ChangePasswordDto {

    private String email;

   private  String existingPassword;

    private String newPassword;

    public ChangePasswordDto() {
    }

    public ChangePasswordDto(String email, String existingPassword, String newPassword) {
        this.email = email;
        this.existingPassword = existingPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExistingPassword() {
        return existingPassword;
    }

    public void setExistingPassword(String existingPassword) {
        this.existingPassword = existingPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
