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

import newproject.ProjectService;
import newproject.model.Project;
import newproject.service.impl.ProjectServiceImpl;
import rms.servlet.business.ProjectListBean;

/**
 * Servlet implementation class RAAddProjectServlet
 */
@WebServlet("/RASelectProjectServlet")
public class RASelectProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProjectService projectService = new ProjectServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RASelectProjectServlet() {
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
				List<Project> projectList = projectService.retrieveProjects();
				ProjectListBean projectBean = new ProjectListBean();
				projectBean.setProjectList(projectList);
				request.setAttribute("projectList", projectBean);
				
				ServletContext context = getServletContext();
				context.getRequestDispatcher("/jsp/qualityManager/raAddProject.jsp").forward(request, response);
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
