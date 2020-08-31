package com.example.mc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mc.entity.CreditCardApprovalEntity;

@Repository
public interface CreditCardApprovalRepo extends JpaRepository<CreditCardApprovalEntity,String>{

	Optional<CreditCardApprovalEntity> findByApprovalRequestId(String requestId);

}
