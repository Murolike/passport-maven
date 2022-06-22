package org.murolike.passportService.services;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.murolike.passportService.models.MasterPassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class MasterPassportServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MasterPassportService service;

    private MasterPassport passport;
    private List<MasterPassport> passportList;

    @BeforeEach
    void setUp() {
        passport = new MasterPassport("0011", "001122");
        passportList = Arrays.asList(passport);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() throws Exception {

        given(service.findAll()).willReturn(passportList);

        mvc.perform(get("/v1/master-passport/index").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].series", Matchers.is(passport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(passport.getNumber())));

    }

    @Test
    void findAllBySeriesAndNumber() throws Exception {
        given(service.findAllBySeriesAndNumber(passport.getSeries(), passport.getNumber())).willReturn(passportList);

        mvc.perform(get("/v1/master-passport/search/" + passport.getSeries() + "/" + passport.getNumber()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].series", Matchers.is(passport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(passport.getNumber())));
    }
}