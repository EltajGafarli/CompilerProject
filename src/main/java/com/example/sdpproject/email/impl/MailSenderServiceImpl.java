package com.example.sdpproject.email.impl;

import com.example.sdpproject.email.MailSenderService;
import com.example.sdpproject.entity.User;
import static com.example.sdpproject.util.JavaMailSenderConstants.*;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender javaMailSender;
    @Override
    @SneakyThrows
    public void sendVerificationCodeToAdmin(User admin, String verificationCode) {
        String replaceContent = ADMIN_REGISTRATION_CONTENT.replace(ADMIN_KEY, admin.getUsername());
        sendEmail(admin.getEmail(), replaceContent, verificationCode);
    }

    @Override
    @SneakyThrows
    public void sendVerificationCodeToUser(User user, String verificationCode) {
        String replacedContent = USER_REGISTRATION_CONTENT.replace(USER_KEY, user.getUsername());
        sendEmail(user.getEmail(), replacedContent, verificationCode);
    }

    @Override
    public void sendPasswordResetTokenToAdmin(User admin, String resetToken) {
        String replacedContent = ADMIN_PASSWORD_RESET_TOKEN.replace(ADMIN_KEY, admin.getUsername());
        sendEmail(admin.getEmail(), replacedContent, resetToken);
    }

    @Override
    public void sendPasswordResetTokenToUser(User user, String resetToken) {
        String replacedContent = USER_PASSWORD_RESET_TOKEN.replace(USER_KEY, user.getUsername());
        sendEmail(user.getEmail(), replacedContent, resetToken);
    }

    @Override
    public void sendToUser(User user, String adminUsername) {
        String replacedContent = SEND_TO_USER_CONTENT.replace(USER_KEY, user.getUsername());
        replacedContent = replacedContent.replace(ADMIN_KEY, adminUsername);
        sendEmail(user.getEmail(), replacedContent, SEND_TO_USER_SUBJECT);
    }

    @Override
    public String adminToUserMessage(User user, String adminUsername) {
        String replacedContent = SEND_TO_USER_CONTENT.replace(USER_KEY, user.getUsername());
        replacedContent = replacedContent.replace(ADMIN_KEY, adminUsername);
        return replacedContent;
    }

    @Override
    public void sendToAdmin(User admin) {
        String replacedContent = ADMIN_REGISTRATION_CONTENT.replace(ADMIN_KEY, admin.getUsername());
        sendEmail(admin.getEmail(), replacedContent, SEND_TO_ADMIN_SUBJECT);
    }

    @Override
    public String systemToAdminMessage(User admin) {
        return ADMIN_REGISTRATION_CONTENT.replace(ADMIN_KEY, admin.getUsername());
    }

    @Override
    public void sendEmail(String toAddress, String content, String subject) {
        MimeMessage message = message();
        mimeMessageHelper(message, toAddress, content, subject);
        javaMailSender.send(message);
    }

    private MimeMessage message() {
        return javaMailSender.createMimeMessage();
    }

    @SneakyThrows
    private void mimeMessageHelper(MimeMessage message, String toAddress, String content, String subject) {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(SENDER_ADDRESS, SENDER_NAME);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
    }
}
