package org.assignemnt.utility.analyzer;

import org.assignemnt.GUI.Gui;
import org.assignemnt.model.Directory;

public interface SourceAnalyzer {


    void analyzeSources(final Directory dir, final int MAXL, final int NI, final Gui gui);
    void stopActors();
}
