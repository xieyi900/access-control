package com.management.access.control.service;

import com.management.access.control.exception.AccessException;
import com.management.access.control.model.AccessInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UseAccessServiceTest {

    @InjectMocks
    private UseAccessService useAccessService;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private Resource resource;

    private AccessInfo accessInfo = new AccessInfo();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        // 加载测试资源文件
        Path testFilePath = Paths.get("src/test/resources/access.json");
        assertTrue(Files.exists(testFilePath));

        // 创建一个真正的Resource对象，代表测试文件
        Resource testResource = new DefaultResourceLoader().getResource(testFilePath.toUri().toString());

        // 当调用resourceLoader.getResource时，返回我们的测试Resource对象
        when(resourceLoader.getResource(anyString())).thenReturn(testResource);
        useAccessService.init();
    }

    @Test
    public void addUser() throws Exception {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setUserId(Long.getLong("123456"));
        List<String> endpoints = Arrays.asList("resource A","resource B","resource C");
        accessInfo.setEndpoints(endpoints);
        useAccessService.addUserAccess("123456", accessInfo);
    }

    @Test
    public void checkUser() throws Exception {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setUserId(Long.getLong("123456"));
        List<String> endpoints = Arrays.asList("resource A","resource B","resource C");
        accessInfo.setEndpoints(endpoints);
        useAccessService.checkUserAccess("123456","resource A");
    }

    @Test(expected = AccessException.class)
    public void noAccess() throws Exception {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setUserId(Long.getLong("123456"));
        List<String> endpoints = Arrays.asList("resource A","resource B","resource C");
        accessInfo.setEndpoints(endpoints);
        useAccessService.checkUserAccess("123456","resource D");
    }
}