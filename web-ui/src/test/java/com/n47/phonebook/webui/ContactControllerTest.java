package com.n47.phonebook.webui;

import com.google.gson.Gson;
import com.n47.phonebook.business.ContactService;
import com.n47.phonebook.domain.Contact;
import com.n47.phonebook.webui.exception.CustomRestExceptionHandler;
import com.n47.phonebook.webui.exception.apierror.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private Gson gson;

    @BeforeEach
    public void setup() {

        gson = new Gson();

        mockMvc = MockMvcBuilders.standaloneSetup(contactController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @Test
    public void createContactWithValidPayload() throws Exception {
        //given
        Contact contact = new Contact();
        contact.setName("Name");
        contact.setPhoneNumber("+38970123456");
        given(contactService.createContact("Name", "+38970123456"))
                .willReturn(new Contact((long) 1, "Name", "+38970123456"));

        //when
        MockHttpServletResponse response = mockMvc.perform(
                post("/phoneBook/contacts").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(contact))).andReturn().getResponse();

        //then
        ContactResponse contactResponse = gson.fromJson(response.getContentAsString(), ContactResponse.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(contactResponse.getId()).isEqualTo("1");
    }

    @Test
    public void createContactWhenNameIsNullOrEmpty() throws Exception {
        //given
        Contact contact = new Contact();
        contact.setPhoneNumber("+38970123456");

        //when
        MockHttpServletResponse responseNameNull = mockMvc.perform(
                post("/phoneBook/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(contact))).andReturn().getResponse();
        contact.setName("");
        MockHttpServletResponse responseNameEmpty = mockMvc.perform(
                post("/phoneBook/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(contact))).andReturn().getResponse();

        //then
        ApiError apiErrorNameNull = gson.fromJson(responseNameNull.getContentAsString(), ApiError.class);
        ApiError apiErrorNameEmpty = gson.fromJson(responseNameEmpty.getContentAsString(), ApiError.class);

        assertThat(responseNameNull.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(apiErrorNameNull.getErrorCode()).isEqualTo("INVALID_PAYLOAD");
        assertThat(apiErrorNameNull.getErrorMessage()).isEqualTo("field_name");

        assertThat(responseNameEmpty.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(apiErrorNameEmpty.getErrorCode()).isEqualTo("INVALID_PAYLOAD");
        assertThat(apiErrorNameEmpty.getErrorMessage()).isEqualTo("field_name");
    }

    @Test
    public void createContactWhenPhoneNumberIsNullOrEmpty() throws Exception {
        //given
        Contact contact = new Contact();
        contact.setName("Name");

        //when
        MockHttpServletResponse responsePhoneNumberNull = mockMvc.perform(
                post("/phoneBook/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(contact))).andReturn().getResponse();
        contact.setPhoneNumber("");
        MockHttpServletResponse responsePhoneNumberEmpty = mockMvc.perform(
                post("/phoneBook/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(contact))).andReturn().getResponse();

        //then
        ApiError apiErrorPhoneNumberNull = gson.fromJson(responsePhoneNumberNull.getContentAsString(), ApiError.class);
        ApiError apiErrorPhoneNumberEmpty = gson.fromJson(responsePhoneNumberEmpty.getContentAsString(), ApiError.class);

        assertThat(responsePhoneNumberNull.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(apiErrorPhoneNumberNull.getErrorCode()).isEqualTo("INVALID_PAYLOAD");
        assertThat(apiErrorPhoneNumberNull.getErrorMessage()).isEqualTo("phoneNumber");

        assertThat(responsePhoneNumberEmpty.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(apiErrorPhoneNumberEmpty.getErrorCode()).isEqualTo("INVALID_PAYLOAD");
        assertThat(apiErrorPhoneNumberEmpty.getErrorMessage()).isEqualTo("phoneNumber");
    }

    @Test
    public void createContactWhenPhoneNumberIsInInvalidFormat() throws Exception {
        //given
        Contact contact = new Contact();
        contact.setName("Name");
        contact.setPhoneNumber("InvalidFormat");

        //when
        MockHttpServletResponse response = mockMvc.perform(
                post("/phoneBook/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(contact))).andReturn().getResponse();

        //then
        ApiError apiError = gson.fromJson(response.getContentAsString(), ApiError.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(apiError.getErrorCode()).isEqualTo("INVALID_PAYLOAD");
        assertThat(apiError.getErrorMessage()).isEqualTo("phoneNumber");
    }

    @Test
    public void createContactWhenNameAndPhoneNumberAreInvalid() throws Exception {
        //given
        Contact contact = new Contact();
        contact.setName("");
        contact.setPhoneNumber("InvalidFormat");

        //when
        MockHttpServletResponse response = mockMvc.perform(
                post("/phoneBook/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(contact))).andReturn().getResponse();

        //then
        ApiError apiError = gson.fromJson(response.getContentAsString(), ApiError.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(apiError.getErrorCode()).isEqualTo("INVALID_PAYLOAD");
        assertThat(apiError.getErrorMessage()).contains(",");
    }
}
