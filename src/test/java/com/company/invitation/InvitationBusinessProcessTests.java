package com.company.invitation;

import com.company.invitation.domain.Countries;
import com.company.invitation.domain.Country;
import com.company.invitation.domain.Partner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = "feign.hystrix.enabled=false")
public class InvitationBusinessProcessTests {

	@Autowired
	private InvitationBusinessProcess service;

	@Test
	public void whenExecuteProcessInvitationThenReturnResponseEntity() {
		ResponseEntity<Map> response = service.processInvitation();
		int expected = HttpStatus.OK.value();
		int current = response.getStatusCodeValue();
		Assert.assertEquals(expected, current);
	}

	@Test
	public void givenPartnersWhenProcessInvitationThenReturnCountries() {
		String countryName = "Ireland";
		Country country = new Country(2, Arrays.asList("test1@email","test2@email"), countryName, "2019-01-01");
		Countries expected = new Countries(Collections.singletonList(country));

		Partner partner1 = new Partner("firstName", "lastName", "test1@email", countryName, Arrays.asList("2018-12-31","2019-01-01", "2019-01-02"));
		Partner partner2 = new Partner("firstName", "lastName", "test2@email", countryName, Arrays.asList("2019-01-01", "2019-01-02", "2019-01-03"));
		List<Partner> partners = Arrays.asList(partner1, partner2);
		Countries actual = service.processInvitation(partners);

		Assert.assertEquals(expected, actual);
	}
}