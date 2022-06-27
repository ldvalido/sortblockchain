package com.rooftop.challenge;

import com.rooftop.challenge.Service.SortService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@SpringBootTest
@ContextConfiguration(classes = BasicConfiguration.class)
class SortblockchainApplicationIntegrationTests {

	@Autowired
	SortService sortService;

	@Test
	void BasicTest() {

		String[] result = new String[]{"f319", "3720", "4e3e", "46ec", "c7df", "c1c7", "80fd", "c4ea"};

		String token = "b93ac073-eae4-405d-b4ef-bb82e0036a1d";

		String[] expected = new String[]{"f319", "46ec", "c1c7", "3720", "c7df", "c4ea", "4e3e", "80fd"};
		String[] realResult = sortService.check(result, token );
		Assert.assertNotEquals(expected, realResult);
	}

}
