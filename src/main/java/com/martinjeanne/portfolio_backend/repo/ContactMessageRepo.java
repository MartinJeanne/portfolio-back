package com.martinjeanne.portfolio_backend.repo;

import com.martinjeanne.portfolio_backend.model.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepo extends JpaRepository<ContactMessage, Integer> {
}
