package com.example.sdpproject.service.impl;

import com.example.sdpproject.email.MailSenderService;
import com.example.sdpproject.entity.User;
import com.example.sdpproject.entity.Verification;
import com.example.sdpproject.repository.UserRepository;
import com.example.sdpproject.repository.VerificationRepository;
import com.example.sdpproject.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final MailSenderService mailSenderService;

    @Override
    public Boolean verifyUser(String email, String verificationCode) {
        User user = getUserByEmail(email);
        Verification verification = getVerificationByVerificationCode(verificationCode);
        if (user != null && verification != null && !user.isEnabled()
                && user.getId() == verification.getUser().getId()) {
            user.setEnabled(true);
            userRepository.save(user);
            verificationRepository.delete(verification);
            return true;
        }
        return false;
    }

    @Override
    public void verificationCodeSending(User user) {
        String generatedVerificationCode = generateVerificationCode();
        Verification verification = Verification
                .builder()
                .user(user)
                .verificationCode(generatedVerificationCode)
                .build();
        verificationRepository.save(verification);
        mailSenderService.sendVerificationCodeToUser(user, generatedVerificationCode);

//TODO:        if (user.getAuthorities().toString().equals("ADMIN")) {
//            mailSenderService.sendVerificationCodeToAdmin(user, generatedVerificationCode);
//        } else {
//            mailSenderService.sendVerificationCodeToUser(user, generatedVerificationCode);
//        }
    }

    @Override
    public String generateVerificationCode() {
        int codeLength = 6;
        String characters = "0123456789";
        Random random = new Random();
        return IntStream.range(0, codeLength)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private Verification getVerificationByVerificationCode(String verificationCode) {
        //TODO: () -> new VerificationNotFoundException("Verification not found with this verification code.")

        return verificationRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new RuntimeException("Error"));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                //() -> new UserNotFoundException("User not found with this email.")
        );
    }
}


