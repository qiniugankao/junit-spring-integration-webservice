package com.mycompany.it.eis.webservice.ws;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.mycompany.it.eis.webservice.dto.TestObject;
import com.mycompany.it.enterprise.data.v1.TestServiceRequestType;
import com.mycompany.it.enterprise.data.v1.TestServiceResponseType;
import com.mycompany.it.enterprise.data.v1.TestServiceResponseType.Document;

/**
 * 
 * @author Kanchan
 * 
 */
public class TestServiceActivator {

	private static Logger logger = Logger
			.getLogger(TestServiceActivator.class);

	@Autowired
	SqlMapClientTemplate ibatisTemplate;

	public TestServiceResponseType processRequest(JAXBElement element)
			throws Exception {

		TestServiceRequestType request = (TestServiceRequestType) element
				.getValue();

		String status = "SUCCESS";

		String type = request.getDocument().getType();
		String id = request.getDocument().getId();

		TestObject notifyObject = new TestObject();
		notifyObject.setId(id);
		notifyObject.setType(type);

		ibatisTemplate.insert("testInsert", notifyObject);

		TestServiceResponseType response = new TestServiceResponseType();

		Document doc = new Document();
		doc.setResult(status);
		response.setDocument(doc);

		logger.debug("Successfully saved request");

		return response;
	}

}
