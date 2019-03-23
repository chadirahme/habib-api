package com.habib.api;

import com.habib.api.domains.Employee;
import com.habib.api.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HabibApiApplicationTests {

	//@Autowired
	//private MockMvc mvc;
	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	EmployeeRepository employeeRepository;

	//@Test
	public void contextLoads() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}
	//@Test
	public void givenEmployees_whenGetEmployees_thenStatus200()
			throws Exception {

		//createTestEmployee("bob");

		String uri = "/rest-employees/all";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		//Employee[] productlist = super.mapFromJson(content, Employee[].class);
		//assertTrue(productlist.length > 0);

	}

	@Test
	public void whenFindAll() {

		assertThat(employeeRepository.findAll().size()).isEqualTo(3);
	}
}
