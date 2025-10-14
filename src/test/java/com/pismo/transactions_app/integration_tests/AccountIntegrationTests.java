package com.pismo.transactions_app.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.transactions_app.JsonUtils;
import com.pismo.transactions_app.api.account.v1.response.AccountResponseDto;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AccountIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final static String ACCOUNT_BASE_URL = "/v1/accounts";

    @SneakyThrows
    @Test
    public void createAccount_shouldReturnCreatedAccount() {
        final String json = JsonUtils.getJsonValidAccountRequestDto();
        final AccountResponseDto expectedResponse = JsonUtils.getValidAccountResponseDto();

        final String responseContent = mockMvc.perform(post(ACCOUNT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        final AccountResponseDto response = mapper.readValue(responseContent, AccountResponseDto.class);
        assertNotNull(response);
        assertEquals(expectedResponse.getDocumentNumber(), response.getDocumentNumber());
    }

    @Test
    public void createAccount_whenDocumentNumberIsNull_shouldReturnBadRequest() throws Exception {
        final String json = JsonUtils.getJsonInvalidAccountRequestDto();

        mockMvc.perform(post(ACCOUNT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAccountById_shouldReturnAccount() throws Exception {
        final String json = JsonUtils.getJsonValidAccountRequestDto();
        final AccountResponseDto expectedResponse = JsonUtils.getValidAccountResponseDto();

        String responseContent = mockMvc.perform(post(ACCOUNT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        AccountResponseDto response = mapper.readValue(responseContent, AccountResponseDto.class);
        assertNotNull(response);
        assertEquals(expectedResponse.getDocumentNumber(), response.getDocumentNumber());

        responseContent = mockMvc.perform(get(ACCOUNT_BASE_URL + "/{id}", response.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        response = mapper.readValue(responseContent, AccountResponseDto.class);
        assertNotNull(response);
        assertEquals(expectedResponse.getDocumentNumber(), response.getDocumentNumber());
    }
}
