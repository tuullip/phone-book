package com.n47.phonebook.it;

import com.google.gson.Gson;
import com.n47.phonebook.domain.Contact;
import com.n47.phonebook.domain.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void testAddEmployee() throws Exception {
        Contact contact = new Contact();
        contact.setName("Name");
        contact.setPhoneNumber("+38970123456");

        mockMvc.perform(post("/phoneBook/contacts")
                .contentType("application/json")
                .content(gson.toJson(contact)))
                .andExpect(status().isCreated());

        Contact newContact = contactRepository.findByName("Name");
        assertThat(newContact.getId()).isEqualTo(1);
        assertThat(newContact.getName()).isEqualTo(contact.getName());
        assertThat(newContact.getPhoneNumber()).isEqualTo(contact.getPhoneNumber());
    }
}

