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
import rms.common.DateFormatter;
import rms.common.Role;
import rms.servlet.business.FullRisk;
import rms.servlet.business.FullRiskListBean;
import rms.servlet.business.RiskListBean;
import rms.servlet.business.RiskStateItem;

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
					String[] raIds = new String[riskList.size()];
					int[][] states = new int[riskList.size()][4];
					for(int i=0;i<riskList.size();i++){
						for(int j=0;j<4;j++){
							states[i][j] = -1;
						}
					}
					for(int i=0;i<riskList.size();i++){
						String id = riskList.get(i).getRiskId();
						String raId = riskList.get(i).getRaId();
						for(int j=0;j<count;j++){
							if(id.equals(riskIds[j])&&raId.equals(raIds[j])){
								exist = true;
								switch(riskList.get(i).getState()){
								case UnRemoved:
									states[j][0] = i;
									break;
								case Removed:
									states[j][1] = i;
									break;
								case Problem:
									states[j][2] = i;
									break;
								case ProblemSolved:
									states[j][3] = i;
									break;
								default:
									break;
								}
							}
						}
						if(!exist){
							riskIds[count] = id;
							raIds[count] = raId;
							switch(riskList.get(i).getState()){
							case UnRemoved:
								states[count][0] = i;
								break;
							case Removed:
								states[count][1] = i;
								break;
							case Problem:
								states[count][2] = i;
								break;
							case ProblemSolved:
								states[count][3] = i;
								break;
							default:
								break;
							}
							count++;
						}
						exist = false;
					}
					
					List<FullRisk> fullRiskList = new ArrayList<FullRisk>();
					for(int i=0;i<count;i++){
							FullRisk fullRisk = new FullRisk();
							StateItem item = riskList.get(states[i][0]);
							fullRisk.setRiskId(riskIds[i]);
							fullRisk.setProjectId(item.getProjectId());
							fullRisk.setRaId(item.getRaId());
							fullRisk.setDescription(item.getDescription());
							fullRisk.setPossibility(item.getPossibility());
							fullRisk.setEffectlevel(item.getEffectlevel());
							fullRisk.setThreshold(item.getThreshold());
							List<RiskStateItem> stateList = new ArrayList<RiskStateItem>();
							for(int j=0;j<4;j++){
								if(states[i][j]>-1){
									RiskStateItem stateItem = new RiskStateItem();
									stateItem.setComitter(riskList.get(states[i][j]).getComitter());
									stateItem.setTime(DateFormatter.calendarToString("yyyy-MM-dd HH:mm:ss",riskList.get(states[i][j]).getTime()));
									stateItem.setState(riskList.get(states[i][j]).getState());
									stateItem.setDescription(riskList.get(states[i][j]).getDescription());
									stateList.add(stateItem);
									fullRisk.setState(riskList.get(states[i][j]).getState());
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
