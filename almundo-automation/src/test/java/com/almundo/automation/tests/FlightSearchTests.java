package com.almundo.automation.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.almundo.automation.entities.Airline;
import com.almundo.automation.entities.Cluster;
import com.almundo.automation.entities.Filter;
import com.almundo.automation.entities.Value;
import com.almundo.automation.services.Response;
import com.almundo.automation.utils.DataProviders;

public class FlightSearchTests extends BaseTest {

	@Test(description = "Verifies that the airlines names are not null", groups = { "flight-search" }, dataProvider = "test1", dataProviderClass = DataProviders.class)
	public void verifyAirlineNamesNotNull(Map<String, String> data) {
		this.httpClient.setSearchRequest(data);
		Response response = this.httpClient.post();

		List<Airline> airlines = response.getSearchFlights()
				.getLowestPricesByAirline().getAirlines();

		for (Airline airline : airlines) {
			if (airline.getName().toUpperCase().equals("NULL")) {
				Assert.fail("The airline " + airline.getCode() + " with "
						+ "catalog ID " + airline.getCatalog_id() + " has "
						+ "a null value instead of its name");
			}
		}
	}

	@Test(description = "Verifies that given a number of passengers, "
			+ "the total amount is equal to the detailed amount per passenger", groups = { "flight-search" }, dataProvider = "test1", dataProviderClass = DataProviders.class)
	public void verifyPricesAcordingToNumberOfPax(Map<String, String> data) {
		this.httpClient.setSearchRequest(data);
		Response response = this.httpClient.post();

		List<Cluster> clusters = response.getSearchFlightsAndClusters()
				.getClusters();

		for (Cluster cluster : clusters) {
			float adultPrice = cluster.getPrice().getDetail().getAdultPrice();
			float childPrice = cluster.getPrice().getDetail().getChildPrice();
			float infantPrice = cluster.getPrice().getDetail().getInfantPrice();
			float taxPrice = cluster.getPrice().getDetail().getTaxes();
			float extraTax = cluster.getPrice().getDetail().getExtra_tax();
			float total = cluster.getPrice().getTotal();
			float sum = (adultPrice * Float.parseFloat(data.get("adults")))
					+ (childPrice * Float.parseFloat(data.get("children")))
					+ (infantPrice * Float.parseFloat(data.get("infants")))
					+ taxPrice + extraTax;
			if (total != sum) {
				Assert.fail("The carrier " + cluster.getValidating_carrier()
						+ " should have an amount of " + total
						+ ", but it has " + sum);
			}
		}
	}

	@Test(description = "Verifies that given an international itinerary, "
			+ "the domestic field is false", groups = { "flight-search" }, dataProvider = "test1", dataProviderClass = DataProviders.class)
	public void verifyDomesticFieldFalse(Map<String, String> data) {
		this.httpClient.setSearchRequest(data);
		Response response = this.httpClient.post();

		List<Cluster> clusters = response.getSearchFlightsAndClusters()
				.getClusters();

		for (Cluster cluster : clusters) {
			Assert.assertFalse(
					cluster.isDomestic(),
					"The International itinerary operated by "
							+ cluster.getValidating_carrier()
							+ " has the domestic field TRUE");
		}
	}

	@Test(description = "Verify that the aiports on the filter sections are not null", groups = { "flight-search" }, dataProvider = "test1", dataProviderClass = DataProviders.class)
	public void verifyAirportsOnFiltersResponse(Map<String, String> data) {
		this.httpClient.setSearchRequest(data);
		Response response = this.httpClient.post();

		List<Filter> filters = response.getSearchFlights().getFilters();
		List<Value> values;

		for (Filter filter : filters) {
			values = filter.getValues();
			for (Value value : values) {
				if (value.getCode().equals("null"))
					Assert.fail("The code for " + value.getName() + " is null");
				if (value.getName().equals("null"))
					Assert.fail("The name for " + value.getCode() + " is null");
			}
		}
	}

}