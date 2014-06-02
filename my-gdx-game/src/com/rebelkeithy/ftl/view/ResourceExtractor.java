package com.rebelkeithy.ftl.view;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import com.kurosaru.ftl.archive.datLib;

public class ResourceExtractor 
{
	public static void extract()
	{
		String ftlPath = (String)JOptionPane.showInputDialog(
		                    null,
		                    "What is your FTL install directory:\n",
		                    "Extracting Resources",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    "C:/Program Files (x86)/Steam/SteamApps/common/FTL Faster Than Light");

		
		datLib dat = new datLib(ftlPath + "/resources/resource.dat");
		int[] ids = dat.List();
		
		ProgressMonitor progressMonitor = new ProgressMonitor(null, "Extracting Resources", "", 0, ids.length);
		
		for(int i = 0; i < ids.length; i++)
		{
			String path = dat.Filename(ids[i]);
			File file = new File("resources/" + path);
			if(!file.exists())
			{
				dat.Extract(ids[i], "resources/" + path);
			}
			progressMonitor.setProgress(i);
		}
		
		progressMonitor.close();
	}
}
