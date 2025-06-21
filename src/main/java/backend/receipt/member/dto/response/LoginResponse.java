package backend.receipt.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String name;

    public LoginResponse(String token) {

        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
