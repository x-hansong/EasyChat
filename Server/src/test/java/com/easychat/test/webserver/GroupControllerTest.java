package com.easychat.test.webserver;

import com.easychat.Server;
import com.easychat.repository.GroupRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by king on 2015/12/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Server.class)
@WebIntegrationTest
public class GroupControllerTest {

    private static final String URL = "http://localhost:8080/v1/groups";

    private RestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testCreateGroupApi(){

    }
}
