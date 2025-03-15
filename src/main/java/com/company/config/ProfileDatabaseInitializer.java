package com.company.config;


import com.company.domain.Profile;
import com.company.domain.Role;
import com.company.repository.ProfileRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileDatabaseInitializer {

    public static final String ADMIN_PHONE = "998907777777";
    public static final String ADMIN_PASSWORD = "123";
    public static final String ADMIN_PROFILE_ID = "66fcd807-6026-486a-a212-47ad51139921";

    public static final String PROFILE_PHONE = "998901234567";
    public static final String PROFILE_PASSWORD = "123";
    public static final String PROFILE_ID = "66fcd807-6026-486a-a212-47ad51139922";

    public static final String PROFILE2_PHONE = "998901234568";
    public static final String PROFILE2_PASSWORD = "123";
    public static final String PROFILE2_ID = "66fcd807-6026-486a-a212-47ad51139923";

    private final ProfileRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        if (repository.findByPhoneAndDeletedIsFalse(ADMIN_PHONE).isEmpty()) {
            Profile profile = new Profile();

            profile.setId(ADMIN_PROFILE_ID);
            profile.setFirstname("Admin");
            profile.setFirstname("Adminov");
            profile.setEmail("admin@gmail.com");
            profile.setPhone(ADMIN_PHONE);
            profile.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            profile.setRole(Role.ROLE_ADMIN);
            profile.setCreatedDate(LocalDateTime.now());
            profile.setDeleted(false);

            repository.save(profile);
            log.info("Initial profile saved {}", ADMIN_PHONE);
        }

        if (repository.findByPhoneAndDeletedIsFalse(PROFILE_PHONE).isEmpty()) {
            Profile profile = new Profile();

            profile.setId(PROFILE_ID);
            profile.setFirstname("User");
            profile.setFirstname("Userov");
            profile.setEmail("user@gmail.com");
            profile.setPhone(PROFILE_PHONE);
            profile.setPassword(passwordEncoder.encode(PROFILE_PASSWORD));
            profile.setRole(Role.ROLE_USER);
            profile.setCreatedDate(LocalDateTime.now());
            profile.setDeleted(false);

            repository.save(profile);
            log.info("Initial profile saved {}", PROFILE_PHONE);
        }

        if (repository.findByPhoneAndDeletedIsFalse(PROFILE2_PHONE).isEmpty()) {
            Profile profile = new Profile();

            profile.setId(PROFILE2_ID);
            profile.setFirstname("User2");
            profile.setFirstname("Userov2");
            profile.setEmail("user2@gmail.com");
            profile.setPhone(PROFILE2_PHONE);
            profile.setPassword(passwordEncoder.encode(PROFILE2_PASSWORD));
            profile.setRole(Role.ROLE_USER);
            profile.setCreatedDate(LocalDateTime.now());
            profile.setDeleted(false);

            repository.save(profile);
            log.info("Initial profile saved {}", PROFILE2_PHONE);
        }
    }

}
