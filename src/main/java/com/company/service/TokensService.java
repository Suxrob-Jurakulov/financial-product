package com.company.service;

import com.company.dto.profile.ProfileDto;
import com.company.dto.auth.TokenDto;

/**
 * @author Sukhrob
 */
public interface TokensService {
    TokenDto add(ProfileDto dto);

    TokenDto getRefreshById(String tokenId);
}
