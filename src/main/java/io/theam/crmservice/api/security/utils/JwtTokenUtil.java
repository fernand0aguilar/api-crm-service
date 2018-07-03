package io.theam.crmservice.api.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * Get the username (email) within token JWT.
	 * 
	 * @param token
	 * @return String
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * Return the expiration date of a token JWT.
	 * 
	 * @param token
	 * @return Date
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	/**
	 * Creates a new token (refresh).
	 * 
	 * @param token
	 * @return String
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generatesToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * Verifies and return if a token JWT is valid.
	 * 
	 * @param token
	 * @return boolean
	 */
	public boolean validToken(String token) {
		return !expiredToken(token);
	}

	/**
	 * Return a new token JWT based on user details
	 * 
	 * @param userDetails
	 * @return String
	 */
	public String getToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());

		return generatesToken(claims);
	}

	/**
	 * Makes the parse of the token to extract informations within it.
	 * 
	 * @param token
	 * @return Claims
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * Return an expiration date based on the present one.
	 * 
	 * @return Date
	 */
	private Date generatesExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * Verifies if token is expired
	 * 
	 * @param token
	 * @return boolean
	 */
	private boolean expiredToken(String token) {
		Date expirationDate = this.getExpirationDateFromToken(token);
		if (expirationDate == null) {
			return false;
		}
		return expirationDate.before(new Date());
	}

	/**
	 * Generates a new token with data (claims) given.
	 * 
	 * @param claims
	 * @return String
	 */
	private String generatesToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generatesExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String getUserFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
        
        if (token != null && token.startsWith("Bearer ")) {
        	token = token.substring(7);
        }
        String username = this.getUsernameFromToken(token);
        return username;
	}

}

