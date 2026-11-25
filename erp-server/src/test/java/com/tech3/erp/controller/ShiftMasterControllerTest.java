package com.tech3.erp.controller;

import com.tech3.erp.service.ShiftMasterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ShiftMasterControllerTest {

    private MockMvc mockMvc;
    private ShiftMasterService shiftMasterService;

    @BeforeEach
    void setUp() {
        shiftMasterService = mock(ShiftMasterService.class);
        mockMvc = standaloneSetup(new ShiftMasterController(shiftMasterService)).build();
    }

    @Test
    void getAllShiftsIsMappedAtApiShifts() throws Exception {
        when(shiftMasterService.getAllShifts(null, null, null, 10000, 0))
                .thenReturn(Map.of("data", List.of(), "total", 0L, "limit", 10000, "offset", 0));

        mockMvc.perform(get("/api/shifts").param("limit", "10000").param("offset", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());

        verify(shiftMasterService).getAllShifts(null, null, null, 10000, 0);
    }
}
