package rms.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import newproject.StatService;
import newproject.model.RiskCondition;
import newproject.service.impl.StatServiceImpl;
import rms.common.DateFormatter;
import rms.servlet.business.RiskConditionBean;

/**
 * Servlet implementation class RiskMostAddedStatServlet
 */
@WebServlet("/RiskMostAddedStatServlet")
public class RiskMostAddedStatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StatService statService = new StatServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskMostAddedStatServlet() {
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
				String startDateString = request.getParameter("startDate");
				String endDateString = request.getParameter("endDate");
				Calendar startDate = DateFormatter.stringToCalendar("yyyy-MM-dd", startDateString); 
				Calendar endDate = DateFormatter.stringToCalendar("yyyy-MM-dd", endDateString);
				
				List<RiskCondition> riskList = statService.retrieveFrequentlyAddedRisks(startDate, endDate);
				
				RiskConditionBean frequentlyAddedRisks = new RiskConditionBean();
				frequentlyAddedRisks.setRiskList(riskList);
				
				request.setAttribute("startDate", startDateString);
				request.setAttribute("endDate", endDateString);
				request.setAttribute("mostAddedRisks", frequentlyAddedRisks);
				
				ServletContext context = getServletContext();
				context.getRequestDispatcher("/jsp/qualityManager/riskStat.jsp").forward(request, response);
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
