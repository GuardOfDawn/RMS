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

import newproject.RAService;
import newproject.model.RA;
import newproject.model.StateItem;
import newproject.service.impl.RAServiceImpl;

/**
 * Servlet implementation class RAAddServlet
 */
@WebServlet("/RAAddServlet")
public class RAAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RAService raService = new RAServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RAAddServlet() {
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
				RA newRA = (RA)session.getAttribute("newRA");
				String followerString = request.getParameter("followerString");
				String[] followerArray = followerString.split("\":\"");
				List<StateItem> riskList = newRA.getRiskList();
				for(int i=0;i<followerArray.length;i++){
					String[] parts = followerArray[i].split("\",\"");
					for(int j=0;j<riskList.size();j++){
						if(riskList.get(j).getRiskId().equals(parts[0])){
							riskList.get(j).setFollower(parts[1]);
							break;
						}
					}
				}
				newRA.setRiskList(riskList);
				newRA.setDescription(request.getParameter("raDescription"));
				
				boolean res = raService.addRA(newRA);
				request.setAttribute("addRes", res);
				session.setAttribute("newRA", null);
				ServletContext context = getServletContext();
				context.getRequestDispatcher("/jsp/qualityManager/raAddRes.jsp").forward(request, response);
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
