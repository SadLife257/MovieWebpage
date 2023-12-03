package tdtu.edu.vn.Backend.Utilities.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import tdtu.edu.vn.Backend.Securities.CustomUserDetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtToken {

	@Value("${jwt.token.secret}")
	private String secret;

	@Value("${jwt.token.time}")
	private Long tokenTime;
	
	@Value("${jwt.token.cookie}")
	private String cookieName;

	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, cookieName);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

	public ResponseCookie generateJwtCookie(CustomUserDetails userPrincipal) {
		String jwt = generateTokenFromUsername(userPrincipal.getUsername());
		ResponseCookie cookie = ResponseCookie.from(cookieName, jwt).path("/auth").maxAge(24 * 60 * 60).httpOnly(true)
				.build();
		return cookie;
	}

	public String generateJwtToken(CustomUserDetails userPrincipal) {
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + tokenTime)).signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	}

	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(cookieName, null).path("/api/auth").build();
		return cookie;
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(key()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + tokenTime)).signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	}

}
