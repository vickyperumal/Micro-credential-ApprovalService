package com.example.mc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreditCardApprovalModel {

	private String approvalRequestId;
	private String creditCardRequestId;
	private String status;
	private String userEmailId;
	private String approverId;
}
