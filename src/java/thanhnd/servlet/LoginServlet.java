/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import thanhnd.entity.Category;
import thanhnd.entity.User;
import thanhnd.service.CategoryService;
import thanhnd.service.UserService;
import thanhnd.utils.HibernateUtil;

/**
 *
 * @author thanh
 */
public class LoginServlet extends HttpServlet {

    private final String invaidPage = "invalid.html";
    private final String indexPage = "index.jsp";
    private final String dashboardPage = "dashboard.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        HttpSession session = request.getSession();
        UserService userService = new UserService();
        CategoryService categoryService = new CategoryService(hibernateSession);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        String url = invaidPage;
        try {
            User user = userService.findByUsernameAndPassword(username, password);
            if (user != null) {
                if (user.getRole().equals("user")) {
                    url = indexPage;
                    
                    session.setAttribute("user", user);
                    List<Category> categories = categoryService.getAllCategories();
                    request.setAttribute("categories", categories);
                    
                }
                else if (user.getRole().equals("admin")) {
                    session.setAttribute("user", user);
                    url = dashboardPage;
                }

            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
//            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
