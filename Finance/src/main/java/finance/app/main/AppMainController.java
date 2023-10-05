package finance.app.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi") // 이곳으로 들어오는 API주소를 mapping, /api주소로 받겠다(localhost:8080/api)
public class AppMainController {

    @GetMapping("/example")
    public ResponseEntity<String> getExample() {
        // HTTP 상태 코드와 응답 본문 데이터 설정
        String responseBody = "Hello, World!";
        return ResponseEntity.ok(responseBody); // 200 OK 응답 반환
    }
    
    //  @RequestMapping(method = RequestMethod.POST, path = "/postMethod") // 아래랑 동일
	@PostMapping("/postMethod")
	public String postMethod(@RequestBody Map<String, Object> requestData) {
		System.out.println("requestData : "+requestData.toString());
		return "OK";
	}
    
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> saveUser(@RequestBody Map<String, Object> requestData) {
        try {
            // 클라이언트에서 전달받은 JSON 데이터 처리
        	String test1 = requestData.get("text1").toString();
        	String test2 = requestData.get("text2").toString();
        	String test3 = requestData.get("text3").toString();
        	
        	System.out.println(test1 + " / " + test2 + " / " + test3 );

            // 처리된 데이터를 응답으로 보내기
            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("message", "Data received successfully");

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
