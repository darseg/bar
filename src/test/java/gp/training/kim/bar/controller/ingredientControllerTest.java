package gp.training.kim.bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ingredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetDayReport() throws Exception {
        mockMvc.perform(get("/admins/report").contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .header("admin", 1))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"storeHouse\": [\n" +
                        "    {\n" +
                        "      \"name\": \"Жигули\",\n" +
                        "      \"balance\": 560,\n" +
                        "      \"costPrice\": 5.6\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"name\": \"Ноги\",\n" +
                        "      \"balance\": 26,\n" +
                        "      \"costPrice\": 1\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"name\": \"Панировка для ног\",\n" +
                        "      \"balance\": 1000,\n" +
                        "      \"costPrice\": 0.5\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"name\": \"Сыр Чеддер\",\n" +
                        "      \"balance\": 1,\n" +
                        "      \"costPrice\": 20\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"name\": \"Old Bobby\",\n" +
                        "      \"balance\": 1,\n" +
                        "      \"costPrice\": 4\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"costPrice\": 316.4,\n" +
                        "  \"profit\": 763.6\n" +
                        "}"));
    }
}
