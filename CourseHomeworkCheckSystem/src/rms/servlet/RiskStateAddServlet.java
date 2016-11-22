package rms.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import newproject.StateItemService;
import newproject.model.StateItem;
import newproject.service.impl.StateItemServiceImpl;
import rms.common.DateFormatter;
import rms.common.IdProducer;
import rms.common.RiskState;
import rms.servlet.business.FullRiskListBean;
import rms.servlet.business.RiskStateItem;

/**
 * Servlet implementation class RiskStateAddServlet
 */
@WebServlet("/RiskStateAddServlet")
public class RiskStateAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StateItemService stateItemService = new StateItemServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiskStateAddServlet() {
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
				FullRiskListBean fullRiskBean = (FullRiskListBean)session.getAttribute("fullRiskList");
				
				String addedStateString = request.getParameter("addedStateString");
				String[] parts = addedStateString.split("\",\"");
				StateItem item = new StateItem();
				RiskStateItem stateItem = new RiskStateItem();
				for(int i=0;i<fullRiskBean.getSize();i++){
					if(fullRiskBean.getFullRisk(i).getRiskId().equals(parts[0])&&fullRiskBean.getFullRisk(i).getRaId().equals(parts[3])){
						item.setStateId(IdProducer.produceStateId());
						item.setRiskId(parts[0]);
						item.setComitter(userId);
						item.setFollower(userId);
						item.setProjectId(fullRiskBean.getFullRisk(i).getProjectId());
						item.setRaId(parts[3]);
						item.setDescription(parts[2]);
						item.setState(RiskState.valueOf(parts[1]));
						item.setPossibility(fullRiskBean.getFullRisk(i).getPossibility());
						item.setEffectlevel(fullRiskBean.getFullRisk(i).getEffectlevel());
						item.setThreshold(fullRiskBean.getFullRisk(i).getThreshold());
						item.setTime(Calendar.getInstance());
						
						stateItem.setComitter(userId);
						stateItem.setState(RiskState.valueOf(parts[1]));
						stateItem.setDescription(parts[2]);
						stateItem.setTime(DateFormatter.calendarToString("yyyy-MM-dd HH:mm:ss",item.getTime()));
						break;
					}
				}
				
				boolean res = stateItemService.addStateItem(item);
				request.setAttribute("riskIdCheck", parts[0]);
				request.setAttribute("riskRaId", parts[3]);
				request.setAttribute("res", res);
				if(res){
					for(int i=0;i<fullRiskBean.getSize();i++){
						if(fullRiskBean.getFullRisk(i).getRiskId().equals(parts[0])&&fullRiskBean.getFullRisk(i).getRaId().equals(parts[3])){
							fullRiskBean.getFullRisk(i).getRiskStateList().add(stateItem);
							fullRiskBean.getFullRisk(i).setState(stateItem.getState());
							break;
						}
					}
					session.setAttribute("fullRiskList", fullRiskBean);
				}
				
				ServletContext context = getServletContext();
				context.getRequestDispatcher("/jsp/softwareEngineer/riskDetailForSE.jsp").forward(request, response);
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
