package com.n47.phonebook.business;

import com.n47.phonebook.domain.Contact;
import com.n47.phonebook.domain.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    @Transactional
    public Contact createContact(String name, String phoneNumber) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);

        contactRepository.save(contact);
        return contact;
    }
}
