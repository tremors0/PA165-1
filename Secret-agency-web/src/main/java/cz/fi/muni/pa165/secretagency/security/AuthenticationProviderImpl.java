package cz.fi.muni.pa165.secretagency.security;

import cz.fi.muni.pa165.secretagency.dto.AgentAuthenticateDTO;
import cz.fi.muni.pa165.secretagency.dto.AgentDTO;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.facade.AgentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private AgentFacade agentFacade;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String codeName = authentication.getName();
        String password = (String) authentication.getCredentials();

        // TODO - create custom exception
        AgentDTO agentDTO;
        try {
            agentDTO = agentFacade.getAgentByCodeName(codeName);
        } catch (NullPointerException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        AgentAuthenticateDTO authenticateDTO = new AgentAuthenticateDTO();
        authenticateDTO.setUserId(agentDTO.getId());
        authenticateDTO.setPassword(password);


        if (!agentFacade.authenticate(authenticateDTO)) {
            throw new BadCredentialsException("Invalid password");
        }

        String userRole = agentDTO.getRank() == AgentRankEnum.AGENT_IN_CHARGE ? "ADMIN" : "USER";

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRole);
        return new UsernamePasswordAuthenticationToken(codeName, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
