package gp.training.kim.bar.controller.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTableWasBooked() throws Exception {
        // given
        // when
        mockMvc.perform(post("/visitor/book").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"count\": 4,\n" +
                        "  \"date\": \"20-02-2020\",\n" +
                        "  \"from\": \"18:30\",\n" +
                        "  \"to\": \"23:00\",\n" +
                        "  \"barRack\": true\n" +
                        "}"))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"table\":\"Table 2\",\"visitors\":[3,4,5,6]}"));
    }
}
