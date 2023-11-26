package finance.app.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.app.portfolio.service.AppPortfolioService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/util") 
public class AppUtilController extends DefaultController {
	
	@Autowired
	private AppUtilService appUtilService;
	
	@GetMapping("/yearList")
    public ResponseEntity<Map<String, Object>> getYearList() throws Exception{
        
        try {
        	 Map<String, Object> param = new HashMap<String, Object>();

        	 List<Map<String, Object>> yearList = appUtilService.getYearList(param);

            Map<String, Object> responseData = new HashMap<String, Object>();
            System.out.println("yearList : "+yearList.toString());
            responseData.put("yearList", yearList);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }

}
