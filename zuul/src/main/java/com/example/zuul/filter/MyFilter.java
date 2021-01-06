package com.example.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.example.zuul.dto.CommonResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
@Component
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //如果返回true，则执行run方法
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("==============网关过滤器start===================");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (StringUtils.isEmpty(request.getHeader("token"))) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            sendErrorResult(ctx);
        }
        return null;
    }

    private void sendErrorResult(RequestContext ctx) {
        CommonResponse response = new CommonResponse();
        response.setMessage("token为空");
        response.setCode(-1);
        try {
            HttpServletResponse httpServletResponse = ctx.getResponse();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
