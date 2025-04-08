package com.martinjeanne.portfolio_backend.controller;

import com.martinjeanne.portfolio_backend.model.dto.ContactMessageDto;
import com.martinjeanne.portfolio_backend.model.entity.ContactMessage;
import com.martinjeanne.portfolio_backend.repo.ContactMessageRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageRepo contactMessageRepo;

    @PostMapping
    public ResponseEntity<ContactMessageDto> receiveMessage(@RequestBody @Valid ContactMessageDto cm) {
        System.out.println("Message from " + cm.getAuthor() + " received: " + cm.getMessage());
        ContactMessage toSave = ContactMessage.builder().author(cm.getAuthor()).message(cm.getMessage()).build();
        contactMessageRepo.save(toSave);
        return ResponseEntity.ok(cm);
    }
}
