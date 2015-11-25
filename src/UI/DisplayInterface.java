/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author Nilanjan Daw
 */
public interface DisplayInterface {
    
    public void showScreen();
    public boolean isRead();
    public void getData();
    public boolean showSearchResult(String[][] searchData);
    public int getChoice();
    public void showNextTab(int tabNo);
}
