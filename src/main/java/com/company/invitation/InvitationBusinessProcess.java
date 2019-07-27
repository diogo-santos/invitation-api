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
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.containsAny;

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

    public int processInvitation() {
        Partners partners = partnersService.getPartners();
        Countries countries = processInvitation(partners.getPartners());
        ResponseEntity response = countriesService.postCountries(countries);
        return response.getStatusCodeValue();
    }

    public Countries processInvitation(List<Partner> partners) {
        List<Country> countryList = new ArrayList<>();
        Map<String, List<Partner>> partnerMap = partners.stream().collect(groupingBy(Partner::getCountry));

        for (Map.Entry<String, List<Partner>> entry : partnerMap.entrySet()) {
            Country country = new Country();
            country.setName(entry.getKey());

            Map<LocalDate, List<Partner>> availableDatesPartnersMap = new HashMap<>();
            List<Partner> partnersCountry = entry.getValue();
            for(Partner partner : partnersCountry) {
                for (String date : partner.getAvailableDates()) {
                    LocalDate availableDate = LocalDate.parse(date);
                    if (availableDatesPartnersMap.get(availableDate) == null) {
                        availableDatesPartnersMap.put(availableDate, new ArrayList<>(Collections.singleton(partner)));
                    } else {
                        availableDatesPartnersMap.get(availableDate).add(partner);
                    }
                }
            }

            List<LocalDate> datesAvailableSorted = availableDatesPartnersMap.keySet().stream().sorted().collect(toList());
            int maxCountAttendee = 0;
            LocalDate startDateInvitation = null;
            List<String> attendeeEmails = new ArrayList<>();
            for(int i=0; i < datesAvailableSorted.size()-1; i++) {
                LocalDate currentDay = datesAvailableSorted.get(i);
                LocalDate nextDay = i+1 < datesAvailableSorted.size()? datesAvailableSorted.get(i+1) : null;

                List<Partner> partnersCurrentDay = availableDatesPartnersMap.get(currentDay);
                List<Partner> partnersNextDay = nextDay != null? availableDatesPartnersMap.get(nextDay) : null;

                if (nextDay != null && currentDay.isEqual(nextDay.minusDays(1))
                        && containsAny(partnersCurrentDay, partnersNextDay)) {
                    List<Partner> attendees = new ArrayList<>(partnersCurrentDay);
                    attendees.retainAll(partnersNextDay);
                    int countAttendees = attendees.size();
                    if (countAttendees > maxCountAttendee ||
                            (countAttendees == maxCountAttendee && startDateInvitation != null && currentDay.isBefore(startDateInvitation))) {
                        maxCountAttendee = countAttendees;
                        startDateInvitation = currentDay;
                        attendeeEmails = attendees.stream().map(Partner::getEmail).collect(toList());
                    }
                }
            }

            country.setAttendeeCount(maxCountAttendee);
            if (startDateInvitation != null) {
                country.setStartDate(startDateInvitation.toString());
                country.setAttendees(attendeeEmails);
            }
            countryList.add(country);
        }

        return new Countries(countryList);
    }
}