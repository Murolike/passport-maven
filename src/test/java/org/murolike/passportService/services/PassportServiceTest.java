package org.murolike.passportService.services;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.murolike.passportService.models.Passport;
import org.murolike.passportService.models.SlavePassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PassportServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PassportService service;

    private Passport passport;
    private List<Passport> passportList;

    @BeforeEach
    void setUp() {
        passport = new Passport("1100", "221100");
        passportList = Arrays.asList(passport);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() throws Exception {
        given(service.findAll()).willReturn(passportList);

        mvc.perform(get("/v1/passport/index").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].series", Matchers.is(passport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(passport.getNumber())));

    }

    @Test
    void findAllBySeriesAndNumber() throws Exception {
        given(service.findAllBySeriesAndNumber(passport.getSeries(), passport.getNumber())).willReturn(passportList);

        mvc.perform(get("/v1/passport/search/" + passport.getSeries() + "/" + passport.getNumber()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].series", Matchers.is(passport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(passport.getNumber())));
    }
}