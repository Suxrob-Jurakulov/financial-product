package com.company.service;

import com.company.domain.Profile;
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
        Optional<Profile> profileOpl = repository.findById(id);
        return profileOpl.map(this::map).orElse(null);
    }

    @Override
    public ProfileDto edit(ProfileEditForm form) {
        Optional<Profile> profileOpl = repository.findById(form.getProfileId());
        if (profileOpl.isPresent()) {
            Profile profile = profileOpl.get();

            profile.setFirstname(form.getFirstname());
            profile.setLastname(form.getLastname());
            profile.setEmail(profile.getEmail());

            return map(repository.save(profile));
        }
        return null;
    }

    @Override
    public ProfileDto changePassword(PasswordForm form) {
        Optional<Profile> profileOpl = repository.findById(form.getProfileId());
        if (profileOpl.isPresent()) {
            Profile profile = profileOpl.get();

            profile.setPassword(form.getPassword());

            return map(repository.save(profile));
        }
        return null;
    }


    // MAPPER
    private ProfileDto map(Profile profile) {
        ProfileDto dto = new ProfileDto();

        dto.setId(profile.getId());
        dto.setFirstname(profile.getFirstname());
        dto.setLastname(profile.getLastname());
        dto.setEmail(profile.getEmail());
        dto.setPhone(profile.getPhone());
        dto.setCreatedDate(profile.getCreatedDate());

        return dto;
    }
}
