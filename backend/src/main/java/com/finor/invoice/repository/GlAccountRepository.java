package com.finor.invoice.repository;

import com.finor.invoice.entity.GlAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GlAccountRepository extends JpaRepository<GlAccount, Long> {
    Optional<GlAccount> findByGlCode(String glCode);
}
