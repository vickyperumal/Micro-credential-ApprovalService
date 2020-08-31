package com.example.mc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CREDIT_CARD_APPROVAL_STATUS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardApprovalEntity {

	@Id
	@GeneratedValue(generator ="system-uuid")
	@GenericGenerator(name="system-uuid",strategy = "uuid")
	private String approvalRequestId;
	private String creditCardRequestId;
	private String status;
	private String userEmailId;
	private String approverId;
	
}
