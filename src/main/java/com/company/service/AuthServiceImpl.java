package com.company.service;

import com.company.domain.Profile;
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
        Optional<Profile> profileOpt = repository.findByPhoneAndDeletedIsFalse(username);
        return profileOpt.map(this::map).orElse(null);
    }

    @Override
    public ProfileDto getById(String id) {
        Optional<Profile> profileOpt = repository.findByIdAndDeletedIsFalse(id);

        return profileOpt.map(this::map).orElse(null);
    }

    @Override
    public ProfileDto add(ProfileForm form) {
        Profile profile = new Profile();

        profile.setId(UUID.randomUUID().toString());
        profile.setFirstname(form.getFirstname());
        profile.setLastname(form.getLastname());
        profile.setEmail(form.getEmail());
        profile.setPhone(form.getPhone());
        profile.setPassword(form.getPassword());
        profile.setRole(Role.ROLE_USER);
        profile.setCreatedDate(LocalDateTime.now());
        profile.setDeleted(false);

        repository.save(profile);
        return map(profile);
    }

    // MAPPER
    private ProfileDto map(Profile profile) {
        ProfileDto dto = new ProfileDto();

        dto.setId(profile.getId());
        dto.setFirstname(profile.getFirstname());
        dto.setLastname(profile.getLastname());
        dto.setEmail(profile.getEmail());
        dto.setPhone(profile.getPhone());
        dto.setPassword(profile.getPassword());
        dto.setCreatedDate(profile.getCreatedDate());
        dto.setModules(profile.getModules());

        return dto;
    }

}