package com.management.access.control.controller;

import com.management.access.control.service.UseAccessService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UseAccessControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UseAccessService useAccessService;

    private MockMvc mockMvc;

    private String postData;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        postData = "{\n" +
                "    \"userId\": 123456,\n" +
                "    \"endpoints\": [\n" +
                "        \"resource A\",\n" +
                "        \"resource B\",\n" +
                "        \"resource C\"\n" +
                "    ]\n" +
                "}";
    }

    @Test
    public void userCreateSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header("Authorization", "MTIzNDU2OjEyMzQ1NjphZG1pbg==")
                .contentType(MediaType.APPLICATION_JSON) // 设置请求内容类型
                .content(postData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    public void missingUserIdOrRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header("Authorization", "MTIzNDU2OjEyMzQ1Ng==")
                        .contentType(MediaType.APPLICATION_JSON) // 设置请求内容类型
                        .content(postData))
                .andExpect(status().isLengthRequired());
    }

    @Test
    public void notAdminUrlAccessWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header("Authorization", "MTIzNDU2OjEyMzQ1Njp1c2Vy")
                        .contentType(MediaType.APPLICATION_JSON) // 设置请求内容类型
                        .content(postData))
                .andExpect(status().isForbidden());
    }

    @Test
    public void checkAccess() {
    }
}