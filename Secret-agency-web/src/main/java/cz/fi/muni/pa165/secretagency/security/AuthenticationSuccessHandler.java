package cz.fi.muni.pa165.secretagency.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.fi.muni.pa165.secretagency.dto.AgentDTO;
import cz.fi.muni.pa165.secretagency.facade.AgentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handler for successful authorization. It return HTTP code 200 and logged
 *   user in JSON format.
 *
 * @author Jan Pavlu (487548)
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String AUTHENTICATED_USER_SESSION_KEY = "authenticatedUser";

    @Autowired
    private AgentFacade agentFacade;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        AgentDTO loggedAgent = agentFacade.getAgentByCodeName(authentication.getName());
        request.getSession().setAttribute(AUTHENTICATED_USER_SESSION_KEY, loggedAgent);

        // Object -> JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String loggedAgentJson = objectMapper.writeValueAsString(loggedAgent);

        // response body should contain signed user in JSON format3

        response.getOutputStream().print(loggedAgentJson);

        // same as in parent class
        clearAuthenticationAttributes(request);
    }
}
