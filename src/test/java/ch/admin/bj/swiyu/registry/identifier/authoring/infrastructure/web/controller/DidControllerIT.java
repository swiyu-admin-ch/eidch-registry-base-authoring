/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.identifier.authoring.test.TestWebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(TestWebSecurityConfig.class)
class DidControllerIT {

    private static final String ENTRY_BASE_URL = "/api/v1/entry/";
    private static final String ENTRY_DID_URL = "/api/v1/did/";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUploadDidWeb_response() throws Exception {
        var result = mvc
                .perform(MockMvcRequestBuilders.post(ENTRY_BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SETUP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri", Matchers.containsString("DID:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.isConfigured").value(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString("HTTP:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.FALSE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createResponse = objectMapper.readTree(result);
        var newId = createResponse.get("id").textValue();

        mvc
                .perform(
                        MockMvcRequestBuilders.put(ENTRY_DID_URL + "%s/did.json".formatted(newId)).content("\"TESTCONTENT\"")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(newId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri", Matchers.containsString("DID:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri", Matchers.containsString(newId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.isConfigured").value(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString("HTTP:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.FALSE));
    }

    @Test
    void testUploadDidTdw_response() throws Exception {
        var result = mvc
                .perform(MockMvcRequestBuilders.post(ENTRY_BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SETUP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri", Matchers.containsString("DID:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.isConfigured").value(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString("HTTP:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.FALSE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createResponse = objectMapper.readTree(result);
        var newId = createResponse.get("id").textValue();

        mvc
                .perform(put(ENTRY_DID_URL + "%s/did.jsonl".formatted(newId)).content("\"TESTCONTENT\""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(newId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri", Matchers.containsString("DID:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.isConfigured").value(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString("HTTP:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString(newId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.TRUE));
    }
}
