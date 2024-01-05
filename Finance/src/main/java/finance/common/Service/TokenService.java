package finance.common.Service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
	
	private String kDevToken = "";

    public String getKDevToken() {
        return kDevToken;
    }

    public void setKDevToken(String newToken) {
        this.kDevToken = newToken;
    }
    
    public Boolean isKDevToken() {
    	return !kDevToken.equals("");
    }
}