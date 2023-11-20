package finance.common.Service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
	
	private String kisDevToken = "";

    public String getKisDevToken() {
        return kisDevToken;
    }

    public void setKisDevToken(String newToken) {
        this.kisDevToken = newToken;
    }
    
    public Boolean isKisDevToken() {
    	return !kisDevToken.equals("");
    }
}