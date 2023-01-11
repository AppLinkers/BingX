package AppLinkers.BingX.admin.service;

import AppLinkers.BingX.admin.domain.User;
import AppLinkers.BingX.admin.domain.UserDetail;
import AppLinkers.BingX.admin.dto.UpdateUserLoginPwReq;
import AppLinkers.BingX.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 아이디 -> 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLoginId(username).orElseThrow(() -> new UsernameNotFoundException("invalidID"));
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return UserDetail.builder()
                .username(user.getLoginId())
                .password(user.getLoginPw())
                .authorityList(roles)
                .build();
    }

    /**
     * 비밀번호 변경 서비스
     */
    @Transactional
    public void changeUserLoginPw(UpdateUserLoginPwReq request, String userLoginId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        user.setLoginPw(passwordEncoder.encode(request.getPassword()));
    }
}
