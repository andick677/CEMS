package org.example.cems.resporse;

public class UserResporse {
    private final String jwt;

    public UserResporse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt(){return jwt; }
}
