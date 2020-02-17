package gp.training.kim.bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    public void testTableWasBooked() throws Exception {
        // given
        // when
        mockMvc.perform(post("/visitors/book").contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
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
    }*/

    @Test
    public void testGetMenu() throws Exception {
        //given
        //when
        mockMvc.perform(get("/menu").contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                // then
            .andExpect(status().isOk())
            .andExpect(content().json("{\n" +
                    "  \"beer\": [\n" +
                    "    {\n" +
                    "      \"id\": 1,\n" +
                    "      \"name\": \"Жигули\",\n" +
                    "      \"description\": \"Четкое пиво\",\n" +
                    "      \"params\": {\n" +
                    "        \"param2\": \"2\",\n" +
                    "        \"param1\": \"1\"\n" +
                    "      },\n" +
                    "      \"price\": 20\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 2,\n" +
                    "      \"name\": \"Old bobby\",\n" +
                    "      \"description\": \"не такое четкое пиво\",\n" +
                    "      \"params\": {\n" +
                    "        \"param1\": \"3\"\n" +
                    "      },\n" +
                    "      \"price\": 12\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"food\": [\n" +
                    "    {\n" +
                    "      \"id\": 4,\n" +
                    "      \"name\": \"Сырная нарезка\",\n" +
                    "      \"description\": \"Хороша\",\n" +
                    "      \"params\": {\n" +
                    "        \"calories\": \"очень много\"\n" +
                    "      },\n" +
                    "      \"price\": 12\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 3,\n" +
                    "      \"name\": \"Ноги Буша\",\n" +
                    "      \"description\": \"Так себе закусь\",\n" +
                    "      \"params\": {\n" +
                    "        \"calories\": \"много\"\n" +
                    "      },\n" +
                    "      \"price\": 10\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}"));
    }

    /*@Test
    public void testMakeOrder() throws Exception {
        // given
        // when
        mockMvc.perform(post("/visitors/order").contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .header("visitorId", 3)
                .content("[1, 3]"))
                // then
                .andExpect(status().isAccepted());
    }

    @Test
    public void testGetVisitorCheck() throws Exception {
        mockMvc.perform(get("/visitors/check").contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .header("visitorId", 3))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"details\": {\n" +
                        "    \"Жигули\": {\n" +
                        "      \"price\": 20,\n" +
                        "      \"count\": 14,\n" +
                        "      \"sum\": 280\n" +
                        "    },\n" +
                        "    \"Ноги Буша\": {\n" +
                        "      \"price\": 10,\n" +
                        "      \"count\": 1,\n" +
                        "      \"sum\": 10\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"price\": 290\n" +
                        "}"));
    }

    @Test
    public void testGetTableCheck() throws Exception {
        mockMvc.perform(get("/visitors/tableCheck").contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .header("visitorId", 3))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"table\": {\n" +
                        "    \"details\": {\n" +
                        "      \"Old bobby\": {\n" +
                        "        \"price\": 12,\n" +
                        "        \"count\": 30,\n" +
                        "        \"sum\": 360\n" +
                        "      },\n" +
                        "      \"Жигули\": {\n" +
                        "        \"price\": 20,\n" +
                        "        \"count\": 34,\n" +
                        "        \"sum\": 680\n" +
                        "      },\n" +
                        "      \"Ноги Буша\": {\n" +
                        "        \"price\": 10,\n" +
                        "        \"count\": 4,\n" +
                        "        \"sum\": 40\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"price\": 1080\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    public void testGetTableCheckAndVisitorsSeparately() throws Exception {
        mockMvc.perform(get("/visitors/tableCheck?visitors=3,4").contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .header("visitorId", 3))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"table\": {\n" +
                        "    \"details\": {\n" +
                        "      \"Old bobby\": {\n" +
                        "        \"price\": 12,\n" +
                        "        \"count\": 30,\n" +
                        "        \"sum\": 360\n" +
                        "      },\n" +
                        "      \"Жигули\": {\n" +
                        "        \"price\": 20,\n" +
                        "        \"count\": 5,\n" +
                        "        \"sum\": 100\n" +
                        "      },\n" +
                        "      \"Ноги Буша\": {\n" +
                        "        \"price\": 10,\n" +
                        "        \"count\": 2,\n" +
                        "        \"sum\": 20\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"price\": 480\n" +
                        "  },\n" +
                        "  \"visitors\": {\n" +
                        "    \"3\": {\n" +
                        "      \"details\": {\n" +
                        "        \"Жигули\": {\n" +
                        "          \"price\": 20,\n" +
                        "          \"count\": 14,\n" +
                        "          \"sum\": 280\n" +
                        "        },\n" +
                        "        \"Ноги Буша\": {\n" +
                        "          \"price\": 10,\n" +
                        "          \"count\": 1,\n" +
                        "          \"sum\": 10\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"price\": 290\n" +
                        "    },\n" +
                        "    \"4\": {\n" +
                        "      \"details\": {\n" +
                        "        \"Жигули\": {\n" +
                        "          \"price\": 20,\n" +
                        "          \"count\": 15,\n" +
                        "          \"sum\": 300\n" +
                        "        },\n" +
                        "        \"Ноги Буша\": {\n" +
                        "          \"price\": 10,\n" +
                        "          \"count\": 1,\n" +
                        "          \"sum\": 10\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"price\": 310\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"));
    }*/
}
