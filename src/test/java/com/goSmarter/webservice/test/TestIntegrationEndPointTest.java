package com.goSmarter.webservice.test;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:META-INF/spring/test-webservice/test-webservice-integration-config.xml",
		"classpath:config/test-webservice-beans-config-test.xml" })
public class TestIntegrationEndPointTest {

	@Autowired
	private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;

	@Before
	public void createClient() {
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}

	@Test
	public void testWsEndPointTest() throws Exception {
		Source requestPayload = new StringSource(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<v1:TestServiceRequest xmlns:v1=\"http://mycompany.com/it/enterprise/contract/TestService/v1\" xmlns:v11=\"http://mycompany.com/it/enterprise/data/v1\">" +
				"<v11:Document><v11:Id>101</v11:Id><v11:Type>MaterialMaster</v11:Type></v11:Document>" +
				"</v1:TestServiceRequest>");

		Source responsePayload = new StringSource(
				"<testServiceResponseType xmlns=\"http://mycompany.com/it/enterprise/data/v1\" xmlns:ns2=\"http://mycompany.com/it/enterprise/contract/TestService/v1\"><Document><Result>SUCCESS</Result></Document></testServiceResponseType>");

		mockClient.sendRequest(withPayload(requestPayload)).andExpect(
				payload(responsePayload));
	}
}