/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.controller;

import hotel.web.model.hotel.Hotel;
import hotel.web.model.hotel.HotelService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 *
 * @author Mitch
 */
@WebServlet(name = "hotelController", urlPatterns = {"/hotel"})
public class HotelController extends HttpServlet {

    private static final String RESULT_PAGE = "home.jsp";

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HotelService hs = new HotelService();

        if (request.getParameter("hotelId") != null) {
            Hotel hotel = hs.getHotelById(request.getParameter("hotelId"));
            request.setAttribute("hotelId", hotel.getHotelId());
            request.setAttribute("name", hotel.getHotelName());
            request.setAttribute("address", hotel.getStreetAddress());
            request.setAttribute("city", hotel.getCity());
            request.setAttribute("notes", hotel.getNotes());
            request.setAttribute("postalCode", hotel.getPostalCode());
            request.setAttribute("state", hotel.getState());
            request.setAttribute("tableVisible", "block");
        } else {
            request.setAttribute("tableVisible", "none");
        }
        if (request.getParameter("submit") != null  && request.getParameter("hotelId") != null) {
            if (request.getParameter("submit").equals("Save")) {
                Hotel hotel = hs.getHotelById(request.getParameter("hotelId"));
                hotel.setHotelName(request.getParameter("name"));
                hotel.setStreetAddress(request.getParameter("address"));
                hotel.setCity(request.getParameter("city"));
                hotel.setState(request.getParameter("state"));
                hotel.setPostalCode(request.getParameter("postalCode"));
                hotel.setNotes(request.getParameter("notes"));
                hs.saveHotel(hotel);
            }
            else if(request.getParameter("submit").equals("Delete")){
                hs.deleteHotel(hs.getHotelById(request.getParameter("hotelId"))); 
            }
            request.setAttribute("tableVisible", "none");
        }
        if(request.getParameter("submit") != null && request.getParameter("submit").equals("Add Hotel")){
                Hotel hotel = new Hotel();
                hotel.setHotelName(request.getParameter("name"));
                hotel.setStreetAddress(request.getParameter("address"));
                hotel.setCity(request.getParameter("city"));
                hotel.setState(request.getParameter("state"));
                hotel.setPostalCode(request.getParameter("postalCode"));
                hotel.setNotes(request.getParameter("notes"));
                hs.saveHotel(hotel);
            }
        List hotels = hs.getAllHotels();
        request.setAttribute("hotels", hotels);
        RequestDispatcher view
                = request.getRequestDispatcher(RESULT_PAGE);
        view.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(HotelController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(HotelController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
