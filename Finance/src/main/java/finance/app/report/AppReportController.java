package finance.app.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import finance.app.portfolio.service.AppPortfolioService;
import finance.common.Controller.DefaultController;
import finance.common.Service.TokenService;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/report") 
public class AppReportController extends DefaultController{

	@Autowired
	private AppPortfolioService appPortfolioService;
	
	@Autowired
	private TokenService tokenService;
		
	@PostMapping("/getStockPrice")
	public ResponseEntity<Map<String, Object>> getPortAmount(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{ 
	
		RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> responseData = new HashMap<String, Object>();
        
        String stockCode = commandMap.get("corpCode").toString();

        String apiUrl = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice";

        // 파라미터 설정
        String parameters = "?fid_cond_mrkt_div_code=J&fid_input_iscd="+stockCode+"&fid_input_date_1=20230101&fid_input_date_2=20231101&fid_period_div_code=D&fid_org_adj_prc=0";

        // URL과 파라미터 조합
        String uri = apiUrl + parameters;

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type","application/json");
        headers.add("authorization", "Bearer "+tokenService.getKisDevToken());
		headers.add("appkey", kisDeveloperAppKey);
		headers.add("appsecret", kisDeveloperAppSecretKey);
		headers.add("tr_id", "FHKST03010100");
        // 다른 헤더 필요시 추가
		
		System.out.println(
				tokenService.getKisDevToken()+"\n"+kisDeveloperAppKey+"\n"+kisDeveloperAppSecretKey
				);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            entity,
            String.class
        );

        if (response.getStatusCodeValue() == 200) {
            String responseBody = response.getBody();
            // 성공적으로 응답을 받았을 때의 처리
            System.out.println("정보 가져오기 성공: " + responseBody);
            
            Map<String, Object> data = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            
            // 값 추출
            Object value = data.get("output2");
            // 토큰 처리 등 성공적인 작업 수행
            if(value != null) {
            	
            	System.out.println("정보 가져오기 성공: " + value);
            	
            	responseData.put("value", value);
    			return ResponseEntity.ok(responseData);
            }else {
                System.out.println("정보 가져오기 실패");
                return ResponseEntity.badRequest().body(responseData);
            }
        } else {
            // 요청이 실패했을 때의 처리
            System.out.println("정보 가져오기 실패");
            return ResponseEntity.badRequest().body(responseData);
        }
	}
}
