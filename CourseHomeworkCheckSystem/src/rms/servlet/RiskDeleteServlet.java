package rms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import rms.servlet.business.RiskListBean;

/**
 * Servlet implementation class DeleteRiskServlet
 */
 @WebServlet("/RiskDeleteServlet")
public class RiskDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RiskService riskService = new RiskServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskDeleteServlet() {
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
				session = null;
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			else{
				ServletContext context = getServletContext();
				RiskListBean riskList = (RiskListBean) session.getAttribute("riskList");
				String riskId = request.getParameter("riskToDelete");
				List<RiskItem> newList = new ArrayList<RiskItem>();
				for(int i=0;i<riskList.getSize();i++){
					if(riskList.getRisk(i).getRiskId().equals(riskId)){
						riskService.deleteRisk(riskId);
					}
					else{
						newList.add(riskList.getRisk(i));
					}
				}
				riskList.setRiskList(newList);
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
