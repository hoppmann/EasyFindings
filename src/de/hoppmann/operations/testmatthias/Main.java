/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations.testmatthias;

import de.hoppmann.operations.testmatthias.dbaccess.DbGeneInfoRepository;
import de.hoppmann.operations.testmatthias.view.GeneInfoViewController;

/**
 *
 * @author hoppmann
 */
public class Main {


    public void test() {
	// zum Lesen: Dependency Injection bzw. Inversion of Control (IOC)
	
	
	GeneInfoViewController g = new GeneInfoViewController();
	g.setGeneInfoRepository(new DbGeneInfoRepository());
	//g.setGeneInfoRepository(new FileSystemGeneInfoRepository());
	g.startUp(); 
	
    }





}
