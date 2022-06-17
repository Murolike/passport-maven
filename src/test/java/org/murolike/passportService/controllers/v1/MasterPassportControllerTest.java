package org.murolike.passportService.controllers.v1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.murolike.passportService.dao.MasterPassportRepository;
import org.murolike.passportService.models.MasterPassport;
import org.murolike.passportService.services.MasterPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MasterPassportControllerTest {
    private final static String FIXED_SERIES = "0011";
    private final static String FIXED_NUMBER = "001122";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MasterPassportService service;

    private MasterPassport passport;

    @BeforeEach
    public void setUp() {
        service.deleteAll();
        passport = new MasterPassport(FIXED_SERIES, FIXED_NUMBER);
        service.save(passport);
    }

    @AfterEach
    public void tearDown() {
        service.deleteAll();
    }

    @Test
    public void index() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/v1/master-passport/index")
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
        MockHttpServletRequestBuilder requestBuilder = get("/v1/master-passport/search/" + passport.getSeries() + "/" + passport.getNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].series", Matchers.is(passport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(passport.getNumber())))
                .andReturn();

    }
}