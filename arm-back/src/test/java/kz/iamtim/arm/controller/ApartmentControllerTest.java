package kz.iamtim.arm.controller;

import kz.iamtim.arm.controllers.ApartmentController;
import kz.iamtim.arm.models.Apartment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


/**
 * ApartmentControllerTest class will test all APIs in ApartmentController.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApartmentControllerTest {

  MockMvc mockMvc;

  @Mock
  private ApartmentController apartmentController;

  @Autowired
  private TestRestTemplate template;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(apartmentController).build();
  }

  @Test
  public void testApartmentShouldBeRegistered() throws Exception {
    HttpEntity<Object> apartment = getHttpEntity(
        "{"
                + "\"name\" : \"Hello World\",\n"
                + "\"description\" : \"TIMUR\",\n"
                + "\"floorAreaSize\" : \"37.3\",\n"
                + "\"pricePerMonth\" : \"200\",\n"
                + "\"numberOfRooms\" : \"3\",\n"
                + "\"latitude\" : \"1.1\",\n"
                + "\"longitude\" : \"2.2\"\n"
                + "}");
    ResponseEntity<Apartment> response = template.postForEntity(
        "/apartments", apartment, Apartment.class);
    Assert.assertEquals(200, response.getStatusCode().value());
  }

  @Test
  public void testApartmentShouldNotBeRegistered() throws Exception {
    HttpEntity<Object> apartment = getHttpEntity(
            "{"
                    + "\"name\" : \"Hello World\",\n"
                    + "\"description\" : \"TIMUR\",\n"
                    + "\"floorAreaSize\" : \"37.3\",\n"
                    + "\"pricePerMonth\" : \"200\",\n"
                    + "\"latitude\" : \"1.1\",\n"
                    + "\"longitude\" : \"2.2\"\n"
                    + "}");
    ResponseEntity<Apartment> response = template.postForEntity(
            "/apartments", apartment, Apartment.class);
    Assert.assertEquals(500, response.getStatusCode().value());
  }

//  @Test
//  public void testSaveHourlyElectricityForExistingApartment() throws Exception {
//    final String apartment = "1234567890123456";
//    HttpEntity<Object> hourlyElectricity = getHttpEntity(
//            "{\"generatedElectricity\": \"123\", \"readingAt\": \"2017-05-21 19:30:00\" }");
//    ResponseEntity<HourlyElectricity> response = template.postForEntity(
//            "/api/apartments/{apartment-serial}/hourly", hourlyElectricity, HourlyElectricity.class,
//            apartment);
//    Assert.assertEquals(200, response.getStatusCode().value());
//  }
//
//  @Test
//  public void testSaveHourlyElectricityForNonExistingApartment() throws Exception {
//    final String apartment = "abcXYZ$#@123qwer";
//    HttpEntity<Object> hourlyElectricity = getHttpEntity(
//            "{\"generatedElectricity\": \"123\", \"readingAt\": \"2017-05-21 19:30:00\" }");
//    ResponseEntity<HourlyElectricity> response = template.postForEntity(
//            "/api/apartments/{apartment-serial}/hourly", hourlyElectricity, HourlyElectricity.class,
//            apartment);
//    Assert.assertEquals(404, response.getStatusCode().value());
//  }
//
//  @Test
//  public void testGetHourlyElectricityForExistingApartment() throws Exception {
//    final String apartment = "1234567890123456";
//    ResponseEntity<HourlyElectricity> response = template.getForEntity(
//            "/api/apartments/{apartment-serial}/hourly", HourlyElectricity.class,
//            apartment);
//    Assert.assertEquals(200, response.getStatusCode().value());
//  }
//
//  @Test
//  public void testGetHourlyElectricityForNonExistingApartment() throws Exception {
//    final String apartment = "abcXYZ$#@123qwer";
//    ResponseEntity<HourlyElectricity> response = template.getForEntity(
//            "/api/apartments/{apartment-serial}/hourly", HourlyElectricity.class,
//            apartment);
//    Assert.assertEquals(404, response.getStatusCode().value());
//  }

//  @Test
//  public void testAllDailyElectricityFromYesterdayForExistingApartment() throws Exception {
//    final String apartment = "1234567890123456";
//    ResponseEntity<List> response = template.getForEntity(
//            "/api/apartments/{apartment-serial}/daily", List.class,
//            apartment);
//    Assert.assertEquals(200, response.getStatusCode().value());
//  }
//
//  @Test
//  public void testAllDailyElectricityFromYesterdayForNonExistingApartment() throws Exception {
//    final String apartment = "abcXYZ$#@123qwer";
//    ResponseEntity<List> response = template.getForEntity(
//            "/api/apartments/{apartment-serial}/daily", List.class,
//            apartment);
//    Assert.assertEquals(404, response.getStatusCode().value());
//  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
