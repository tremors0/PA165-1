package cz.fi.muni.pa165.secretagency.dto;

/**
 * Data for authentication.
 *
 * @author Jan Pavlu (487548)
 */
public class AgentAuthenticateDTO {
    private Long userId;
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
