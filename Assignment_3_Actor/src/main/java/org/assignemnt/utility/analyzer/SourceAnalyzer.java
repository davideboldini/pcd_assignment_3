package org.assignemnt.utility.analyzer;

import org.assignemnt.GUI.Gui;
import org.assignemnt.model.Directory;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

public interface SourceAnalyzer {

    CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> getReport(final Directory dir,
                                                                                                     final int MAXL, final int NI);
    void analyzeSources(final Directory dir, final int MAXL, final int NI, final Gui gui);
    void stopActors();
}
