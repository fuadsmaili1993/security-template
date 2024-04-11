package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.entity.user.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(PasswordResetToken passwordResetToken) throws UnknownHostException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String link = getServerUrlPrefix() + "/user/changePassword?token=" + passwordResetToken.getToken();
        simpleMailMessage.setSubject("Reset Password");
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Reset password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        simpleMailMessage.setText(content);
        simpleMailMessage.setTo(passwordResetToken.getEmail());
        simpleMailMessage.setFrom("fuad.edina1@gmail.com");
        this.mailSender.send(simpleMailMessage);
    }

    private String getHostname() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private String getServerUrlPrefix() throws UnknownHostException {
        return "http://" + getHostname() + ":" + getPort();
    }

    private String getPort() {
        return environment.getProperty("local.server.port");
    }
}
