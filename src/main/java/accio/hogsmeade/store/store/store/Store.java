package accio.hogsmeade.store.store.store;

import accio.hogsmeade.store.common.Active;
import accio.hogsmeade.store.common.TimeBaseEntity;
import accio.hogsmeade.store.common.exception.AuthorityException;
import accio.hogsmeade.store.common.exception.EditException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static accio.hogsmeade.store.common.Active.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
public class Store extends TimeBaseEntity implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;
    @Column(unique = true, nullable = false, updatable = false, length = 20)
    private String loginId;
    @Column(nullable = false, length = 20)
    private String loginPw;
    @Column(nullable = false, updatable = false, length = 50)
    private String shopkeeper;
    @Column(unique = true, nullable = false, length = 13)
    private String tel;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false, updatable = false, length = 50)
    private String storeName;
    @Column(nullable = false) @Lob
    private String storeInfo;
    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private Active active;

    @ElementCollection(fetch = EAGER)
    private List<String> roles = new ArrayList<>();

    public Store() {
    }

    @Builder
    public Store(Long id, String loginId, String loginPw, String shopkeeper, String tel, String email, String storeName, String storeInfo, Active active, List<String> roles) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.shopkeeper = shopkeeper;
        this.tel = tel;
        this.email = email;
        this.storeName = storeName;
        this.storeInfo = storeInfo;
        this.active = active;
        this.roles = roles;
    }

    public static Store createStore(String loginId, String loginPw, String shopkeeper, String tel, String email, String storeName, String storeInfo) {
        return Store.builder()
                .loginId(loginId)
                .loginPw(loginPw)
                .shopkeeper(shopkeeper)
                .tel(tel)
                .email(email)
                .storeName(storeName)
                .storeInfo(storeInfo)
                .active(ACTIVE)
                .roles(Collections.singletonList("STORE"))
                .build();
    }

    /*
    비지니스 로직
     */
    public void withdrawal(String loginPw) {
        if (!this.loginPw.equals(loginPw)) {
            throw new AuthorityException();
        }
        this.active = DEACTIVE;
    }

    public void editLoginPw(String nowLoginPw, String newLoginPw) {
        if (!this.loginPw.equals(nowLoginPw)) {
            throw new EditException();
        }
        this.loginPw = newLoginPw;
    }

    public void editTel(String newTel) {
        this.tel = newTel;
    }

    public void editEmail(String newEmail) {
        this.email = newEmail;
    }

    /*
    시큐리티
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return loginPw;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
