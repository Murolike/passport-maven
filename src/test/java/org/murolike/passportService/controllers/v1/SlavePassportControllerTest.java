package org.murolike.passportService.controllers.v1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.murolike.passportService.dao.MasterPassportRepository;
import org.murolike.passportService.dao.SlavePassportRepository;
import org.murolike.passportService.models.MasterPassport;
import org.murolike.passportService.models.SlavePassport;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SlavePassportControllerTest {
    private final static String FIXED_SERIES = "1100";
    private final static String FIXED_NUMBER = "221100";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SlavePassportService service;

    private SlavePassport passport;

    @BeforeEach
    public void setUp() {
        service.deleteAll();
        passport = new SlavePassport(FIXED_SERIES, FIXED_NUMBER);
        service.save(passport);
    }

    @AfterEach
    public void tearDown() {
        service.deleteAll();
    }

    @Test
    public void index() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/v1/slave-passport/index")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].series", Matchers.is(passport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(passport.getNumber())))
                .andReturn();
    }

    @Test
    public void search() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/v1/slave-passport/search/" + passport.getSeries() + "/" + passport.getNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].series", Matchers.is(passport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(passport.getNumber())))
                .andReturn();

    }
}