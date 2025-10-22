package com.pismo.transactions_app.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.transactions_app.JsonUtils;
import com.pismo.transactions_app.api.account.v1.response.AccountResponseDto;
import com.pismo.transactions_app.api.transaction.v1.request.TransactionRequestDto;
import com.pismo.transactions_app.api.transaction.v1.response.TransactionResponseDto;
import com.pismo.transactions_app.domain.enums.OperationTypeEnum;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class TransactionIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final static String TRANSACTION_BASE_URL = "/v1/transactions";
    private final static String ACCOUNT_BASE_URL = "/v1/accounts";

    private Long accountId;

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("testArguments")
    public void createTransaction(final TransactionRequestDto request, final TransactionResponseDto expectedResponse) {
        givenAccount();
        request.setAccountId(accountId);
        expectedResponse.setAccountId(accountId);
        final String json = mapper.writeValueAsString(request);

        final String responseContent = createTransaction(json);

        final TransactionResponseDto response = mapper.readValue(responseContent, TransactionResponseDto.class);
        assertNotNull(response);
        assertResponse(expectedResponse, response);

    }

    @Test
    public void processTransactions() throws Exception {
        givenAccount();
        TransactionRequestDto transaction = JsonUtils.getValidTransactionRequestDtoForTypeAndValue(OperationTypeEnum.NORMAL_PURCHASE, BigDecimal.valueOf(50), accountId);
        Map<Long, BigDecimal> expectedBalanceForTransactionId = new HashMap<>();
        String json = mapper.writeValueAsString(transaction);
        TransactionResponseDto response = mapper.readValue(createTransaction(json), TransactionResponseDto.class);
        expectedBalanceForTransactionId.put(response.getId(), BigDecimal.valueOf(0));

        transaction = JsonUtils.getValidTransactionRequestDtoForTypeAndValue(OperationTypeEnum.NORMAL_PURCHASE, BigDecimal.valueOf(23.50), accountId);
        json = mapper.writeValueAsString(transaction);
        response = mapper.readValue(createTransaction(json), TransactionResponseDto.class);
        expectedBalanceForTransactionId.put(response.getId(), BigDecimal.valueOf(-13.50));

        transaction = JsonUtils.getValidTransactionRequestDtoForTypeAndValue(OperationTypeEnum.NORMAL_PURCHASE, BigDecimal.valueOf(18.70), accountId);
        json = mapper.writeValueAsString(transaction);
        response = mapper.readValue(createTransaction(json), TransactionResponseDto.class);
        expectedBalanceForTransactionId.put(response.getId(), BigDecimal.valueOf(-18.70));

        transaction = JsonUtils.getValidTransactionRequestDtoForTypeAndValue(OperationTypeEnum.CREDIT_VOUCHER, BigDecimal.valueOf(60), accountId);
        json = mapper.writeValueAsString(transaction);
        response = mapper.readValue(createTransaction(json), TransactionResponseDto.class);
        expectedBalanceForTransactionId.put(response.getId(), BigDecimal.valueOf(0));

        assertPreviousBalanceAfterCredit(expectedBalanceForTransactionId);
    }

    private void assertPreviousBalanceAfterCredit(Map<Long, BigDecimal> expectedBalanceForTransactionId) throws Exception {
        for (Map.Entry<Long, BigDecimal> entry : expectedBalanceForTransactionId.entrySet()) {
            BigDecimal transactionBalance = getTransactionById(entry.getKey()).getBalance();
            assertEquals(entry.getValue(), transactionBalance);
        }

    }

    private String createTransaction(String json) throws Exception {
        return mockMvc.perform(post(TRANSACTION_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void createTransaction_whenAccountDoesNotExist_shouldReturnNotFound() throws Exception {
        final TransactionRequestDto request = JsonUtils.getValidTransactionRequestDtoForType(OperationTypeEnum.NORMAL_PURCHASE);
        request.setAccountId(100L);
        final String json = mapper.writeValueAsString(request);

        mockMvc.perform(post(TRANSACTION_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTransaction_whenInvalidOperationType_shouldReturnBadRequest() throws Exception {
        final TransactionRequestDto request = JsonUtils.getInvalidTransactionRequestOperationTypeInvalid();
        givenAccount();
        request.setAccountId(accountId);
        final String json = mapper.writeValueAsString(request);

        mockMvc.perform(post(TRANSACTION_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_whenAccountIdIsNull_shouldReturnBadRequest() throws Exception {
        final TransactionRequestDto request = JsonUtils.getInvalidTransactionRequestAccountIdNull();
        final String json = mapper.writeValueAsString(request);

        mockMvc.perform(post(TRANSACTION_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_whenOperationTypeIsNull_shouldReturnBadRequest() throws Exception {
        final TransactionRequestDto request = JsonUtils.getInvalidTransactionRequestOperationTypeIdNull();
        givenAccount();
        request.setAccountId(accountId);
        final String json = mapper.writeValueAsString(request);

        mockMvc.perform(post(TRANSACTION_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_whenAmountIsNull_shouldReturnBadRequest() throws Exception {
        final TransactionRequestDto request = JsonUtils.getInvalidTransactionRequestAmountNull();
        givenAccount();
        request.setAccountId(accountId);
        final String json = mapper.writeValueAsString(request);

        mockMvc.perform(post(TRANSACTION_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    private TransactionResponseDto getTransactionById(long id) throws Exception {
        String responseContent = mockMvc.perform(get(TRANSACTION_BASE_URL + "/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return mapper.readValue(responseContent, TransactionResponseDto.class);
    }

    private void assertResponse(final TransactionResponseDto expectedResponse, final TransactionResponseDto response) {
        assertEquals(expectedResponse.getAccountId(), response.getAccountId());
        assertEquals(expectedResponse.getOperationTypeId(), response.getOperationTypeId());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertNotNull(response.getEventDate());
    }


    @SneakyThrows
    private void givenAccount() {
        final String json = JsonUtils.getJsonValidAccountRequestDto();
        final String responseContent = mockMvc.perform(post(ACCOUNT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final AccountResponseDto responseDto = mapper.readValue(responseContent, AccountResponseDto.class);
        accountId = responseDto.getId();
    }

    private static Stream<Arguments> testArguments() {

        return Stream.of(
                Arguments.of(JsonUtils.getValidTransactionRequestDtoForType(OperationTypeEnum.NORMAL_PURCHASE),
                        JsonUtils.getValidTransactionResponseDtoForType(OperationTypeEnum.NORMAL_PURCHASE)),
                Arguments.of(JsonUtils.getValidTransactionRequestDtoForType(OperationTypeEnum.INSTALLMENT_PURCHASE),
                        JsonUtils.getValidTransactionResponseDtoForType(OperationTypeEnum.INSTALLMENT_PURCHASE)),
                Arguments.of(JsonUtils.getValidTransactionRequestDtoForType(OperationTypeEnum.WITHDRAWAL),
                        JsonUtils.getValidTransactionResponseDtoForType(OperationTypeEnum.WITHDRAWAL)),
                Arguments.of(JsonUtils.getValidTransactionRequestDtoForType(OperationTypeEnum.CREDIT_VOUCHER),
                        JsonUtils.getValidTransactionResponseDtoForType(OperationTypeEnum.CREDIT_VOUCHER))
        );
    }
}
