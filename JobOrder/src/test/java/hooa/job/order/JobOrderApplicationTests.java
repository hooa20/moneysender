package hooa.job.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import hooa.job.order.controller.ControllerRS;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JobOrderApplicationTests {
	
	 
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	public void api1() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-USER-ID", "kakao435872");
		headers.add("X-ROOM-ID", "kakaoroom2352");
		
        ResponseEntity<ControllerRS> result = testRestTemplate.exchange("/sprinklemoney/60000/3", HttpMethod.GET, new HttpEntity<>(headers), ControllerRS.class);
        //assertThat(result).isEqualTo("hello saelobi");
       
        String token = "";
        if("Y".equals(result.getBody().getSuccessyn())){
        	
        	token = result.getBody().getServicers().getToken();
        	
        	
        	headers = new HttpHeaders();
    		headers.add("X-USER-ID", "kakao99999");
    		headers.add("X-ROOM-ID", "kakaoroom2352");
        	
        	result = testRestTemplate.exchange("/takemoney/" + token, HttpMethod.GET, new HttpEntity<>(headers), ControllerRS.class);
        	result = testRestTemplate.exchange("/takemoney/" + token, HttpMethod.GET, new HttpEntity<>(headers), ControllerRS.class);
        	
        }
        
        
        headers = new HttpHeaders();
		headers.add("X-USER-ID", "kakao435872");
		headers.add("X-ROOM-ID", "kakaoroom2352");
		
		result = testRestTemplate.exchange("/searchmyorderlist/" + token, HttpMethod.GET, new HttpEntity<>(headers), ControllerRS.class);
    }

}
