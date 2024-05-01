package com.example.sdpproject.service.user.impl;

import com.example.sdpproject.dto.user.ContactDto;
import com.example.sdpproject.dto.user.UserRequestDto;
import com.example.sdpproject.email.MailSenderService;
import com.example.sdpproject.entity.enums.RoleEnum;
import com.example.sdpproject.entity.user.Contact;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.repository.ContactRepository;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.service.user.ContactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final MailSenderService mailSenderService;

    private final UserRepository userRepository;
    @Override
    public String contact(ContactDto contactDto) {
        Contact contact = dtoToContact(contactDto);
        contactRepository.save(contact);
        List<User> admins = userRepository.findByRole(RoleEnum.ADMIN);

        for(var admin: admins) {
            String adminEmail = admin.getEmail();
            String content = contact.getMessage() + "<br>" + contact.getPhoneNumber();
            mailSenderService.sendEmail(adminEmail, content, contact.getFullName());
        }
        return "Message sent successfully";
    }

    private Contact dtoToContact(ContactDto contactDto) {
        return Contact
                .builder()
                .email(contactDto.getEmail())
                .fullName(contactDto.getFullName())
                .phoneNumber(contactDto.getPhoneNumber())
                .message(contactDto.getMessage())
                .build();
    }
}
