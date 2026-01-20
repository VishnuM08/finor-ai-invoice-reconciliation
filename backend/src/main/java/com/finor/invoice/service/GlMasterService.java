package com.finor.invoice.service;

import com.finor.invoice.entity.GlAccount;
import com.finor.invoice.repository.GlAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlMasterService {

    private final GlAccountRepository glRepo;

    public GlAccount create(GlAccount gl) {
        return glRepo.save(gl);
    }

    public List<GlAccount> getAll() {
        return glRepo.findAll();
    }

    public void delete(Long id) {
        glRepo.deleteById(id);
    }
}
