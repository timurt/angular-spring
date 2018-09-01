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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * RestaurantControllerTest class will test all APIs in RestaurantController.java.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestaurantControllerTest {

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
  public void testSaveRestaurant() throws Exception {
    String accessToken = obtainAccessToken("timurt", "123");

    String restaurantString = "{\n"
            + "\t\"name\" : \"Hello World\",\n"
            + "\t\"description\" : \"Hello World Description\",\n"
            + "\t\"averageCheck\" : 1.0,\n"
            + "\t\"numberOfSeats\" : 5,\n"
            + "\t\"longitude\" : 5.4,\n"
            + "\t\"latitude\" : 3.2,\n"
            + "\t\"ownerId\" : 2\n"
            + "}";

    mockMvc.perform(post("/api/restaurants")
            .header("Authorization", "Bearer " + accessToken)
            .content(restaurantString)
            .contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk());
  }

  @Test
  public void testUpdateRestaurant() throws Exception {
    String accessToken = obtainAccessToken("timurt", "123");

    String restaurantString = "{\n"
            + "\t\"name\" : \"Hello World\",\n"
            + "\t\"description\" : \"Hello World Description\",\n"
            + "\t\"averageCheck\" : 1.0,\n"
            + "\t\"numberOfSeats\" : 5,\n"
            + "\t\"longitude\" : 5.4,\n"
            + "\t\"latitude\" : 3.2,\n"
            + "\t\"ownerId\" : 2\n"
            + "}";

    ResultActions result
            = mockMvc.perform(post("/api/restaurants")
            .content(restaurantString)
            .contentType("application/json;charset=UTF-8")
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));

    String resultString = result.andReturn().getResponse().getContentAsString();

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    String restaurantId = jsonParser.parseMap(resultString).get("id").toString();

    String updateRestaurantString = "{\n"
            + "\t\"id\" : \""+ restaurantId + "\",\n"
            + "\t\"name\" : \"Hello World\",\n"
            + "\t\"description\" : \"Hello World Description\",\n"
            + "\t\"averageCheck\" : 1.0,\n"
            + "\t\"numberOfSeats\" : 5,\n"
            + "\t\"longitude\" : 5.4,\n"
            + "\t\"latitude\" : 3.2,\n"
            + "\t\"ownerId\" : 2\n"
            + "}";

    mockMvc.perform(post("/api/restaurants")
            .header("Authorization", "Bearer " + accessToken)
            .content(updateRestaurantString)
            .contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk());
  }

  @Test
  public void testGetRestaurant() throws Exception {
    String accessToken = obtainAccessToken("timurt", "123");

    String restaurantString = "{\n"
            + "\t\"name\" : \"Hello World\",\n"
            + "\t\"description\" : \"Hello World Description\",\n"
            + "\t\"averageCheck\" : 1.0,\n"
            + "\t\"numberOfSeats\" : 5,\n"
            + "\t\"longitude\" : 5.4,\n"
            + "\t\"latitude\" : 3.2,\n"
            + "\t\"ownerId\" : 2\n"
            + "}";

    ResultActions result
            = mockMvc.perform(post("/api/restaurants")
            .content(restaurantString)
            .contentType("application/json;charset=UTF-8")
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));

    String resultString = result.andReturn().getResponse().getContentAsString();

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    String restaurantId = jsonParser.parseMap(resultString).get("id").toString();

    mockMvc.perform(get("/api/restaurants/" + restaurantId)
            .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk());
  }

  @Test
  public void testDeleteRestaurant() throws Exception {
    String accessToken = obtainAccessToken("timurt", "123");

    String restaurantString = "{\n"
            + "\t\"name\" : \"Hello World\",\n"
            + "\t\"description\" : \"Hello World Description\",\n"
            + "\t\"averageCheck\" : 1.0,\n"
            + "\t\"numberOfSeats\" : 5,\n"
            + "\t\"longitude\" : 5.4,\n"
            + "\t\"latitude\" : 3.2,\n"
            + "\t\"ownerId\" : 2\n"
            + "}";

    ResultActions result
            = mockMvc.perform(post("/api/restaurants")
            .content(restaurantString)
            .contentType("application/json;charset=UTF-8")
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));

    String resultString = result.andReturn().getResponse().getContentAsString();

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    String restaurantId = jsonParser.parseMap(resultString).get("id").toString();

    mockMvc.perform(delete("/api/restaurants/" + restaurantId)
            .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk());
  }
}
