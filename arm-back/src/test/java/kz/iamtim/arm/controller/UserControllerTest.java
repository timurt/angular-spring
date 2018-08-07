package kz.iamtim.arm.controller;

import kz.iamtim.arm.controllers.ApartmentController;
import kz.iamtim.arm.models.Apartment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * ApartmentControllerTest class will test all APIs in ApartmentController.java.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

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
  public void testSaveUser() throws Exception {
    String accessToken = obtainAccessToken("timurt", "123");
    System.out.println(accessToken);

    String userString = "{\"login\":\"jim@yahoo.com" + System.currentTimeMillis() +
            "\",\"name\":\"Jim\", \"password\":\"Jim\", "
            + "\"roleType\":\"Client\"}";

    mockMvc.perform(post("/users")
            .header("Authorization", "Bearer " + accessToken)
            .content(userString)
            .contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk());
  }

  @Test
  public void testSaveWrongUser() throws Exception {
    String accessToken = obtainAccessToken("timurt", "123");
    System.out.println(accessToken);

    String userString = "{\"name\":\"Jim\", \"password\":\"Jim\", "
            + "\"roleType\":\"Client\"}";

    mockMvc.perform(post("/users")
            .header("Authorization", "Bearer " + accessToken)
            .content(userString)
            .contentType("application/json;charset=UTF-8"))
            .andExpect(status().isBadRequest());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
