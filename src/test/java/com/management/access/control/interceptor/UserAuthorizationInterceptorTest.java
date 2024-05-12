package com.management.access.control.interceptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


@RunWith(MockitoJUnitRunner.class)
public class UserAuthorizationInterceptorTest {

    @InjectMocks
    private UserAuthorizationInterceptor userAuthorizationInterceptor;

    @Mock
    private MockHttpServletRequest request;

    @Mock
    private MockHttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void preHandle() throws Exception {
        request.addHeader("Authorization","MTIzNDU2OjEyMzQ1NjphZG1pbg==");
        userAuthorizationInterceptor.preHandle(request,response, new Object());
    }

    @Test
    public void headerNotPassed() throws Exception {
        userAuthorizationInterceptor.preHandle(request,response, new Object());

    }

    @Test
    public void invalidAuthorization() throws Exception {
        request.addHeader("Authorization","MTIzNDU2OjEyMzQ1Ng==");
        userAuthorizationInterceptor.preHandle(request,response, new Object());

    }

}