package com.campusconnect.backend.service;

import com.campusconnect.backend.model.Activity;
import com.campusconnect.backend.repository.ActivityRepository;
import com.campusconnect.backend.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepo;
    private final RegistrationRepository registrationRepo;

    public List<Activity> getAll() {
        return activityRepo.findAll();
    }

    public Activity create(Activity activity) {
        return activityRepo.save(activity);
    }

    public Activity update(Integer id, Activity updates) {
        Activity existing = activityRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Activity not found"));

        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getDescription() != null) existing.setDescription(updates.getDescription());
        if (updates.getDate() != null) existing.setDate(updates.getDate());
        if (updates.getCategory() != null) existing.setCategory(updates.getCategory());

        return activityRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        registrationRepo.deleteByActivityId(id);
        activityRepo.deleteById(id);
    }
}
