package edu.nju.courseHomeworkCheck.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class OnlySoftwaresFilter
 */
@WebFilter("/StudentOperation")
public class OnlySoftwaresFilter implements Filter {

    /**
     * Default constructor. 
     */
    public OnlySoftwaresFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 只有软件学院的学生才能登陆
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest httpReq = (HttpServletRequest)request;
			HttpServletResponse httpRes = (HttpServletResponse)response;
			String user = httpReq.getParameter("userid");
			if(null != user ) {
				if(user.length()==9){
					String part = user.substring(2, 7);
					if(!part.equals("12500")){
						httpRes.sendError(HttpServletResponse.SC_FORBIDDEN, "Only software students allowed");
						return;
					}
				}
			} 
		}//在servlet处理请求之前截获
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
