package com.example.mc.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.mc.entity.CreditCardApprovalEntity;
import com.example.mc.model.CreditCardApprovalModel;
import com.example.mc.model.CreditCardConfirmation;
import com.example.mc.repo.CreditCardApprovalRepo;

@ExtendWith(SpringExtension.class)
 class CreditCardApprovalServiceImplTest {

	@InjectMocks
	private CreditCardApprovalService creditCardApprovalService;
	
	@Mock
	private ModelMapper modelMapper;
	
	private CreditCardApprovalEntity creditCardApprovalEntity;
	
	@Mock
	private CreditCardApprovalRepo approvalRepo;
	
	@MockBean
	private CreditCardApprovalModel approvalModel;
	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		creditCardApprovalEntity=new CreditCardApprovalEntity();
		approvalModel = new CreditCardApprovalModel();
		approvalModel.setApprovalRequestId("approvalRequestId1");
		approvalModel.setCreditCardRequestId("requestId1");
		approvalModel.setStatus("APPROVED");
		approvalModel.setUserEmailId("abc@xyz.com");
		approvalModel.setApproverId("MANAGER");
		
		creditCardApprovalEntity=new CreditCardApprovalEntity();
		//creditCardApprovalEntity.setApproverId("approvalRequestId1");
		creditCardApprovalEntity.setApprovalRequestId("approvalRequestId1");
	}
	
	@Test
	void saveCreditCardApproval() {
		when(modelMapper.map(Mockito.any(CreditCardApprovalModel.class),Mockito.eq(CreditCardApprovalEntity.class)))
		.thenReturn(creditCardApprovalEntity);
		when(approvalRepo.save(Mockito.any(CreditCardApprovalEntity.class))).thenReturn(creditCardApprovalEntity);
		String response=creditCardApprovalService.saveCreditCardApproval(approvalModel);
		assertNotNull(response);
		verify(approvalRepo, times(1)).save(Mockito.any(CreditCardApprovalEntity.class));
		
	}
	
	@Test
	void approveOrRejectCreditCard(){
		CreditCardConfirmation confirmation=new CreditCardConfirmation();
		confirmation.setRequestId("approvalRequestId1");
		confirmation.setStatus("APPROVED");
		when(approvalRepo.findByApprovalRequestId(Mockito.anyString())).thenReturn(Optional.of(creditCardApprovalEntity));
		when(approvalRepo.save(Mockito.any(CreditCardApprovalEntity.class))).thenReturn(creditCardApprovalEntity);
		when(restTemplate.postForObject(Mockito.anyString(),Mockito.any(CreditCardApprovalModel.class),Mockito.any())).thenReturn("");
		String response = creditCardApprovalService.approveOrRejectCreditCard(confirmation);
		assertNotNull(response);
		verify(approvalRepo, times(1)).findByApprovalRequestId(Mockito.anyString());
		verify(approvalRepo, times(1)).save(Mockito.any(CreditCardApprovalEntity.class));
	}
}
