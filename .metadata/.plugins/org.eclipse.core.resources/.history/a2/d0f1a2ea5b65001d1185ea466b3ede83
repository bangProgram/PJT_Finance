package finance.cms.dart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.dart.service.DartService;
import finance.cms.interest.service.InterestService;
import finance.cms.portfolio.service.PortfolioService;
import finance.common.Controller.CommonController;

@Controller
public class DartController {

	@Autowired
	private DartService dartService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Resource(name="CommonController")
	private CommonController commonController;
	
}
