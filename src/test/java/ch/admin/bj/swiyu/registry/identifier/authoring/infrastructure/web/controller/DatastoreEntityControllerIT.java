/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.identifier.authoring.test.TestWebSecurityConfig;
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

@Import(TestWebSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class DatastoreEntityControllerIT {

    private static final String ENTRY_BASE_URL = "/api/v1/entry/";

    @Autowired
    protected MockMvc mvc;

    @Test
    void testCreateEntry_response() throws Exception {
        mvc
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.FALSE));
    }

    @Test
    void testCheckEntry_StatusInSetup_response() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get(ENTRY_BASE_URL + "00000000-0000-0000-0000-000000000000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.FALSE));
    }

    @Test
    void testCheckEntry_validDidTdw_response() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get(ENTRY_BASE_URL + "00000000-0000-0000-0000-000000000001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString("TEST_READ")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.TRUE));
    }

    @Test
    void testCheckEntry_validDidWeb_response() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get(ENTRY_BASE_URL + "00000000-0000-0000-0000-000000000002"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri", Matchers.containsString("TEST_READ")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.isConfigured").value(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString("HTTP:TESTROUTE"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.FALSE));
    }

    @Test
    void testCheckEntry_validDidWeb_And_validDidTdw_response() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get(ENTRY_BASE_URL + "00000000-0000-0000-0000-000000000003"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.readUri", Matchers.containsString("TEST_READ")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_WEB.isConfigured").value(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.readUri", Matchers.containsString("TEST_READ")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.files.DID_TDW.isConfigured").value(Boolean.TRUE));
    }

    @Test
    void testCheckEntry_statusIsdisabled_response() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get(ENTRY_BASE_URL + "00000000-0000-0000-0000-000000000004"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DISABLED"));
    }

    @Test
    void testCheckEntry_statusIsdeactivated_response() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get(ENTRY_BASE_URL + "00000000-0000-0000-0000-000000000005"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DEACTIVATED"));
    }
}
