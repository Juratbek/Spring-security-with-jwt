package com.example.demo.security;

import com.example.demo.entity.ApplicationUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.secret}")
    private String secretWord;

    @Value("${jwt.token.validity}")
    private Long validityTime;

    public JwtProvider(@Qualifier("applicationUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    protected void init() {
        secretWord = passwordEncoder.encode(secretWord);
    }

    public String createToken(ApplicationUser user) {
        Claims claims = Jwts.claims();
        claims.put("authorities", user.getAuthorities());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityTime))
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
    }

    public boolean validateToken(String token) {
        Claims body = getClaims(token);
        return body.getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        Claims body = getClaims(token);
        List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
        Set<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.get("authority"))).collect(Collectors.toSet());
        return new UsernamePasswordAuthenticationToken(body.getSubject(), "", grantedAuthorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody();
    }

}
