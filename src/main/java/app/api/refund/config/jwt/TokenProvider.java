package app.api.refund.config.jwt;


import app.api.refund.config.security.CustomUserDetails;
import app.api.refund.domain.DomainValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String access_token_secret;
    private final String refresh_token_secret;

    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private Key access_key;
    private Key refresh_key;


    private final UserDetailsService userDetailsService;


    public TokenProvider(
            @Value("${jwt.access-token-secret}") String access_token_secret,
            @Value("${jwt.refresh-token-secret}") String refresh_token_secret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds,
            UserDetailsService userDetailsService) {
        this.access_token_secret = access_token_secret;
        this.refresh_token_secret = refresh_token_secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(access_token_secret);
        this.access_key = Keys.hmacShaKeyFor(keyBytes);
        keyBytes = Decoders.BASE64.decode(refresh_token_secret);
        this.refresh_key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userId, String authorities) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(userId)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(access_key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken() {

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .signWith(refresh_key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(access_key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(
                principal.getUsername());

        return new UsernamePasswordAuthenticationToken(customUserDetails, token, authorities);
    }

    public boolean validateAccessToken(String accessToken) {
        Jwts.parserBuilder().setSigningKey(access_key).build().parseClaimsJws(accessToken);
        return true;
    }

    public boolean validateRefreshToken(String refreshToken) {
        Jwts.parserBuilder().setSigningKey(refresh_key).build().parseClaimsJws(refreshToken);
        return true;

    }

    public String reissueAccessToken(String accessToken, String refreshToken) {

        try {
            Authentication authentication = getAuthentication(accessToken);

            if (validateRefreshToken(refreshToken)) {
                String userId = authentication.getName();
                String authorities = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));
                return createAccessToken(userId, authorities);
            } else {
                return null;
            }

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.error("JWT : " + refreshToken + " 잘못된 JWT 서명입니다.");
            throw new DomainValidationException(JwtDomainValidationMessage.WRONG_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            logger.error("JWT : " + refreshToken + " 만료된 JWT 토큰입니다.");
            throw new DomainValidationException(
                    JwtDomainValidationMessage.EXPIRED_JWT_REFRESH_TOKEN);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT : " + refreshToken + " 지원되지 않는 JWT 토큰입니다.");
            throw new DomainValidationException(JwtDomainValidationMessage.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            logger.error("JWT : " + refreshToken + " JWT 토큰이 잘못되었습니다.");
            throw new DomainValidationException(JwtDomainValidationMessage.WRONG_TYPE_JWT_TOKEN);
        }

    }
}
