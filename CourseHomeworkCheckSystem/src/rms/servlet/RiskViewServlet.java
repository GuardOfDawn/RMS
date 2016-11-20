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
import newproject.StateItemService;
import newproject.model.RiskItem;
import newproject.model.StateItem;
import newproject.model.User;
import newproject.service.impl.RiskServiceImpl;
import newproject.service.impl.StateItemServiceImpl;
import rms.common.Role;
import rms.servlet.business.FullRisk;
import rms.servlet.business.FullRiskListBean;
import rms.servlet.business.RiskListBean;
import rms.servlet.business.RiskStateItem;
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
				session.invalidate();
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
					List<StateItem> riskList = stateItemService.retrieveRisks(userId);
					int count = 0;
					boolean exist = false;
					String[] riskIds = new String[riskList.size()];
					int[][] states = new int[riskList.size()][4];
					for(int i=0;i<riskList.size();i++){
						for(int j=0;j<4;j++){
							states[i][j] = -1;
						}
					}
					for(int i=0;i<riskList.size();i++){
						String id = riskList.get(i).getRiskId();
						for(int j=0;j<count;j++){
							if(id.equals(riskIds[j])){
								exist = true;
								switch(riskList.get(i).getState()){
								case UnRemoved:
									states[count-1][0] = j;
									break;
								case Removed:
									states[count-1][1] = j;
									break;
								case Problem:
									states[count-1][2] = j;
									break;
								case ProblemSolved:
									states[count-1][3] = j;
									break;
								default:
									break;
								}
							}
						}
						if(!exist){
							riskIds[count] = id;
							count++;
						}
					}
					
					List<FullRisk> fullRiskList = new ArrayList<FullRisk>();
					for(int i=0;i<count;i++){
						FullRisk fullRisk = new FullRisk();
						StateItem item = riskList.get(states[i][0]);
						fullRisk.setRiskId(riskIds[i]);
						fullRisk.setProjectId(item.getProjectId());
						fullRisk.setDescription(item.getDescription());
						fullRisk.setPossibility(item.getPossibility());
						fullRisk.setEffectlevel(item.getEffectlevel());
						fullRisk.setThreshold(item.getThreshold());
						List<RiskStateItem> stateList = new ArrayList<RiskStateItem>();
						for(int j=0;j<4;j++){
							if(states[i][j]>-1){
								RiskStateItem stateItem = new RiskStateItem();
								stateItem.setComitter(riskList.get(states[i][0]).getComitter());
								stateItem.setState(riskList.get(states[i][0]).getState());
								stateItem.setDescription(riskList.get(states[i][0]).getDescription());
								stateList.add(stateItem);
								fullRisk.setState(riskList.get(states[i][0]).getState());
							}
						}
						fullRisk.setRiskStateList(stateList);
						fullRiskList.add(fullRisk);
					}
					
					FullRiskListBean fullRiskBean = new FullRiskListBean();
					fullRiskBean.setFullRiskList(fullRiskList);
					session.setAttribute("fullRiskList", fullRiskBean);
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
