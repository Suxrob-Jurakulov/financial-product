package com.company.service;

import com.company.domain.UserProfile;
import com.company.domain.Role;
import com.company.dto.profile.ProfileDto;
import com.company.form.ProfileForm;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Sukhrob
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ProfileRepository repository;

    @Override
    public ProfileDto check(String username) {
        Optional<UserProfile> profileOpt = repository.findByPhoneAndDeletedIsFalse(username);
        return profileOpt.map(this::map).orElse(null);
    }

    @Override
    public ProfileDto getById(String id) {
        Optional<UserProfile> profileOpt = repository.findByIdAndDeletedIsFalse(id);

        return profileOpt.map(this::map).orElse(null);
    }

    @Override
    public ProfileDto add(ProfileForm form) {
        UserProfile userProfile = new UserProfile();

        userProfile.setId(UUID.randomUUID().toString());
        userProfile.setFirstname(form.getFirstname());
        userProfile.setLastname(form.getLastname());
        userProfile.setEmail(form.getEmail());
        userProfile.setPhone(form.getPhone());
        userProfile.setPassword(form.getPassword());
        userProfile.setRole(Role.ROLE_USER);
        userProfile.setCreatedDate(LocalDateTime.now());
        userProfile.setDeleted(false);

        repository.save(userProfile);
        return map(userProfile);
    }

    // MAPPER
    private ProfileDto map(UserProfile userProfile) {
        ProfileDto dto = new ProfileDto();

        dto.setId(userProfile.getId());
        dto.setFirstname(userProfile.getFirstname());
        dto.setLastname(userProfile.getLastname());
        dto.setEmail(userProfile.getEmail());
        dto.setPhone(userProfile.getPhone());
        dto.setPassword(userProfile.getPassword());
        dto.setCreatedDate(userProfile.getCreatedDate());
        dto.setModules(userProfile.getModules());

        return dto;
    }

}