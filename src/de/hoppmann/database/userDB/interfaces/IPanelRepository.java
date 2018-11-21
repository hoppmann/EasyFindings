/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB.interfaces;

import de.hoppmann.database.userDB.PanelDB.PanelInfo;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public interface IPanelRepository {

    public boolean isRepoValid();
    public boolean makeRepoValid();
    public List<String> getPanelNames();
    public void getPanelGenes(PanelInfo panelInfo);
    public void newPanel(PanelInfo panelInfo);
    public void savePanel(PanelInfo panelInfo);
    public void removePanel(PanelInfo panelInfo);

    
    
    
    
}
