package com.elleined.spring_oauth_social_login.service.authority;

import com.elleined.spring_oauth_social_login.exception.resource.ResourceNotFoundException;
import com.elleined.spring_oauth_social_login.mapper.AuthorityMapper;
import com.elleined.spring_oauth_social_login.model.authority.Authority;
import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import com.elleined.spring_oauth_social_login.repository.authority.AuthorityRepository;
import com.elleined.spring_oauth_social_login.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class SocialUserAuthorityService implements AuthorityService<SocialUser> {

    private final UserRepository<SocialUser> userRepository;

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    @Override
    public Authority save(String authority) {
        Authority savedAuthority = authorityMapper.toEntity(authority);

        authorityRepository.save(savedAuthority);
        log.debug("Saving authority {} success", authority);
        return savedAuthority;
    }

    @Override
    public Authority getByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority).orElseThrow(() -> new ResourceNotFoundException("Authority with name of " + authority + " doesn't exists!"));
    }

    @Override
    public boolean isAlreadyExists(String name) {
        return authorityRepository.findAll().stream()
                .map(Authority::getAuthority)
                .anyMatch(name::equalsIgnoreCase);
    }

    @Override
    public void add(SocialUser currentUser, Authority authority) {
        currentUser.getAuthorities().add(authority);
        authority.getSocialUsers().add(currentUser);

        userRepository.save(currentUser);
        authorityRepository.save(authority);
        log.debug("Adding authority {} to current user with id of {} success", authority.getAuthority(), currentUser.getId());
    }

    @Override
    public void remove(SocialUser currentUser, Authority authority) {
        currentUser.getAuthorities().remove(authority);
        authority.getSocialUsers().remove(currentUser);

        userRepository.save(currentUser);
        authorityRepository.save(authority);
        log.debug("Removing authority {} to current user with id of {} success", authority.getAuthority(), currentUser.getId());
    }

    @Override
    public void addAll(SocialUser currentUser, List<Authority> authorities) {
        currentUser.getAuthorities().addAll(authorities);

        userRepository.save(currentUser);
        log.debug("Merging authorities ");
    }
}
