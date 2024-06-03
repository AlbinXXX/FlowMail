package com.flowform.FlowForm.repository;

import com.flowform.FlowForm.model.EmailFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmailFormatRepository extends JpaRepository<EmailFormat, Long> {
    List<EmailFormat> findByDomain(String domain);
}
