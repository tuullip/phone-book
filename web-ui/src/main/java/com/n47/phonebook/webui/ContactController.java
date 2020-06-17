package com.n47.phonebook.webui;

import com.n47.phonebook.business.ContactService;
import com.n47.phonebook.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("phoneBook/contacts")
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody Contact contact) {
        Contact newContact = contactService.createContact(contact.getName(), contact.getPhoneNumber());
        return new ResponseEntity<>(new ContactResponse(newContact.getId().toString()), HttpStatus.CREATED);
    }
}
