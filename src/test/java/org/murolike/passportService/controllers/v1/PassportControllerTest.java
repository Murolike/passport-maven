package org.murolike.passportService.controllers.v1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.murolike.passportService.models.MasterPassport;
import org.murolike.passportService.models.SlavePassport;
import org.murolike.passportService.services.MasterPassportService;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PassportControllerTest {
    private final static String MASTER_FIXED_SERIES = "0011";
    private final static String MASTER_FIXED_NUMBER = "001122";

    private final static String SLAVE_FIXED_SERIES = "1100";
    private final static String SLAVE_FIXED_NUMBER = "221100";


    @Autowired
    private MockMvc mvc;

    @Autowired
    private MasterPassportService masterPassportService;

    @Autowired
    private SlavePassportService slavePassportService;

    private MasterPassport masterPassport;
    private SlavePassport slavePassport;

    @BeforeEach
    public void setUp() {
        masterPassportService.deleteAll();
        slavePassportService.deleteAll();

        masterPassport = new MasterPassport(MASTER_FIXED_SERIES, MASTER_FIXED_NUMBER);
        slavePassport = new SlavePassport(SLAVE_FIXED_SERIES, SLAVE_FIXED_NUMBER);

        masterPassportService.save(masterPassport);
        slavePassportService.save(slavePassport);
    }

    @AfterEach
    public void tearDown() {
        masterPassportService.deleteAll();
        slavePassportService.deleteAll();
    }

    @Test
    public void index() throws Exception {
        MockHttpServletRequestBuilder request = get("/v1/passport/index")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void search() throws Exception {
        MockHttpServletRequestBuilder masterRequestBuilder = get("/v1/passport/search/" + masterPassport.getSeries() + "/" + masterPassport.getNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(masterRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].series", Matchers.is(masterPassport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(masterPassport.getNumber())));

        MockHttpServletRequestBuilder slaveRequestBuilder = get("/v1/passport/search/" + slavePassport.getSeries() + "/" + slavePassport.getNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(slaveRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].series", Matchers.is(slavePassport.getSeries())))
                .andExpect(jsonPath("$[0].number", Matchers.is(slavePassport.getNumber())));


    }
}