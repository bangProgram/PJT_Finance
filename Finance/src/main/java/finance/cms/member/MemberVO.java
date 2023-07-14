package finance.cms.member;

import java.util.Date;

public class MemberVO {

	private String userId;		/* 사용자id */
    private String password;	/* 비밀번호 */
    private String userName;	/* 사용자이름 */
    private String userNick; 	/* 사용자별명 */
    private String certified; 	/* 인증코드(플래그) */
    private String userGroup; 	/* 사용자 그룹 */
    private String groupAuthor; /* 그룹 권한 */
    private String useAt;		/* 상태 플래그 */
    private String phoneNumber;	/* 전화번호 */
    private String email;		/* e-mail */
    private String sessionId;	/* 사용자 session id */
    private String pwChangeDate;	/* 비밀번호 변경일 */
    private String createDate;	/* 생성일 */
    private String changeDate;	/* 변경일 */
    private String errorTime;		/* 접속에러 시간 */
    private int errorCount;		/* 접속에러 횟수 */
    private String fileId;		/* 사용자 파일id */
    
 // Getter 메서드
    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public String getCertified() {
        return certified;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public String getGroupAuthor() {
        return groupAuthor;
    }

    public String getUseAt() {
        return useAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPwChangeDate() {
        return pwChangeDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public String getErrorTime() {
        return errorTime;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public String getFileId() {
        return fileId;
    }

    // Setter 메서드
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public void setCertified(String certified) {
        this.certified = certified;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public void setGroupAuthor(String groupAuthor) {
        this.groupAuthor = groupAuthor;
    }

    public void setUseAt(String useAt) {
        this.useAt = useAt;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setPwChangeDate(String pwChangeDate) {
        this.pwChangeDate = pwChangeDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public void setErrorTime(String errorTime) {
        this.errorTime = errorTime;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    
}
