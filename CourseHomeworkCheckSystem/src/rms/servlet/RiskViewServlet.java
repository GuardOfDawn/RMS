package rms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rms.common.Role;
import rms.model.RiskItem;
import rms.service.RiskService;
import rms.service.RiskServiceImpl;
import rms.servlet.business.RiskListBean;

/**
 * Servlet implementation class RiskViewServlet
 */
@WebServlet("/RiskViewServlet")
public class RiskViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RiskService riskService = new RiskServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskViewServlet() {
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
			String roleString = String.valueOf(session.getAttribute("userType"));
			String userId = String.valueOf(session.getAttribute("userid"));
			if(roleString.equals("null")||userId.equals("null")){
				session = null;
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			else{
				ServletContext context = getServletContext();
				Role role = Role.valueOf(roleString);
				
				List<RiskItem> ristList = riskService.retrieveRisks(userId);
				RiskListBean riskListBean = new RiskListBean();
				riskListBean.setRiskList(ristList);
				session.setAttribute("riskList", riskListBean);
				
				if(Role.QualityManager==role){
					session.setAttribute("userTypeInChinese", "质量管理员");
					context.getRequestDispatcher("/jsp/qualityManager/riskViewForQm.jsp").forward(request, response);
				}
				else if(Role.SoftwareEngineer==role){
					session.setAttribute("userTypeInChinese", "软件工程师");
					
				}
				else{
					
				}
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
