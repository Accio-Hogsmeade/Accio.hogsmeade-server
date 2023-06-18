package accio.hogsmeade.store.store.store.service;

import accio.hogsmeade.store.store.store.Store;
import accio.hogsmeade.store.store.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomStoreDetailsService implements UserDetailsService {

    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return storeRepository.findByLoginId(loginId)
                .map(this::createStoreDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    //해당하는 Store의 데이터가 존재하면 UserDetails 객체롤 만들어서 반환
    private UserDetails createStoreDetails(Store store) {
        return User.builder()
                .username(store.getStoreName())
                .password(passwordEncoder.encode(store.getPassword()))
                .roles(store.getRoles().toArray(new String[0]))
                .build();
    }
}
