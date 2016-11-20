package rms.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import newproject.RiskService;
import newproject.model.RiskItem;
import newproject.service.impl.RiskServiceImpl;

/**
 * Servlet implementation class RiskModifyServlet
 */
@WebServlet("/RiskModifyServlet")
public class RiskModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RiskService riskService = new RiskServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskModifyServlet() {
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
			if(userId.equals("null")){
				session.invalidate();
				session = null;
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			else{
				RiskItem risk = new RiskItem();
				risk.setRiskId(request.getParameter("riskId"));
				risk.setTitle(request.getParameter("title"));
				risk.setDescription(request.getParameter("description"));
				boolean res = riskService.modifyRisk(risk);
				request.setAttribute("modifyRes", res);
				String url = "/jsp/qualityManager/modifyRisk.jsp";
				if(res==false){
					url = url+"?riskIdModify="+risk.getRiskId();
				}
				else{
					request.setAttribute("riskItem", risk);
				}
				ServletContext context = getServletContext();
				context.getRequestDispatcher(url).forward(request, response);
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
