package com.rongmei.config.filter;

import com.rongmei.security.jwt.JwtEmployees;
import com.rongmei.security.jwt.JwtUser;
import com.rongmei.security.jwt.JwtUserDetailsService;
import com.rongmei.security.jwt.JwtService;
import com.rongmei.util.HttpUtil;
import com.rongmei.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${domain.port}")
    private String domainPort;

    @Autowired
    private JwtService jwtService;

    @Autowired
    public JwtAuthenticationTokenFilter() {
    }
    //////////////注释测试111
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead) && !authHeader.contains("null")) {
            String url = "https://" + domainPort + "/role/checkToken?token=" + authHeader;
            //String url = "http://" + domainPort + "/role/checkToken?token=" + authHeader;
            JwtEmployees jwtEmployees = HttpUtil.getUserDetails(url);
            //判断是否是员工用户登录
            if (jwtEmployees != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        jwtEmployees, null, jwtEmployees.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            //如果不是员工登录就给普通用户鉴权
            else {
                String url1 = "https://" + domainPort + "/role/checkUserToken?token=" + authHeader;
                //String url1 = "http://" + domainPort + "/role/checkToken?token=" + authHeader;

                JwtUser jwtUser = HttpUtil.getJwtUser(url1);
                if (jwtUser != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            jwtUser, null, jwtUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            ThreadLocalUtil.setToken(authHeader);
        }
        chain.doFilter(request, response);
    }
}
