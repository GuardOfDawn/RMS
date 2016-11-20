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

import newproject.RiskService;
import newproject.StateItemService;
import newproject.model.RiskItem;
import newproject.model.StateItem;
import newproject.model.User;
import newproject.service.impl.RiskServiceImpl;
import newproject.service.impl.StateItemServiceImpl;
import rms.common.Role;
import rms.servlet.business.RiskListBean;
import rms.servlet.business.StateItemBean;

/**
 * Servlet implementation class RiskViewServlet
 */
@WebServlet("/RiskViewServlet")
public class RiskViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RiskService riskService = new RiskServiceImpl();
	private StateItemService stateItemService = new StateItemServiceImpl();
       
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
			String userId = String.valueOf(session.getAttribute("userId"));
			User user = (User)session.getAttribute("user");
			if(user==null){
				session = null;
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			else{
				ServletContext context = getServletContext();
				Role role = user.getRole();
				
				if(Role.QualityManager==role){
					List<RiskItem> ristList = riskService.retrieveAllRisks();
					RiskListBean riskListBean = new RiskListBean();
					riskListBean.setRiskList(ristList);
					session.setAttribute("riskList", riskListBean);
					session.setAttribute("userTypeInChinese", "质量管理员");
					context.getRequestDispatcher("/jsp/qualityManager/riskViewForQm.jsp").forward(request, response);
				}
				else if(Role.SoftwareEngineer==role){
					List<StateItem> ristList = stateItemService.retrieveRisks(userId);
					StateItemBean riskListBean = new StateItemBean();
					riskListBean.setStateItemList(ristList);
					session.setAttribute("riskList", riskListBean);
					session.setAttribute("userTypeInChinese", "软件工程师");
					context.getRequestDispatcher("/jsp/softwareEngineer/riskViewForSE.jsp").forward(request, response);
				}
				else if(Role.Manager==role){
					session.setAttribute("userTypeInChinese", "项目经理");
					context.getRequestDispatcher("/jsp/softwareEngineer/riskViewForSE.jsp").forward(request, response);
					//TODO
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
