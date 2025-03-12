package com.company.service;

import com.company.dto.ProfileDto;
import com.company.form.ProfileForm;

/**
 * @author Sukhrob
 */
public interface AuthService {

    ProfileDto check(String username);

    ProfileDto add(ProfileForm form);

    ProfileDto getById(String id);
}