package com.campusconnect.backend.service;

import com.campusconnect.backend.model.Registration;
import com.campusconnect.backend.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepo;

    public List<Registration> getAll() {
        return registrationRepo.findAll();
    }

    public Registration create(Registration reg) {
        registrationRepo.findByUserIdAndActivityId(reg.getUserId(), reg.getActivityId())
            .ifPresent(r -> { throw new RuntimeException("Already registered"); });
        return registrationRepo.save(reg);
    }

    public Registration updateStatus(Integer id, String status) {
        Registration reg = registrationRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Registration not found"));
        reg.setStatus(Registration.Status.valueOf(status));
        return registrationRepo.save(reg);
    }

    public void delete(Integer id) {
        registrationRepo.deleteById(id);
    }
}
