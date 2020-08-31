package com.example.mc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.mc.model.CreditCardApprovalModel;
import com.example.mc.model.CreditCardConfirmation;
import com.example.mc.service.CreditCardApprovalService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
class CreditCardApprovalControllerTest {

	private MockMvc mockMvc;

	private ObjectMapper mapper;

	@MockBean
	private CreditCardApprovalService approvalService;

	@MockBean
	private CreditCardApprovalModel approvalModel;

	@MockBean
	private CreditCardConfirmation confirmation;
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CreditCardApprovalController(approvalService)).build();
		this.mapper = new ObjectMapper();
MockitoAnnotations.initMocks(this);
		approvalModel = new CreditCardApprovalModel();
		approvalModel.setApprovalRequestId("approvalRequestId1");
		approvalModel.setCreditCardRequestId("requestId1");
		approvalModel.setStatus("APPROVED");
		approvalModel.setUserEmailId("abc@xyz.com");
		approvalModel.setApproverId("MANAGER");
	}

	@Test
	void saveCreditCardApproval() throws Exception {
		when(approvalService.saveCreditCardApproval(Mockito.any(CreditCardApprovalModel.class))).thenReturn("saved");
		MvcResult result = this.mockMvc.perform(post("/approval/saveCreditCard").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(approvalModel))).andReturn();
		assertNotNull(result.getResponse(), "");
	}

	@Test
	void saveCreditCardApprovalNegative() throws Exception {
		MvcResult result = this.mockMvc.perform(post("/approval/saveCreditCard")).andReturn();
		assertNotNull(result.getResponse(), "");
	}

	@Test
	void approveOrRejectCreditCard() throws Exception {
		CreditCardConfirmation confirmation=CreditCardConfirmation.builder().remarks("qwer").requestId("12345").status("APPROVED").build();
		MvcResult result =	this.mockMvc
				.perform(put("/approval/creditCardDecison").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(confirmation)))
				.andExpect(status().isOk()).andReturn();
		assertNotNull(result.getResponse().getContentAsString(),"");
	}

	@Test
	void approveOrRejectCreditCardNegativeScenario() throws Exception {
		MvcResult result = this.mockMvc.perform(put("/approval/creditCardDecison")).andReturn();
		assertNotNull(result.getResponse(), "");
	}

}
