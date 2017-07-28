package de.kasperczyk.rkbudget.register;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocationServiceTest {

    private static final String GERMAN_IP = "88.198.21.11";

    private LocationService locationService;

    @Before
    public void setup() throws IOException {
        locationService = new LocationService();
    }

    @Test
    public void getCountryByIpShouldReturnCorrectCountryCode() throws IOException, GeoIp2Exception {
        String countryCode = locationService.getCountryCodeByIp(GERMAN_IP);
        assertThat(countryCode, is("de"));
    }
}
