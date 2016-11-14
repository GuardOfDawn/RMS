package rms.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rms.model.RiskItem;
import rms.service.RiskService;
import rms.service.RiskServiceImpl;
import rms.servlet.business.RiskListBean;

/**
 * Servlet implementation class FollowRiskServlet
 */
@WebServlet("/FollowRiskServlet")
public class FollowRiskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RiskService riskService = new RiskServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowRiskServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null){
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		}
		else{
			String userId = String.valueOf(session.getAttribute("userid"));
			if(userId.equals("null")){
				session = null;
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			else{
				ServletContext context = getServletContext();
				RiskListBean riskList = (RiskListBean) session.getAttribute("riskList");
				String riskId = request.getParameter("riskIdFollow");
				for(int i=0;i<riskList.getSize();i++){
					if(riskList.getRisk(i).getRiskId().equals(riskId)){
						RiskItem item = riskList.getRisk(i);
						String followerId = item.getFollowerId();
						if(followerId.equals("null")){
							followerId = userId;
						}
						else{
							followerId = followerId.concat(",").concat(userId);
						}
						item.setFollowerId(followerId);
						riskList.setFollowCondition(i, 1);
						riskService.modifyRisk(item);
						break;
					}
				}
				session.setAttribute("riskList", riskList);
				context.getRequestDispatcher("/jsp/qualityManager/riskViewForQm.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
