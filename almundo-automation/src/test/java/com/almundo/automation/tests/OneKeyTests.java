package com.almundo.automation.tests;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.almundo.automation.entities.Geonames;
import com.almundo.automation.services.SearchController;

public class OneKeyTests {

	@Test(description = "Spring REST services", groups = { "spring-REST" })
	public void verifyREST() {
		SearchController searchController = new SearchController();
		// SearchFlights sf = searchController.retrieveFlights();
	}

	@Test(description = "Spring REST services", groups = { "spring-REST" })
	public void prueba() {
		String url = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
//		headers.set("X-UOW", "gbl-agustin");
//		headers.set("X-ApiKey", "5592f8fd99325b40cba48649");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Geonames geonames= template.getForObject(url, Geonames.class, entity);
		
//		ResponseEntity<Geonames> response = template.exchange(url,
//				HttpMethod.GET, entity, Geonames.class);
//		Geonames sf = response.getBody();
		// System.out.println(sf.getKey() + " " + sf.getOne());
		System.out.println(geonames.getLocal().get(0).getCountrycode());
	}
}
