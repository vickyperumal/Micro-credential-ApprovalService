package com.example.mc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.mc.controller.CreditCardApprovalController;
import com.example.mc.service.CreditCardApprovalService;

@SpringBootTest
@TestPropertySource(properties = {"service.creditCard=credit-card-service"})
class CreditcardApprovalServiceApplicationTests {

	@Autowired
	private CreditCardApprovalController creditCardApprovalController;
	
	@Autowired
	private CreditCardApprovalService creditCardApprovalService;

	@Test
	void contextLoads() {
		assertNotNull(creditCardApprovalController, "Controller is null");
		assertNotNull(creditCardApprovalService, "Service is null");
	}
	

}
