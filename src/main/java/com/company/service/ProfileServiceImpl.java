package com.company.service;

import com.company.domain.UserProfile;
import com.company.dto.profile.ProfileDto;
import com.company.form.PasswordForm;
import com.company.form.ProfileEditForm;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Sukhrob
 */

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;

    @Override
    public ProfileDto getProfile(String id) {
        Optional<UserProfile> profileOpl = repository.findById(id);
        return profileOpl.map(this::map).orElse(null);
    }

    @Override
    public ProfileDto edit(ProfileEditForm form) {
        Optional<UserProfile> profileOpl = repository.findById(form.getId());
        if (profileOpl.isPresent()) {
            UserProfile userProfile = profileOpl.get();

            userProfile.setFirstname(form.getFirstname());
            userProfile.setLastname(form.getLastname());
            userProfile.setEmail(userProfile.getEmail());

            return map(repository.save(userProfile));
        }
        return null;
    }

    @Override
    public ProfileDto changePassword(PasswordForm form) {
        Optional<UserProfile> profileOpl = repository.findById(form.getId());
        if (profileOpl.isPresent()) {
            UserProfile userProfile = profileOpl.get();

            userProfile.setPassword(form.getPassword());

            return map(repository.save(userProfile));
        }
        return null;
    }


    // MAPPER
    private ProfileDto map(UserProfile userProfile) {
        ProfileDto dto = new ProfileDto();

        dto.setId(userProfile.getId());
        dto.setFirstname(userProfile.getFirstname());
        dto.setLastname(userProfile.getLastname());
        dto.setEmail(userProfile.getEmail());
        dto.setPhone(userProfile.getPhone());
        dto.setCreatedDate(userProfile.getCreatedDate());

        return dto;
    }
}
