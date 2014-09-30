/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package com.googlecode.goclipse.tooling;

import static java.util.Collections.unmodifiableList;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import melnorme.utilbox.collections.ArrayList2;
import melnorme.utilbox.misc.MiscUtil;
import melnorme.utilbox.misc.StringUtil;

/**
 * Helper class to work with a GOPATH entry list.
 */
public class GoPath {
	
	protected final List<String> goPathElements;
	
	public GoPath(String goPathString) {
		this.goPathElements = unmodifiableList(new ArrayList2<>(goPathString.split(File.pathSeparator)));
	}
	
	public List<String> getGoPathElements() {
		return goPathElements;
	}
	
	public String asString() {
		return getGoPathWorkspaceString(); 
	}
	
	public String getGoPathWorkspaceString() {
		return StringUtil.collToString(goPathElements, File.pathSeparator);
	}
	
	public Path getGoWorkspacePathEntry(Path path) {
		if(path == null) {
			return null;
		}
		for (String pathElement : goPathElements) {
			if(path.startsWith(pathElement)) {
				return MiscUtil.createPathOrNull(pathElement);
			}
		}
		return null;
	}
	
	public Path getGoPackageFromSourceModule(Path goModulePath) {
		Path goWorkspaceEntry = getGoWorkspacePathEntry(goModulePath);
		if(goWorkspaceEntry == null) {
			return null;
		}
		
		Path sourceRoot = goWorkspaceEntry.resolve("src");
		return GoRoot.getGoPackageFromSourceModule(goModulePath, sourceRoot);
	}
	
}