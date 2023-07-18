package finance.cms.member;

import java.util.Date;

public class MemberVO {

	private String user_id;		/* 사용자id */
    private String password;	/* 비밀번호 */
    private String user_name;	/* 사용자이름 */
    private String user_nick; 	/* 사용자별명 */
    private String certified; 	/* 인증코드(플래그) */
    private String user_group; 	/* 사용자 그룹 코드 */
    private String group_author; /* 그룹 권한 코드 */
    private String use_at;		/* 상태 플래그 */
    private String phone_number;	/* 전화번호 */
    private String email;		/* e-mail */
    private String session_id;	/* 사용자 session id */
    private String pw_change_date;	/* 비밀번호 변경일 */
    private String create_date;	/* 생성일 */
    private String change_date;	/* 변경일 */
    private String error_time;		/* 접속에러 시간 */
    private int error_count;		/* 접속에러 횟수 */
    private String file_id;		/* 사용자 파일id */
    
    private String user_group_nm; 	/* 사용자 그룹명 */
    private String group_author_nm; /* 그룹 권한명 */
    
 // Getter 메서드
    public String getUser_id() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public String getCertified() {
        return certified;
    }

    public String getUser_group() {
        return user_group;
    }

    public String getUser_group_nm() {
        return user_group_nm;
    }

    public String getGroup_author() {
        return group_author;
    }

    public String getGroup_author_nm() {
        return group_author_nm;
    }
    
    public String getUse_at() {
        return use_at;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getPw_change_date() {
        return pw_change_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getChange_date() {
        return change_date;
    }

    public String getError_time() {
        return error_time;
    }

    public int getError_count() {
        return error_count;
    }

    public String getFile_id() {
        return file_id;
    }

    // Setter 메서드
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public void setCertified(String certified) {
        this.certified = certified;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }
    
    public void setUser_group_nm(String user_group_nm) {
        this.user_group_nm = user_group_nm;
    }

    public void setGroup_author(String group_author) {
        this.group_author = group_author;
    }
    
    public void setGroup_author_nm(String group_author_nm) {
        this.group_author_nm = group_author_nm;
    }

    public void setUse_at(String use_at) {
        this.use_at = use_at;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setPw_change_date(String pw_change_date) {
        this.pw_change_date = pw_change_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setChange_date(String change_date) {
        this.change_date = change_date;
    }

    public void setError_time(String error_time) {
        this.error_time = error_time;
    }

    public void setError_count(int error_count) {
        this.error_count = error_count;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
    
}
