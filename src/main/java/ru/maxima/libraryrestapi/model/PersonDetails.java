package ru.maxima.libraryrestapi.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


//Реализации не используются непосредственно Spring Security в целях безопасности. Они просто хранят пользовательскую информацию,
// которая позже инкапсулируется в Authentication объекты. Это позволяет хранить информацию пользователя,
// не связанную с безопасностью (например, адреса электронной почты, номера телефонов и т. д.), в удобном месте.
@RequiredArgsConstructor
public class PersonDetails implements UserDetails {


    private final Person person;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }


    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getEmail();
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
        return  person.getRemovedAt() == null;
    }

}
