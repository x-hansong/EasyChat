package com.easychat.test.webserver;

import com.easychat.Server;
import com.easychat.model.entity.User;
import com.easychat.model.error.ErrorType;
import com.easychat.repository.UserRepository;
import com.easychat.utils.CommonUtils;
import com.easychat.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yonah on 15-12-8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Server.class)
@WebIntegrationTest
public class UserControllerTest {

    private static final String URL = "http://localhost:8080/v1/users";
    private static final String URL_testAuthenticateApi = "http://localhost:8080/v1/users/authorization";

    private RestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUserApi() {
        String name = "testuser";
        String password = "123456";

        //设置 Request Body 和 HttpHeaders
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("password", password);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //创建http请求
        HttpEntity<String> httpEntity = new HttpEntity<>(JsonUtils.encode(requestBody),requestHeaders);

        //调用API
//        Map<String, String> apiResponse = restTemplate.postForObject(URL, httpEntity, Map.class);
        ResponseEntity<String> response = restTemplate.postForEntity(URL, httpEntity, String.class);

        Map<String, String> responseBody = JsonUtils.decode(response.getBody(), Map.class);

        assertNotNull(response);

        //测试状态码
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        //测试返回值
        assertEquals(name, responseBody.get("name"));
        assertEquals(password, responseBody.get("password"));


        //测试数据库是否正常
        User user = userRepository.findByName(name);
        assertNotNull(user);


        //测试重复用户名异常
        response = restTemplate.postForEntity(URL, httpEntity, String.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        responseBody = JsonUtils.decode(response.getBody(), Map.class);
        assertEquals(ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS, responseBody.get("error"));

        //删除测试用户
        userRepository.delete(user);
    }

    @Test
    public void testAuthenticateApi(){
        String name = "testuser";
        String password = "123456";

        //创建一个用户
        User user = new User();
        user.setName(name);
        user.setPassword(CommonUtils.md5(password));
        userRepository.save(user);

        //设置 Request Body 和 HttpHeaders
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("password",CommonUtils.md5(password));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //创建http请求
        HttpEntity<String> httpEntity = new HttpEntity<>(JsonUtils.encode(requestBody),requestHeaders);

        //调用API
        ResponseEntity<String> response = restTemplate.postForEntity(URL_testAuthenticateApi, httpEntity, String.class);

        Map<String, String> responseBody = JsonUtils.decode(response.getBody(), Map.class);

        assertNotNull(response);
        //测试状态码
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        //删除测试用户
        userRepository.delete(user);
    }


}
