package kz.iamtim.arm.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * ApartmentControllerTest class will test all APIs in ApartmentController.java.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApartmentControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  private String obtainAccessToken(String username, String password) throws Exception {

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("login", username);
    params.add("password", password);

    String authString = "{\"login\":\"admin\",\"password\":\"123\"}";

    ResultActions result
            = mockMvc.perform(post("/api/auth/login")
            .content(authString)
            .contentType("application/json;charset=UTF-8")
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));

    String resultString = result.andReturn().getResponse().getContentAsString();

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("accessToken").toString();
  }

  @Test
  public void testSaveApartment() throws Exception {
    String accessToken = obtainAccessToken("timurt", "123");

    String apartmentString = "{\n"
            + "\t\"name\" : \"Hello World\",\n"
            + "\t\"description\" : \"Hello World Description\",\n"
            + "\t\"floorAreaSize\" : 1.0,\n"
            + "\t\"pricePerMonth\" : 2.0,\n"
            + "\t\"numberOfRooms\" : 5,\n"
            + "\t\"longitude\" : 5.4,\n"
            + "\t\"latitude\" : 3.2,\n"
            + "\t\"realtorId\" : 16\n"
            + "}";

    mockMvc.perform(post("/apartments")
            .header("Authorization", "Bearer " + accessToken)
            .content(apartmentString)
            .contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk());
  }
}
