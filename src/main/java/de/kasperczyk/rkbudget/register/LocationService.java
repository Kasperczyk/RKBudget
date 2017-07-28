package de.kasperczyk.rkbudget.register;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class LocationService {

    private DatabaseReader dbReader;

    LocationService() throws IOException {
        File countryDatabase = new File("src/main/resources/geoDB/GeoLite2-Country.mmdb");
        this.dbReader = new DatabaseReader.Builder(countryDatabase).build();
    }

    String getCountryCodeByIp(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse countryResponse = dbReader.country(ipAddress);
            Country country = countryResponse.getCountry();
            return country.getIsoCode().toLowerCase();
        } catch (IOException | GeoIp2Exception e) {
            // todo
            return "de";
        }
    }
}
