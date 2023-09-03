package ru.maxima.libraryrestapi.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authorities {

    @SuppressWarnings("InstantiationOfUtilityClass")
    @Getter
    private static final Authorities instance = new Authorities();
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
}
