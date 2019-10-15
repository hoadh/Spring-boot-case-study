package cg.wbd.grandemonstration.formatter;


import com.codegym.model.Location;
import com.codegym.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class LocationFormatter implements Formatter<Location> {
    private LocationService locationService;

    public LocationFormatter(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public Location parse(String text, Locale locale) throws ParseException {
        return locationService.findOne(Long.valueOf(text));
    }

    @Override
    public String print(Location object, Locale locale) {
        return object.toString();
    }
}
