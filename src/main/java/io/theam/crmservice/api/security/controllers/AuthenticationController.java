package io.theam.crmservice.api.security.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.theam.crmservice.api.response.Response;
import io.theam.crmservice.api.security.dto.JwtAuthenticationDto;
import io.theam.crmservice.api.security.dto.TokenDto;
import io.theam.crmservice.api.security.utils.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Generates and return a new Token
	 * 
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 */
	@PostMapping
	public ResponseEntity<Response<TokenDto>> generatesTokenJwt(
			@Valid @RequestBody JwtAuthenticationDto authenticationDto, BindingResult result)
			throws AuthenticationException {
		
		Response<TokenDto> response = new Response<TokenDto>();

		if (result.hasErrors()) {
			log.error("Error validating the token: {}", result.getAllErrors());
			
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			
			return ResponseEntity.badRequest().body(response);
		}

		log.info("Generating Token for the email {}.", authenticationDto.getEmail());
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationDto.getEmail(), authenticationDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
		String token = jwtTokenUtil.getToken(userDetails);
		response.setData(new TokenDto(token));

		return ResponseEntity.ok(response);
	}

	/**
	 * Generates a token with a new Expiration Date
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
	@PostMapping(value = "/refresh")
	public ResponseEntity<Response<TokenDto>> generatesRefreshTokenJwt(HttpServletRequest request) {
		
		log.info("Generating refresh token JWT.");
		
		Response<TokenDto> response = new Response<TokenDto>();
		
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
		
		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
        }
		
		if (!token.isPresent()) {
			response.getErrors().add("Token was not given.");
		} else if (!jwtTokenUtil.validToken(token.get())) {
			response.getErrors().add("Token is invalid or expired.");
		}
		
		if (!response.getErrors().isEmpty()) { 
			return ResponseEntity.badRequest().body(response);
		}
		
		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		response.setData(new TokenDto(refreshedToken));
		
		return ResponseEntity.ok(response);
	}

}
