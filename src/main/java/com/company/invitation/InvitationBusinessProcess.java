package com.company.invitation;

import com.company.invitation.client.CountriesService;
import com.company.invitation.client.PartnersService;
import com.company.invitation.domain.Countries;
import com.company.invitation.domain.Country;
import com.company.invitation.domain.Partners;
import com.company.invitation.domain.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.time.LocalDate.parse;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class InvitationBusinessProcess {
    @Autowired
    private final PartnersService partnersService;
    @Autowired
    private final CountriesService countriesService;

    public InvitationBusinessProcess(PartnersService partnersService, CountriesService countriesService) {
        this.partnersService = partnersService;
        this.countriesService = countriesService;
    }

    public ResponseEntity<Map> processInvitation() {
        Partners partners = partnersService.getPartners();
        Countries countries = processInvitation(partners.getPartners());
        return countriesService.postCountries(countries);
    }

    Countries processInvitation(List<Partner> partners) {
        List<Country> countryList = new ArrayList<>();
        Map<String, List<Partner>> countryPartnersMap = partners.stream().collect(groupingBy(Partner::getCountry));
        for (Map.Entry<String, List<Partner>> countryPartners : countryPartnersMap.entrySet()) {
            Map<LocalDate, List<String>> availableDatesMap = new TreeMap<>();
            countryPartners.getValue().forEach(partner ->
                partner.getAvailableDates().forEach(date ->
                        availableDatesMap.computeIfAbsent(parse(date), k -> new ArrayList<>()).add(partner.getEmail())));

            int attendeeCount = 0;
            String startDate = null;
            List<String> attendees = new ArrayList<>();
            for (Map.Entry<LocalDate, List<String>> availableDatesAttendeesEntry : availableDatesMap.entrySet()) {
                LocalDate currentDay = availableDatesAttendeesEntry.getKey();
                List<String> attendeesCurrentDay = availableDatesAttendeesEntry.getValue();
                List<String> attendeesNextDay = availableDatesMap.get(currentDay.plusDays(1));
                List<String> attendeeList = attendeesNextDay != null ? attendeesCurrentDay.stream().filter(attendeesNextDay::contains).collect(toList()) : emptyList();

                if (attendeeList.size() > attendeeCount) {
                    attendeeCount = attendeeList.size();
                    startDate = currentDay.toString();
                    attendees = attendeeList;
                }
            }
            countryList.add(new Country(attendeeCount, attendees, countryPartners.getKey(), startDate));
        }

        return new Countries(countryList);
    }

}