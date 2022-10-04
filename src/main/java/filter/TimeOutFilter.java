package filter;

import utils.RequestUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})//如果是通过注解来注册filter程序的话,其执行顺序跟首字母排序有关,如果一样则比较下一个字母
public class TimeOutFilter extends HttpFilter {//会话超时过滤器
    //设置需要放行的请求,即登录和注册                                                //注册中包含岗位和部门的查询需要放行
    private List<String> url = Arrays.asList("/user/login","/user/register","/post/query","/dept/query");
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestUrl = RequestUtil.getRequestUrl(request);
        if (url.contains(requestUrl)){//如果在放行的集合有包含的url就不处理,直接放行
            chain.doFilter(request,response);
        }else {//处理不直接放行的请求
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");//从会话中取出用户的信息
            if (user!=null){//如果用户信息存在就放行
                chain.doFilter(request,response);
            }else {//否则会话超时发错误码,因为前后端分离不用做其他处理,由前端拦截器来决定重定向
                //状态码401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//此状态码为未认证的错误类型状态码
            }
        }
    }
}
