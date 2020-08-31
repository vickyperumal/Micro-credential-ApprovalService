package com.example.mc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mc.model.CreditCardApprovalModel;
import com.example.mc.model.CreditCardConfirmation;
import com.example.mc.service.CreditCardApprovalService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 605750 
 * This controller used for saving/approval details
 */
@RestController
@RequestMapping("/v1/approval")
@Slf4j
public class CreditCardApprovalController {
	
	private CreditCardApprovalService approvalService;
	
	

	public CreditCardApprovalController(CreditCardApprovalService approvalService) {
		super();
		this.approvalService = approvalService;
	}

	/**
	 * @param approvalModel
	 * @return response
	 */
	@PostMapping("/saveCreditCard")
	public String saveCreditCardApporvalDetails(@RequestBody CreditCardApprovalModel approvalModel) {
		log.info("Saving creditCard approval info for {}",approvalModel.getApprovalRequestId());
		String response="";
		if(approvalModel!=null) {
			approvalService.saveCreditCardApproval(approvalModel);

	}
		return response;
}

	/**
	 * @param confirmation
	 * @return
	 */
	@PutMapping("/creditCardDecison")
	public String creditCardConfirmation(@RequestBody CreditCardConfirmation confirmation) {
		log.info("Credit Card decision for {}",confirmation.getRequestId());
		return approvalService.approveOrRejectCreditCard(confirmation);
		
	}
}