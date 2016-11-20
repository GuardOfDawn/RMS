package rms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import newproject.ProjectService;
import newproject.model.RA;
import newproject.model.StateItem;
import newproject.model.User;
import newproject.service.impl.ProjectServiceImpl;
import rms.common.IdProducer;
import rms.common.RiskState;
import rms.servlet.business.SimpleUser;
import rms.servlet.business.StateItemBean;
import rms.servlet.business.UserListBean;

/**
 * Servlet implementation class RAEnsureAddRiskServlet
 */
@WebServlet("/RAEnsureAddRiskServlet")
public class RAEnsureAddRiskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProjectService projectService = new ProjectServiceImpl();;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RAEnsureAddRiskServlet() {
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
				RA newRA = (RA)session.getAttribute("newRA");
				String riskListString = request.getParameter("riskListString");
				String[] riskListArray = riskListString.split("\":\"");
				List<StateItem> riskList = new ArrayList<StateItem>();
				for(int i=0;i<riskListArray.length;i++){
					String[] parts = riskListArray[i].split("\",\"");
					StateItem item = new StateItem();
					item.setStateId(IdProducer.produceStateId());
					item.setRiskId(parts[0]);
					item.setPossibility(parts[1]);
					item.setEffectlevel(parts[2]);
					item.setState(RiskState.valueOf(parts[3]));
					item.setThreshold(parts[4]);
					item.setDescription(parts[5]);
					item.setProjectId(newRA.getProjectId());
					item.setComitter(userId);
					item.setTime(Calendar.getInstance());
					riskList.add(item);
				}
				newRA.setRiskList(riskList);
				session.setAttribute("newRA", newRA);
				StateItemBean riskListBean = new StateItemBean();
				riskListBean.setStateItemList(riskList);
				request.setAttribute("riskList", riskListBean);
				
				List<User> userList = projectService.retrieveUsersInProject(newRA.getProjectId());
				List<SimpleUser> developerList = new ArrayList<SimpleUser>();
				for(int j=0;j<userList.size();j++){
					SimpleUser u = new SimpleUser();
					u.setUserID(userList.get(j).getUserID());
					u.setRole(userList.get(j).getRole());
					u.setUserName(userList.get(j).getUserName());
					developerList.add(u);
				}
				UserListBean developerListBean = new UserListBean();
				developerListBean.setUserList(developerList);
				request.setAttribute("developerList", developerListBean);
				request.setAttribute("developerCount", developerListBean.getSize());
		
				ServletContext context = getServletContext();
				context.getRequestDispatcher("/jsp/qualityManager/raAllocateRisk.jsp").forward(request, response);
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
