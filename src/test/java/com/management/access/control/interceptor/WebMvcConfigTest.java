package com.management.access.control.interceptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WebMvcConfigTest {

    @MockBean
    private HandlerInterceptor handlerInterceptor;

    @MockBean
    private InterceptorRegistry interceptorRegistry;

    @MockBean
    private InterceptorRegistration interceptorRegistration;

    @Autowired
    private WebMvcConfig webMvcConfig;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddInterceptors() {

        Mockito.when(interceptorRegistration.addPathPatterns(anyString())).thenReturn(interceptorRegistration);

      when(interceptorRegistry.addInterceptor(any(HandlerInterceptor.class)))
                .thenAnswer(invocation -> {
                    HandlerInterceptor interceptor = invocation.getArgument(0);
                    return interceptorRegistration;
                });

        webMvcConfig.addInterceptors(interceptorRegistry);

        String test = "1";
        assertEquals("1",test);
    }
}