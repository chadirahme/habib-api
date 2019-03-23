package com.habib.api;
import com.habib.api.domains.Employee;
import com.habib.api.domains.ListValue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmployeeTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    // @Test
    public void getProductsList() throws Exception {
        String uri = "/rest-employees/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee[] productlist = super.mapFromJson(content, Employee[].class);
        assertTrue(productlist.length > 0);
    }

    @Test
    public void getListValue() throws Exception {
        String uri = "/rest/listvalue";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ListValue[] productlist = super.mapFromJson(content, ListValue[].class);
        assertTrue(productlist.length > 0);
    }
}
