package rms.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rms.common.EffectLevel;
import rms.common.IdProducer;
import rms.common.Possibility;
import rms.common.RiskState;
import rms.model.RiskItem;
import rms.service.RiskService;
import rms.service.RiskServiceImpl;

/**
 * Servlet implementation class RiskAddServlet
 */
@WebServlet("/RiskAddServlet")
public class RiskAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RiskService riskService = new RiskServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskAddServlet() {
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
				RiskItem risk = new RiskItem();
				risk.setRiskId(IdProducer.produceRiskId());
				risk.setPossibility(Possibility.values()[Integer.parseInt(request.getParameter("possibility"))]);
				risk.setEffect(EffectLevel.values()[Integer.parseInt(request.getParameter("effectLevel"))]);
				risk.setTrigger(request.getParameter("trigger"));
				risk.setDescription(request.getParameter("description"));
				risk.setCommiterId(userId);
				risk.setState(RiskState.UnRemoved);
				boolean res = riskService.addRisk(risk);
				request.setAttribute("addRes", res);
				request.setAttribute("restItem", risk);
				ServletContext context = getServletContext();
				context.getRequestDispatcher("/jsp/qualityManager/addRisk.jsp").forward(request, response);
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