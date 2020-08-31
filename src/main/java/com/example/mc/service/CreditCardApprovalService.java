package com.example.mc.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.mc.entity.CreditCardApprovalEntity;
import com.example.mc.model.CreditCardApprovalModel;
import com.example.mc.model.CreditCardConfirmation;
import com.example.mc.repo.CreditCardApprovalRepo;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 605750
 *CreditCardApprovalService to approve/reject
 */

@Component
@Slf4j
public class CreditCardApprovalService implements CreditCardApproval {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private CreditCardApprovalRepo approvalRepo;

	@Autowired
	private JavaMailSender sender;

	@Value("${userservice.url}")
	private String userServiceUrl;

	@Value("${notification.sender}")
	private String emailReciepients;

	public static final String APPROVED = "APPROVED";

	public static final String REJECTED = "REJECTED";

	public static final String EMAIL_STATUS = "EMAIL SENT SUCCESSFULLY";

	public static final String EMAIL_SUBJECT = "STATUS -Credit Card";

	public static final String STATUS = "SAVED SUCCESSFULLY";

	
	/**
	 *param approvalModel
	 *return status
	 */
	@Override
	public String saveCreditCardApproval(CreditCardApprovalModel approvalModel) {
		CreditCardApprovalEntity entity = modelMapper.map(approvalModel, CreditCardApprovalEntity.class);
		log.info("Saving approval details for {}",approvalModel.getApprovalRequestId());
		approvalRepo.save(entity);
		return STATUS;
	}
	/**
	 *CreditCardConfirmation
	 *email status
	 */
	@Override
	public String approveOrRejectCreditCard(CreditCardConfirmation confirmation) {
		log.info("Credit card decision");
		if (confirmation != null) {
			Optional<CreditCardApprovalEntity> approvalEntity = approvalRepo
					.findByApprovalRequestId(confirmation.getRequestId());
			if (approvalEntity.isPresent()) {
				CreditCardApprovalEntity creditCardApprovalEntity = approvalEntity.get();
				if (APPROVED.equalsIgnoreCase(confirmation.getStatus()))
					creditCardApprovalEntity.setStatus(APPROVED);

				else if (REJECTED.equalsIgnoreCase(confirmation.getStatus()))
					creditCardApprovalEntity.setStatus(REJECTED);
				CreditCardConfirmation request = CreditCardConfirmation.builder()
						.requestId(creditCardApprovalEntity.getCreditCardRequestId()).status(confirmation.getStatus())
						.build();
				approvalRepo.save(creditCardApprovalEntity);

				restTemplate.postForEntity(userServiceUrl, request, String.class);

			}
			log.info("Credit card final status -->{}",confirmation.getStatus());
		}
		return EMAIL_STATUS;

	}

	void sendEmail(String status, String requestId) {
     log.info("Sending notification");
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(emailReciepients);
		msg.setTo(emailReciepients);
		msg.setSubject(EMAIL_SUBJECT);
		msg.setText("Dear Customer  Your CreditCard " + requestId + " has been " + status);
		sender.send(msg);

	}

}
