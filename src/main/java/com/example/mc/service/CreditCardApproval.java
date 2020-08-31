package com.example.mc.service;

import com.example.mc.model.CreditCardApprovalModel;
import com.example.mc.model.CreditCardConfirmation;

public interface CreditCardApproval {

	public String saveCreditCardApproval(CreditCardApprovalModel approvalModel);
	
	public String approveOrRejectCreditCard(CreditCardConfirmation confirmation);
}
