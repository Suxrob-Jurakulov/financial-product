package com.company.service;

import com.company.dto.profile.ProfileDto;
import com.company.form.PasswordForm;
import com.company.form.ProfileEditForm;

/**
 * @author Sukhrob
 */

public interface ProfileService {

    ProfileDto getProfile(String id);

    ProfileDto edit(ProfileEditForm form);

    ProfileDto changePassword(PasswordForm form);
}
