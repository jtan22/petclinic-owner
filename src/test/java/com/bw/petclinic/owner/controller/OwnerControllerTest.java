package com.bw.petclinic.owner.controller;

import com.bw.petclinic.owner.domain.Owner;
import com.bw.petclinic.owner.repository.OwnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OwnerControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Owner owner;

    @BeforeEach
    public void setup() {
        owner = new Owner(1, "Jason", "Tan", "1 Martin Place", "Sydney", "0123456789");
    }

    @Test
    public void testFindByLastName() throws Exception {
        Pageable pageable = PageRequest.of(0, 3);
        when(ownerRepository.findPageByLastName("Tan", pageable)).thenReturn(
                new PageImpl<>(List.of(
                        new Owner(1, "Jason", "Tan",
                                "ADD 1 Martin Place", "Sydney", "0123456789"),
                        new Owner(2, "Jackson", "Tanner",
                                "ADD 2 Martin Place", "Canberra", "1123456789"),
                        new Owner(3, "Jack", "Tang",
                                "ADD 3 Martin Place", "Brisbane", "2123456789")),
                    pageable,
                    12));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owners/find")
                        .param("lastName", "Tan")
                        .param("pageNumber", "0")
                        .param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.pageable.pageNumber", is(0)))
                .andExpect(jsonPath("$.pageable.pageSize", is(3)))
                .andExpect(jsonPath("$.totalElements", is(12)));
    }

    @Test
    public void testSaveFailed() throws Exception {
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/owners")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(""))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", startsWith("Required request body is missing")));
    }

    @Test
    public void testSave() throws Exception {
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/owners")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Jason")));
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(ownerRepository.findById(0)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owners")
                        .param("ownerId", "0"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Owner [0] not found")));
    }

    @Test
    public void testGetById() throws Exception {
        when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owners")
                        .param("ownerId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Jason")));
    }

}
