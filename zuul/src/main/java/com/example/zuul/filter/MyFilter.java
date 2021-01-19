package com.example.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.example.commons.constants.MyConstants;
import com.example.commons.utils.RedisUtils;
import com.example.zuul.config.FreeUrl;
import com.example.zuul.dto.CommonResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
@Component
public class MyFilter extends ZuulFilter {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private FreeUrl freeUrl;


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        //如果返回true，则执行run方法
        log.info("==============网关过滤器start===================");
        RequestContext ctx = RequestContext.getCurrentContext();
        //不校验token的路径
        for (String url : freeUrl.getList()) {
            if (ctx.getRequest().getRequestURI().equals(url)) {
                return true;
            }
        }
        return checkToken(ctx);
    }

    @Override
    public Object run() throws ZuulException {
        log.info("=======通过过滤============");
        return null;
    }


    /**
     * 校验token是否有效
     * 有效：刷新token有效时间
     * 无效：报错
     */
    private boolean checkToken(RequestContext ctx) {
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        CommonResponse response = new CommonResponse();
        if (StringUtils.isEmpty(token)) {
            response.setMessage("token为空");
            response.setCode(MyConstants.TOKEN_IS_NULL);
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            sendErrorResult(ctx, response);
        } else {
            String key = MyConstants.USER_TOKEN_KEY + token;
            if (redisUtils.exitst(key)) {
                redisUtils.expireKey(key, 20L, TimeUnit.SECONDS);
                return true;
            } else {
                response.setCode(MyConstants.TOKEN_EXIT);
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(200);
                response.setMessage("token已过期");
                sendErrorResult(ctx, response);
            }
        }
        return false;

    }

    /**
     * 统用错误返回函数
     */
    private void sendErrorResult(RequestContext ctx, CommonResponse response) {
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
