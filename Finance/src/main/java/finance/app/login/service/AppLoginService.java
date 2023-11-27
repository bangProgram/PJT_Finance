package finance.app.login.service;

import org.springframework.stereotype.Service;

@Service
public class AppLoginService {
	
	private String curUserId = "";

    public String getCurUserId() {
        return curUserId;
    }

    public void setCurUserId(String userId) {
        this.curUserId = userId;
    }
    
    public Boolean isLogin() {
    	return !curUserId.equals("");
    }
    
    public void setLogoutUser() {
        this.curUserId = "";
    }
}
