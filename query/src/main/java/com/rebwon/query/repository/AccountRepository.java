package com.rebwon.query.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rebwon.query.entity.HolderAccountSummary;

public interface AccountRepository extends JpaRepository<HolderAccountSummary, String> {
	Optional<HolderAccountSummary> findByHolderId(String holderId);
}
