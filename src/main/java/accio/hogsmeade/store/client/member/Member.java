package accio.hogsmeade.store.client.member;

import accio.hogsmeade.store.common.Active;
import accio.hogsmeade.store.common.Address;
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
import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Member extends TimeBaseEntity implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true, nullable = false, updatable = false, length = 20)
    private String loginId;
    @Column(nullable = false, length = 20)
    private String loginPw;
    @Column(nullable = false, updatable = false, length = 50)
    private String name;
    @Column(unique = true, nullable = false, length = 13)
    private String tel;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Embedded
    private Address address;
    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private Identity identity;
    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private SchoolGroup schoolGroup;
    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private Active active;

    @ElementCollection(fetch = EAGER)
    private List<String> roles = new ArrayList<>();

    //== 생성자 ==//
    public Member() {
    }

    @Builder
    public Member(Long id, String loginId, String loginPw, String name, String tel, String email, Address address, Identity identity, SchoolGroup schoolGroup, Active active, List<String> roles) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.identity = identity;
        this.schoolGroup = schoolGroup;
        this.active = active;
        this.roles = roles;
    }

    //== 연관관계 편의 메서드 ==//
    public static Member createMember(String loginId, String loginPw, String name, String tel, String email, String zipcode, String mainAddress, String detailAddress, Identity identity, SchoolGroup schoolGroup) {
        Address address = Address.builder()
                .zipcode(zipcode)
                .mainAddress(mainAddress)
                .detailAddress(detailAddress)
                .build();
        return Member.builder()
                .loginId(loginId)
                .loginPw(loginPw)
                .name(name)
                .tel(tel)
                .email(email)
                .address(address)
                .identity(identity)
                .schoolGroup(schoolGroup)
                .active(ACTIVE)
                .roles(Collections.singletonList("MEMBER"))
                .build();
    }

    //== 비즈니스 로직 ==//
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

    public void editAddress(Address address) {
        this.address = address;
    }

    //== 시큐리티 설정 ==//
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
