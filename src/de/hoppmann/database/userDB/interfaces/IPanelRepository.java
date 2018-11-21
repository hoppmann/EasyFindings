/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB.interfaces;

import de.hoppmann.database.userDB.PanelDB.IPanelInfo;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public interface IPanelRepository {

    public boolean isRepoValid();
    public boolean makeRepoValid();
    public List<String> getPanelNames();
    public void getPanelGenes(IPanelInfo panelInfo);
    public void newPanel(IPanelInfo panelInfo);
    public void savePanel(IPanelInfo panelInfo);
    public void removePanel(IPanelInfo panelInfo);

    
    
    
    
}
